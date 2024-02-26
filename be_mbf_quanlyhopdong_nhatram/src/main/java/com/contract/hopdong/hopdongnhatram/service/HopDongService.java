// package com.contract.hopdong.hopdongnhatram.service;

// import java.io.BufferedWriter;
// import java.io.ByteArrayOutputStream;
// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.FileWriter;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.nio.file.StandardCopyOption;
// import java.sql.Timestamp;
// import java.text.DecimalFormat;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.time.Clock;
// import java.time.Instant;
// import java.util.ArrayList;
// import java.util.Calendar;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.LinkedHashSet;
// import java.util.List;
// import java.util.Map;
// import java.util.concurrent.CompletableFuture;
// import java.util.concurrent.ExecutionException;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
// import java.util.concurrent.atomic.AtomicBoolean;
// import java.util.stream.Collectors;
// import java.util.zip.ZipEntry;
// import java.util.zip.ZipOutputStream;

// import javax.persistence.EntityGraph;
// import javax.persistence.EntityManager;
// import javax.persistence.TypedQuery;

// import org.apache.commons.lang3.ObjectUtils;
// import org.apache.poi.xssf.usermodel.XSSFCellStyle;
// import org.apache.poi.xssf.usermodel.XSSFRow;
// import org.apache.poi.xssf.usermodel.XSSFSheet;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// import org.apache.tomcat.util.http.fileupload.FileUtils;
// import org.json.JSONObject;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.io.ByteArrayResource;
// import org.springframework.data.domain.Sort;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import
// org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.web.multipart.MultipartFile;

// import com.contract.authentication.component.KiemTraQuyenModuleHopDong;
// import com.contract.authentication.component.KiemTraQuyenModuleTram;
// import com.contract.base.model.QueryResultModel;
// import com.contract.base.service.BaseService;
// import com.contract.common.exception.BadRequestException;
// import com.contract.common.exception.DataExistsException;
// import com.contract.common.exception.InvalidDataException;
// import com.contract.common.exception.NotFoundException;
// import com.contract.common.util.CompareUtil;
// import com.contract.common.util.DateUtil;
// import com.contract.common.util.ExcelUtil;
// import com.contract.common.util.StringUtil;
// import
// com.contract.danhmuc.doituongkyhopdong.service.DmDoiTuongKyHopDongService;
// import com.contract.danhmuc.hinhthucdautu.service.DmHinhThucDauTuService;
// import
// com.contract.danhmuc.hinhthuckyhopdong.service.DmHinhThucKyHopDongService;
// import
// com.contract.danhmuc.hinhthucthanhtoan.service.DmHinhThucThanhToanService;
// import com.contract.danhmuc.huyen.service.DmHuyenService;
// import com.contract.danhmuc.loaicotangten.service.DmLoaiCotAngtenService;
// import com.contract.danhmuc.loaicsht.service.DmLoaiCshtService;
// import
// com.contract.danhmuc.loaihopdongphutro.entity.DmLoaiHopDongPhuTroEntity;
// import
// com.contract.danhmuc.loaihopdongphutro.service.DmLoaiHopDongPhuTroService;
// import com.contract.danhmuc.loaithietbiran.service.DmLoaiThietBiRanService;
// import com.contract.danhmuc.loaitram.service.DmLoaiTramService;
// import com.contract.danhmuc.phongdai.model.DmPhongDaiModel;
// import com.contract.danhmuc.phongdai.service.DmPhongDaiService;
// import com.contract.danhmuc.tinh.service.DmTinhService;
// import com.contract.danhmuc.to.service.DmToService;
// import com.contract.danhmuc.tramkhuvuc.service.DmTramKhuVucService;
// import com.contract.danhmuc.xa.service.DmXaService;
// import com.contract.hopdong.hopdongdoitac.model.HopDongDoiTacModel;
// import com.contract.hopdong.hopdongdoitac.service.HopDongDoiTacService;
// import com.contract.hopdong.hopdongdungchung.model.HopDongDungChungModel;
// import com.contract.hopdong.hopdongdungchung.service.HopDongDungChungService;
// import com.contract.hopdong.hopdongfile.entity.HopDongFileEntity;
// import com.contract.hopdong.hopdongfile.model.HopDongFileModel;
// import com.contract.hopdong.hopdongfile.model.LoaiFile;
// import com.contract.hopdong.hopdongfile.service.HopDongFileService;
// import
// com.contract.hopdong.hopdongkythanhtoan.entity.HopDongKyThanhToanEntity;
// import com.contract.hopdong.hopdongkythanhtoan.model.HopDongKyThanhToanModel;
// import
// com.contract.hopdong.hopdongkythanhtoan.service.HopDongKyThanhToanService;
// import com.contract.hopdong.hopdongnhatram.entity.HopDongEntity;
// import com.contract.hopdong.hopdongnhatram.model.BangKeChiHoRequest;
// import
// com.contract.hopdong.hopdongnhatram.model.BangKeKhaiThanhToanTrongKyRequest;
// import com.contract.hopdong.hopdongnhatram.model.HopDongModel;
// import com.contract.hopdong.hopdongnhatram.model.InfoFileUploadModel;
// import com.contract.hopdong.hopdongnhatram.model.ThanhToanHopDongRequest;
// import com.contract.hopdong.hopdongnhatram.model.TrangThaiHopDong;
// import com.contract.hopdong.hopdongnhatram.repository.HopDongRepository;
// import com.contract.hopdong.hopdongpheduyet.model.HopDongPheDuyetModel;
// import com.contract.hopdong.hopdongphuluc.model.HopDongPhuLucModel;
// import com.contract.hopdong.hopdongphuluc.service.HopDongPhuLucService;
// import com.contract.hopdong.hopdongphutro.model.HopDongPhuTroModel;
// import com.contract.hopdong.hopdongphutro.service.HopDongPhuTroService;
// import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
// import com.contract.nguoidung.nguoidung.service.NguoiDungService;
// import com.contract.nguoidung.nguoidungkhuvuc.entity.NguoiDungKhuVucEntity;
// import com.contract.nguoidung.nguoidungkhuvuc.service.NguoiDungKhuVucService;
// import com.contract.process.model.ProcessModel;
// import com.contract.process.service.ProcessService;
// import com.contract.sys.module.model.MODULE;
// import com.contract.tram.tram.entity.TramEntity;
// import com.contract.tram.tram.model.TramModel;
// import com.contract.tram.tram.service.TramService;
// import com.fasterxml.jackson.databind.ObjectMapper;

// @Service
// public class HopDongService extends BaseService {
// @Autowired
// private HopDongRepository hopDongRepository;
// @Autowired
// private HopDongDoiTacService hopDongDoiTacService;
// @Autowired
// private HopDongFileService fileService;
// @Autowired
// private HopDongPhuTroService hopDongPhuTroService;
// @Autowired
// private HopDongKyThanhToanService hopDongKyThanhToanService;
// @Autowired
// private NguoiDungService nguoiDungService;
// @Autowired
// private KiemTraQuyenModuleHopDong kiemTraQuyenModuleHopDong;
// @Autowired
// private HopDongPhuLucService hopDongPhuLucService;
// @Autowired
// private HopDongDungChungService hopDongDungChungService;
// @Autowired
// private NguoiDungKhuVucService nguoiDungKhuVucService;
// @Autowired
// private TramService tramService;
// @Autowired
// private DmPhongDaiService dmPhongDaiService;
// @Autowired
// private DmToService dmToService;
// @Autowired
// private DmTinhService dmTinhService;
// @Autowired
// private DmHuyenService dmHuyenService;
// @Autowired
// private DmXaService dmXaService;
// @Autowired
// private DmTramKhuVucService dmTramKhuVucService;
// @Autowired
// private DmLoaiCshtService dmLoaiCshtService;
// @Autowired
// private DmLoaiTramService dmLoaiTramService;
// @Autowired
// private DmLoaiThietBiRanService dmLoaiThietBiRanService;
// @Autowired
// private DmHinhThucKyHopDongService dmHinhThucKyHopDongService;
// @Autowired
// private DmHinhThucDauTuService dmHinhThucDauTuService;
// @Autowired
// private DmDoiTuongKyHopDongService dmDoiTuongKyHopDongService;
// @Autowired
// private DmHinhThucThanhToanService dmHinhThucThanhToanService;
// @Autowired
// private DmLoaiCotAngtenService dmLoaiCotAngtenService;
// @Autowired
// private ProcessService processService;
// @Autowired
// private KiemTraQuyenModuleTram kiemTraQuyenModuleTram;
// @Autowired
// private DmLoaiHopDongPhuTroService dmLoaiHopDongPhuTroService;
// @Autowired
// private HopDongFileService hopDongFileService;
// @Autowired
// private EntityManager entityManager;

// private Map<String, Object> hopDongError = new HashMap<>();

// private Map<String, Object> tramError = new HashMap<>();

// private Long processId = null;

// public HopDongEntity getDetail(Long id) {
// HopDongEntity hopDongEntity = findById(id);
// if (nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
// return hopDongEntity;
// }
// if (hopDongEntity == null) {
// throw new NotFoundException();
// }

// List<Integer> ids =
// kiemTraQuyenModuleHopDong.layDanhSachIdPhongDaiDuocQuyenXem();
// TramEntity tramEntity = hopDongEntity.getTramEntity();
// if (ids.contains(tramEntity.getPhongDaiId())) {
// return hopDongEntity;
// } else {
// throw new NotFoundException();
// }
// }

// public HopDongModel getThuePhuTroHopDong(HopDongEntity hopDongEntity) {
// TramModel tramModel = TramModel.fromEntity(hopDongEntity.getTramEntity(),
// true);

// HopDongModel hopDongModel = HopDongModel.fromEntity(hopDongEntity, true);

// hopDongModel.setTram(tramModel);

// List<HopDongFileModel> hopDongFileModelList = new ArrayList<>();
// if (hopDongEntity.getFileEntityList() != null) {
// hopDongEntity.getFileEntityList().forEach(hopDongFileEntity -> {
// try {
// hopDongFileModelList.add(HopDongFileModel.fromEntity(hopDongFileEntity,
// true));
// } catch (Exception e) {
// System.out.println(e);
// }
// });
// }
// List<HopDongPhuTroModel> hopDongPhuTroModelList = new ArrayList<>();
// if (hopDongEntity.getHopDongPhuTroEntityList() != null) {
// hopDongEntity.getHopDongPhuTroEntityList().forEach(hopDongPhuTroEntity -> {
// try {
// hopDongPhuTroModelList
// .add(HopDongPhuTroModel.fromEntity(hopDongPhuTroEntity, true));
// } catch (Exception e) {
// System.out.println(e);
// }
// });
// }
// List<HopDongKyThanhToanModel> hopDongKyThanhToanModelList = new
// ArrayList<>();
// if (hopDongEntity.getHopDongKyThanhToanEntityList() != null) {
// hopDongEntity.getHopDongKyThanhToanEntityList().forEach(hopDongKyThanhToanEntity
// -> {
// try {
// hopDongKyThanhToanModelList.add(
// HopDongKyThanhToanModel.fromEntity(hopDongKyThanhToanEntity, true));
// } catch (Exception e) {
// System.out.println(e);
// }
// });
// }

// List<HopDongPhuLucModel> hopDongPhuLucModelList = new ArrayList<>();
// if (hopDongEntity.getHopDongPhuLucEntityList() != null) {
// hopDongEntity.getHopDongPhuLucEntityList().forEach(hopDongPhuLucEntity -> {
// hopDongPhuLucModelList
// .add(HopDongPhuLucModel.fromEntity(hopDongPhuLucEntity, true));
// });
// }

// List<HopDongPheDuyetModel> hopDongPheDuyetModelList = new ArrayList<>();
// if (hopDongEntity.getHopDongPheDuyetEntityList() != null) {
// hopDongEntity.getHopDongPheDuyetEntityList().forEach(hopDongPheDuyetEntity ->
// {
// hopDongPheDuyetModelList
// .add(HopDongPheDuyetModel.fromEntity(hopDongPheDuyetEntity, true));
// });
// }

// hopDongModel.setHopDongPhuTroList(hopDongPhuTroModelList);
// hopDongModel.setFileList(hopDongFileModelList);
// hopDongModel.setHopDongKyThanhToanList(hopDongKyThanhToanModelList);
// hopDongModel.setHopDongPhuLucList(hopDongPhuLucModelList);
// hopDongModel.setHopDongPheDuyetList(hopDongPheDuyetModelList);
// return hopDongModel;
// }

// public HopDongModel getListFileHopDong(HopDongEntity entity, Date from, Date
// to, LoaiFile loai,
// String search) {
// HopDongModel hopDongModel = HopDongModel.fromEntity(entity, true);

// List<HopDongFileModel> hopDongFileModelList = new ArrayList<>();
// if (entity.getFileEntityList() != null) {
// entity.getFileEntityList().forEach(hopDongFileEntity -> {
// if ((from == null || hopDongFileEntity.getCreatedAt().compareTo(from) >= 0)
// && (to == null || hopDongFileEntity.getCreatedAt().compareTo(to) <= 0)
// && (loai == null || hopDongFileEntity.getLoai().equals(loai))
// && (search == null || hopDongFileEntity.getTen().contains(search))) {
// hopDongFileModelList.add(HopDongFileModel.fromEntity(hopDongFileEntity,
// false));
// }
// });
// }

// hopDongModel.setFileList(hopDongFileModelList);
// return hopDongModel;
// }

// public HopDongEntity findById(Long id) {
// return hopDongRepository.findById(id).orElse(null);
// }

// public QueryResultModel<HopDongEntity> findAll(Integer responseType, String
// maTram, int size, int page,
// String search,
// String soHopDong, String soHopDongErp, Date ngayKyFrom, Date ngayKyTo,
// Date ngayKetThucFrom, Date ngayKetThucTo, Integer hinhThucDauTu,
// Integer hinhThucKyHopDong, Integer doiTuongKyHopDong,
// String trangThaiThanhToan, String tinhTrangThanhToan, Integer phongDaiId,
// TrangThaiHopDong trangThaiHopDong)
// throws ParseException, ExecutionException, InterruptedException {

// SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
// // Nếu NGÀY KÝ chỉ nhập ngày FROM
// if (ngayKyFrom != null && ngayKyTo == null) {
// ngayKyTo = formatDate.parse("2200-12-31");
// }
// // Nếu NGÀY KÝ chỉ nhập ngày TO
// if (ngayKyFrom == null && ngayKyTo != null) {
// ngayKyFrom = formatDate.parse("1975-04-30");
// }
// // Nếu NGÀY KẾT THÚC chỉ nhập ngày FROM
// if (ngayKetThucFrom != null && ngayKetThucTo == null) {
// ngayKetThucTo = formatDate.parse("2200-12-31");
// }
// // Nếu NGÀY KẾT THÚC nhập ngày TO
// if (ngayKetThucFrom == null && ngayKetThucTo != null) {
// ngayKetThucFrom = formatDate.parse("1975-04-30");
// }

// Date now = new Date();
// Calendar c = Calendar.getInstance();
// c.setTime(new Date());
// c.add(Calendar.DATE, 90);
// Date after = c.getTime();

// List<Integer> ids = null;

// QueryResultModel<HopDongEntity> result = new QueryResultModel<>();

// if (!nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
// ids = kiemTraQuyenModuleHopDong.layDanhSachIdPhongDaiDuocQuyenXem();
// }
// if (responseType == 0) {
// result = getListHopDong(ids, maTram, search, soHopDong,
// soHopDongErp, hinhThucDauTu, hinhThucKyHopDong, doiTuongKyHopDong,
// ngayKyFrom,
// ngayKyTo, ngayKetThucFrom, ngayKetThucTo, trangThaiThanhToan,
// tinhTrangThanhToan, now, after,
// phongDaiId, trangThaiHopDong, page, size);

// } else {
// Sort sort = Sort.by("createdAt").descending();
// List<HopDongEntity> listHopDong = null;
// if (ids == null) {
// listHopDong = hopDongRepository.adminFindAllWithoutFetch(sort);
// } else {
// listHopDong = hopDongRepository.findAllWithoutFetch(ids, sort);
// }

// result.setTotalElements((long) listHopDong.size());
// result.setContent(listHopDong);
// result.setPage(page);
// result.setSize(size);
// }

// return result;
// }

// public List<HopDongModel> convertListEntityToModel(List<HopDongEntity>
// listHopDong) {
// List<HopDongModel> listReturn = new ArrayList<>();
// if (listHopDong != null) {
// listHopDong.forEach(hopDongEntity -> {
// HopDongModel hopDongModel = HopDongModel.fromEntity(hopDongEntity, true);
// try {
// TramEntity tramEntity = hopDongEntity.getTramEntity();
// hopDongModel.setTram(TramModel.fromEntity(tramEntity, true));
// } catch (Exception e) {
// System.out.println(e);
// }
// List<HopDongFileModel> hopDongFileModelList = new ArrayList<>();
// if (hopDongEntity.getFileEntityList() != null) {
// hopDongEntity.getFileEntityList().forEach(hopDongFileEntity -> {
// hopDongFileModelList
// .add(HopDongFileModel.fromEntity(hopDongFileEntity, false));
// });
// }
// List<HopDongPhuTroModel> hopDongPhuTroModelList = new ArrayList<>();
// if (hopDongEntity.getHopDongPhuTroEntityList() != null) {
// hopDongEntity.getHopDongPhuTroEntityList().forEach(hopDongPhuTroEntity -> {
// hopDongPhuTroModelList
// .add(HopDongPhuTroModel.fromEntity(hopDongPhuTroEntity, true));
// });
// }

// List<HopDongPhuLucModel> hopDongPhuLucModelList = new ArrayList<>();
// if (hopDongEntity.getHopDongPhuLucEntityList() != null) {
// hopDongEntity.getHopDongPhuLucEntityList().forEach(hopDongPhuLucEntity -> {
// hopDongPhuLucModelList
// .add(HopDongPhuLucModel.fromEntity(hopDongPhuLucEntity, true));
// });
// }

// List<HopDongKyThanhToanModel> hopDongKyThanhToanModelList = new
// ArrayList<>();
// if (hopDongEntity.getHopDongKyThanhToanEntityList() != null) {
// hopDongEntity.getHopDongKyThanhToanEntityList().forEach(hopDongKyThanhToanEntity
// -> {
// hopDongKyThanhToanModelList
// .add(HopDongKyThanhToanModel.fromEntity(hopDongKyThanhToanEntity, true));
// });
// }

// List<HopDongPheDuyetModel> hopDongPheDuyetModelList = new ArrayList<>();
// if (hopDongEntity.getHopDongPheDuyetEntityList() != null) {
// hopDongEntity.getHopDongPheDuyetEntityList().forEach(hopDongPheDuyetEntity ->
// {
// hopDongPheDuyetModelList
// .add(HopDongPheDuyetModel.fromEntity(hopDongPheDuyetEntity, true));
// });
// }

// hopDongModel.setHopDongPhuTroList(hopDongPhuTroModelList);
// hopDongModel.setFileList(hopDongFileModelList);
// hopDongModel.setHopDongPhuLucList(hopDongPhuLucModelList);
// hopDongModel.setHopDongKyThanhToanList(hopDongKyThanhToanModelList);
// hopDongModel.setHopDongPheDuyetList(hopDongPheDuyetModelList);
// listReturn.add(hopDongModel);
// });
// }
// return listReturn;
// }

// public boolean checkExists(Long id, String soHopDong, String soHopDongErp) {
// List<HopDongEntity> hopDongEntityList =
// this.hopDongRepository.findByIdAndSoHopDong(id, soHopDong,
// soHopDongErp);
// if (ObjectUtils.isEmpty(hopDongEntityList)) {
// return false;
// }
// return true;
// }

// @Transactional
// public HopDongModel create(HopDongModel hopDongModel, String hopDong,
// MultipartFile[] files,
// List<LoaiFile> loaiFiles) throws Exception {

// if (ObjectUtils.isEmpty(hopDongModel)) {
// try {
// ObjectMapper mapper = new ObjectMapper();
// hopDongModel = mapper.readValue(hopDong, HopDongModel.class);
// } catch (Exception e) {
// System.out.println(e);
// throw new InvalidDataException();
// }
// }

// if (hopDongModel == null) {
// throw new InvalidDataException();
// }

// if (checkExists(null, hopDongModel.getSoHopDong(),
// hopDongModel.getSoHopDongErp())) {
// throw new DataExistsException();
// }

// HopDongEntity toSave = new HopDongEntity();
// toSave = convertModelToEntity(toSave, hopDongModel);
// HopDongModel hopDongSaved = save(toSave);

// if (hopDongModel.getHopDongDoiTac() != null) {
// HopDongDoiTacModel hopDongDoiTacModel = hopDongModel.getHopDongDoiTac();
// // hopDongDoiTacModel.setHopDongNhaTramId(hopDongSaved.getId());
// // hopDongDoiTacService.create(hopDongDoiTacModel);
// }

// if (hopDongModel.getHopDongDungChung() != null) {
// HopDongDungChungModel hopDongDungChungModel =
// hopDongModel.getHopDongDungChung();
// hopDongDungChungModel.setHopDongNhaTramId(hopDongSaved.getId());
// hopDongDungChungService.create(hopDongDungChungModel);
// }

// if (hopDongModel.getHopDongPhuTroList() != null) {
// for (int i = 0; i < hopDongModel.getHopDongPhuTroList().size(); i++) {
// HopDongPhuTroModel hopDongPhuTroModel =
// hopDongModel.getHopDongPhuTroList().get(i);
// hopDongPhuTroModel.setHopDongId(hopDongSaved.getId());
// hopDongPhuTroService.create(hopDongPhuTroModel);
// }
// }

// if (hopDongModel.getHopDongKyThanhToanList() != null) {
// hopDongModel.getHopDongKyThanhToanList().forEach(hopDongKyThanhToanModel -> {
// hopDongKyThanhToanModel.setHopDongId(hopDongSaved.getId());
// hopDongKyThanhToanService.create(hopDongKyThanhToanModel);
// });
// }

// if (files != null && loaiFiles != null && files.length != loaiFiles.size()) {
// throw new InvalidDataException();
// }

// try {
// if (files != null && loaiFiles != null && files.length == loaiFiles.size()) {
// for (int i = 0; i < files.length; i++) {
// HopDongFileModel fileModel = new HopDongFileModel();
// fileModel.setTen(files[i].getOriginalFilename());
// fileModel.setHopDongNhaTramId(hopDongSaved.getId());
// fileModel.setLoai(loaiFiles.get(i));
// fileService.create(fileModel, files[i]);
// }
// }
// } catch (Exception e) {
// System.out.println(e);
// fileService.deleteFolderByHopDong(hopDongSaved.getId());
// throw new InvalidDataException();
// }

// return hopDongSaved;
// }

// @Transactional
// public HopDongModel update(Long id, HopDongModel hopDongModel, String
// hopDong, MultipartFile[] files,
// List<LoaiFile> loaiFiles) throws Exception {
// if (ObjectUtils.isEmpty(hopDongModel)) {
// try {
// ObjectMapper mapper = new ObjectMapper();
// hopDongModel = mapper.readValue(hopDong, HopDongModel.class);
// } catch (Exception e) {
// System.out.println(e);
// throw new InvalidDataException();
// }
// }

// if (hopDongModel == null) {
// throw new InvalidDataException();
// }

// if (id == null) {
// throw new InvalidDataException();
// }

// if (checkExists(id, hopDongModel.getSoHopDong(),
// hopDongModel.getSoHopDongErp())) {
// throw new DataExistsException();
// }

// HopDongEntity toSave = findById(id);

// if (toSave == null) {
// throw new NotFoundException();
// }

// if (!TrangThaiHopDong.NHAP.equals(toSave.getTrangThai())) {
// throw new InvalidDataException();
// }

// toSave = convertModelToEntity(toSave, hopDongModel);
// HopDongModel hopDongSaved = save(toSave);

// if (hopDongModel.getHopDongDoiTac() != null) {
// HopDongDoiTacModel hopDongDoiTacModel = hopDongModel.getHopDongDoiTac();
// // hopDongDoiTacModel.setHopDongNhaTramId(hopDongSaved.getId());
// // hopDongDoiTacService.update(hopDongDoiTacModel);
// }
// if (hopDongModel.getHopDongDungChung() != null) {
// HopDongDungChungModel hopDongDungChungModel =
// hopDongModel.getHopDongDungChung();
// hopDongDungChungModel.setHopDongNhaTramId(hopDongSaved.getId());
// try {
// hopDongDungChungService.update(hopDongDungChungModel);
// } catch (NotFoundException e) {
// System.out.println(e);
// hopDongDungChungService.create(hopDongDungChungModel);
// }
// }

// if (hopDongModel.getHopDongPhuTroList() != null) {
// hopDongPhuTroService.deleteByHopDong(hopDongSaved.getId());
// hopDongPhuTroService.flush();
// for (int i = 0; i < hopDongModel.getHopDongPhuTroList().size(); i++) {
// HopDongPhuTroModel hopDongPhuTroModel =
// hopDongModel.getHopDongPhuTroList().get(i);
// hopDongPhuTroModel.setHopDongId(hopDongSaved.getId());
// hopDongPhuTroService.create(hopDongPhuTroModel);
// }
// }

// if (hopDongModel.getHopDongKyThanhToanList() != null) {
// hopDongKyThanhToanService.deleteByHopDong(hopDongSaved.getId());
// hopDongKyThanhToanService.flush();
// hopDongModel.getHopDongKyThanhToanList().forEach(hopDongKyThanhToanModel -> {
// if (hopDongKyThanhToanModel.getDaThanhToanNgay() == null) {
// hopDongKyThanhToanModel.setHopDongId(hopDongSaved.getId());
// hopDongKyThanhToanService.create(hopDongKyThanhToanModel);
// }
// });
// }

// if (hopDongModel.getFileList() != null) {
// List<Long> listIdFileRemove = new ArrayList<>();
// List<Long> listCurrentId = hopDongModel.getFileList().stream().map(m ->
// m.getId())
// .collect(Collectors.toList());
// List<HopDongFileEntity> listSavedFile = new
// ArrayList<>(toSave.getFileEntityList());

// hopDongModel.getFileList().forEach(hopDongFileModel -> {
// try {
// if (hopDongFileModel.getId() == null) {
// fileService.create(hopDongFileModel, null);
// }
// } catch (Exception e) {
// System.out.println(e);
// throw new InvalidDataException();
// }
// });

// if (listSavedFile != null) {
// List<Long> listSavedId = listSavedFile.stream().map(e ->
// e.getId()).collect(Collectors.toList());

// listIdFileRemove.addAll(listSavedId);
// listIdFileRemove.removeAll(listCurrentId);

// fileService.deleteOldFile(listIdFileRemove, hopDongSaved.getId());
// }
// }

// List<HopDongFileModel> listFileSaved = new ArrayList<>();

// if (files != null && loaiFiles != null && files.length != loaiFiles.size()) {
// throw new InvalidDataException();
// }

// try {
// if (hopDongModel.getHopDongPhuLucList() != null) {
// NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();

// if (nguoiDungEntity == null) {
// throw new BadRequestException();
// }

// HopDongPhuLucModel hopDongPhuLucModel =
// hopDongModel.getHopDongPhuLucList().get(0);
// hopDongPhuLucModel.setHopDongId(hopDongSaved.getId());
// hopDongPhuLucModel.setNguoiTaoId(nguoiDungEntity.getId());
// HopDongPhuLucModel hopDongPhuLucSaved =
// hopDongPhuLucService.create(hopDongPhuLucModel);

// if (files != null && loaiFiles != null && files.length == loaiFiles.size()) {
// for (int i = 0; i < files.length; i++) {
// if (LoaiFile.PHU_LUC.equals(loaiFiles.get(i))) {
// HopDongFileModel fileModel = new HopDongFileModel();
// fileModel.setTen(files[i].getOriginalFilename());
// fileModel.setHopDongNhaTramId(hopDongSaved.getId());
// fileModel.setLoai(LoaiFile.PHU_LUC);
// if (hopDongPhuLucSaved != null) {
// fileModel.setHopDongPhuLucId(hopDongPhuLucSaved.getId());
// }
// listFileSaved.add(fileService.create(fileModel, files[i]));
// }
// }
// }
// }

// if (files != null && loaiFiles != null && files.length == loaiFiles.size()) {
// for (int i = 0; i < files.length; i++) {
// if (!LoaiFile.PHU_LUC.equals(loaiFiles.get(i))) {
// HopDongFileModel fileModel = new HopDongFileModel();
// fileModel.setTen(files[i].getOriginalFilename());
// fileModel.setHopDongNhaTramId(hopDongSaved.getId());
// fileModel.setLoai(loaiFiles.get(i));
// listFileSaved.add(fileService.create(fileModel, files[i]));
// }
// }
// }
// } catch (Exception e) {
// System.out.println(e);
// for (int i = 0; i < files.length; i++) {
// fileService.deleteListFileByHopDong(hopDongSaved.getId(), listFileSaved);
// }
// throw new InvalidDataException();
// }

// return hopDongSaved;
// }

// public HopDongModel save(HopDongEntity entity) {
// try {
// HopDongEntity saved = hopDongRepository.save(entity);
// return HopDongModel.fromEntity(saved, false);
// } catch (Exception e) {
// System.out.println(e);
// throw new InvalidDataException();
// }
// }

// public List<HopDongModel> thanhToanHopDong(List<ThanhToanHopDongRequest>
// listIdHopDong) {
// if (listIdHopDong == null) {
// throw new BadRequestException();
// }
// List<HopDongModel> listHopDongReturn = new ArrayList<>();
// for (ThanhToanHopDongRequest map : listIdHopDong) {
// Long hopDongId = map.getHopHongId();
// Date ngayThanhToan = map.getNgayThanhToan();

// HopDongEntity hopDongEntity = findById(hopDongId);
// if (hopDongId <= 0 || hopDongEntity == null) {
// throw new NotFoundException();
// }

// HopDongKyThanhToanEntity kyThanhToan =
// hopDongKyThanhToanService.findKyCanThanhToan(hopDongId);
// if (kyThanhToan != null) {
// kyThanhToan.setDaThanhToanNgay(ngayThanhToan);
// kyThanhToan.setThanhToanBy(nguoiDungService.getNguoiDung().getId());
// kyThanhToan.setThanhToanNgay(new Date());
// hopDongKyThanhToanService.save(kyThanhToan);
// listHopDongReturn.add(HopDongModel.fromEntity(hopDongEntity, false));
// }
// }
// return listHopDongReturn;
// }

// // TODO admin vs user
// public Map<String, Object> thongKeHopDong(Integer phongDaiId,
// TrangThaiHopDong trangThaiHopDong) {
// Map<String, Object> dataReturn = new HashMap<>();

// ExecutorService executorService = null;

// try {
// Date now = new Date();
// Calendar c = Calendar.getInstance();
// c.setTime(new Date());
// c.add(Calendar.DATE, 90);
// Date after = c.getTime();
// executorService = Executors.newFixedThreadPool(4);

// CompletableFuture<Long> futureOfCountHopDong = null;
// CompletableFuture<Long> futureOfCountHopDongSapThanhToan = null;
// CompletableFuture<Long> futureOfCountHopDongCanThanhToan = null;
// CompletableFuture<Long> futureOfCountHopDongQuaHan = null;

// if (!nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
// List<Integer> ids =
// kiemTraQuyenModuleHopDong.layDanhSachIdPhongDaiDuocQuyenXem();
// final List<Integer> listIdPhongDai = ids;
// futureOfCountHopDong = CompletableFuture.supplyAsync(
// () -> hopDongRepository.countHopDong(listIdPhongDai, phongDaiId,
// trangThaiHopDong),
// executorService);
// futureOfCountHopDongSapThanhToan = CompletableFuture.supplyAsync(() ->
// hopDongRepository
// .countHopDongChuanBiThanhToan(listIdPhongDai, now, after, phongDaiId,
// trangThaiHopDong),
// executorService);
// futureOfCountHopDongCanThanhToan = CompletableFuture.supplyAsync(() ->
// hopDongRepository
// .countHopDongCanThanhToan(listIdPhongDai, now, phongDaiId, trangThaiHopDong),
// executorService);
// futureOfCountHopDongQuaHan = CompletableFuture.supplyAsync(() ->
// hopDongRepository
// .countHopDongQuaHanThanhToan(listIdPhongDai, now, phongDaiId,
// trangThaiHopDong),
// executorService);
// } else {
// futureOfCountHopDong = CompletableFuture.supplyAsync(
// () -> hopDongRepository.adminCountHopDong(phongDaiId, trangThaiHopDong),
// executorService);
// futureOfCountHopDongSapThanhToan = CompletableFuture.supplyAsync(() ->
// hopDongRepository
// .adminCountHopDongChuanBiThanhToan(now, after, phongDaiId, trangThaiHopDong),
// executorService);
// futureOfCountHopDongCanThanhToan = CompletableFuture.supplyAsync(
// () -> hopDongRepository.adminCountHopDongCanThanhToan(now, phongDaiId,
// trangThaiHopDong),
// executorService);
// futureOfCountHopDongQuaHan = CompletableFuture.supplyAsync(
// () -> hopDongRepository.adminCountHopDongQuaHanThanhToan(now, phongDaiId,
// trangThaiHopDong),
// executorService);
// }

// dataReturn.put("ALL", futureOfCountHopDong.get());
// dataReturn.put("CHUAN_BI", futureOfCountHopDongSapThanhToan.get());
// dataReturn.put("CAN", futureOfCountHopDongCanThanhToan.get());
// dataReturn.put("QUA_HAN", futureOfCountHopDongQuaHan.get());
// } catch (Exception e) {
// System.out.println(e);
// } finally {
// if (executorService != null) {
// executorService.shutdownNow();
// }
// }

// return dataReturn;
// }

// public List<HopDongModel> getHopDongByTinhTrangThanhToan(List<HopDongModel>
// hopDongModelList,
// String tinhTrangThanhToan) {
// List<HopDongModel> listReturn = new ArrayList<>();

// if (ObjectUtils.isEmpty(hopDongModelList)) {
// return listReturn;
// }
// if (ObjectUtils.isEmpty(tinhTrangThanhToan)) {
// return hopDongModelList;
// }

// hopDongModelList.forEach(e -> {
// AtomicBoolean isHetHan = new AtomicBoolean(false);
// AtomicBoolean isCanThanhToan = new AtomicBoolean(false);
// AtomicBoolean isSapDen = new AtomicBoolean(false);
// List<HopDongKyThanhToanModel> hopDongKyThanhToanModelList =
// e.getHopDongKyThanhToanList();
// if (hopDongKyThanhToanModelList != null) {
// hopDongKyThanhToanModelList.forEach(hopDongKyThanhToanModel -> {
// if (hopDongKyThanhToanModel.getDaThanhToanNgay() == null) {
// try {
// Date now = new Date();
// Calendar c = Calendar.getInstance();
// c.setTime(new Date());
// c.add(Calendar.DATE, 90);
// Date after = c.getTime();
// if (hopDongKyThanhToanModel.getDenNgay() != null
// && now.compareTo(hopDongKyThanhToanModel.getDenNgay()) > 0) {
// isHetHan.set(true);
// }
// if (hopDongKyThanhToanModel.getTuNgay() != null
// && hopDongKyThanhToanModel.getTuNgay().compareTo(now) <= 0
// && hopDongKyThanhToanModel.getDenNgay().compareTo(now) >= 0) {
// isCanThanhToan.set(true);
// }
// if (hopDongKyThanhToanModel.getTuNgay() != null
// && hopDongKyThanhToanModel.getTuNgay().compareTo(now) > 0
// && hopDongKyThanhToanModel.getTuNgay().compareTo(after) <= 0) {
// isSapDen.set(true);
// }
// } catch (Exception ex) {
// System.out.println(ex);
// }
// }
// });
// }
// if ("HET_HAN".equals(tinhTrangThanhToan) && isHetHan.get()) {
// listReturn.add(e);
// }
// if ("CAN_THANH_TOAN".equals(tinhTrangThanhToan) && isCanThanhToan.get() &&
// !isHetHan.get()) {
// listReturn.add(e);
// }
// if ("SAP_DEN".equals(tinhTrangThanhToan) && isSapDen.get() && !isHetHan.get()
// && !isCanThanhToan.get()) {
// listReturn.add(e);
// }
// });

// return listReturn;
// }

// public ResponseEntity<?> exportTemplateDeNghiChiHo(BangKeChiHoRequest
// request) throws Exception {
// if (request == null || request.getListHopDongId() == null) {
// throw new BadRequestException();
// }
// Date ngayLap = request.getNgayLap();
// if (ngayLap == null) {
// ngayLap = new Date();
// }
// Date tuNgay = request.getTuNgay();
// Date denNgay = request.getDenNgay();
// LinkedHashSet<DmPhongDaiModel> setPhongDai = new LinkedHashSet<>();
// List<HopDongModel> listHopDong = new ArrayList<>();
// for (Long idHopDong : request.getListHopDongId()) {
// HopDongEntity hopDongEntity =
// hopDongRepository.findById(idHopDong).orElse(null);
// if (hopDongEntity != null) {
// listHopDong.add(HopDongModel.fromEntity(hopDongEntity, true));
// setPhongDai.add(DmPhongDaiModel.fromEntity(hopDongEntity.getTramEntity().getDmPhongDaiEntity()));
// }
// }
// List<DmPhongDaiModel> listPhongDai = setPhongDai.stream().toList();
// String rootName = "src/main/resources/templates/hopdong";
// String fileNameSource = "Template_export_bangKeDeNghiChiHo.xlsx";
// List<NguoiDungKhuVucEntity> listKhuVuc = nguoiDungKhuVucService
// .findByNguoiDungId(nguoiDungService.getNguoiDung().getId());
// Instant instant = Instant.now(Clock.systemUTC());
// long currentTimeMillis = instant.toEpochMilli();

// Date dateTitle = null;
// if (denNgay != null) {
// dateTitle = denNgay;
// } else {
// dateTitle = ngayLap;
// }

// if (listPhongDai.size() == 1) {
// String fileNameCopy = currentTimeMillis +
// "_Template_export_de_nghi_chi_ho.xlsx";
// Path fileTemplateGoc = Path.of(rootName + "/" + fileNameSource);
// Path target = Path.of(rootName + "/" + fileNameCopy);
// // Sao chép file template gốc ra một bản sao
// File templateToExport = Files.copy(fileTemplateGoc, target,
// StandardCopyOption.REPLACE_EXISTING).toFile();
// XSSFWorkbook workbook = new XSSFWorkbook(templateToExport);
// XSSFSheet sheet = workbook.getSheetAt(0);
// try {
// exportTableDeNghiChiHo(workbook, sheet, listHopDong, listPhongDai.get(0),
// ngayLap, tuNgay, denNgay);

// ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
// workbook.write(outputStream);
// workbook.close();

// File fileDelete = new File(Path.of(rootName + "/" + fileNameCopy).toUri());
// fileDelete.delete();

// String fileNameReturn = "BANG_KE_DE_NGHI_CHI_HO T" +
// DateUtil.formatDate(dateTitle, "MM") + "."
// + DateUtil.formatDate(dateTitle, "yyyy") + " NH CTNT.xlsx";
// return new ResponseEntity<>(outputStream.toByteArray(),
// ExcelUtil.getHeaders(fileNameReturn),
// HttpStatus.OK);
// } catch (Exception e) {
// File fileDelete = new File(Path.of(rootName + "/" + fileNameCopy).toUri());
// fileDelete.delete();
// return
// ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
// }
// } else {
// Files.createDirectory(Paths.get(rootName + "/" + currentTimeMillis));
// try {
// for (DmPhongDaiModel phongDai : listPhongDai) {
// File templateToExport = Files.copy(
// Path.of(rootName + "/" + fileNameSource),
// Path.of(rootName + "/" + currentTimeMillis + "/" + "BANG_KE_DE_NGHI_CHI_HO T
// "
// + DateUtil.formatDate(dateTitle, "MM") + "."
// + DateUtil.formatDate(dateTitle, "yyyy") + " NH CTNT_" +
// phongDai.getTenVietTat()
// + ".xlsx"),
// StandardCopyOption.REPLACE_EXISTING).toFile();
// XSSFWorkbook workbook = new XSSFWorkbook(templateToExport);
// XSSFSheet sheet = workbook.getSheetAt(0);
// List<HopDongModel> listHopDongToExport = new ArrayList<>();
// for (HopDongModel hopDongModel : listHopDong) {
// if (hopDongModel.getTram().getPhongDaiId() - phongDai.getId() == 0) {
// listHopDongToExport.add(hopDongModel);
// }
// }
// // Thông tin hợp đồng
// exportTableDeNghiChiHo(workbook, sheet, listHopDongToExport, phongDai,
// ngayLap, tuNgay, denNgay);
// ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
// workbook.write(outputStream);
// workbook.close();
// }
// // Zip thư mục
// FileOutputStream fos = new FileOutputStream(rootName + "/" +
// currentTimeMillis + ".zip");
// ZipOutputStream zipOut = new ZipOutputStream(fos);
// File sourceFolder = new File(rootName + "/" + currentTimeMillis);
// for (File file : sourceFolder.listFiles()) {
// String entryPath = sourceFolder.getName() + "/" + file.getName();
// ZipEntry zipEntry = new ZipEntry(entryPath);
// zipOut.putNextEntry(zipEntry);
// Path filePath = Paths.get(file.getAbsolutePath());
// Files.copy(filePath, zipOut);
// zipOut.closeEntry();
// }
// zipOut.close();
// fos.close();
// // Xóa thư mục tạm chứa các file excel
// Path pathFolder = Paths.get(rootName + "/" + currentTimeMillis);
// FileUtils.deleteDirectory(new File(pathFolder.toUri()));
// // Đường dẫn của tệp .zip gốc
// String originalFilePath = rootName + "/" + currentTimeMillis + ".zip";
// // Đọc dữ liệu của tệp .zip vào một mảng byte
// byte[] zipData = Files.readAllBytes(new File(originalFilePath).toPath());
// // Lấy tên tệp .zip để đặt tên trong phần HEADER
// String fileName = "BANG_KE_DE_NGHI_CHI_HO T" + DateUtil.formatDate(dateTitle,
// "MM") + "."
// + DateUtil.formatDate(dateTitle, "yyyy") + " NH CTNT_"
// + listKhuVuc.get(0).getDmPhongDaiEntity().getTenVietTat() + ".zip";
// // Xóa tệp .zip gốc
// File originalFile = new File(originalFilePath);
// originalFile.delete();
// // Tạo một ByteArrayResource từ dữ liệu .zip
// ByteArrayResource resourceZip = new ByteArrayResource(zipData);
// return ResponseEntity.ok()
// .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName
// + "\"")
// .contentType(MediaType.APPLICATION_OCTET_STREAM)
// .body(resourceZip);
// } catch (Exception e) {
// Path pathFolder = Paths.get(rootName + "/" + currentTimeMillis);
// FileUtils.deleteDirectory(new File(pathFolder.toUri()));
// File originalFile = new File(rootName + "/" + currentTimeMillis + ".zip");
// originalFile.delete();
// return
// ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
// }
// }
// }

// public Long importHopDong(List<HopDongModel> hopDongModelList) {
// if (ObjectUtils.isEmpty(hopDongModelList)) {
// throw new BadRequestException();
// }
// // tao process
// NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();

// ProcessModel processModel = new ProcessModel();
// processModel.setUserId(nguoiDungEntity.getId());
// processModel.setModule(MODULE.HOP_DONG.name());
// processModel.setTongSo((long) hopDongModelList.size());
// processModel = processService.create(processModel);
// final ProcessModel savedProcess = processModel;

// if (nguoiDungEntity != null) {
// CompletableFuture.runAsync(() -> importFromExcel(hopDongModelList,
// savedProcess, nguoiDungEntity.getEmail(),
// nguoiDungEntity.getMatKhau()));
// }

// processId = processModel.getId();

// return processModel.getId();
// }

// public void importFromExcel(List<HopDongModel> hopDongModelList, ProcessModel
// processModel, String userName,
// String password) {
// if (ObjectUtils.isEmpty(hopDongModelList)) {
// throw new InvalidDataException();
// }
// setUserNewThread(userName, password);
// long count = 0L;
// long numberError = 0L;

// for (int i = 0; i < hopDongModelList.size(); i++) {
// HopDongModel hopDongModel = hopDongModelList.get(i);
// TramModel tramModel = hopDongModel.getTram();

// Boolean isImportSuccess;
// try {
// isImportSuccess = importData(hopDongModel, tramModel);
// } catch (Exception e) {
// System.out.println("Import fail hopdong:" + hopDongModel.getSoHopDong());
// isImportSuccess = false;
// }
// if (!isImportSuccess) {
// exportErrorFile();
// numberError++;
// }
// count++;
// if (count % 10 == 0 || i == hopDongModelList.size() - 1) {
// processModel.setHoanThanh(count);
// processModel.setSoLuongLoi(numberError);
// processService.update(processModel);
// }
// }

// processModel.setKetThuc(true);
// processService.update(processModel);
// }

// public void setUserNewThread(String email, String matKhau) {
// UserDetails userDetails = new
// org.springframework.security.core.userdetails.User(email,
// matKhau, new ArrayList<>());
// UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
// UsernamePasswordAuthenticationToken(
// userDetails, null,
// userDetails.getAuthorities());
// SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
// }

// @Transactional
// public Boolean importData(HopDongModel hopDongModel, TramModel tramModel)
// throws Exception {
// if (tramModel == null || !checkTram(tramModel)) {
// throw new RuntimeException("Invalid tram!!!");
// }
// try {
// TramModel saved = tramService.create(tramModel);
// hopDongModel.setTramId(saved.getId());
// } catch (DataExistsException e) {
// try {
// tramModel = tramService.findByMaTram(tramModel.getMaTram());
// Long id = tramModel.getId();
// hopDongModel.setTramId(tramModel.getId());
// tramService.update(tramModel, id);
// } catch (Exception ex) {
// System.out.println(ex);
// throw ex;
// }
// } catch (Exception e) {
// System.out.println(e);
// throw e;
// }

// if (hopDongModel == null || !checkHopDong(hopDongModel)) {
// throw new RuntimeException("Invalid hopdong!!!");
// }
// try {
// create(hopDongModel, null, null, null);
// } catch (Exception e) {
// throw e;
// }

// return true;
// }

// public void exportErrorFile() {
// try {
// hopDongError.put("tram", tramError);
// JSONObject jsonObject = new JSONObject(hopDongError);

// String fileFolder = Paths.get("process_data").toFile().getAbsolutePath();
// ;
// String fileName = processId.toString();

// Path path = Paths.get(fileFolder, fileName);
// File file = path.toFile();

// if (!Paths.get(fileFolder).toFile().exists()) {
// Paths.get(fileFolder).toFile().mkdirs();
// }
// if (!file.exists()) {
// file.createNewFile();
// }

// FileWriter fw = new FileWriter(path.toFile().getAbsolutePath(), true);
// BufferedWriter writer = new BufferedWriter(fw);
// writer.write(jsonObject.toString());
// writer.newLine();
// writer.close();
// } catch (Exception e) {
// System.out.println(e);
// }
// }

// public ResponseEntity<?>
// exportTemplateBangKeKhaiThanhToanTrongKy(BangKeKhaiThanhToanTrongKyRequest
// request)
// throws Exception {
// if (request == null || request.getListHopDongId() == null) {
// throw new BadRequestException();
// }
// LinkedHashSet<DmPhongDaiModel> setPhongDai = new LinkedHashSet<>();
// List<HopDongModel> listHopDong = new ArrayList<>();
// for (Long idHopDong : request.getListHopDongId()) {
// HopDongEntity hopDongEntity =
// hopDongRepository.findById(idHopDong).orElse(null);
// if (hopDongEntity != null) {
// listHopDong.add(HopDongModel.fromEntity(hopDongEntity, true));
// setPhongDai.add(DmPhongDaiModel.fromEntity(hopDongEntity.getTramEntity().getDmPhongDaiEntity()));
// }
// }
// List<DmPhongDaiModel> listPhongDai = setPhongDai.stream().toList();
// String rootName = "src/main/resources/templates/hopdong";
// String fileNameSource = "Template_export_bangKeKhaiThanhToanTrongKy.xlsx";
// // List<NguoiDungKhuVucEntity> listKhuVuc =
// //
// nguoiDungKhuVucService.findByNguoiDungId(nguoiDungService.getNguoiDung().getId());
// long currentTimeMillis = Instant.now(Clock.systemUTC()).toEpochMilli();

// if (listPhongDai.size() == 1) {
// String fileNameCopy = currentTimeMillis +
// "_Template_export_bangKeKhaiThanhToanTrongKy.xlsx";
// Path fileTemplateGoc = Path.of(rootName + "/" + fileNameSource);
// Path target = Path.of(rootName + "/" + fileNameCopy);
// // Sao chép file template gốc ra một bản sao và new thành workbook
// XSSFWorkbook workbook = new XSSFWorkbook(
// Files.copy(fileTemplateGoc, target,
// StandardCopyOption.REPLACE_EXISTING).toFile());
// XSSFSheet sheet = workbook.getSheetAt(0);
// try {
// exportTableOfBangKeKhaiThanhToanTrongKy(workbook, sheet, listHopDong,
// listPhongDai.get(0),
// request.getKyThanhToan());
// //
// ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
// workbook.write(outputStream);
// workbook.close();
// // Xóa file clone
// File fileDelete = new File(Path.of(rootName + "/" + fileNameCopy).toUri());
// fileDelete.delete();
// return new ResponseEntity<>(outputStream.toByteArray(),
// ExcelUtil.getHeaders("Mau export Bang ke khai thanh toan trong ky.xlsx"),
// HttpStatus.OK);
// } catch (Exception e) {
// File fileDelete = new File(Path.of(rootName + "/" + fileNameCopy).toUri());
// fileDelete.delete();
// return
// ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
// }
// } else {
// Files.createDirectory(Paths.get(rootName + "/" + currentTimeMillis));
// try {
// for (DmPhongDaiModel phongDaiModel : listPhongDai) {
// File templateToExport = Files.copy(
// Path.of(rootName + "/" + fileNameSource),
// Path.of(rootName + "/" + currentTimeMillis + "/"
// + "Mau export Bang ke khai thanh toan trong ky_" +
// phongDaiModel.getTenVietTat()
// + ".xlsx"),
// StandardCopyOption.REPLACE_EXISTING).toFile();
// XSSFWorkbook workbook = new XSSFWorkbook(templateToExport);
// XSSFSheet sheet = workbook.getSheetAt(0);
// List<HopDongModel> listHopDongToExport = new ArrayList<>();
// for (HopDongModel hopDongModel : listHopDong) {
// if (hopDongModel.getTram().getPhongDaiId() - phongDaiModel.getId() == 0) {
// listHopDongToExport.add(hopDongModel);
// }
// }
// // Thông tin hợp đồng
// exportTableOfBangKeKhaiThanhToanTrongKy(workbook, sheet, listHopDongToExport,
// phongDaiModel,
// request.getKyThanhToan());
// ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
// workbook.write(outputStream);
// workbook.close();
// }
// // Zip thư mục
// FileOutputStream fos = new FileOutputStream(rootName + "/" +
// currentTimeMillis + ".zip");
// ZipOutputStream zipOut = new ZipOutputStream(fos);
// File sourceFolder = new File(rootName + "/" + currentTimeMillis);
// for (File file : sourceFolder.listFiles()) {
// String entryPath = sourceFolder.getName() + "/" + file.getName();
// ZipEntry zipEntry = new ZipEntry(entryPath);
// zipOut.putNextEntry(zipEntry);
// Path filePath = Paths.get(file.getAbsolutePath());
// Files.copy(filePath, zipOut);
// zipOut.closeEntry();
// }
// zipOut.close();
// fos.close();
// // Xóa thư mục tạm chứa các file excel export
// Path pathFolder = Paths.get(rootName + "/" + currentTimeMillis);
// FileUtils.deleteDirectory(new File(pathFolder.toUri()));
// // Đường dẫn của tệp .zip gốc
// String originalFilePath = rootName + "/" + currentTimeMillis + ".zip";
// // Đọc dữ liệu của tệp .zip vào một mảng byte
// byte[] zipData = Files.readAllBytes(new File(originalFilePath).toPath());
// // Xóa tệp .zip gốc
// File originalFile = new File(originalFilePath);
// originalFile.delete();
// // Tạo một ByteArrayResource từ dữ liệu .zip
// ByteArrayResource resourceZip = new ByteArrayResource(zipData);
// return ResponseEntity.ok()
// .header(HttpHeaders.CONTENT_DISPOSITION,
// "attachment; filename=Bang ke khai thanh toan trong ky.zip")
// .contentType(MediaType.APPLICATION_OCTET_STREAM)
// .body(resourceZip);
// } catch (Exception e) {
// Path pathFolder = Paths.get(rootName + "/" + currentTimeMillis);
// FileUtils.deleteDirectory(new File(pathFolder.toUri()));
// File originalFile = new File(rootName + "/" + currentTimeMillis + ".zip");
// originalFile.delete();
// return
// ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
// }
// }
// }

// public Map<String, Object> getMapDifference(HopDongModel hopDongModel1,
// HopDongModel hopDongModel2) {
// hopDongModel1 = prepareObject(hopDongModel1);
// hopDongModel2 = prepareObject(hopDongModel2);

// HopDongDoiTacModel hopDongDoiTacModel1 = hopDongModel1.getHopDongDoiTac();
// HopDongDoiTacModel hopDongDoiTacModel2 = hopDongModel2.getHopDongDoiTac();

// HopDongDungChungModel hopDongDungChungModel1 =
// hopDongModel1.getHopDongDungChung();
// HopDongDungChungModel hopDongDungChungModel2 =
// hopDongModel2.getHopDongDungChung();

// List<HopDongFileModel> hopDongFileModelList1 = hopDongModel1.getFileList();
// List<HopDongFileModel> hopDongFileModelList2 = hopDongModel2.getFileList();

// List<HopDongPhuTroModel> hopDongPhuTroModelList1 =
// hopDongModel1.getHopDongPhuTroList();
// List<HopDongPhuTroModel> hopDongPhuTroModelList2 =
// hopDongModel2.getHopDongPhuTroList();

// List<HopDongKyThanhToanModel> hopDongKyThanhToanModelList1 =
// hopDongModel1.getHopDongKyThanhToanList();
// List<HopDongKyThanhToanModel> hopDongKyThanhToanModelList2 =
// hopDongModel2.getHopDongKyThanhToanList();

// List<HopDongPhuLucModel> hopDongPhuLucModelList1 =
// hopDongModel1.getHopDongPhuLucList();
// List<HopDongPhuLucModel> hopDongPhuLucModelList2 =
// hopDongModel2.getHopDongPhuLucList();

// hopDongModel1.setHopDongDoiTac(null);
// hopDongModel1.setHopDongDungChung(null);
// hopDongModel1.setFileList(null);
// hopDongModel1.setHopDongPhuTroList(null);
// hopDongModel1.setHopDongKyThanhToanList(null);
// hopDongModel1.setHopDongPhuLucList(null);

// hopDongModel2.setHopDongDoiTac(null);
// hopDongModel2.setHopDongDungChung(null);
// hopDongModel2.setFileList(null);
// hopDongModel2.setHopDongPhuTroList(null);
// hopDongModel2.setHopDongKyThanhToanList(null);
// hopDongModel2.setHopDongPhuLucList(null);

// Map<String, Object> hopDongDiffMap = CompareUtil.getDiff(hopDongModel1,
// hopDongModel2);
// Map<String, Object> hopDongDoiTacDiffMap =
// CompareUtil.getDiff(hopDongDoiTacModel1, hopDongDoiTacModel2);
// Map<String, Object> hopDongDungChungDiffMap =
// CompareUtil.getDiff(hopDongDungChungModel1,
// hopDongDungChungModel2);
// List<HopDongFileModel> listHopDongFileDiff = new ArrayList<>(
// hopDongFileModelList2 != null ? hopDongFileModelList2 : new ArrayList<>());
// listHopDongFileDiff.removeAll(hopDongFileModelList1 != null ?
// hopDongFileModelList1 : new ArrayList<>());
// List<HopDongPhuTroModel> listHopDongPhuTroDiff = new ArrayList<>(
// hopDongPhuTroModelList2 != null ? hopDongPhuTroModelList2 : new
// ArrayList<>());
// listHopDongPhuTroDiff.removeAll(hopDongPhuTroModelList1 != null ?
// hopDongPhuTroModelList1 : new ArrayList<>());
// List<HopDongPhuLucModel> listHopDongPhuLucDiff = new ArrayList<>(
// hopDongPhuLucModelList2 != null ? hopDongPhuLucModelList2 : new
// ArrayList<>());
// listHopDongPhuLucDiff.removeAll(hopDongPhuLucModelList1 != null ?
// hopDongPhuLucModelList1 : new ArrayList<>());
// List<HopDongKyThanhToanModel> listHopDongKyThanhToanModel = new ArrayList<>(
// hopDongKyThanhToanModelList2 != null ? hopDongKyThanhToanModelList2 : new
// ArrayList<>());
// listHopDongKyThanhToanModel
// .removeAll(hopDongKyThanhToanModelList1 != null ?
// hopDongKyThanhToanModelList1 : new ArrayList<>());

// if (hopDongFileModelList1 != null) {
// hopDongFileModelList1.forEach(data -> {
// if (hopDongFileModelList2 != null && !hopDongFileModelList2.contains(data)) {
// data.setDeletedAt(new Date());
// listHopDongFileDiff.add(data);
// }
// });
// }
// if (hopDongPhuTroModelList1 != null) {
// hopDongPhuTroModelList1.forEach(data -> {
// if (hopDongPhuTroModelList2 != null &&
// !hopDongPhuTroModelList2.contains(data)) {
// data.setDeletedAt(new Date());
// listHopDongPhuTroDiff.add(data);
// }
// });
// }
// if (hopDongPhuLucModelList1 != null) {
// hopDongPhuLucModelList1.forEach(data -> {
// if (hopDongPhuLucModelList2 != null &&
// !hopDongPhuLucModelList2.contains(data)) {
// data.setDeletedAt(new Date());
// listHopDongPhuLucDiff.add(data);
// }
// });
// }
// if (hopDongKyThanhToanModelList1 != null) {
// hopDongKyThanhToanModelList1.forEach(data -> {
// if (hopDongKyThanhToanModelList2 != null &&
// !hopDongKyThanhToanModelList2.contains(data)) {
// data.setDeletedAt(new Date());
// listHopDongKyThanhToanModel.add(data);
// }
// });
// }

// hopDongDiffMap.put("hopDongDoiTac", hopDongDoiTacDiffMap);
// hopDongDiffMap.put("hopDongDungChung", hopDongDungChungDiffMap);
// hopDongDiffMap.put("fileList", listHopDongFileDiff);
// hopDongDiffMap.put("hopDongPhuTroList", listHopDongPhuTroDiff);
// hopDongDiffMap.put("hopDongKyThanhToanList", listHopDongKyThanhToanModel);
// hopDongDiffMap.put("hopDongPhuLucList", listHopDongPhuLucDiff);

// return hopDongDiffMap;
// }

// public List<HopDongEntity> findHopDongByListId(List<Long> ids) {
// return hopDongRepository.findAllById(ids);
// }

// public HopDongModel delete(Long id) {
// if (ObjectUtils.isEmpty(id)) {
// throw new InvalidDataException();
// }
// HopDongEntity toDelete = hopDongRepository.findById(id).orElse(null);
// if (ObjectUtils.isEmpty(toDelete) ||
// !TrangThaiHopDong.NHAP.equals(toDelete.getTrangThai())) {
// throw new NotFoundException();
// }
// toDelete.setDeletedAt(new Date());
// hopDongRepository.save(toDelete);

// return HopDongModel.fromEntity(toDelete, false);
// }

// public List<Integer> upload(List<InfoFileUploadModel> infoFileList,
// MultipartFile[] files) {
// if (infoFileList.size() != files.length) {
// throw new InvalidDataException();
// }
// List<Integer> listIdFileSaved = new ArrayList<>();
// for (int i = 0; i < files.length; i++) {
// try {
// hopDongFileService.saveFileWithPath(infoFileList.get(i).getPath(), files[i]);
// listIdFileSaved.add(i);
// } catch (Exception e) {
// System.out.println(e);
// }
// }

// return listIdFileSaved;
// }

// // TODO
// public QueryResultModel<HopDongEntity> getListHopDong(List<Integer> ids,
// String maTram,
// String search,
// String soHopDong,
// String soHopDongErp,
// Integer hinhThucDauTu,
// Integer hinhThucKyHopDongId,
// Integer doiTuongKyHopDongId,
// Date ngayKyFrom,
// Date ngayKyTo,
// Date ngayKetThucFrom,
// Date ngayKetThucTo,
// String trangThaiThanhToan,
// String tinhTrangThanhToan,
// Date ngayHienTai,
// Date ngayTuongLai,
// Integer phongDaiId,
// TrangThaiHopDong trangThaiHopDong,
// Integer page, Integer size) throws ExecutionException, InterruptedException {
// NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();
// String sqlRoot = "from HopDongEntity a "
// + "left join a.tramEntity b "
// + "left join a.dmHinhThucKyHopDongEntity c "
// + "left join a.dmHinhThucDauTuEntity d "
// + "left join a.dmDoiTuongKyHopDongEntity e "
// + "left join a.hopDongDoiTacEntity "
// + "left join a.fileEntityList "
// + "left join a.hopDongPhuTroEntityList "
// + "left join a.hopDongKyThanhToanEntityList "
// + "left join a.hopDongPhuLucEntityList ";

// if (ObjectUtils.isEmpty(trangThaiHopDong) ||
// !trangThaiHopDong.equals(TrangThaiHopDong.CAN_PHE_DUYET)) {
// sqlRoot += "left join a.hopDongPheDuyetEntityList ";
// }
// boolean firstCondition = true;
// boolean isCanPheDuyet = false;
// if (!ObjectUtils.isEmpty(trangThaiHopDong) &&
// trangThaiHopDong.equals(TrangThaiHopDong.CAN_PHE_DUYET)) {
// isCanPheDuyet = true;
// sqlRoot += "inner join a.hopDongPheDuyetEntityList hdpd ";
// sqlRoot += "left join hdpd.hopDongPheDuyetNguoiDungEntityList hdpd_nd ";
// sqlRoot += firstCondition ? "where " : "and ";
// sqlRoot += "a.trangThai = 1 and hdpd_nd.nguoiDungId = :nguoiPheDuyetId "; //
// CHO_PHE_DUYET
// firstCondition = false;
// }

// if (!ObjectUtils.isEmpty(maTram)) {
// sqlRoot += firstCondition ? "where " : "and ";
// sqlRoot += "b.maTram = :maTram ";
// firstCondition = false;
// }
// if (!ObjectUtils.isEmpty(phongDaiId)) {
// sqlRoot += firstCondition ? "where " : "and ";
// sqlRoot += "b.phongDaiId = :phongDaiId ";
// firstCondition = false;
// }
// if (!ObjectUtils.isEmpty(ids)) {
// sqlRoot += firstCondition ? "where " : "and ";
// sqlRoot += "b.phongDaiId in :ids ";
// firstCondition = false;
// }
// if (!ObjectUtils.isEmpty(search)) {
// sqlRoot += firstCondition ? "where " : "and ";
// sqlRoot += "b.maTram like %:search% or b.maTramErp like %:search% or
// b.maDauTuXayDung like %:search% or a.soHopDong like %:search% or
// a.soHopDongErp like %:search% ";
// firstCondition = false;
// }
// if (!ObjectUtils.isEmpty(soHopDong)) {
// sqlRoot += firstCondition ? "where " : "and ";
// sqlRoot += "a.soHopDong like %:soHopDong%) ";
// firstCondition = false;
// }
// if (!ObjectUtils.isEmpty(soHopDongErp)) {
// sqlRoot += firstCondition ? "where " : "and ";
// sqlRoot += "a.soHopDongErp like %:soHopDongErp% ";
// firstCondition = false;
// }
// if (!ObjectUtils.isEmpty(hinhThucDauTu)) {
// sqlRoot += firstCondition ? "where " : "and ";
// sqlRoot += "d.id = :hinhThucDauTu ";
// firstCondition = false;
// }
// if (!ObjectUtils.isEmpty(hinhThucKyHopDongId)) {
// sqlRoot += firstCondition ? "where " : "and ";
// sqlRoot += "c.id = :hinhThucKyHopDongId ";
// firstCondition = false;
// }
// if (!ObjectUtils.isEmpty(doiTuongKyHopDongId)) {
// sqlRoot += firstCondition ? "where " : "and ";
// sqlRoot += "e.id = :doiTuongKyHopDongId ";
// firstCondition = false;
// }
// if (!ObjectUtils.isEmpty(trangThaiHopDong) && !isCanPheDuyet) {
// sqlRoot += firstCondition ? "where " : "and ";
// firstCondition = false;
// if (trangThaiHopDong.equals(TrangThaiHopDong.HOAT_DONG)) {
// // get hop dong hoat_dong hoặc đang được phê duyệt
// sqlRoot += "a.trangThai = :trangThaiHopDong or exists (select 1 from
// HopDongPheDuyetEntity hopDongPheDuyet where hopDongPheDuyet.hopDongId = a.id
// and hopDongPheDuyet.trangThaiPheDuyet = 1) ";
// } else {
// // can_phe_duyet have above inner join
// sqlRoot += "a.trangThai = :trangThaiHopDong ";
// }
// }
// if (!ObjectUtils.isEmpty(ngayKyFrom) && !ObjectUtils.isEmpty(ngayKyTo)) {
// sqlRoot += firstCondition ? "where " : "and ";
// sqlRoot += "a.ngayKy >= :ngayKyFrom and a.ngayKy <= :ngayKyTo ";
// firstCondition = false;
// }
// if (!ObjectUtils.isEmpty(ngayKetThucFrom) &&
// !ObjectUtils.isEmpty(ngayKetThucTo)) {
// sqlRoot += firstCondition ? "where " : "and ";
// sqlRoot += "a.ngayKetThuc >= :ngayKetThucFrom and a.ngayKetThuc <=
// :ngayKetThucTo ";
// firstCondition = false;
// }
// if (!ObjectUtils.isEmpty(trangThaiThanhToan)) {
// sqlRoot += firstCondition ? "where " : "and ";
// sqlRoot += ":trangThaiThanhToan = 'CHUA_THANH_TOAN' and exists (select 1 from
// HopDongKyThanhToanEntity s where s.hopDongEntity = a and s.daThanhToanNgay is
// null) ";
// firstCondition = false;
// }
// if (!ObjectUtils.isEmpty(tinhTrangThanhToan) &&
// !ObjectUtils.isEmpty(ngayHienTai)
// && !ObjectUtils.isEmpty(ngayTuongLai)) {
// sqlRoot += firstCondition ? "where " : "and ";
// firstCondition = false;
// sqlRoot += "((:tinhTrangThanhToan = 'HET_HAN' and exists (select 1 from
// HopDongKyThanhToanEntity s where s.hopDongEntity = a and s.daThanhToanNgay is
// null and :ngayHienTai > s.denNgay)) ";
// sqlRoot += "or (:tinhTrangThanhToan = 'CAN_THANH_TOAN' and not exists (select
// 1 from HopDongKyThanhToanEntity s where s.hopDongEntity = a and
// s.daThanhToanNgay is null and :ngayHienTai > s.denNgay) and exists (select 1
// from HopDongKyThanhToanEntity s where s.hopDongEntity = a and
// s.daThanhToanNgay is null and s.tuNgay <= :ngayHienTai and s.denNgay >=
// :ngayHienTai)) ";
// sqlRoot += "or (:tinhTrangThanhToan = 'SAP_DEN' and not exists (select 1 from
// HopDongKyThanhToanEntity s where s.hopDongEntity = a and s.daThanhToanNgay is
// null and :ngayHienTai > s.denNgay) and not exists (select 1 from
// HopDongKyThanhToanEntity s where s.hopDongEntity = a and s.daThanhToanNgay is
// null and s.tuNgay <= :ngayHienTai and s.denNgay >= :ngayHienTai) and exists
// (select 1 from HopDongKyThanhToanEntity s where s.hopDongEntity = a and
// s.daThanhToanNgay is null and s.tuNgay > :ngayHienTai and s.tuNgay <=
// :ngayTuongLai))) ";
// }

// sqlRoot += "order by a.createdAt ";

// String sqlQuery = "select distinct a " + sqlRoot;
// String sqlCount = "select count(distinct a) " + sqlRoot;

// int firstResult = page * size;

// TypedQuery<HopDongEntity> queryList = entityManager.createQuery(sqlQuery,
// HopDongEntity.class);
// if (!"CHUA_THANH_TOAN".equals(trangThaiThanhToan)) {
// queryList.setFirstResult(firstResult);
// queryList.setMaxResults(size);
// }

// EntityGraph entityGraph =
// entityManager.getEntityGraph("hopdong-entity-graph");
// TypedQuery<Long> queryCount = entityManager.createQuery(sqlCount,
// Long.class);

// if (!ObjectUtils.isEmpty(maTram)) {
// queryList.setParameter("maTram", maTram);
// queryCount.setParameter("maTram", maTram);
// }
// if (!ObjectUtils.isEmpty(phongDaiId)) {
// queryList.setParameter("phongDaiId", phongDaiId);
// queryCount.setParameter("phongDaiId", phongDaiId);
// }
// if (!ObjectUtils.isEmpty(ids)) {
// queryList.setParameter("ids", ids);
// queryCount.setParameter("ids", ids);
// }
// if (!ObjectUtils.isEmpty(search)) {
// queryList.setParameter("search", search);
// queryCount.setParameter("search", search);
// }
// if (!ObjectUtils.isEmpty(soHopDong)) {
// queryList.setParameter("soHopDong", soHopDong);
// queryCount.setParameter("soHopDong", soHopDong);
// }
// if (!ObjectUtils.isEmpty(soHopDongErp)) {
// queryList.setParameter("soHopDongErp", soHopDongErp);
// queryCount.setParameter("soHopDongErp", soHopDongErp);
// }
// if (!ObjectUtils.isEmpty(hinhThucDauTu)) {
// queryList.setParameter("hinhThucDauTu", hinhThucDauTu);
// queryCount.setParameter("hinhThucDauTu", hinhThucDauTu);
// }
// if (!ObjectUtils.isEmpty(hinhThucKyHopDongId)) {
// queryList.setParameter("hinhThucKyHopDongId", hinhThucKyHopDongId);
// queryCount.setParameter("hinhThucKyHopDongId", hinhThucKyHopDongId);
// }
// if (!ObjectUtils.isEmpty(doiTuongKyHopDongId)) {
// queryList.setParameter("doiTuongKyHopDongId", doiTuongKyHopDongId);
// queryCount.setParameter("doiTuongKyHopDongId", doiTuongKyHopDongId);
// }
// if (isCanPheDuyet) {
// queryList.setParameter("nguoiPheDuyetId", nguoiDungEntity.getId());
// queryCount.setParameter("nguoiPheDuyetId", nguoiDungEntity.getId());
// } else if (!ObjectUtils.isEmpty(trangThaiHopDong)) {
// queryList.setParameter("trangThaiHopDong", trangThaiHopDong);
// queryCount.setParameter("trangThaiHopDong", trangThaiHopDong);
// }
// if (!ObjectUtils.isEmpty(ngayKyFrom) && !ObjectUtils.isEmpty(ngayKyTo)) {
// queryList.setParameter("ngayKyFrom", ngayKyFrom);
// queryCount.setParameter("ngayKyFrom", ngayKyFrom);
// queryList.setParameter("ngayKyTo", ngayKyTo);
// queryCount.setParameter("ngayKyTo", ngayKyTo);
// }
// if (!ObjectUtils.isEmpty(ngayKetThucFrom) &&
// !ObjectUtils.isEmpty(ngayKetThucTo)) {
// queryList.setParameter("ngayKetThucFrom", ngayKetThucFrom);
// queryCount.setParameter("ngayKetThucFrom", ngayKetThucFrom);
// queryList.setParameter("ngayKetThucTo", ngayKetThucTo);
// queryCount.setParameter("ngayKetThucTo", ngayKetThucTo);
// }
// if (!ObjectUtils.isEmpty(trangThaiThanhToan)) {
// queryList.setParameter("trangThaiThanhToan", trangThaiThanhToan);
// queryCount.setParameter("trangThaiThanhToan", trangThaiThanhToan);
// }
// if (!ObjectUtils.isEmpty(tinhTrangThanhToan) &&
// !ObjectUtils.isEmpty(ngayHienTai)
// && !ObjectUtils.isEmpty(ngayTuongLai)) {
// queryList.setParameter("tinhTrangThanhToan", tinhTrangThanhToan);
// queryCount.setParameter("tinhTrangThanhToan", tinhTrangThanhToan);

// queryList.setParameter("ngayHienTai", ngayHienTai);
// queryCount.setParameter("ngayHienTai", ngayHienTai);

// queryList.setParameter("ngayTuongLai", ngayTuongLai);
// queryCount.setParameter("ngayTuongLai", ngayTuongLai);
// }

// ExecutorService executorService = Executors.newFixedThreadPool(2);

// CompletableFuture<List<HopDongEntity>> futureQuery = null;
// CompletableFuture<Long> futureCount = null;

// try {
// futureQuery = CompletableFuture.supplyAsync(() -> queryList.getResultList(),
// executorService);
// futureCount = CompletableFuture.supplyAsync(() ->
// queryCount.getSingleResult(), executorService);
// } catch (Exception e) {
// System.out.println(e);
// }

// // Wait for all CompletableFuture instances to complete
// // CompletableFuture<Void> allFutures = CompletableFuture.allOf(futureQuery,
// // futureCount);
// // allFutures.join();

// QueryResultModel<HopDongEntity> resultModel = new QueryResultModel<>();
// resultModel.setPage(page);
// resultModel.setSize(size);
// resultModel.setContent(futureQuery != null ? futureQuery.get() : null);
// resultModel.setTotalElements(futureCount != null ? futureCount.get() : null);
// return resultModel;
// }

// private HopDongEntity convertModelToEntity(HopDongEntity entity, HopDongModel
// model) {
// entity.setTramId(model.getTramId());
// entity.setSoHopDong(model.getSoHopDong());
// entity.setSoHopDongErp(model.getSoHopDongErp());
// entity.setHinhThucKyId(model.getHinhThucKyId());
// entity.setHinhThucDauTuId(model.getHinhThucDauTuId());
// entity.setDoiTuongKyId(model.getDoiTuongKyId());
// entity.setCoKyQuy(model.getCoKyQuy());
// entity.setNgayKy(model.getNgayKy());
// entity.setNgayKetThuc(model.getNgayKetThuc());
// entity.setGhiChu(model.getGhiChu());
// entity.setGiaThue(model.getGiaThue());
// entity.setThueVat(model.getThueVat());
// entity.setHinhThucThanhToanId(model.getHinhThucThanhToanId());
// entity.setChuKyNam(model.getChuKyNam());
// entity.setChuKyThang(model.getChuKyThang());
// entity.setChuKyNgay(model.getChuKyNgay());
// entity.setTrangThai(model.getTrangThai());
// entity.setNgayBatDauYeuCauThanhToan(model.getNgayBatDauYeuCauThanhToan());
// entity.setGiaDienKhoan(model.getGiaDienKhoan());
// entity.setLoaiPhongMayPhatDienId(model.getLoaiPhongMayPhatDienId());
// entity.setLoaiTramVHKTId(model.getLoaiTramVHKTId());
// entity.setLoaiPhongMayId(model.getLoaiPhongMayId());

// return entity;
// }

// private void exportTableDeNghiChiHo(XSSFWorkbook workbook, XSSFSheet sheet,
// List<HopDongModel> listHopDong, DmPhongDaiModel phongDai,
// Date ngayLap, Date tuNgay, Date denNgay) throws ParseException {
// sheet.shiftRows(10, 15, listHopDong.size());
// // Tên phòng đài
// sheet.getRow(1).getCell(1).setCellValue(sheet.getRow(1).getCell(1).getStringCellValue().replace("{phongDaiTen}",
// phongDai.getTen().toUpperCase()));
// // Tên viết tắt
// sheet.getRow(2).getCell(1).setCellValue(
// sheet.getRow(2).getCell(1).getStringCellValue().replace("{phongDaiTVT}",
// phongDai.getTenVietTat()));
// // Ngày lặp bảng kê
// if (ngayLap == null) {
// ngayLap = new Date();
// }
// String ngayLapBang = sheet.getRow(2).getCell(6).getStringCellValue();
// sheet.getRow(2).getCell(6).setCellValue(ngayLapBang.replace("{dd}",
// DateUtil.formatDate(ngayLap, "dd")));
// ngayLapBang = sheet.getRow(2).getCell(6).getStringCellValue();
// sheet.getRow(2).getCell(6).setCellValue(ngayLapBang.replace("{MM}",
// DateUtil.formatDate(ngayLap, "MM")));
// ngayLapBang = sheet.getRow(2).getCell(6).getStringCellValue();
// sheet.getRow(2).getCell(6).setCellValue(ngayLapBang.replace("{yyyy}",
// DateUtil.formatDate(ngayLap, "yyyy")));
// // Từ ngày đến ngày
// String tuNgayDenNgay = sheet.getRow(5).getCell(0).getStringCellValue();
// if (tuNgay != null && denNgay != null) {
// sheet.getRow(5).getCell(0)
// .setCellValue(tuNgayDenNgay.replace("{tuNgay}", DateUtil.formatDate(tuNgay,
// "dd/MM/yyyy")));
// tuNgayDenNgay = sheet.getRow(5).getCell(0).getStringCellValue();
// sheet.getRow(5).getCell(0)
// .setCellValue(tuNgayDenNgay.replace("{denNgay}", DateUtil.formatDate(denNgay,
// "dd/MM/yyyy")));
// }
// if (tuNgay != null && denNgay == null) {
// sheet.getRow(5).getCell(0)
// .setCellValue(tuNgayDenNgay.replace("{tuNgay}", DateUtil.formatDate(tuNgay,
// "dd/MM/yyyy")));
// tuNgayDenNgay = sheet.getRow(5).getCell(0).getStringCellValue();
// sheet.getRow(5).getCell(0).setCellValue(tuNgayDenNgay.replace("{denNgay}",
// ""));
// }
// if (tuNgay == null && denNgay != null) {
// sheet.getRow(5).getCell(0).setCellValue(tuNgayDenNgay.replace("{tuNgay}",
// ""));
// tuNgayDenNgay = sheet.getRow(5).getCell(0).getStringCellValue();
// sheet.getRow(5).getCell(0)
// .setCellValue(tuNgayDenNgay.replace("{denNgay}", DateUtil.formatDate(denNgay,
// "dd/MM/yyyy")));
// }
// if (tuNgay == null && denNgay == null) {
// sheet.getRow(5).getCell(0).setCellValue(tuNgayDenNgay.replace("{tuNgay}",
// ""));
// tuNgayDenNgay = sheet.getRow(5).getCell(0).getStringCellValue();
// sheet.getRow(5).getCell(0).setCellValue(tuNgayDenNgay.replace("{denNgay}",
// ""));
// }
// // Có hiệu lực đến ngày
// String coHieuLucDenNgay = sheet.getRow(6).getCell(0).getStringCellValue();
// if (denNgay != null) {
// sheet.getRow(6).getCell(0).setCellValue(
// coHieuLucDenNgay.replace("{coHieuLucDenNgay}", DateUtil.formatDate(denNgay,
// "dd/MM/yyyy")));
// } else {
// sheet.getRow(6).getCell(0).setCellValue(coHieuLucDenNgay.replace("{coHieuLucDenNgay}",
// ""));
// }

// double totalTien = 0;
// for (int i = 0; i < listHopDong.size(); i++) {
// XSSFRow row = sheet.createRow(i + 10);
// XSSFCellStyle cellStyle = workbook.createCellStyle();
// cellStyle.setWrapText(true);
// row.createCell(0).setCellValue(i + 1);
// row.createCell(1).setCellValue(listHopDong.get(i).getSoHopDong());
// row.createCell(2).setCellValue(listHopDong.get(i).getTram().getMaTram());
// row.createCell(3).setCellValue(listHopDong.get(i).getTram().getTen());
// row.createCell(4).setCellValue(listHopDong.get(i).getTram().getDiaChi());
// row.createCell(5).setCellValue(listHopDong.get(i).getHopDongDoiTac().getTen());
// // Kỳ thanh toán
// HopDongKyThanhToanModel kyThanhToan = HopDongKyThanhToanModel
// .fromEntity(hopDongKyThanhToanService.findKyCanThanhToan(listHopDong.get(i).getId()),
// true);
// row.createCell(6).setCellValue(DateUtil.formatDate(kyThanhToan.getTuNgay(),
// "dd/MM/yyyy") + " - "
// + DateUtil.formatDate(kyThanhToan.getDenNgay(), "dd/MM/yyyy"));
// // Lý do chi
// row.createCell(7).setCellValue("MB");
// // Số tiền (VNĐ)
// row.createCell(8).setCellValue(kyThanhToan.getTien());
// totalTien += kyThanhToan.getTien();
// // Thông tin người nhận tiền
// StringBuilder nguoiNhanTien = new
// StringBuilder(listHopDong.get(i).getHopDongDoiTac().getTen());
// nguoiNhanTien.append(" số tài khoản " +
// listHopDong.get(i).getHopDongDoiTac().getSoTaiKhoan());
// nguoiNhanTien.append(" tại " +
// listHopDong.get(i).getHopDongDoiTac().getNganHangChiNhanh());
// row.createCell(9).setCellValue(nguoiNhanTien.toString());
// // Ghi chú
// row.createCell(10).setCellValue(listHopDong.get(i).getGhiChu());
// // Mã hợp đồng
// row.createCell(11).setCellValue("");
// // Mã trạm
// row.createCell(12).setCellValue(listHopDong.get(i).getTram().getMaTram());
// for (int j = 0; j <= 12; j++) {
// row.getCell(j).setCellStyle(ExcelUtil.setBorder(cellStyle));
// }
// }
// DecimalFormat decimalFormat = new DecimalFormat("#");
// String formattedTotalTien = decimalFormat.format(totalTien);
// if (formattedTotalTien.contains(".")) {
// formattedTotalTien = formattedTotalTien.substring(0,
// formattedTotalTien.indexOf("."));
// }
// // Total tiền
// sheet.getRow(10 +
// listHopDong.size()).getCell(8).setCellValue(Double.parseDouble(formattedTotalTien));
// // Total tiền bằng chữ
// String totalText = sheet.getRow(11 +
// listHopDong.size()).getCell(1).getStringCellValue();
// sheet.getRow(11 + listHopDong.size()).getCell(1).setCellValue(
// totalText.replace("{totalText}",
// StringUtil.convertMoneyFromNumberToText(formattedTotalTien)));
// sheet.getRow(13 + listHopDong.size()).getCell(3).setCellValue(sheet.getRow(13
// + listHopDong.size()).getCell(3)
// .getStringCellValue().replace("{phongDaiTen}", phongDai.getTen()));
// }

// private Boolean checkTram(TramModel tramModel) {
// Boolean isValid = true;

// if (ObjectUtils.isEmpty(tramModel)) {
// isValid = false;
// }

// if (!kiemTraQuyenModuleTram.kiemTraQuyenImport(tramModel)) {
// isValid = false;
// }

// if (ObjectUtils.isEmpty(tramModel.getMaTram())) {
// tramError.put("maTram", "null");
// isValid = false;
// }

// try {
// if (ObjectUtils.isEmpty(tramModel.getTinhId())
// || ObjectUtils.isEmpty(dmTinhService.findById(tramModel.getTinhId()))) {
// tramError.put("tinhId", tramModel.getTinhId() != null ? tramModel.getTinhId()
// : "null");
// isValid = false;
// }
// } catch (Exception e) {
// System.out.println(e);
// tramError.put("tinhId", tramModel.getTinhId() != null ? tramModel.getTinhId()
// : "null");
// isValid = false;
// }
// try {
// if (ObjectUtils.isEmpty(tramModel.getPhongDaiId())
// ||
// ObjectUtils.isEmpty(dmPhongDaiService.findById(tramModel.getPhongDaiId()))) {
// tramError.put("phongDaiId", tramModel.getPhongDaiId() != null ?
// tramModel.getPhongDaiId() : "null");
// isValid = false;
// }
// } catch (Exception e) {
// System.out.println(e);
// tramError.put("phongDaiId", tramModel.getPhongDaiId() != null ?
// tramModel.getPhongDaiId() : "null");
// isValid = false;
// }

// // set null
// try {
// if (ObjectUtils.isEmpty(tramModel.getHuyenId())
// || ObjectUtils.isEmpty(dmHuyenService.findById(tramModel.getHuyenId()))) {
// tramModel.setHuyenId(null);
// }
// } catch (Exception e) {
// tramModel.setHuyenId(null);
// System.out.println(e);
// }
// try {
// if (ObjectUtils.isEmpty(tramModel.getXaId())
// || ObjectUtils.isEmpty(dmXaService.findById(tramModel.getXaId()))) {
// tramModel.setXaId(null);
// }
// } catch (Exception e) {
// tramModel.setXaId(null);
// System.out.println(e);
// }
// try {
// if (ObjectUtils.isEmpty(tramModel.getToId())
// || ObjectUtils.isEmpty(dmToService.findById(tramModel.getToId()))) {
// tramModel.setToId(null);
// }
// } catch (Exception e) {
// tramModel.setToId(null);
// System.out.println(e);
// }

// try {
// if (ObjectUtils.isEmpty(tramModel.getKhuVucId())
// ||
// ObjectUtils.isEmpty(dmTramKhuVucService.findById(tramModel.getKhuVucId()))) {
// tramModel.setKhuVucId(null);
// }
// } catch (Exception e) {
// tramModel.setKhuVucId(null);
// System.out.println(e);
// }

// try {
// if (ObjectUtils.isEmpty(tramModel.getLoaiCshtId())
// ||
// ObjectUtils.isEmpty(dmLoaiCshtService.findById(tramModel.getLoaiCshtId()))) {
// tramModel.setLoaiCshtId(null);
// }
// } catch (Exception e) {
// tramModel.setLoaiCshtId(null);
// System.out.println(e);
// }

// try {
// if (ObjectUtils.isEmpty(tramModel.getLoaiTramId())
// ||
// ObjectUtils.isEmpty(dmLoaiTramService.findById(tramModel.getLoaiTramId()))) {
// tramModel.setLoaiTramId(null);
// }
// } catch (Exception e) {
// tramModel.setLoaiTramId(null);
// System.out.println(e);
// }

// try {
// if (ObjectUtils.isEmpty(tramModel.getLoaiCotAngtenId())
// ||
// ObjectUtils.isEmpty(dmLoaiCotAngtenService.findById(tramModel.getLoaiCotAngtenId())))
// {
// tramModel.setLoaiCotAngtenId(null);
// }
// } catch (Exception e) {
// tramModel.setLoaiCotAngtenId(null);
// System.out.println(e);
// }

// return isValid;
// }

// private Boolean checkHopDong(HopDongModel hopDongModel) {
// Map<String, Object> hopDongDoiTacError = new HashMap<>();
// Boolean isValid = true;

// if (ObjectUtils.isEmpty(hopDongModel)) {
// isValid = false;
// }
// if (!kiemTraQuyenModuleHopDong.kiemTraQuyenImport(hopDongModel)) {
// isValid = false;
// }

// TramModel tramModel = hopDongModel.getTram();
// List<HopDongPhuTroModel> hopDongPhuTroModelList =
// hopDongModel.getHopDongPhuTroList();

// // hop dong doi tac
// HopDongDoiTacModel hopDongDoiTacModel = hopDongModel.getHopDongDoiTac();
// if (ObjectUtils.isEmpty(hopDongDoiTacModel)) {
// isValid = false;
// }
// if (!ObjectUtils.isEmpty(hopDongDoiTacModel) &&
// ObjectUtils.isEmpty(hopDongDoiTacModel.getTen())) {
// hopDongDoiTacError.put("ten", "null");
// isValid = false;
// }
// if (!ObjectUtils.isEmpty(hopDongDoiTacModel) &&
// ObjectUtils.isEmpty(hopDongDoiTacModel.getDiaChi())) {
// hopDongDoiTacError.put("diaChi", "null");
// isValid = false;
// }

// // hop dong
// try {
// if (ObjectUtils.isEmpty(tramService.findById(hopDongModel.getTramId()))
// || ObjectUtils.isEmpty(tramService.findByMaTram(tramModel.getMaTram()))) {
// hopDongError.put("tramId", hopDongModel.getTramId() != null ?
// hopDongModel.getTramId() : "null");
// isValid = false;
// }
// } catch (Exception e) {
// System.out.println(e);
// hopDongError.put("tramId", hopDongModel.getTramId() != null ?
// hopDongModel.getTramId() : "null");
// isValid = false;
// }

// if (ObjectUtils.isEmpty(hopDongModel.getSoHopDong())) {
// hopDongError.put("soHopDong", "null");
// isValid = false;
// }

// if (ObjectUtils.isEmpty(hopDongModel.getNgayKy())) {
// hopDongError.put("ngayKy", "null");
// isValid = false;
// }

// if (ObjectUtils.isEmpty(hopDongModel.getNgayKetThuc())) {
// hopDongError.put("ngayKetThuc", "null");
// isValid = false;
// }
// if (ObjectUtils.isEmpty(hopDongModel.getGiaThue())) {
// hopDongError.put("giaThue", "null");
// isValid = false;
// }
// if (ObjectUtils.isEmpty(hopDongModel.getChuKyNam())) {
// hopDongError.put("chuKyNam", "null");
// isValid = false;
// }
// if (ObjectUtils.isEmpty(hopDongModel.getChuKyThang())) {
// hopDongError.put("chuKyThang", "null");
// isValid = false;
// }
// if (ObjectUtils.isEmpty(hopDongModel.getChuKyNgay())) {
// hopDongError.put("chuKyNgay", "null");
// isValid = false;
// }
// if (ObjectUtils.isEmpty(hopDongModel.getNgayBatDauYeuCauThanhToan())) {
// hopDongError.put("ngayBatDauYeuCauThanhToan", "null");
// isValid = false;
// }

// // set null
// try {
// if (ObjectUtils.isEmpty(hopDongModel.getHinhThucKyId())
// ||
// ObjectUtils.isEmpty(dmHinhThucKyHopDongService.findById(hopDongModel.getHinhThucKyId())))
// {
// hopDongModel.setHinhThucKyId(null);
// }
// } catch (Exception e) {
// hopDongModel.setHinhThucKyId(null);
// System.out.println(e);
// }

// try {
// if (ObjectUtils.isEmpty(hopDongModel.getHinhThucDauTuId())
// ||
// ObjectUtils.isEmpty(dmHinhThucDauTuService.findById(hopDongModel.getHinhThucDauTuId())))
// {
// hopDongModel.setHinhThucDauTuId(null);
// }
// } catch (Exception e) {
// hopDongModel.setHinhThucDauTuId(null);
// System.out.println(e);
// }

// try {
// if (ObjectUtils.isEmpty(hopDongModel.getDoiTuongKyId())
// ||
// ObjectUtils.isEmpty(dmDoiTuongKyHopDongService.findById(hopDongModel.getDoiTuongKyId())))
// {
// hopDongModel.setDoiTuongKyId(null);
// }
// } catch (Exception e) {
// hopDongModel.setDoiTuongKyId(null);
// System.out.println(e);
// }

// try {
// if (ObjectUtils.isEmpty(hopDongModel.getHinhThucThanhToanId()) || ObjectUtils
// .isEmpty(dmHinhThucThanhToanService.findById(hopDongModel.getHinhThucThanhToanId())))
// {
// hopDongModel.setHinhThucThanhToanId(null);
// }
// } catch (Exception e) {
// hopDongModel.setHinhThucThanhToanId(null);
// System.out.println(e);
// }

// try {
// if (!ObjectUtils.isEmpty(hopDongPhuTroModelList)) {
// List<HopDongPhuTroModel> listInsert = new ArrayList<>();
// hopDongPhuTroModelList.forEach(hopDongPhuTroModel -> {
// try {
// DmLoaiHopDongPhuTroEntity dmLoaiHopDongPhuTroEntity =
// dmLoaiHopDongPhuTroService
// .findById(hopDongPhuTroModel.getDmPhuTroId());
// if (!ObjectUtils.isEmpty(dmLoaiHopDongPhuTroEntity)) {
// hopDongPhuTroModel.setGia(dmLoaiHopDongPhuTroEntity.getGia());
// listInsert.add(hopDongPhuTroModel);
// }
// } catch (Exception e) {
// System.out.println(e);
// }
// });
// hopDongModel.setHopDongPhuTroList(listInsert);
// }
// } catch (Exception e) {
// System.out.println(e);
// }

// hopDongError.put("hopDongDoiTac", hopDongDoiTacError);

// return isValid;
// }

// private void exportTableOfBangKeKhaiThanhToanTrongKy(XSSFWorkbook workbook,
// XSSFSheet sheet,
// List<HopDongModel> listHopDong, DmPhongDaiModel phongDaiModel,
// Date kyThanhToan) throws ParseException {
// // Tên phòng đài
// sheet.getRow(1).getCell(0).setCellValue(phongDaiModel.getTen().toUpperCase());
// // Kỳ thanh toán tháng
// if (kyThanhToan != null) {
// String kyThanhToanThang = DateUtil.formatDate(kyThanhToan, "MM") + "/"
// + DateUtil.formatDate(kyThanhToan, "yyyy");
// sheet.getRow(2).getCell(0).setCellValue(
// sheet.getRow(2).getCell(0).getStringCellValue().replace("{MM/yyyy}",
// kyThanhToanThang));
// } else {
// sheet.getRow(2).getCell(0)
// .setCellValue(sheet.getRow(2).getCell(0).getStringCellValue().replace("{MM/yyyy}",
// ""));
// }
// for (int i = 0; i < listHopDong.size(); i++) {
// XSSFRow row = sheet.createRow(i + 6);
// XSSFCellStyle cellStyle = workbook.createCellStyle();
// cellStyle.setWrapText(true);
// row.createCell(0).setCellValue(i + 1);
// row.createCell(1).setCellValue(listHopDong.get(i).getSoHopDong());
// row.createCell(2).setCellValue(listHopDong.get(i).getSoHopDongErp());
// row.createCell(3).setCellValue(DateUtil.formatDate(listHopDong.get(i).getNgayKetThuc(),
// "dd/MM/yyyy"));
// row.createCell(4).setCellValue(listHopDong.get(i).getHopDongDoiTac().getTen());
// row.createCell(5).setCellValue(listHopDong.get(i).getTram().getMaTram());
// String maTramErp = listHopDong.get(i).getTram().getMaTramErp();
// row.createCell(6).setCellValue(maTramErp);
// row.createCell(7).setCellValue(listHopDong.get(i).getTram().getSiteNameErp());
// HopDongKyThanhToanModel kyThanhToanModel = HopDongKyThanhToanModel
// .fromEntity(hopDongKyThanhToanService.findKyCanThanhToan(listHopDong.get(i).getId()),
// true);
// if (kyThanhToanModel != null) {
// row.createCell(8).setCellValue(DateUtil.formatDate(kyThanhToanModel.getTuNgay(),
// "dd/MM/yyyy"));
// row.createCell(9).setCellValue(DateUtil.formatDate(kyThanhToanModel.getDenNgay(),
// "dd/MM/yyyy"));
// }
// row.createCell(10).setCellValue(listHopDong.get(i).getGiaThue());
// row.createCell(11).setCellValue("");
// row.createCell(12).setCellValue("");
// if (kyThanhToanModel.getSoTienThanhToan() != null) {
// row.createCell(13).setCellValue(kyThanhToanModel.getSoTienThanhToan());
// } else {
// row.createCell(13);
// }
// String noiDungChi = "MB trạm " + listHopDong.get(i).getTram().getMaTram() +
// "/"
// + (maTramErp != null ? maTramErp : "") + "-"
// + DateUtil.formatDate(kyThanhToanModel.getTuNgay(), "dd/MM/yyyy") + " đến "
// + DateUtil.formatDate(kyThanhToanModel.getDenNgay(), "dd/MM/yyyy");
// row.createCell(14).setCellValue(noiDungChi);
// row.createCell(15).setCellValue(listHopDong.get(i).getHopDongDoiTac().getTen());
// row.createCell(16).setCellValue(listHopDong.get(i).getHopDongDoiTac().getSoTaiKhoan());
// row.createCell(17).setCellValue(listHopDong.get(i).getHopDongDoiTac().getNganHangChiNhanh());
// row.createCell(18).setCellValue("");
// for (int j = 0; j <= 18; j++) {
// row.getCell(j).setCellStyle(ExcelUtil.setBorder(cellStyle));
// }
// }
// }

// private HopDongModel prepareObject(HopDongModel hopDongModel) {
// hopDongModel.setId(0L);

// if (hopDongModel.getNgayKy() != null) {
// try {
// Timestamp stamp = new Timestamp(hopDongModel.getNgayKy().getTime());
// Date date = new Date(stamp.getTime());
// hopDongModel.setNgayKy(date);
// } catch (Exception e) {
// }
// }

// if (hopDongModel.getNgayKetThuc() != null) {
// try {
// Timestamp stamp = new Timestamp(hopDongModel.getNgayKetThuc().getTime());
// Date date = new Date(stamp.getTime());
// hopDongModel.setNgayKetThuc(date);
// } catch (Exception e) {
// }
// }

// if (hopDongModel.getNgayBatDauYeuCauThanhToan() != null) {
// try {
// Timestamp stamp = new
// Timestamp(hopDongModel.getNgayBatDauYeuCauThanhToan().getTime());
// Date date = new Date(stamp.getTime());
// hopDongModel.setNgayBatDauYeuCauThanhToan(date);
// } catch (Exception e) {
// }
// }

// hopDongModel.setTram(null);
// hopDongModel.setDmHinhThucKyHopDong(null);
// hopDongModel.setDmHinhThucDauTu(null);
// hopDongModel.setDmDoiTuongKyHopDong(null);
// hopDongModel.setDmHinhThucThanhToan(null);
// hopDongModel.setDmLoaiPhongMayPhatDien(null);
// hopDongModel.setDmLoaiTramVHKT(null);
// hopDongModel.setDmLoaiPhongMay(null);

// return hopDongModel;
// }
// }