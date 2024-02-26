package com.contract.tram.tram.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.contract.authentication.component.KiemTraQuyenModuleTram;
import com.contract.common.exception.BadRequestException;
import com.contract.common.exception.DataExistsException;
import com.contract.common.exception.InvalidDataException;
import com.contract.common.exception.NotFoundException;
import com.contract.common.util.DateUtil;
import com.contract.common.util.ExcelUtil;
import com.contract.danhmuc.huyen.entity.DmHuyenEntity;
import com.contract.danhmuc.huyen.service.DmHuyenService;
import com.contract.danhmuc.loaicotangten.entity.DmLoaiCotAngtenEntity;
import com.contract.danhmuc.loaicotangten.service.DmLoaiCotAngtenService;
import com.contract.danhmuc.loaicsht.entity.DmLoaiCshtEntity;
import com.contract.danhmuc.loaicsht.service.DmLoaiCshtService;
import com.contract.danhmuc.loaithietbiran.entity.DmLoaiThietBiRanEntity;
import com.contract.danhmuc.loaithietbiran.model.DmLoaiThietBiRanModel;
import com.contract.danhmuc.loaithietbiran.service.DmLoaiThietBiRanService;
import com.contract.danhmuc.loaitram.entity.DmLoaiTramEntity;
import com.contract.danhmuc.loaitram.service.DmLoaiTramService;
import com.contract.danhmuc.phongdai.entity.DmPhongDaiEntity;
import com.contract.danhmuc.phongdai.service.DmPhongDaiService;
import com.contract.danhmuc.tinh.entity.DmTinhEntity;
import com.contract.danhmuc.tinh.service.DmTinhService;
import com.contract.danhmuc.to.entity.DmToEntity;
import com.contract.danhmuc.to.service.DmToService;
import com.contract.danhmuc.tramkhuvuc.entity.DmTramKhuVucEntity;
import com.contract.danhmuc.tramkhuvuc.service.DmTramKhuVucService;
import com.contract.danhmuc.xa.entity.DmXaEntity;
import com.contract.danhmuc.xa.service.DmXaService;
import com.contract.hopdong.hopdong.model.HopDongModel;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.model.ChangeLogModel;
import com.contract.log.service.LogService;
import com.contract.log.service.TramLogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.tram.tram.entity.TramEntity;
import com.contract.tram.tram.model.TramModel;
import com.contract.tram.tram.model.TrangThaiTram;
import com.contract.tram.tram.repository.TramRepository;

@Service
public class TramService {

    @Autowired
    private TramRepository tramRepository;
    @Autowired
    private DmLoaiThietBiRanService dmLoaiThietBiRanService;
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private KiemTraQuyenModuleTram kiemTraQuyenModuleTram;
    @Autowired
    private LogService logService;
    @Autowired
    private DmPhongDaiService dmPhongDaiService;
    @Autowired
    private DmToService dmToService;
    @Autowired
    private DmTinhService dmTinhService;
    @Autowired
    private DmHuyenService dmHuyenService;
    @Autowired
    private DmXaService dmXaService;
    @Autowired
    private DmTramKhuVucService dmTramKhuVucService;
    @Autowired
    private DmLoaiCshtService dmLoaiCshtService;
    @Autowired
    private DmLoaiTramService dmLoaiTramService;
    @Autowired
    private DmLoaiCotAngtenService dmLoaiCotAngtenService;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private TramLogService tramLogService;

    public TramEntity findById(Long id) {
        return tramRepository.findById(id).orElse(null);
    }

    public List<TramEntity> findAllByIdIn(Set<Long> ids) {
        return tramRepository.findAllByIdIn(ids);
    }

    public TramEntity findByMaTram(String maTram) {
        List<TramEntity> tramEntity = tramRepository.findByMaTram(maTram);
        if (tramEntity != null && tramEntity.size() == 1) {
            return tramEntity.get(0);
        }
        return null;
    }

    public List<TramEntity> findByMaTramErp(String maTramErp) {
        List<TramEntity> lsTramEntity = tramRepository.findByMaTramErp(maTramErp);
        if (lsTramEntity != null) {
            return lsTramEntity;
        }
        return null;
    }

    public TramEntity findByMaDauTuXayDung(String maDTXD) {
        return tramRepository.findByMaDauTuXayDung(maDTXD);
    }

    public boolean checkExists(Long id, String maTram, String maTramErp) {
        try {
            List<TramEntity> tramEntityList = tramRepository.findAllByMaTramAndMaTramErp(id, maTram, maTramErp);
            if (ObjectUtils.isEmpty(tramEntityList)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public TramModel findChiTietTram(Long id) {
        TramEntity tramEntity = tramRepository.findByIdWithGraphDanhMuc(id);
        if (tramEntity == null) {
            throw new NotFoundException();
        }
        List<Integer> ids = null;
        if (!nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
            ids = kiemTraQuyenModuleTram.layDanhSachIdPhongDaiDuocQuyenXem();
        }
        if (ids != null && !ids.contains(tramEntity.getPhongDaiId())) {
            tramEntity = null;
        }
        if (tramEntity == null) {
            throw new NotFoundException();
        }

        TramModel tramModel = TramModel.fromEntity(tramEntity, true);

        List<DmLoaiThietBiRanModel> listThietBiRan = new ArrayList<>();
        if (!ObjectUtils.isEmpty(tramEntity.getDmLoaiThietBiRanEntityList())) {
            tramEntity.getDmLoaiThietBiRanEntityList().forEach(ran -> {
                listThietBiRan.add(DmLoaiThietBiRanModel.fromEntity(ran));
            });
        }
        tramModel.setDmLoaiThietBiRanList(listThietBiRan);

        return tramModel;
    }

    public Page<TramEntity> findAll(Integer responseType, String search, Integer tinh,
            Integer huyen, Integer xa, Integer phongDai, Integer to, TrangThaiTram trangThaiTram,
            Integer loaiCsht, Boolean trangThaiPhatSong, Integer limit, Integer offset) {
        Page<TramEntity> listTram = null;
        Pageable pageRequest = PageRequest.of(offset, limit, Sort.by("createdAt").descending());

        List<Integer> ids = kiemTraQuyenModuleTram.layDanhSachIdPhongDaiDuocQuyenXem();

        List<DmPhongDaiEntity> listPhong = dmPhongDaiService.findAllByLoai("Phòng");
        List<Integer> listIdPhong = listPhong.stream().map(e -> e.getId()).collect(Collectors.toList());
        boolean isUserInPhong = ids.stream().anyMatch(id -> listIdPhong.contains(id));

        if (nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin() || isUserInPhong) {
            if (responseType.equals(Integer.valueOf(1))) {
                listTram = tramRepository.adminFindAllByPhongDaiIdAndFetchAllWhereSearch(search, tinh, huyen,
                        xa, phongDai, to, trangThaiTram, loaiCsht, trangThaiPhatSong, pageRequest);
            } else {
                // Sort sort = Sort.by("createdAt").descending();
                pageRequest = PageRequest.of(0, Integer.MAX_VALUE, Sort.by("createdAt").descending());
                listTram = tramRepository.adminFindAllBySearch(search, phongDai, trangThaiTram, trangThaiPhatSong,
                        pageRequest);
            }
        } else {
            if (responseType.equals(Integer.valueOf(1))) {
                listTram = tramRepository.findAllByPhongDaiIdAndFetchAllWhereSearch(search, tinh, huyen,
                        xa, phongDai, to, trangThaiTram, loaiCsht, ids, trangThaiPhatSong, pageRequest);
            } else {
                // Sort sort = Sort.by("createdAt").descending();
                pageRequest = PageRequest.of(0, Integer.MAX_VALUE, Sort.by("createdAt").descending());
                listTram = tramRepository.findAllBySearch(search, phongDai, trangThaiTram, ids, trangThaiPhatSong,
                        pageRequest);
            }
        }

        return listTram;
    }

    public List<TramModel> convertListTramEntityToModel(Page<TramEntity> tramEntityPage) {
        List<TramModel> listReturn = new ArrayList<>();
        List<TramEntity> listTram = null;
        if (tramEntityPage != null) {
            listTram = tramEntityPage.getContent();
        }
        if (listTram != null) {
            listTram.forEach(tramEntity -> {
                TramModel tramModel = TramModel.fromEntity(tramEntity, true);
                List<DmLoaiThietBiRanModel> listThietBiRan = new ArrayList<>();
                if (!ObjectUtils.isEmpty(tramEntity.getDmLoaiThietBiRanEntityList())) {
                    tramEntity.getDmLoaiThietBiRanEntityList().forEach(ran -> {
                        listThietBiRan.add(DmLoaiThietBiRanModel.fromEntity(ran));
                    });
                }
                List<HopDongModel> listHopDong = new ArrayList<>();
                // if (!ObjectUtils.isEmpty(tramEntity.getHopDongEntityList())) {
                // tramEntity.getHopDongEntityList().forEach(hopDongEntity -> {
                // listHopDong.add(HopDongModel.fromEntity(hopDongEntity, true));
                // });
                // }
                tramModel.setDmLoaiThietBiRanList(listThietBiRan);
                // TODO add hop dong list to tram
                // tramModel.setHopDongList(listHopDong);
                listReturn.add(tramModel);
            });
        }
        return listReturn;
    }

    public TramModel create(TramModel data) {
        if (checkExists(null, data.getMaTram(), data.getMaTramErp())) {
            throw new DataExistsException();
        }
        TramEntity toSave = convertModelToEntity(new TramEntity(), data);
        List<DmLoaiThietBiRanEntity> dmLoaiThietBiRanEntityList = new ArrayList<>();
        if (data.getDmLoaiThietBiRanList() != null) {
            data.getDmLoaiThietBiRanList().forEach(e -> {
                DmLoaiThietBiRanEntity entity = dmLoaiThietBiRanService.findById(e.getId());
                if (!ObjectUtils.isEmpty(entity)) {
                    dmLoaiThietBiRanEntityList.add(entity);
                }
            });
        }
        toSave.setDmLoaiThietBiRanEntityList(
                new HashSet<>(dmLoaiThietBiRanEntityList != null ? dmLoaiThietBiRanEntityList : new HashSet<>()));
        TramModel saved = save(toSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            // keep current data of data
            saveLog(null, toSave, "create", nguoiDungId, LogActionEnum.CREATE);
        });

        return saved;
    }

    public TramModel update(TramModel data, Long id) throws Exception {
        TramEntity tramEntity = tramRepository.findByIdWithGraphDanhMuc(id);
        if (ObjectUtils.isEmpty(tramEntity)) {
            throw new NotFoundException();
        }
        TramEntity originalData = (TramEntity) tramEntity.clone();

        if (checkExists(id, data.getMaTram(), data.getMaTramErp())) {
            throw new DataExistsException();
        }
        TramEntity toSave = convertModelToEntity(tramEntity, data);
        List<DmLoaiThietBiRanEntity> dmLoaiThietBiRanEntityList = new ArrayList<>();
        if (data.getDmLoaiThietBiRanList() != null) {
            data.getDmLoaiThietBiRanList().forEach(e -> {
                DmLoaiThietBiRanEntity entity = dmLoaiThietBiRanService.findById(e.getId());
                if (!ObjectUtils.isEmpty(entity)) {
                    dmLoaiThietBiRanEntityList.add(entity);
                }
            });
        }
        toSave.setDmLoaiThietBiRanEntityList(
                new HashSet<>(dmLoaiThietBiRanEntityList != null && dmLoaiThietBiRanEntityList.size() > 0
                        ? dmLoaiThietBiRanEntityList
                        : new HashSet<>()));
        TramModel saved = save(toSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            // keep current data of data
            saveLog(originalData, toSave, "update", nguoiDungId, LogActionEnum.UPDATE);
        });
        return saved;
    }

    public TramModel delete(Long id) throws Exception {
        TramEntity tramEntity = findById(id);

        if (ObjectUtils.isEmpty(tramEntity)) {
            throw new NotFoundException();
        }

        tramEntity.setDeletedAt(new Date());

        // check data

        return save(tramEntity);
    }

    public TramModel save(TramEntity tramEntity) {
        try {
            TramEntity saved = tramRepository.save(tramEntity);
            return TramModel.fromEntity(saved, false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InvalidDataException();
        }
    }

    public TramEntity convertModelToEntity(TramEntity tramEntity, TramModel tramModel) {
        tramEntity.setPhongDaiId(tramModel.getPhongDaiId());
        tramEntity.setToId(tramModel.getToId());
        tramEntity.setMaTram(tramModel.getMaTram());
        tramEntity.setMaTramErp(tramModel.getMaTramErp());
        tramEntity.setTen(tramModel.getTen());
        tramEntity.setTinhId(tramModel.getTinhId());
        tramEntity.setHuyenId(tramModel.getHuyenId());
        tramEntity.setXaId(tramModel.getXaId());
        tramEntity.setDiaChi(tramModel.getDiaChi());
        tramEntity.setKinhDo(tramModel.getKinhDo());
        tramEntity.setViDo(tramModel.getViDo());
        tramEntity.setKhuVucId(tramModel.getKhuVucId());
        tramEntity.setNgayPhatSong(tramModel.getNgayPhatSong());
        tramEntity.setLoaiCshtId(tramModel.getLoaiCshtId());
        tramEntity.setLoaiTramId(tramModel.getLoaiTramId());
        tramEntity.setLoaiCotAngtenId(tramModel.getLoaiCotAngtenId());
        tramEntity.setDoCaoAngten(tramModel.getDoCaoAngten());
        tramEntity.setGhiChu(tramModel.getGhiChu());
        tramEntity.setTrangThaiHoatDong(tramModel.getTrangThaiHoatDong());
        tramEntity.setSiteNameErp(tramModel.getSiteNameErp());
        tramEntity.setMaDauTuXayDung(tramModel.getMaDauTuXayDung());
        if (tramModel.getDaPhatSong() == null) {
            tramEntity.setDaPhatSong(false);
        } else {
            tramEntity.setDaPhatSong(tramModel.getDaPhatSong());
        }

        return tramEntity;
    }

    /*
     * Export Trạm
     */
    public ResponseEntity<?> exportToExcel(LinkedHashMap<String, String> listCacTruongCanExport,
            List<String> listIdToExport) {
        // Nếu người dùng không chọn bất kỳ trường nào thì ném ra ngoại lệ
        if (listCacTruongCanExport.size() == 0) {
            throw new BadRequestException();
        }
        String tenFileExport = "Export_DanhSachTram_" + DateUtil.getThoiGianHienTai() + ".xlsx";
        String tenSheet = "DanhSachTram";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(tenSheet);
        XSSFSheet hsheet = workbook.createSheet("DanhMucHeThong");
        workbook.setSheetHidden(1, true);
        workbook.setActiveSheet(0);

        // Ghi tiêu đề (dòng đầu tiên)
        writeTitleToExcelExport(workbook, sheet, listCacTruongCanExport);

        // Đọc dữ liệu từ database lên
        StringBuilder sql = new StringBuilder("select ");
        for (Map.Entry<String, String> column : listCacTruongCanExport.entrySet()) {
            sql.append("t." + column.getKey() + ", ");
        }
        sql.append("from TramEntity t ");
        sql.append("where t.dmPhongDaiEntity in (");
        // Nếu không phải là superadmin thì check quyền theo phòng đài
        if (!nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
            List<Integer> listIdTramDuocXem = kiemTraQuyenModuleTram.layDanhSachIdPhongDaiDuocQuyenXem();
            if (listIdTramDuocXem.size() > 0) {
                for (int i = 0; i < listIdTramDuocXem.size(); i++) {
                    // Duyệt đến phần tử cuối thì đóng ngoặc cú pháp sql lại
                    if (i == listIdTramDuocXem.size() - 1) {
                        sql.append(listIdTramDuocXem.get(i) + ")");
                        break;
                    }
                    sql.append(listIdTramDuocXem.get(i) + ",");
                }
            } else { // Trường list được phân quyền rỗng -> xuất ra file dữ liệu rỗng
                sql.append(")");
            }
        } else {
            // Nếu người dùng là superadmin -> xuất all phòng đài
            sql.append("t.dmPhongDaiEntity)");
        }
        // Case người dụng chỉ định các trạm cần export
        if (listIdToExport != null && listIdToExport.size() > 0) {
            sql.append(" and t.id in (");
            for (int i = 0; i < listIdToExport.size(); i++) {
                // Duyệt đến phần tử cuối thì đóng ngoặc cú pháp sql lại
                if (i == listIdToExport.size() - 1) {
                    sql.append(listIdToExport.get(i) + ")");
                    break;
                } else {
                    sql.append(listIdToExport.get(i) + ",");
                }
            }
        }
        String sqlToExecute = sql.toString().replace(", from", " from");
        Query query = entityManager.createQuery(sqlToExecute);
        List<Object[]> listDuLieuLayRaTuOracle = query.getResultList();

        // Ghi nội dung file
        writeBodyToExcelExport(listDuLieuLayRaTuOracle, workbook, sheet, hsheet);

        // Build file
        try {
            return new ResponseEntity<>(ExcelUtil.buildFile(workbook),
                    ExcelUtil.getHeaders(tenFileExport), HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Có lỗi xảy ra! " + e.getCause().toString());
        }
    }

    // Export
    public void writeTitleToExcelExport(XSSFWorkbook workbook, XSSFSheet sheet,
            LinkedHashMap<String, String> listExport) {
        XSSFRow dongTieuDe = sheet.createRow(0);
        int column = 0;
        for (Map.Entry<String, String> title : listExport.entrySet()) {
            XSSFCell cellTitle = dongTieuDe.createCell(column);
            cellTitle.setCellValue(title.getValue());
            // Im đậm và tô màu chữ
            XSSFFont fontTitle = workbook.createFont();
            fontTitle.setBold(true);
            fontTitle.setColor(IndexedColors.BLACK.index);
            // Đóng khung và tô nền
            XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
            cellStyleTitle.setFont(fontTitle);
            cellTitle.setCellStyle(ExcelUtil.setBackgroundAndBorder(cellStyleTitle));
            column++;
        }
    }

    // Export
    public void writeBodyToExcelExport(List<Object[]> listDuLieuLayRaTuOracle,
            XSSFWorkbook workbook, XSSFSheet sheet, XSSFSheet hsheet) {
        int rowBodyIndex = 1;
        // Đọc qua từng dòng dữ liệu lấy ra dưới database
        for (Object[] rowValue : listDuLieuLayRaTuOracle) {
            XSSFRow rowBodyToWrite = sheet.createRow(rowBodyIndex);
            // Đọc qua từng cột và ghi vào file excel
            for (int column = 0; column < rowValue.length; column++) {
                XSSFCell cell = rowBodyToWrite.createCell(column);
                String rowTitle = sheet.getRow(0).getCell(column).getStringCellValue();
                // Nếu column chỉ lưu id (trường hợp nối bảng) thì xử lý lấy ra tên thay vì chỉ
                // id
                if (rowValue[column] != null) {
                    switch (rowTitle) {
                        case "Phòng Đài":
                            List<String> lsTenPhongDai = new ArrayList<>();
                            kiemTraQuyenModuleTram.layDanhSachIdPhongDaiDuocQuyenXem()
                                    .forEach(idPhongDai -> {
                                        lsTenPhongDai
                                                .add(dmPhongDaiService.findById(idPhongDai).getTen());
                                    });
                            ExcelUtil.createDropdownList(workbook, sheet, hsheet, lsTenPhongDai,
                                    rowBodyIndex, column, "PhongDaiList_" + rowBodyIndex);
                            // Default value
                            cell.setCellValue(((DmPhongDaiEntity) rowValue[column]).getTen());
                            break;
                        case "Tổ":
                            List<String> listTenTo = new ArrayList<>();
                            dmToService.findAll().forEach(to -> {
                                listTenTo.add(to.getTen());
                            });
                            ExcelUtil.createDropdownList(workbook, sheet, hsheet, listTenTo,
                                    rowBodyIndex, column, "ToList_" + rowBodyIndex);
                            // Default value
                            cell.setCellValue(((DmToEntity) rowValue[column]).getTen());
                            break;
                        case "Khu Vực":
                            List<String> listTenTramKhuVuc = new ArrayList<>();
                            dmTramKhuVucService.findAll().forEach(tramKhuVuc -> {
                                listTenTramKhuVuc.add(tramKhuVuc.getTen());
                            });
                            ExcelUtil.createDropdownList(workbook, sheet, hsheet, listTenTramKhuVuc,
                                    rowBodyIndex, column, "KhuVucList_" + rowBodyIndex);
                            // Default value
                            cell.setCellValue(((DmTramKhuVucEntity) rowValue[column]).getTen());
                            break;
                        case "Loại Cơ Sở Hạ Tầng":
                            List<String> listTenLoaiCsht = new ArrayList<>();
                            dmLoaiCshtService.findAll().forEach(loaiCsht -> {
                                listTenLoaiCsht.add(loaiCsht.getTen());
                            });
                            ExcelUtil.createDropdownList(workbook, sheet, hsheet, listTenLoaiCsht,
                                    rowBodyIndex, column, "CSHTList_" + rowBodyIndex);
                            // Default value
                            cell.setCellValue(((DmLoaiCshtEntity) rowValue[column]).getTen());
                            break;
                        case "Loại Trạm":
                            List<String> listTenLoaiTram = new ArrayList<>();
                            dmLoaiTramService.findAll().forEach(loaiTram -> {
                                listTenLoaiTram.add(loaiTram.getTen());
                            });
                            ExcelUtil.createDropdownList(workbook, sheet, hsheet, listTenLoaiTram,
                                    rowBodyIndex, column, "TramList_" + rowBodyIndex);
                            // Default value
                            cell.setCellValue(((DmLoaiTramEntity) rowValue[column]).getTen());
                            break;
                        case "Loại Cột Angten":
                            List<String> listTenCotAngten = new ArrayList<>();
                            dmLoaiCotAngtenService.findAll().forEach(cotAngten -> {
                                listTenCotAngten.add(cotAngten.getTen());
                            });
                            ExcelUtil.createDropdownList(workbook, sheet, hsheet, listTenCotAngten,
                                    rowBodyIndex, column, "CotAngtenList_" + rowBodyIndex);
                            // Default value
                            cell.setCellValue(((DmLoaiCotAngtenEntity) rowValue[column]).getTen());
                            break;
                        case "Tỉnh":
                            List<String> listTenTinh = new ArrayList<>();
                            dmTinhService.findAll().forEach(tinh -> {
                                listTenTinh.add(tinh.getTen());
                            });
                            ExcelUtil.createDropdownList(workbook, sheet, hsheet, listTenTinh,
                                    rowBodyIndex, column, "TinhList_" + rowBodyIndex);
                            // Default value
                            cell.setCellValue(((DmTinhEntity) rowValue[column]).getTen());
                            break;
                        case "Huyện":
                            List<String> listTenHuyen = new ArrayList<>();
                            dmHuyenService.findAll().forEach(huyen -> {
                                listTenHuyen.add(huyen.getTen());
                            });
                            ExcelUtil.createDropdownList(workbook, sheet, hsheet, listTenHuyen,
                                    rowBodyIndex, column, "HuyenList_" + rowBodyIndex);
                            // Default value
                            cell.setCellValue(((DmHuyenEntity) rowValue[column]).getTen());
                            break;
                        case "Xã":
                            List<String> listTenXa = new ArrayList<>();
                            dmXaService.findAll().forEach(xa -> {
                                listTenXa.add(xa.getTen());
                            });
                            ExcelUtil.createDropdownList(workbook, sheet, hsheet, listTenXa,
                                    rowBodyIndex, column, "XaList_" + rowBodyIndex);
                            // Default value
                            cell.setCellValue(((DmXaEntity) rowValue[column]).getTen());
                            break;
                        default:
                            // Ghi dữ liệu thô của các cột không có quan hệ bảng ra file
                            cell.setCellValue(String.valueOf(rowValue[column]));
                            sheet.autoSizeColumn(column);
                            break;
                    }
                } else {
                    cell.setCellValue("");
                }
            }
            rowBodyIndex++;
        }
    }

    // Import
    public ResponseEntity<?> importFromExcel(List<TramModel> listTramToImport, String loaiImport) {
        if (listTramToImport == null || listTramToImport.size() == 0) {
            throw new BadRequestException();
        }
        List<TramModel> listImportOK = new ArrayList<>();
        List<TramModel> listImportNOK = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("DanhSachTramImportNOK");

        String tenFileExport = "TramImportNOK_" + DateUtil.getThoiGianHienTai() + ".xlsx";

        for (TramModel tramModel : listTramToImport) {
            if (loaiImport.equals("create")) {
                boolean statusValidate = (boolean) validateImport(tramModel, loaiImport).get("status");
                TramEntity tramEntitySave = (TramEntity) validateImport(tramModel, loaiImport).get("entity");
                if (statusValidate) {
                    if (!tramModel.getMaTram().isEmpty()) {
                        // Nếu kiểm tra Trạm chưa có trong Database -> Thực hiện thêm mới
                        if (findByMaTram(tramModel.getMaTram()) == null) {
                            save(tramEntitySave);
                            listImportOK.add(tramModel);
                        } else {
                            // Nếu đã tồn tại mã này trong Database -> Không thêm mới
                            listImportNOK.add(tramModel);
                        }
                    } else {
                        // Nếu mã Trạm đầu vào blank -> Không thêm mới
                        // save(tramEntitySave);
                        listImportNOK.add(tramModel);
                    }
                } else {
                    // Validate fail -> Không thêm mới
                    listImportNOK.add(tramModel);
                }
            }
            if (loaiImport.equals("update")) {
                // TramEntity entityToUpdate = null;
                // Nếu mã trạm blank -> Không update
                if (tramModel.getMaTram().isEmpty()) {
                    listImportNOK.add(tramModel);
                    continue;
                }
                // Nếu mã trạm chưa tồn tại trong Database -> Không update
                if (findByMaTram(tramModel.getMaTram()) == null) {
                    listImportNOK.add(tramModel);
                    continue;
                }
                TramEntity entityToUpdate = findByMaTram(tramModel.getMaTram());
                boolean statusValidate = (boolean) validateImport(tramModel, loaiImport).get("status");
                TramEntity tramEntitySave = (TramEntity) validateImport(tramModel, loaiImport).get("entity");
                // Nếu validate dữ liệu đầu vào hợp lệ -> Thực hiện update
                if (statusValidate) {
                    tramEntitySave.setId(entityToUpdate.getId());
                    save(tramEntitySave);
                    listImportOK.add(tramModel);
                } else {
                    listImportNOK.add(tramModel);
                }
            }
        }
        System.out.println("Danh sách trạm OK: ");
        for (TramModel tram : listImportOK) {
            System.out.println(tram.getMaTram());
        }
        if (listImportNOK.size() == 0) {
            System.out.println("SuccessAll\n");
        }
        System.out.println("Danh sách trạm NOK: ");
        for (TramModel tram : listImportNOK) {
            System.out.println(tram.getMaTram());
        }
        if (listImportNOK.size() == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("SuccessAll");
        }
        System.out.println("==========");
        XSSFRow rowTitle = sheet.createRow(0);
        for (int i = 0; i < listImportNOK.size(); i++) {
            if (i == 0) {
                rowTitle.createCell(0).setCellValue("Tên Trạm");
                rowTitle.createCell(1).setCellValue("Mã Trạm");
                rowTitle.createCell(2).setCellValue("Mã Trạm ERP");
                rowTitle.createCell(3).setCellValue("Phòng Đài");
                rowTitle.createCell(4).setCellValue("Tổ");
                rowTitle.createCell(5).setCellValue("Địa Chỉ");
                rowTitle.createCell(6).setCellValue("Kinh Độ");
                rowTitle.createCell(7).setCellValue("Vĩ Độ");
                rowTitle.createCell(8).setCellValue("Khu Vực");
                rowTitle.createCell(9).setCellValue("Loại Trạm");
                rowTitle.createCell(10).setCellValue("Loại Cơ Sở Hạ Tầng");
                rowTitle.createCell(11).setCellValue("Loại Cột Angten");
                rowTitle.createCell(12).setCellValue("Tỉnh");
                rowTitle.createCell(13).setCellValue("Huyện");
                rowTitle.createCell(14).setCellValue("Xã");
                rowTitle.createCell(15).setCellValue("Độ Cao Angten");
                rowTitle.createCell(16).setCellValue("Ngày Phát Sóng");
                rowTitle.createCell(17).setCellValue("Ghi Chú");
                rowTitle.createCell(18).setCellValue("Trạng Thái Hoạt Động");
                for (int colTitle = 0; colTitle <= 18; colTitle++) {
                    XSSFCellStyle cellStyle = workbook.createCellStyle();
                    rowTitle.getCell(colTitle)
                            .setCellStyle(ExcelUtil.setBackgroundAndBorder(cellStyle));
                    XSSFFont fontTitle = workbook.createFont();
                    fontTitle.setBold(true);
                    fontTitle.setColor(IndexedColors.BLACK.index);
                    cellStyle.setFont(fontTitle);
                }
            }
            XSSFRow rowBody = sheet.createRow(i + 1);
            rowBody.createCell(0).setCellValue(listImportNOK.get(i).getTen());
            rowBody.createCell(1).setCellValue(listImportNOK.get(i).getMaTram());
            rowBody.createCell(2).setCellValue(listImportNOK.get(i).getMaTramErp());
            rowBody.createCell(3).setCellValue(listImportNOK.get(i).getDmPhongDai().getTen());
            rowBody.createCell(4).setCellValue(listImportNOK.get(i).getDmTo().getTen());
            rowBody.createCell(5).setCellValue(listImportNOK.get(i).getDiaChi());
            rowBody.createCell(6).setCellValue(listImportNOK.get(i).getKinhDo());
            rowBody.createCell(7).setCellValue(listImportNOK.get(i).getViDo());
            rowBody.createCell(8).setCellValue(listImportNOK.get(i).getDmTramKhuVuc().getTen());
            rowBody.createCell(9).setCellValue(listImportNOK.get(i).getDmLoaiTram().getTen());
            rowBody.createCell(10).setCellValue(listImportNOK.get(i).getDmLoaiCsht().getTen());
            rowBody.createCell(11).setCellValue(listImportNOK.get(i).getDmLoaiCotAngten().getTen());
            rowBody.createCell(12).setCellValue(listImportNOK.get(i).getDmTinh().getTen());
            rowBody.createCell(13).setCellValue(listImportNOK.get(i).getDmHuyen().getTen());
            rowBody.createCell(14).setCellValue(listImportNOK.get(i).getDmXa().getTen());
            rowBody.createCell(15).setCellValue(listImportNOK.get(i).getDoCaoAngten());
            rowBody.createCell(16).setCellValue(listImportNOK.get(i).getNgayPhatSong());
            rowBody.createCell(17).setCellValue(listImportNOK.get(i).getGhiChu());
            // rowBody.createCell(18).setCellValue(listImportNOK.get(i).getTrangThaiHoatDong());
        }
        // Build file
        try {
            return new ResponseEntity<>(ExcelUtil.buildFile(workbook),
                    ExcelUtil.getHeaders(tenFileExport), HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fail! " + e.getCause().toString());
        }
    }

    public LinkedHashMap<String, Object> validateImport(TramModel tramModel, String loaiImport) {
        LinkedHashMap<String, Object> result = new LinkedHashMap();
        TramEntity tramInOracle = new TramEntity();
        boolean statusData = true;
        // Validate trường hợp thêm mới
        if (loaiImport.equals("create")) {
            // Nếu kinh độ và vĩ độ BLANK thì set mặc định = 0
            tramModel.setKinhDo(tramModel.getKinhDo().isEmpty() ? "0" : tramModel.getKinhDo());
            tramModel.setViDo(tramModel.getViDo().isEmpty() ? "0" : tramModel.getViDo());
            // Nếu mã trạm đầu vào là BLANK hoặc mã trạm đã tồn tại trong Database ->
            // validate false
            if (tramModel.getMaTram().isEmpty()) {
                statusData = false;
            }
            if (findByMaTram(tramModel.getMaTram()) != null) {
                statusData = false;
            }
            // Nếu mã trạm erp đã tồn tại trong Database -> validate false
            if (!tramModel.getMaTramErp().isEmpty()
                    && findByMaTramErp(tramModel.getMaTramErp()).size() > 0) {
                statusData = false;
            }
            if (tramModel.getDiaChi().isEmpty()) {
                statusData = false;
            }
            if (tramModel.getTrangThaiHoatDong().equals("")) {
                statusData = false;
            }
            // Validate các trường danh mục và chuyển từ TÊN DANH MỤC input sang ID DANH MỤC
            // lưu vào
            // db
            // Nếu phòng đài blank hoặc phòng đài có dữ liệu nhưng không khớp tên với danh
            // mục ->
            // validate false
            if (tramModel.getDmPhongDai().getTen().isEmpty()
                    || dmPhongDaiService.findByTen(tramModel.getDmPhongDai().getTen()) == null) {
                statusData = false;
            } else {
                // Nếu phòng đài có dữ liệu khớp với database thì kiểm tra người dùng có được
                // phân
                // quyền tại phòng đài đó
                if (!nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
                    int idPhongDaiModelToValidate = dmPhongDaiService.findByTen(tramModel.getDmPhongDai().getTen())
                            .getId();
                    List<Integer> listDuocPhanQuyen = kiemTraQuyenModuleTram.layDanhSachIdPhongDaiDuocQuyenXem();
                    if (listDuocPhanQuyen.contains(idPhongDaiModelToValidate)) {
                        tramModel.setPhongDaiId(dmPhongDaiService
                                .findByTen(tramModel.getDmPhongDai().getTen()).getId());
                    } else {
                        // Nếu người dùng không được phân quyền tại phòng đài này -> validate false
                        statusData = false;
                    }
                } else {
                    // Trường hợp người import là superadmin thì không cần check quyền phòng đài
                    tramModel.setPhongDaiId(dmPhongDaiService
                            .findByTen(tramModel.getDmPhongDai().getTen()).getId());
                }
            }
            // Kiểm tra danh mục TỔ blank hoặc nhập sai
            if (tramModel.getDmTo().getTen().isEmpty()
                    || dmToService.findByTen(tramModel.getDmTo().getTen()) == null) {
                statusData = false;
            } else {
                tramModel.setToId(dmToService.findByTen(tramModel.getDmTo().getTen()).getId());
            }
            // Kiểm tra danh mục LOẠI TRẠM blank hoặc nhập sai
            if (tramModel.getDmLoaiTram().getTen().isEmpty()
                    || dmLoaiTramService.findByTen(tramModel.getDmLoaiTram().getTen()) == null) {
                statusData = false;
            } else {
                tramModel.setLoaiTramId(
                        dmLoaiTramService.findByTen(tramModel.getDmLoaiTram().getTen()).getId());
            }
            // Kiểm tra danh mục TRẠM KHU VỰC blank hoặc nhập sai
            if (tramModel.getDmTramKhuVuc().getTen().isEmpty() || dmTramKhuVucService
                    .findByTen(tramModel.getDmTramKhuVuc().getTen()) == null) {
                statusData = false;
            } else {
                tramModel.setKhuVucId(dmTramKhuVucService
                        .findByTen(tramModel.getDmTramKhuVuc().getTen()).getId());
            }
            // Kiểm tra danh mục LOẠI CƠ SỞ HẠ TẦNG blank hoặc nhập sai
            if (tramModel.getDmLoaiCsht().getTen().isEmpty()
                    || dmLoaiCshtService.findByTen(tramModel.getDmLoaiCsht().getTen()) == null) {
                statusData = false;
            } else {
                tramModel.setLoaiCshtId(
                        dmLoaiCshtService.findByTen(tramModel.getDmLoaiCsht().getTen()).getId());
            }
            // Kiểm tra danh mục LOẠI CỘT ANGTEN blank hoặc nhập sai
            if (tramModel.getDmLoaiCotAngten().getTen().isEmpty() || dmLoaiCotAngtenService
                    .findByTen(tramModel.getDmLoaiCotAngten().getTen()) == null) {
                statusData = false;
            } else {
                tramModel.setLoaiCotAngtenId(dmLoaiCotAngtenService
                        .findByTen(tramModel.getDmLoaiCotAngten().getTen()).getId());
            }
            // Kiểm tra danh mục TỈNH blank hoặc nhập sai
            if (tramModel.getDmTinh().getTen().isEmpty()
                    || dmTinhService.findByTen(tramModel.getDmTinh().getTen()) == null) {
                statusData = false;
            } else {
                tramModel
                        .setTinhId(dmTinhService.findByTen(tramModel.getDmTinh().getTen()).getId());
            }
            // Kiểm tra danh mục HUYỆN blank hoặc nhập sai
            if (tramModel.getDmHuyen().getTen().isEmpty()
                    || dmHuyenService.findByTen(tramModel.getDmHuyen().getTen()) == null) {
                statusData = false;
            } else {
                tramModel.setHuyenId(
                        dmHuyenService.findByTen(tramModel.getDmHuyen().getTen()).getId());
            }
            // Kiểm tra danh mục XÃ blank hoặc nhập sai
            if (tramModel.getDmXa().getTen().isEmpty()
                    || dmXaService.findByTen(tramModel.getDmXa().getTen()) == null) {
                statusData = false;
            } else {
                tramModel.setXaId(dmXaService.findByTen(tramModel.getDmXa().getTen()).getId());
            }
        }
        // Validate trường hợp update
        if (loaiImport.equals("update")) {
            // Nếu mã trạm đầu vào là BLANK hoặc mã trạm chưa tồn tại trong Database ->
            // Validate
            // false
            if (tramModel.getMaTram().isEmpty() || findByMaTram(tramModel.getMaTram()) == null) {
                statusData = false;
                result.put("status", statusData);
                result.put("entity", convertModelToEntity(new TramEntity(), tramModel));
                return result;
            } else {
                // Nếu mã trạm hợp lệ thì get trạm dưới Database lên để sử dụng cho trường hợp
                // người
                // dùng chỉ update 1 vài trường
                tramInOracle = findByMaTram(tramModel.getMaTram());
            }
            // Trường hợp chỉ cần update một số trường -> các trường còn lại giữ nguyên giá
            // trị như
            // dưới Database
            tramModel.setTen(
                    tramModel.getTen().isEmpty() ? tramInOracle.getTen() : tramModel.getTen());
            tramModel.setKinhDo(tramModel.getKinhDo().isEmpty() ? tramInOracle.getKinhDo()
                    : tramModel.getKinhDo());
            tramModel.setViDo(
                    tramModel.getViDo().isEmpty() ? tramInOracle.getViDo() : tramModel.getViDo());
            tramModel.setDiaChi(tramModel.getDiaChi().isEmpty() ? tramInOracle.getDiaChi()
                    : tramModel.getDiaChi());
            tramModel.setDoCaoAngten(
                    tramModel.getDoCaoAngten() == null ? tramInOracle.getDoCaoAngten()
                            : tramModel.getDoCaoAngten());
            tramModel.setGhiChu(tramModel.getGhiChu().isEmpty() ? tramInOracle.getGhiChu()
                    : tramModel.getGhiChu());
            tramModel.setNgayPhatSong(
                    tramModel.getNgayPhatSong() == null ? tramInOracle.getNgayPhatSong()
                            : tramModel.getNgayPhatSong());
            tramModel.setTrangThaiHoatDong(
                    tramModel.getTrangThaiHoatDong() == null ? tramInOracle.getTrangThaiHoatDong()
                            : tramModel.getTrangThaiHoatDong());
            // Validate update PHÒNG ĐÀI
            if (tramModel.getDmPhongDai().getTen().isEmpty()) {
                tramModel.setPhongDaiId(tramInOracle.getPhongDaiId());
            } else {
                if (dmPhongDaiService.findByTen(tramModel.getDmPhongDai().getTen()) == null) {
                    statusData = false;
                } else {
                    tramModel.setPhongDaiId(dmPhongDaiService
                            .findByTen(tramModel.getDmPhongDai().getTen()).getId());
                }
            }
            // Validate update TỔ
            if (tramModel.getDmTo().getTen().isEmpty()) {
                tramModel.setToId(tramInOracle.getToId());
            } else {
                if (dmToService.findByTen(tramModel.getDmTo().getTen()) == null) {
                    statusData = false;
                } else {
                    tramModel.setToId(dmToService.findByTen(tramModel.getDmTo().getTen()).getId());
                }
            }
            // Validate update LOẠI TRẠM
            if (tramModel.getDmLoaiTram().getTen().isEmpty()) {
                tramModel.setLoaiTramId(tramInOracle.getLoaiTramId());
            } else {
                if (dmLoaiTramService.findByTen(tramModel.getDmLoaiTram().getTen()) == null) {
                    statusData = false;
                } else {
                    tramModel.setLoaiTramId(dmLoaiTramService
                            .findByTen(tramModel.getDmLoaiTram().getTen()).getId());
                }
            }
            // Validate update TRẠM KHU VỰC
            if (tramModel.getDmTramKhuVuc().getTen().isEmpty()) {
                tramModel.setKhuVucId(tramInOracle.getKhuVucId());
            } else {
                if (dmTramKhuVucService.findByTen(tramModel.getDmTramKhuVuc().getTen()) == null) {
                    statusData = false;
                } else {
                    tramModel.setKhuVucId(dmTramKhuVucService
                            .findByTen(tramModel.getDmTramKhuVuc().getTen()).getId());
                }
            }
            // Validate update LOẠI CSHT
            if (tramModel.getDmLoaiCsht().getTen().isEmpty()) {
                tramModel.setLoaiCshtId(tramInOracle.getLoaiCshtId());
            } else {
                if (dmLoaiCshtService.findByTen(tramModel.getDmLoaiCsht().getTen()) == null) {
                    statusData = false;
                } else {
                    tramModel.setLoaiCshtId(dmLoaiCshtService
                            .findByTen(tramModel.getDmLoaiCsht().getTen()).getId());
                }
            }
            // Validate update LOẠI CỘT ANGTEN
            if (tramModel.getDmLoaiCotAngten().getTen().isEmpty()) {
                tramModel.setLoaiCshtId(tramInOracle.getLoaiCotAngtenId());
            } else {
                if (dmLoaiCotAngtenService
                        .findByTen(tramModel.getDmLoaiCotAngten().getTen()) == null) {
                    statusData = false;
                } else {
                    tramModel.setLoaiCotAngtenId(dmLoaiCotAngtenService
                            .findByTen(tramModel.getDmLoaiCotAngten().getTen()).getId());
                }
            }
            // Validate update TỈNH
            if (tramModel.getDmTinh().getTen().isEmpty()) {
                tramModel.setTinhId(tramInOracle.getTinhId());
            } else {
                if (dmTinhService.findByTen(tramModel.getDmTinh().getTen()) == null) {
                    statusData = false;
                } else {
                    tramModel.setTinhId(
                            dmTinhService.findByTen(tramModel.getDmTinh().getTen()).getId());
                }
            }
            // Validate update HUYỆN
            if (tramModel.getDmHuyen().getTen().isEmpty()) {
                tramModel.setHuyenId(tramInOracle.getHuyenId());
            } else {
                if (dmHuyenService.findByTen(tramModel.getDmHuyen().getTen()) == null) {
                    statusData = false;
                } else {
                    tramModel.setHuyenId(
                            dmHuyenService.findByTen(tramModel.getDmHuyen().getTen()).getId());
                }
            }
            // Validate update XÃ
            if (tramModel.getDmXa().getTen().isEmpty()) {
                tramModel.setXaId(tramInOracle.getXaId());
            } else {
                if (dmXaService.findByTen(tramModel.getDmXa().getTen()) == null) {
                    statusData = false;
                } else {
                    tramModel.setXaId(dmXaService.findByTen(tramModel.getDmXa().getTen()).getId());
                }
            }
        }
        result.put("status", statusData);
        result.put("entity", convertModelToEntity(new TramEntity(), tramModel));
        return result;
    }

    public Map<String, Object> thongKeTram() {
        Map<String, Object> dataReturn = new HashMap<>();

        ExecutorService executorService = null;

        try {
            executorService = Executors.newFixedThreadPool(7);

            CompletableFuture<Long> futureOfCountTram = null;
            CompletableFuture<Long> futureOfCountTramHoatDong = null;
            CompletableFuture<Long> futureOfCountTramNgungHoatDong = null;
            CompletableFuture<Long> futureOfCountTramPhatSongHoatDong = null;
            CompletableFuture<Long> futureOfCountTramPhatSongNgungHoatDong = null;
            CompletableFuture<Long> futureOfCountTramChuaPhatSongHoatDong = null;
            CompletableFuture<Long> futureOfCountTramChuaPhatSongNgungHoatDong = null;

            if (!nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
                List<Integer> ids = kiemTraQuyenModuleTram.layDanhSachIdPhongDaiDuocQuyenXem();
                final List<Integer> listIdPhongDai = ids;
                futureOfCountTram = CompletableFuture.supplyAsync(() -> tramRepository.countTram(listIdPhongDai),
                        executorService);
                futureOfCountTramHoatDong = CompletableFuture.supplyAsync(
                        () -> tramRepository.countTramHoatDong(TrangThaiTram.HOAT_DONG, listIdPhongDai),
                        executorService);
                futureOfCountTramNgungHoatDong = CompletableFuture.supplyAsync(
                        () -> tramRepository.countTramHoatDong(TrangThaiTram.NGUNG_HOAT_DONG, listIdPhongDai),
                        executorService);
                futureOfCountTramPhatSongHoatDong = CompletableFuture.supplyAsync(
                        () -> tramRepository.countTramPhatSong(TrangThaiTram.HOAT_DONG, listIdPhongDai),
                        executorService);
                futureOfCountTramPhatSongNgungHoatDong = CompletableFuture.supplyAsync(
                        () -> tramRepository.countTramPhatSong(TrangThaiTram.NGUNG_HOAT_DONG, listIdPhongDai),
                        executorService);
                futureOfCountTramChuaPhatSongHoatDong = CompletableFuture.supplyAsync(
                        () -> tramRepository.countTramChuaPhatSong(TrangThaiTram.HOAT_DONG, listIdPhongDai),
                        executorService);
                futureOfCountTramChuaPhatSongNgungHoatDong = CompletableFuture.supplyAsync(
                        () -> tramRepository.countTramChuaPhatSong(TrangThaiTram.NGUNG_HOAT_DONG, listIdPhongDai),
                        executorService);
            } else {
                futureOfCountTram = CompletableFuture.supplyAsync(() -> tramRepository.adminCountTram(),
                        executorService);
                futureOfCountTramHoatDong = CompletableFuture.supplyAsync(
                        () -> tramRepository.adminCountTramHoatDong(TrangThaiTram.HOAT_DONG), executorService);
                futureOfCountTramNgungHoatDong = CompletableFuture.supplyAsync(
                        () -> tramRepository.adminCountTramHoatDong(TrangThaiTram.NGUNG_HOAT_DONG), executorService);
                futureOfCountTramPhatSongHoatDong = CompletableFuture.supplyAsync(
                        () -> tramRepository.adminCountTramPhatSong(TrangThaiTram.HOAT_DONG), executorService);
                futureOfCountTramPhatSongNgungHoatDong = CompletableFuture.supplyAsync(
                        () -> tramRepository.adminCountTramPhatSong(TrangThaiTram.NGUNG_HOAT_DONG), executorService);
                futureOfCountTramChuaPhatSongHoatDong = CompletableFuture.supplyAsync(
                        () -> tramRepository.adminCountTramChuaPhatSong(TrangThaiTram.HOAT_DONG), executorService);
                futureOfCountTramChuaPhatSongNgungHoatDong = CompletableFuture.supplyAsync(
                        () -> tramRepository.adminCountTramChuaPhatSong(TrangThaiTram.NGUNG_HOAT_DONG),
                        executorService);
            }

            dataReturn.put("TAT_CA", futureOfCountTram.get());
            dataReturn.put("HOAT_DONG", futureOfCountTramHoatDong.get());
            dataReturn.put("NGUNG_HOAT_DONG", futureOfCountTramNgungHoatDong.get());
            dataReturn.put("PHAT_SONG_HOAT_DONG", futureOfCountTramPhatSongHoatDong.get());
            dataReturn.put("PHAT_SONG_NGUNG_HOAT_DONG", futureOfCountTramPhatSongNgungHoatDong.get());
            dataReturn.put("CHUA_PHAT_SONG_HOAT_DONG", futureOfCountTramChuaPhatSongHoatDong.get());
            dataReturn.put("CHUA_PHAT_SONG_NGUNG_HOAT_DONG", futureOfCountTramChuaPhatSongNgungHoatDong.get());
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (executorService != null) {
                executorService.shutdownNow();
            }
        }

        return dataReturn;
    }

    private void saveLog(TramEntity originalData, TramEntity toData, String type, Long nguoiDungId,
            LogActionEnum action) {
        try {
            ChangeLogModel changeLogModel = tramLogService.getTramChangeLog(originalData, toData, type);
            tramLogService.saveTramLog(changeLogModel, nguoiDungId, action);
            System.out.println("cleanDiffByStringAndNull:" + changeLogModel);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
