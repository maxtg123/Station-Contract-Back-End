package com.contract.tram.tram.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.contract.authentication.component.KiemTraQuyenModuleTram;
import com.contract.common.util.HttpUtil;
import com.contract.danhmuc.huyen.service.DmHuyenService;
import com.contract.danhmuc.loaicotangten.entity.DmLoaiCotAngtenEntity;
import com.contract.danhmuc.loaicotangten.model.DmLoaiCotAngtenModel;
import com.contract.danhmuc.loaicotangten.service.DmLoaiCotAngtenService;
import com.contract.danhmuc.loaicsht.entity.DmLoaiCshtEntity;
import com.contract.danhmuc.loaicsht.model.DmLoaiCshtModel;
import com.contract.danhmuc.loaicsht.service.DmLoaiCshtService;
import com.contract.danhmuc.loaithietbiran.entity.DmLoaiThietBiRanEntity;
import com.contract.danhmuc.loaithietbiran.model.DmLoaiThietBiRanModel;
import com.contract.danhmuc.loaithietbiran.service.DmLoaiThietBiRanService;
import com.contract.danhmuc.phongdai.entity.DmPhongDaiEntity;
import com.contract.danhmuc.phongdai.service.DmPhongDaiService;
import com.contract.danhmuc.tinh.service.DmTinhService;
import com.contract.danhmuc.to.service.DmToService;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.process.enums.ProcessModuleEnum;
import com.contract.process.model.ProcessModel;
import com.contract.process.service.ProcessService;
import com.contract.tram.tram.entity.TramEntity;
import com.contract.tram.tram.model.TrangThaiTram;

@Service
public class SyncTramService {

  @Value("${ptm.url}")
  private String ptmUrl;

  @Autowired
  private KiemTraQuyenModuleTram kiemTraQuyenModuleTram;

  @Autowired
  private DmPhongDaiService dmPhongDaiService;

  @Autowired
  private DmToService dmToService;

  @Autowired
  private DmTinhService dmTinhService;

  @Autowired
  private DmHuyenService dmHuyenService;

  @Autowired
  private DmLoaiCshtService dmLoaiCshtService;

  @Autowired
  private DmLoaiCotAngtenService dmLoaiCotAngtenService;

  @Autowired
  private DmLoaiThietBiRanService dmLoaiThietBiRanService;

  @Autowired
  private NguoiDungService nguoiDungService;

  @Autowired
  private TramService tramService;

  @Autowired
  private ProcessService processService;

  private Long processId = null;

  public Long syncFromPtm() {
    NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();
    // tao process
    ProcessModel processModel = new ProcessModel();
    processModel.setUserId(nguoiDungEntity.getId());
    processModel.setModule(ProcessModuleEnum.TRAM.name());
    processModel.setAction("import_from_ptm");
    processModel.setTongSo((long) 0);
    processModel = processService.create(processModel);
    final ProcessModel savedProcess = processModel;

    if (nguoiDungEntity != null) {
      CompletableFuture.runAsync(() -> backgroundSyncTramFromPTM(savedProcess,
          nguoiDungEntity.getEmail(), nguoiDungEntity.getMatKhau()));
    }

    processId = processModel.getId();
    return processModel.getId();
  }

  public void backgroundSyncTramFromPTM(ProcessModel processModel, String userName,
      String password) {
    setUserNewThread(userName, password);
    long countDone = 0L;
    long countError = 0L;
    long total = 0L;
    JSONArray errors = new JSONArray();

    try {
      Boolean isAdmin = nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin();
      List<String> phongDaiPtmList;

      if (isAdmin) {
        phongDaiPtmList = dmPhongDaiService.getAllPhongDaiPTM();
      } else {
        List<Integer> idPhongDaiList = kiemTraQuyenModuleTram.layDanhSachIdPhongDaiDuocQuyenImport();
        List<DmPhongDaiEntity> dmPhongDaiEntities = dmPhongDaiService.findAllByIdIn(idPhongDaiList);
        phongDaiPtmList = dmPhongDaiService.convertPhongDaiListBetweenHDAndPTM(dmPhongDaiEntities
            .stream().map(entity -> entity.getTenVietTat()).collect(Collectors.toList()));
      }
      String endpoint = "/api/tram/from-ptm/?phongDais="
          + URLEncoder.encode(StringUtils.collectionToDelimitedString(phongDaiPtmList, ","),
              StandardCharsets.UTF_8.toString());
      JSONArray tramPTMs = HttpUtil.callApi(ptmUrl + endpoint);
      total = tramPTMs == null ? 0L : tramPTMs.length();
      if (tramPTMs != null) {
        for (int i = 0; i < tramPTMs.length(); i++) {
          JSONObject tram = (JSONObject) tramPTMs.get(i);
          try {
            handleSaveTramPtm(tram);
            countDone++;
          } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("tram", tram.getString("idPtm"));
            error.put("error", e.getMessage());
            errors.put(error);
            countError++;
          }
        }
      }

      if (errors.length() > 0) {
        exportErrorsFile(errors);
      }
      processModel.setTongSo(total);
      processModel.setHoanThanh(countDone);
      processModel.setSoLuongLoi(countError);
      processModel.setKetThuc(true);
      processService.update(processModel);

    } catch (Exception e) {
      Map<String, Object> error = new HashMap<>();
      error.put("tram", "Get Data From PTM error");
      error.put("error", e.getMessage());
      errors.put(error);
      exportErrorsFile(errors);

      processModel.setTongSo(total);
      processModel.setHoanThanh(countDone);
      processModel.setSoLuongLoi(countError);
      processModel.setKetThuc(true);
      processService.update(processModel);
    }
  }

  public void setUserNewThread(String email, String matKhau) {
    UserDetails userDetails = new org.springframework.security.core.userdetails.User(email, matKhau, new ArrayList<>());
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
  }

  public void exportErrorsFile(JSONArray errors) {
    try {
      String fileFolder = Paths.get("process_data").toFile().getAbsolutePath();
      String fileName = processId.toString();

      Path path = Paths.get(fileFolder, fileName);
      File file = path.toFile();

      if (!Paths.get(fileFolder).toFile().exists()) {
        Paths.get(fileFolder).toFile().mkdirs();
      }
      if (!file.exists()) {
        file.createNewFile();
      }

      FileWriter fw = new FileWriter(path.toFile().getAbsolutePath(), true);
      BufferedWriter writer = new BufferedWriter(fw);
      writer.write(errors.toString());
      writer.newLine();
      writer.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  private void handleSaveTramPtm(JSONObject tram) {
    // Data from PTM
    String maDTXD = tram.optString("idPtm").trim();
    String pdTvt = tram.optString("phongDaiTenVietTat").trim();
    String toTvt = tram.optString("toTenVietTat").trim();
    String tenTram = tram.optString("tenTram").trim();
    String maTinh = tram.optString("maTinh").trim();
    String maHuyen = tram.optString("maHuyen").trim();
    String diaChiDatTram = tram.optString("diaChiDatTram").trim();
    String kinhDo = tram.optString("kinhDo").trim();
    String viDo = tram.optString("viDo").trim();
    String loaiCSHT = tram.optString("loaiCSHT").trim();
    String loaiCotAngten = tram.optString("loaiCot").trim();
    Long doLapAntenBanGiao = tram.optLong("doLapAntenBanGiao");
    String ghiChu = tram.optString("ghiChu");
    JSONArray jsonRans = tram.optJSONArray("loaiThietBiRans");
    List<String> stringRans = new ArrayList<>();
    for (int i = 0; i < jsonRans.length(); i++) {
      String ran = jsonRans.getString(i);
      stringRans.add(ran);
    }

    DmPhongDaiEntity phongDaiEntity = dmPhongDaiService.findByTenVietTat(pdTvt);
    if (phongDaiEntity == null) {
      throw new RuntimeException("Phòng đài không tìm thấy: " + pdTvt);
    }
    // Data will save
    Integer _phongDaiId = phongDaiEntity.getId();
    Integer _toId = getToId(toTvt, phongDaiEntity);
    String _ten = tenTram;
    Integer _tinhId = maTinh.equals("") ? null : dmTinhService.findByMa(maTinh).getId();
    Integer _huyenId = maHuyen.equals("") ? null : dmHuyenService.findByMa(maHuyen).getId();
    String _diaChi = diaChiDatTram;
    String _kinhDo = kinhDo;
    String _viDo = viDo;
    Integer _loaiCshtId = getLoaiCshtId(loaiCSHT);
    Integer _loaiCotAngtenId = getLoaiCotAngtenId(loaiCotAngten);
    Long _doCaoAngten = doLapAntenBanGiao;
    String _ghiChu = ghiChu;
    String _maDauTuXayDung = maDTXD;
    List<DmLoaiThietBiRanEntity> _dmLoaiThietBiRanEntityList = getDmLoaiThietBiRan(stringRans);
    // find and check
    TramEntity tramEntityWillSave = tramService.findByMaDauTuXayDung(maDTXD);
    if (tramEntityWillSave == null) {
      tramEntityWillSave = new TramEntity();
    }
    // fill new data
    tramEntityWillSave.setTrangThaiHoatDong(TrangThaiTram.HOAT_DONG);
    tramEntityWillSave.setPhongDaiId(_phongDaiId);
    tramEntityWillSave.setToId(_toId);
    tramEntityWillSave.setTen(_ten);
    tramEntityWillSave.setTinhId(_tinhId);
    tramEntityWillSave.setHuyenId(_huyenId);
    tramEntityWillSave.setDiaChi(_diaChi);
    tramEntityWillSave.setKinhDo(_kinhDo);
    tramEntityWillSave.setViDo(_viDo);
    tramEntityWillSave.setLoaiCshtId(_loaiCshtId);
    tramEntityWillSave.setLoaiCotAngtenId(_loaiCotAngtenId);
    tramEntityWillSave.setDoCaoAngten(_doCaoAngten);
    tramEntityWillSave.setGhiChu(_ghiChu);
    tramEntityWillSave.setMaDauTuXayDung(_maDauTuXayDung);
    tramEntityWillSave.setDaPhatSong(false);
    tramEntityWillSave.setDmLoaiThietBiRanEntityList(new HashSet<>(
        _dmLoaiThietBiRanEntityList != null ? _dmLoaiThietBiRanEntityList : new HashSet<>()));
    // save
    tramService.save(tramEntityWillSave);
  }

  private Integer getToId(String toTvt, DmPhongDaiEntity dmPhongDaiEntity) {
    return toTvt.equals("") ? null
        : dmToService.findAllByPhongDaiId(dmPhongDaiEntity.getId()).stream()
            .filter(_toEntity -> _toEntity.getTenVietTat().equals(toTvt)).findFirst().orElse(null)
            .getId();
  }

  private Integer getLoaiCshtId(String loaiCsht) {
    DmLoaiCshtEntity loaiCshtEntity = loaiCsht.equals("") ? null : dmLoaiCshtService.findByTen(loaiCsht);
    if (loaiCshtEntity != null) {
      return loaiCshtEntity.getId();
    }
    if (loaiCshtEntity == null && !loaiCsht.equals("")) {
      // create new one
      DmLoaiCshtModel dmLoaiCshtModel = new DmLoaiCshtModel();
      dmLoaiCshtModel.setTen(loaiCsht);
      dmLoaiCshtModel
          .setGhiChu("Được tạo ra trong quá trình đồng bộ từ chương trình Phát Triển Mạng");
      DmLoaiCshtModel savedModel = dmLoaiCshtService.save(dmLoaiCshtModel);
      return savedModel.getId();
    }
    return null;
  }

  private Integer getLoaiCotAngtenId(String loaiCotAngten) {
    DmLoaiCotAngtenEntity loaiCotAngtenEntity = loaiCotAngten.equals("") ? null
        : dmLoaiCotAngtenService.findByTen(loaiCotAngten);
    if (loaiCotAngtenEntity != null) {
      return loaiCotAngtenEntity.getId();
    }
    if (loaiCotAngtenEntity == null && !loaiCotAngten.equals("")) {
      // create new one
      DmLoaiCotAngtenModel model = new DmLoaiCotAngtenModel();
      model.setTen(loaiCotAngten);
      model.setGhiChu("Được tạo ra trong quá trình đồng bộ từ chương trình Phát Triển Mạng");
      DmLoaiCotAngtenModel savedModel = dmLoaiCotAngtenService.save(model);
      return savedModel.getId();
    }
    return null;
  }

  private List<DmLoaiThietBiRanEntity> getDmLoaiThietBiRan(List<String> rans) {
    List<DmLoaiThietBiRanEntity> dmLoaiThietBiRanEntityList = new ArrayList<>();
    rans.forEach(ran -> {
      DmLoaiThietBiRanEntity ranEntity = dmLoaiThietBiRanService.findByTen(ran);
      if (ranEntity == null && !ran.equals("")) {
        // create new one
        DmLoaiThietBiRanModel model = new DmLoaiThietBiRanModel();
        model.setTen(ran);
        model.setGhiChu("Được tạo ra trong quá trình đồng bộ từ chương trình Phát Triển Mạng");
        ranEntity = dmLoaiThietBiRanService.convertModelToEntity(dmLoaiThietBiRanService.save(model));
      }
      dmLoaiThietBiRanEntityList.add(ranEntity);
    });
    return dmLoaiThietBiRanEntityList;
  }
}
