package com.contract.log.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.common.util.DateUtil;
import com.contract.common.util.StringUtil;
import com.contract.danhmuc.huyen.entity.DmHuyenEntity;
import com.contract.danhmuc.huyen.repository.DmHuyenRepository;
import com.contract.danhmuc.loaicotangten.entity.DmLoaiCotAngtenEntity;
import com.contract.danhmuc.loaicotangten.repository.DmLoaiCotAngtenRepository;
import com.contract.danhmuc.loaicsht.entity.DmLoaiCshtEntity;
import com.contract.danhmuc.loaicsht.repository.DmLoaiCshtRepository;
import com.contract.danhmuc.loaithietbiran.entity.DmLoaiThietBiRanEntity;
import com.contract.danhmuc.loaitram.entity.DmLoaiTramEntity;
import com.contract.danhmuc.loaitram.repository.DmLoaiTramRepository;
import com.contract.danhmuc.phongdai.entity.DmPhongDaiEntity;
import com.contract.danhmuc.phongdai.repository.DmPhongDaiRepository;
import com.contract.danhmuc.tinh.entity.DmTinhEntity;
import com.contract.danhmuc.tinh.repository.DmTinhRepository;
import com.contract.danhmuc.to.entity.DmToEntity;
import com.contract.danhmuc.to.repository.DmToRepository;
import com.contract.danhmuc.tramkhuvuc.entity.DmTramKhuVucEntity;
import com.contract.danhmuc.tramkhuvuc.repository.DmTramKhuVucRepository;
import com.contract.danhmuc.xa.entity.DmXaEntity;
import com.contract.danhmuc.xa.repository.DmXaRepository;
import com.contract.log.entity.LogEntity;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.model.ChangeLogModel;
import com.contract.log.model.ChangeModel;
import com.contract.log.model.ForModel;
import com.contract.log.repository.LogRepository;
import com.contract.sys.module.model.MODULE;
import com.contract.tram.tram.entity.TramEntity;

@Service
public class TramLogService {
  @Autowired
  private DmPhongDaiRepository dmPhongDaiRepository;
  @Autowired
  private DmToRepository dmToRepository;
  @Autowired
  private DmTramKhuVucRepository dmTramKhuVucRepository;
  @Autowired
  private DmLoaiCshtRepository dmLoaiCshtRepository;
  @Autowired
  private DmLoaiTramRepository dmLoaiTramRepository;
  @Autowired
  private DmLoaiCotAngtenRepository dmLoaiCotAngtenRepository;
  @Autowired
  private DmTinhRepository dmTinhRepository;
  @Autowired
  private DmHuyenRepository dmHuyenRepository;
  @Autowired
  private DmXaRepository dmXaRepository;
  @Autowired
  private LogRepository logRepository;

  public void saveTramLog(ChangeLogModel changeLogModel, Long nguoiDungId, LogActionEnum action) {

    LogEntity logEntity = new LogEntity();
    logEntity.setModule(MODULE.TRAM.name());
    logEntity.setNguoiDungId(nguoiDungId);
    logEntity.setChangeLog(new JSONObject(changeLogModel).toString());
    logEntity.setAction(action);
    logRepository.save(logEntity);
  }

  public ChangeLogModel getTramChangeLog(TramEntity fromData, TramEntity toData, String type) {
    ChangeLogModel result = new ChangeLogModel();
    List<ChangeModel> changes = new ArrayList<>();
    if (fromData != null) {
      try {
        if ((fromData.getNgayPhatSong() != null && toData.getNgayPhatSong() == null)
            || (fromData.getNgayPhatSong() == null && toData.getNgayPhatSong() != null)) {
          changes.add(getNgayPhatSong(fromData, toData));
        } else if (fromData.getNgayPhatSong() != null && toData.getNgayPhatSong() != null) {
          Calendar cal1 = Calendar.getInstance();
          Calendar cal2 = Calendar.getInstance();
          cal1.setTime(fromData.getNgayPhatSong());
          cal2.setTime(toData.getNgayPhatSong());
          if (!cal1.equals(cal2)) {
            changes.add(getNgayPhatSong(fromData, toData));
          }
        }

        if (!Objects.equals(fromData.getPhongDaiId(), toData.getPhongDaiId())) {
          changes.add(getPhongDai(fromData, toData));
        }
        if (!Objects.equals(fromData.getToId(), toData.getToId())) {
          changes.add(getTo(fromData, toData));
        }
        if (!Objects.equals(fromData.getTinhId(), toData.getTinhId())) {
          changes.add(getTinh(fromData, toData));
        }
        if (!Objects.equals(fromData.getHuyenId(), toData.getHuyenId())) {
          changes.add(getHuyen(fromData, toData));
        }
        if (!Objects.equals(fromData.getXaId(), toData.getXaId())) {
          changes.add(getXa(fromData, toData));
        }
        if (!StringUtil.equals(fromData.getMaTram(), toData.getMaTram())) {
          changes.add(getMaTram(fromData, toData));
        }
        if (!Objects.equals(fromData.getMaDauTuXayDung(), toData.getMaDauTuXayDung())) {
          changes.add(getMaDTXD(fromData, toData));
        }
        if (!fromData.getDaPhatSong().equals(toData.getDaPhatSong())) {
          changes.add(getDaPhatSong(fromData, toData));
        }
        if (!Objects.equals(fromData.getTen(), toData.getTen())) {
          changes.add(getTen(fromData, toData));
        }
        if (!StringUtil.equals(fromData.getMaTramErp(), toData.getMaTramErp())) {
          changes.add(getMaTramErp(fromData, toData));
        }
        if (!StringUtil.equals(fromData.getSiteNameErp(), toData.getSiteNameErp())) {
          changes.add(getSiteNameErp(fromData, toData));
        }
        if (!Objects.equals(fromData.getKhuVucId(), toData.getKhuVucId())) {
          changes.add(getKhuVuc(fromData, toData));
        }
        if (!StringUtil.equals(fromData.getDiaChi(), toData.getDiaChi())) {
          changes.add(getDiaChi(fromData, toData));
        }
        if (!StringUtil.equals(fromData.getKinhDo(), toData.getKinhDo())) {
          changes.add(getKinhDo(fromData, toData));
        }
        if (!StringUtil.equals(fromData.getViDo(), toData.getViDo())) {
          changes.add(getViDo(fromData, toData));
        }
        if (!Objects.equals(fromData.getLoaiCshtId(), toData.getLoaiCshtId())) {
          changes.add(getLoaiCsht(fromData, toData));
        }
        if (!Objects.equals(fromData.getLoaiTramId(), toData.getLoaiTramId())) {
          changes.add(getLoaiTram(fromData, toData));
        }
        if (!Objects.equals(fromData.getLoaiCotAngtenId(), toData.getLoaiCotAngtenId())) {
          changes.add(getLoaiCotAngten(fromData, toData));
        }
        if (!Objects.equals(fromData.getDoCaoAngten(), toData.getDoCaoAngten())) {
          changes.add(getDoCaoAngten(fromData, toData));
        }
        if (!fromData.getTrangThaiHoatDong().equals(toData.getTrangThaiHoatDong())) {
          changes.add(getTrangThaiHoatDong(fromData, toData));
        }
        if (!StringUtil.equals(fromData.getGhiChu(), toData.getGhiChu())) {
          changes.add(getGhiChu(fromData, toData));
        }

        boolean haveChangeRan = false;
        if (fromData.getDmLoaiThietBiRanEntityList() == null && toData.getDmLoaiThietBiRanEntityList() != null
            && toData.getDmLoaiThietBiRanEntityList().size() > 0) {
          haveChangeRan = true;
        }
        if (toData.getDmLoaiThietBiRanEntityList() == null && fromData.getDmLoaiThietBiRanEntityList() != null
            && fromData.getDmLoaiThietBiRanEntityList().size() > 0) {
          haveChangeRan = true;
        }
        if (toData.getDmLoaiThietBiRanEntityList() != null && fromData.getDmLoaiThietBiRanEntityList() != null) {
          if (toData.getDmLoaiThietBiRanEntityList().size() != fromData.getDmLoaiThietBiRanEntityList().size()) {
            haveChangeRan = true;
          } else {
            List<DmLoaiThietBiRanEntity> fromListRan = new ArrayList<DmLoaiThietBiRanEntity>(
                fromData.getDmLoaiThietBiRanEntityList());
            List<DmLoaiThietBiRanEntity> toListRan = new ArrayList<DmLoaiThietBiRanEntity>(
                toData.getDmLoaiThietBiRanEntityList());
            for (int i = 0; i < toListRan.size(); i++) {
              if (!fromListRan.get(i).getTen().equals(toListRan.get(i).getTen())) {
                haveChangeRan = true;
                break;
              }
            }
          }
        }
        if (haveChangeRan) {
          changes.add(getRans(fromData, toData));
        }
      } catch (Exception e) {
        System.out.println("Save log tram error: " + e.getMessage());
      }
    }

    result.setChanges(changes);
    result.setType(type);
    result.setVersion("0.0.0");
    ForModel forModel = new ForModel();
    forModel.setId(toData.getId());
    forModel.setName(toData.getMaTram() == null ? toData.getMaDauTuXayDung() : toData.getMaTram());
    result.setForModel(forModel);

    return result;
  }

  private ChangeModel getPhongDai(TramEntity fromData, TramEntity toData) {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("phongDai"));
    if (fromData.getPhongDaiId() != null) {
      DmPhongDaiEntity oldPd = dmPhongDaiRepository.findById(fromData.getPhongDaiId()).orElse(null);
      model.setOldValue(oldPd != null ? oldPd.getTen() : "");
    }
    if (toData.getPhongDaiId() != null) {
      DmPhongDaiEntity newPd = dmPhongDaiRepository.findById(toData.getPhongDaiId()).orElse(null);
      model.setValue(newPd != null ? newPd.getTen() : "");
    }
    model.setType("update");

    return model;
  }

  private ChangeModel getTo(TramEntity fromData, TramEntity toData) {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("to"));
    if (fromData.getToId() != null) {
      DmToEntity oldDm = dmToRepository.findById(fromData.getToId()).orElse(null);
      model.setOldValue(oldDm != null ? oldDm.getTen() : "");
    }
    if (toData.getToId() != null) {
      DmToEntity mewDm = dmToRepository.findById(toData.getToId()).orElse(null);
      model.setValue(mewDm != null ? mewDm.getTen() : "");
    }
    model.setType("update");

    return model;
  }

  private ChangeModel getTinh(TramEntity fromData, TramEntity toData) {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("tinh"));
    if (fromData.getTinhId() != null) {
      DmTinhEntity oldDm = dmTinhRepository.findById(fromData.getTinhId()).orElse(null);
      model.setOldValue(oldDm != null ? oldDm.getTen() : "");
    }
    if (toData.getTinhId() != null) {
      DmTinhEntity mewDm = dmTinhRepository.findById(toData.getTinhId()).orElse(null);
      model.setValue(mewDm != null ? mewDm.getTen() : "");
    }
    model.setType("update");

    return model;
  }

  private ChangeModel getHuyen(TramEntity fromData, TramEntity toData) {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("huyen"));
    if (fromData.getHuyenId() != null) {
      DmHuyenEntity oldDm = dmHuyenRepository.findById(fromData.getHuyenId()).orElse(null);
      model.setOldValue(oldDm != null ? oldDm.getTen() : "");
    }
    if (toData.getHuyenId() != null) {
      DmHuyenEntity mewDm = dmHuyenRepository.findById(toData.getHuyenId()).orElse(null);
      model.setValue(mewDm != null ? mewDm.getTen() : "");
    }
    model.setType("update");

    return model;
  }

  private ChangeModel getXa(TramEntity fromData, TramEntity toData) {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("xa"));
    if (fromData.getXaId() != null) {
      DmXaEntity oldDm = dmXaRepository.findById(fromData.getXaId()).orElse(null);
      model.setOldValue(oldDm != null ? oldDm.getTen() : "");
    }
    if (toData.getXaId() != null) {
      DmXaEntity mewDm = dmXaRepository.findById(toData.getXaId()).orElse(null);
      model.setValue(mewDm != null ? mewDm.getTen() : "");
    }
    model.setType("update");

    return model;
  }

  private ChangeModel getMaTram(TramEntity fromData, TramEntity toData) {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("maTram"));
    model.setOldValue(fromData.getMaTram());
    model.setValue(toData.getMaTram());
    model.setType("update");

    return model;
  }

  private ChangeModel getMaDTXD(TramEntity fromData, TramEntity toData) {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("maDauTuXayDung"));
    model.setOldValue(fromData.getMaDauTuXayDung());
    model.setValue(toData.getMaDauTuXayDung());
    model.setType("update");

    return model;
  }

  private ChangeModel getNgayPhatSong(TramEntity fromData, TramEntity toData) throws Exception {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("ngayPhatSong"));
    model
        .setOldValue(fromData.getNgayPhatSong() == null ? null
            : DateUtil.formatDate(fromData.getNgayPhatSong(), "dd MMMM yyyy"));
    model.setValue(toData.getNgayPhatSong() == null ? null
        : DateUtil.formatDate(toData.getNgayPhatSong(), "dd MMMM yyyy"));
    model.setType("update");

    return model;
  }

  private ChangeModel getDaPhatSong(TramEntity fromData, TramEntity toData) throws Exception {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("daPhatSong"));
    model.setOldValue(fromData.getDaPhatSong() == null ? null : String.valueOf(fromData.getDaPhatSong()));
    model.setValue(toData.getDaPhatSong() == null ? null : String.valueOf(toData.getDaPhatSong()));
    model.setType("update");

    return model;
  }

  private ChangeModel getTen(TramEntity fromData, TramEntity toData) throws Exception {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("ten"));
    model.setOldValue(fromData.getTen());
    model.setValue(toData.getTen());
    model.setType("update");

    return model;
  }

  private ChangeModel getMaTramErp(TramEntity fromData, TramEntity toData) throws Exception {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("maTramErp"));
    model.setOldValue(fromData.getMaTramErp());
    model.setValue(toData.getMaTramErp());
    model.setType("update");

    return model;
  }

  private ChangeModel getSiteNameErp(TramEntity fromData, TramEntity toData) throws Exception {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("siteNameErp"));
    model.setOldValue(fromData.getSiteNameErp());
    model.setValue(toData.getSiteNameErp());
    model.setType("update");

    return model;
  }

  private ChangeModel getKhuVuc(TramEntity fromData, TramEntity toData) {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("khuVuc"));
    if (fromData.getKhuVucId() != null) {
      DmTramKhuVucEntity oldDm = dmTramKhuVucRepository.findById(fromData.getKhuVucId()).orElse(null);
      model.setOldValue(oldDm != null ? oldDm.getTen() : "");
    }
    if (toData.getKhuVucId() != null) {
      DmTramKhuVucEntity newDm = dmTramKhuVucRepository.findById(toData.getKhuVucId()).orElse(null);
      model.setValue(newDm != null ? newDm.getTen() : "");
    }
    model.setType("update");

    return model;
  }

  private ChangeModel getDiaChi(TramEntity fromData, TramEntity toData) throws Exception {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("diaChi"));
    model.setOldValue(fromData.getDiaChi());
    model.setValue(toData.getDiaChi());
    model.setType("update");

    return model;
  }

  private ChangeModel getKinhDo(TramEntity fromData, TramEntity toData) throws Exception {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("kinhDo"));
    model.setOldValue(fromData.getKinhDo());
    model.setValue(toData.getKinhDo());
    model.setType("update");

    return model;
  }

  private ChangeModel getViDo(TramEntity fromData, TramEntity toData) throws Exception {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("viDo"));
    model.setOldValue(fromData.getViDo());
    model.setValue(toData.getViDo());
    model.setType("update");

    return model;
  }

  private ChangeModel getLoaiCsht(TramEntity fromData, TramEntity toData) {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("loaiCsht"));
    if (fromData.getLoaiCshtId() != null) {
      DmLoaiCshtEntity oldDm = dmLoaiCshtRepository.findById(fromData.getLoaiCshtId()).orElse(null);
      model.setOldValue(oldDm != null ? oldDm.getTen() : "");
    }
    if (toData.getLoaiCshtId() != null) {
      DmLoaiCshtEntity newDm = dmLoaiCshtRepository.findById(toData.getLoaiCshtId()).orElse(null);
      model.setValue(newDm != null ? newDm.getTen() : "");
    }
    model.setType("update");

    return model;
  }

  private ChangeModel getLoaiTram(TramEntity fromData, TramEntity toData) {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("loaiTram"));
    if (fromData.getLoaiTramId() != null) {
      DmLoaiTramEntity oldDm = dmLoaiTramRepository.findById(fromData.getLoaiTramId()).orElse(null);
      model.setOldValue(oldDm != null ? oldDm.getTen() : "");
    }
    if (toData.getLoaiTramId() != null) {
      DmLoaiTramEntity newDm = dmLoaiTramRepository.findById(toData.getLoaiTramId()).orElse(null);
      model.setValue(newDm != null ? newDm.getTen() : "");
    }
    model.setType("update");

    return model;
  }

  private ChangeModel getLoaiCotAngten(TramEntity fromData, TramEntity toData) {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("loaiCotAngten"));
    if (fromData.getLoaiCotAngtenId() != null) {
      DmLoaiCotAngtenEntity oldDm = dmLoaiCotAngtenRepository.findById(fromData.getLoaiCotAngtenId()).orElse(null);
      model.setOldValue(oldDm != null ? oldDm.getTen() : "");
    }
    if (toData.getLoaiTramId() != null) {
      DmLoaiCotAngtenEntity newDm = dmLoaiCotAngtenRepository.findById(toData.getLoaiCotAngtenId()).orElse(null);
      model.setValue(newDm != null ? newDm.getTen() : "");
    }
    model.setType("update");

    return model;
  }

  private ChangeModel getDoCaoAngten(TramEntity fromData, TramEntity toData) throws Exception {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("doCaoAngten"));
    model.setOldValue(fromData.getDoCaoAngten() == null ? null : String.valueOf(fromData.getDoCaoAngten()));
    model.setValue(toData.getDoCaoAngten() == null ? null : String.valueOf(toData.getDoCaoAngten()));
    model.setType("update");

    return model;
  }

  private ChangeModel getTrangThaiHoatDong(TramEntity fromData, TramEntity toData) throws Exception {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("trangThaiHoatDong"));
    model.setOldValue(fromData.getTrangThaiHoatDong() == null ? null : String.valueOf(fromData.getTrangThaiHoatDong()));
    model.setValue(toData.getTrangThaiHoatDong() == null ? null : String.valueOf(toData.getTrangThaiHoatDong()));
    model.setType("update");

    return model;
  }

  private ChangeModel getGhiChu(TramEntity fromData, TramEntity toData) {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("ghiChu"));
    model.setOldValue(fromData.getGhiChu());
    model.setValue(toData.getGhiChu());
    model.setType("update");

    return model;
  }

  private ChangeModel getRans(TramEntity fromData, TramEntity toData) {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList("dmLoaiThietBiRanList"));

    String fromRans = fromData.getDmLoaiThietBiRanEntityList().stream()
        .map(_ranEntity -> _ranEntity.getTen())
        .collect(Collectors.joining(","))
        .toString();
    String toRans = toData.getDmLoaiThietBiRanEntityList().stream()
        .map(_ranEntity -> _ranEntity.getTen())
        .collect(Collectors.joining(","))
        .toString();

    model.setOldValue(fromRans);
    model.setValue(toRans);
    model.setType("update");

    return model;
  }
}
