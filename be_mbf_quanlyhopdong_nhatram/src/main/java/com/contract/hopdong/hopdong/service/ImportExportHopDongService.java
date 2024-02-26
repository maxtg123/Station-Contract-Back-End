package com.contract.hopdong.hopdong.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.contract.authentication.component.KiemTraQuyenModuleHopDong;
import com.contract.base.model.QueryResultModel;
import com.contract.common.util.DateUtil;
import com.contract.common.util.ExcelUtil;
import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.hopdong.hopdong.enums.LoaiHopDongEnum;
import com.contract.hopdong.hopdong.enums.TinhTrangHopDongEnum;
import com.contract.hopdong.hopdong.enums.TinhTrangThanhToanEnum;
import com.contract.hopdong.hopdong.enums.TrangThaiHoatDongQueryEnum;
import com.contract.hopdong.hopdongtram.entity.HopDongTramEntity;
import com.contract.hopdong.hopdongtram_kythanhtoan.entity.HopDongTramKyThanhToanEntity;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class ImportExportHopDongService {
  @Autowired
  private HopDongService hopDongService;
  @Autowired
  private NguoiDungService nguoiDungService;
  @Autowired
  private KiemTraQuyenModuleHopDong kiemTraQuyenModuleHopDong;

  public ResponseEntity<?> export(String listKey, String listIds, String excludeKey, String search, String soHopDong,
      String soHopDongErp,
      Integer hinhThucDauTu,
      Integer hinhThucKyHopDong, Integer doiTuongKyHopDong, Integer phongDaiId,
      TrangThaiHoatDongQueryEnum trangThaiHopDong, LoaiHopDongEnum loaiHopDong, String maTram, Date ngayKyFrom,
      Date ngayKyTo, Date ngayKetThucFrom, Date ngayKetThucTo, Integer idTinh, Integer idHuyen, Integer idXa,
      TinhTrangHopDongEnum tinhTrangHopDong, TinhTrangThanhToanEnum tinhTrangThanhToan, Date kyThanhToanFrom,
      Date kyThanhToanTo)
      throws Exception {

    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    if (ngayKyFrom != null && ngayKyTo == null) {
      ngayKyTo = formatDate.parse("2200-12-31");
    }
    if (ngayKyFrom == null && ngayKyTo != null) {
      ngayKyFrom = formatDate.parse("1975-04-30");
    }
    if (ngayKetThucFrom != null && ngayKetThucTo == null) {
      ngayKetThucTo = formatDate.parse("2200-12-31");
    }
    if (ngayKetThucFrom == null && ngayKetThucTo != null) {
      ngayKetThucFrom = formatDate.parse("1975-04-30");
    }

    List<Integer> pdIds = null;
    if (!nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
      pdIds = kiemTraQuyenModuleHopDong.layDanhSachIdPhongDaiDuocQuyenXem();
    }

    List<String> keyColumns = Stream.of(listKey.split(";"))
        .map(String::trim)
        .collect(Collectors.toList());
    List<String> excludeKeys = Stream.of(excludeKey.split(";")).map(String::trim).collect(Collectors.toList());

    List<Long> hdIds = null;
    if (listIds != null && !listIds.equals("")) {
      hdIds = Arrays.stream(listIds.split(",")).map(Long::parseLong).collect(Collectors.toList());
    }

    QueryResultModel<HopDongEntity> result = new QueryResultModel<>();
    result = hopDongService.getListHopDong(hdIds, pdIds, search, soHopDong, soHopDongErp, hinhThucDauTu,
        hinhThucKyHopDong,
        doiTuongKyHopDong, phongDaiId, trangThaiHopDong, loaiHopDong, maTram, ngayKyFrom, ngayKyTo,
        ngayKetThucFrom, ngayKetThucTo, idTinh, idHuyen, idXa, tinhTrangHopDong, tinhTrangThanhToan, kyThanhToanFrom,
        kyThanhToanTo, 0,
        Integer.MAX_VALUE, 0);

    List<HopDongEntity> allHD = result.getContent();

    InputStream resource = new ClassPathResource("/templates/hopdong/Template_export_HopDong.xlsx").getInputStream();

    XSSFWorkbook workbook = new XSSFWorkbook(resource);
    XSSFSheet sheet = workbook.getSheetAt(0);

    final AtomicInteger startRow = new AtomicInteger(1);
    allHD.forEach(_hopDongEntity -> {
      // each row is each hopdongtram
      _hopDongEntity.getHopDongTramEntities().forEach(_hopDongTramEntity -> {
        Row row = sheet.createRow(startRow.get());
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
        Font font = workbook.createFont();
        font.setFontName("Times New Roman");
        cellStyle.setFont(font);

        startRow.set(startRow.get() + 1);
        keyColumns.forEach(keyColumn -> { // column
          Integer colIndex = mapKeyToColumnIndex(keyColumn);
          if (colIndex != null) {
            try {
              Cell cell = row.createCell(colIndex);
              String val = mapKeyToValue(keyColumn, _hopDongEntity, _hopDongTramEntity, false);

              if (keyColumn.equals("giaThue") || keyColumn.equals("giaDienKhoan") || keyColumn.equals("giaKyQuy")) {
                DataFormat format = workbook.createDataFormat();
                cellStyle.setDataFormat(format.getFormat("_-* #,##0_-;-* #,##0_-;_-* \"-\"??_-;_-@_-"));
                cell.setCellValue(Float.parseFloat(val));
              } else {
                cell.setCellValue(val);
              }
              cell.setCellStyle(cellStyle);
            } catch (Exception e) {
              System.out.println("export e: " + e.getMessage());
              System.out.println("export e key column: " + keyColumn);
              System.out.println("export e hop dong: " + _hopDongEntity.getSoHopDong());
            }
          }
        });
        // if each tram have gia dien khoan -> new row
        if (_hopDongTramEntity.getGiaDienKhoan() != null) {
          Row rowDienKhoan = sheet.createRow(startRow.get());

          startRow.set(startRow.get() + 1);
          keyColumns.forEach(keyColumn -> { // column
            Integer colIndex = mapKeyToColumnIndex(keyColumn);
            if (colIndex != null) {
              try {
                Cell cellDK = rowDienKhoan.createCell(colIndex);
                String val = mapKeyToValue(keyColumn, _hopDongEntity, _hopDongTramEntity, true);
                if (keyColumn.equals("giaThue") || keyColumn.equals("giaDienKhoan") || keyColumn.equals("giaKyQuy")) {
                  DataFormat format = workbook.createDataFormat();
                  cellStyle.setDataFormat(format.getFormat("_-* #,##0_-;-* #,##0_-;_-* \"-\"??_-;_-@_-"));
                  cellDK.setCellValue(Float.parseFloat(val));
                } else {
                  cellDK.setCellValue(val);
                }
                cellDK.setCellStyle(cellStyle);
              } catch (Exception e) {
                System.out.println("export dien khoan e: " + e.getMessage());
                System.out.println("export dien khoan e key column: " + keyColumn);
                System.out.println("export dien khoan e hop dong: " + _hopDongEntity.getSoHopDong());
              }
            }
          });
        }
      });
    });

    // delete columns
    List<Integer> columnsToDelete = new ArrayList<>();
    excludeKeys.forEach(_key -> {
      Integer colIndex = mapKeyToColumnIndex(_key);
      if (colIndex != null) {
        columnsToDelete.add(colIndex);
      }
    });
    Collections.reverse(columnsToDelete);

    Iterator<Row> rowIterator = sheet.iterator();
    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      for (int columnToDelete : columnsToDelete) {
        Cell cellToDelete = row.getCell(columnToDelete);
        if (cellToDelete != null) {
          row.removeCell(cellToDelete);
        }
        // Shift cells to the left to cover the deleted column
        ExcelUtil.shiftCellsToLeft(row, columnToDelete);
      }
    }
    // Collections.reverse(columnsToDelete);
    // System.out.println("columnToDelete: " + columnsToDelete);
    // while (rowIterator.hasNext()) {
    // Row row = rowIterator.next();
    // for (int columnToDelete : columnsToDelete) {
    // ExcelUtil.shiftCellsToLeft(row, columnToDelete);
    // // Shift cells to the left to cover the deleted column
    // // ExcelUtil.shiftCellsToLeft(row, columnToDelete);
    // }
    // }
    // Iterate over rows and delete the specified column in each row
    // excludeKeys.forEach(_excludeKey -> {
    // Integer colWillDeleted = mapKeyToColumnIndex(_excludeKey);
    // if (colWillDeleted != null) {
    // System.out.println("colWillDeleted: " + colWillDeleted);
    // Iterator<Row> rowIterator = sheet.iterator();
    // while (rowIterator.hasNext()) {
    // Row row = rowIterator.next();
    // Cell cellToDelete = row.getCell(colWillDeleted);
    // if (cellToDelete != null) {
    // row.removeCell(cellToDelete);
    // }
    // // Shift cells to the left to cover the deleted column
    // ExcelUtil.shiftCellsToLeft(row, colWillDeleted);
    // }
    // }
    // });

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    workbook.write(outputStream);
    workbook.close();

    String fileNameReturn = "DANH_SACH_HOP_DONG_MAT_BANG_NHA_TRAM.xlsx";
    return new ResponseEntity<>(outputStream.toByteArray(),
        ExcelUtil.getHeaders(fileNameReturn),
        HttpStatus.OK);
  }

  private Integer mapKeyToColumnIndex(String key) {
    if (key == null)
      return null;
    if (key.equals("soHopDong")) {
      return 0;
    }
    if (key.equals("soHopDongErp")) {
      return 1;
    }
    if (key.equals("maTram")) {
      return 2;
    }
    if (key.equals("tenTram")) {
      return 3;
    }
    if (key.equals("maTramErp")) {
      return 4;
    }
    if (key.equals("maDTXD")) {
      return 5;
    }
    if (key.equals("tinh")) {
      return 6;
    }
    if (key.equals("huyen")) {
      return 7;
    }
    if (key.equals("xa")) {
      return 8;
    }
    if (key.equals("khuVuc")) {
      return 9;
    }
    if (key.equals("phongDai")) {
      return 10;
    }
    if (key.equals("to")) {
      return 11;
    }
    if (key.equals("diaChiDatTram")) {
      return 12;
    }
    if (key.equals("kinhDo")) {
      return 13;
    }
    if (key.equals("viDo")) {
      return 14;
    }
    if (key.equals("loaiTram")) {
      return 15;
    }
    if (key.equals("loaiCSHT")) {
      return 16;
    }
    if (key.equals("loaiThietBiRan")) {
      return 17;
    }
    if (key.equals("loaiCotAnten")) {
      return 18;
    }
    if (key.equals("doCaoCotAnten")) {
      return 19;
    }
    if (key.equals("luuLuong")) {
      return 20;
    }
    if (key.equals("tinhTrangPhatSong")) {
      return 21;
    }
    if (key.equals("ngayPhatSong")) {
      return 22;
    }
    if (key.equals("tinhTrangHopDong")) {
      return 23;
    }
    if (key.equals("hinhThucKyHopDong")) {
      return 24;
    }
    if (key.equals("hinhThucDauTu")) {
      return 25;
    }
    if (key.equals("khoanMuc")) {
      return 26;
    }
    if (key.equals("doiTuongKyHopDong")) {
      return 27;
    }
    if (key.equals("kyQuy")) {
      return 28;
    }
    if (key.equals("giaKyQuy")) {
      return 29;
    }
    if (key.equals("ngayKyHopDong")) {
      return 30;
    }
    if (key.equals("ngayKetThucHopDong")) {
      return 31;
    }
    if (key.equals("toTrinh")) {
      return 32;
    }
    if (key.equals("giaThue")) {
      return 33;
    }
    if (key.equals("giaDienKhoan")) {
      return 34;
    }
    if (key.equals("thueVAT")) {
      return 35;
    }
    if (key.equals("chuKyThanhToan")) {
      return 36;
    }
    if (key.equals("hinhThucThanhToan")) {
      return 37;
    }
    if (key.equals("ngayBatDauYeuCauThanhToan")) {
      return 38;
    }
    if (key.equals("daThanhToanDenNgay")) {
      return 39;
    }
    if (key.equals("kyThanhToanKeTiep")) {
      return 40;
    }
    if (key.equals("hoTenChuNha")) {
      return 41;
    }
    if (key.equals("diaChiLienHe")) {
      return 42;
    }
    if (key.equals("soDienThoai")) {
      return 43;
    }
    if (key.equals("maSoThue")) {
      return 44;
    }
    if (key.equals("cccd")) {
      return 45;
    }
    if (key.equals("chuTaiKhoan")) {
      return 46;
    }
    if (key.equals("soTaiKhoan")) {
      return 47;
    }
    if (key.equals("nganHangChiNhanh")) {
      return 48;
    }
    if (key.equals("thuePhongMay")) {
      return 49;
    }
    if (key.equals("thuePhongMayDatMayNoCoDinh")) {
      return 50;
    }
    if (key.equals("thueBaoVe")) {
      return 51;
    }
    if (key.equals("thuePhongMayDatDuPhong")) {
      return 52;
    }
    if (key.equals("thueCotAnten")) {
      return 53;
    }
    if (key.equals("viTriAnten")) {
      return 54;
    }
    if (key.equals("tiepDatChongSet")) {
      return 55;
    }
    if (key.equals("htDienIndoor")) {
      return 56;
    }
    // dung chung
    if (key.equals("loaiHangMucCsht")) {
      return 57;
    }
    if (key.equals("maTramDonViDungChung")) {
      return 58;
    }
    if (key.equals("donViDungChung")) {
      return 59;
    }
    if (key.equals("thoiDiemPhatSinh")) {
      return 60;
    }
    if (key.equals("ngayLapDatThietBi")) {
      return 61;
    }
    if (key.equals("ngayBatDauDungChung")) {
      return 62;
    }
    if (key.equals("ngayKetThucDungChung")) {
      return 63;
    }
    //
    if (key.equals("ghiChu")) {
      return 64;
    }
    return null;
  }

  private String mapKeyToValue(String key, HopDongEntity hopDongEntity, HopDongTramEntity hopDongTramEntity,
      boolean isDienKhoan)
      throws ParseException {
    if (key == null || hopDongEntity == null)
      return "";
    if (key.equals("soHopDong")) {
      return hopDongEntity.getSoHopDong();
    }
    if (key.equals("soHopDongErp")) {
      return hopDongEntity.getSoHopDongErp();
    }
    if (key.equals("maTram")) {
      return hopDongTramEntity.getTramEntity().getMaTram();
    }
    if (key.equals("tenTram")) {
      return hopDongTramEntity.getTramEntity().getTen();
    }
    if (key.equals("maTramErp")) {
      return hopDongTramEntity.getTramEntity().getMaTramErp();
    }
    if (key.equals("maDTXD")) {
      return hopDongTramEntity.getTramEntity().getMaDauTuXayDung();
    }
    if (key.equals("tinh")) {
      return hopDongTramEntity.getTramEntity().getDmTinhEntity() == null ? ""
          : hopDongTramEntity.getTramEntity().getDmTinhEntity().getTen();
    }
    if (key.equals("huyen")) {
      return hopDongTramEntity.getTramEntity().getDmHuyenEntity() == null ? ""
          : hopDongTramEntity.getTramEntity().getDmHuyenEntity().getTen();
    }
    if (key.equals("xa")) {
      return hopDongTramEntity.getTramEntity().getDmXaEntity() == null ? ""
          : hopDongTramEntity.getTramEntity().getDmXaEntity().getTen();
    }
    if (key.equals("khuVuc")) {
      return hopDongTramEntity.getTramEntity().getDmTramKhuVucEntity() == null ? ""
          : hopDongTramEntity.getTramEntity().getDmTramKhuVucEntity().getTen();
    }
    if (key.equals("phongDai")) {
      return hopDongTramEntity.getTramEntity().getDmPhongDaiEntity() == null ? ""
          : hopDongTramEntity.getTramEntity().getDmPhongDaiEntity().getTen();
    }
    if (key.equals("to")) {
      return hopDongTramEntity.getTramEntity().getDmToEntity() == null ? ""
          : hopDongTramEntity.getTramEntity().getDmToEntity().getTen();
    }
    if (key.equals("diaChiDatTram")) {
      return hopDongTramEntity.getTramEntity().getDiaChi();
    }
    if (key.equals("kinhDo")) {
      return hopDongTramEntity.getTramEntity().getKinhDo();
    }
    if (key.equals("viDo")) {
      return hopDongTramEntity.getTramEntity().getViDo();
    }
    if (key.equals("loaiTram")) {
      return hopDongTramEntity.getTramEntity().getDmLoaiTramEntity() == null ? ""
          : hopDongTramEntity.getTramEntity().getDmLoaiTramEntity().getTen();
    }
    if (key.equals("loaiCSHT")) {
      return hopDongTramEntity.getTramEntity().getDmLoaiCshtEntity() == null ? ""
          : hopDongTramEntity.getTramEntity().getDmLoaiCshtEntity().getTen();
    }
    if (key.equals("loaiThietBiRan")) {
      return hopDongTramEntity.getTramEntity().getDmLoaiThietBiRanEntityList() == null ? ""
          : hopDongTramEntity.getTramEntity().getDmLoaiThietBiRanEntityList().stream().map(_ran -> _ran.getTen())
              .collect(Collectors.joining(", ")).toString();
    }
    if (key.equals("loaiCotAnten")) {
      return hopDongTramEntity.getTramEntity().getDmLoaiCotAngtenEntity() == null ? ""
          : hopDongTramEntity.getTramEntity().getDmLoaiCotAngtenEntity().getTen();
    }
    if (key.equals("doCaoCotAnten")) {
      return hopDongTramEntity.getTramEntity().getDoCaoAngten() == null ? ""
          : hopDongTramEntity.getTramEntity().getDoCaoAngten().toString();
    }
    if (key.equals("luuLuong")) {
      // TODO
      return "";
    }
    if (key.equals("tinhTrangPhatSong")) {
      if (hopDongTramEntity.getTramEntity().getDaPhatSong() == null
          || hopDongTramEntity.getTramEntity().getDaPhatSong() == false) {
        return "Chưa phát sóng";
      }
      return "Đã phát sóng";
    }
    if (key.equals("ngayPhatSong")) {
      if (hopDongTramEntity.getTramEntity().getNgayPhatSong() != null) {
        return DateUtil.formatDate(hopDongTramEntity.getTramEntity().getNgayPhatSong(), "dd/MM/yyyy");
      }
    }
    if (key.equals("tinhTrangHopDong")) {
      if (hopDongEntity.getTinhTrangHopDong() == null)
        return "";
      if (hopDongEntity.getTinhTrangHopDong().equals(TinhTrangHopDongEnum.KY_MOI)) {
        return "Mới";
      }
      if (hopDongEntity.getTinhTrangHopDong().equals(TinhTrangHopDongEnum.TAI_KY)) {
        return "Tái ký mới";
      }
      if (hopDongEntity.getTinhTrangHopDong().equals(TinhTrangHopDongEnum.DI_DOI)) {
        return "Di dời";
      }
      if (hopDongEntity.getTinhTrangHopDong().equals(TinhTrangHopDongEnum.THANH_LY)) {
        return "Thanh lý";
      }
    }
    if (key.equals("hinhThucKyHopDong")) {
      return hopDongEntity.getDmHinhThucKyHopDongEntity() == null ? ""
          : hopDongEntity.getDmHinhThucKyHopDongEntity().getTen();
    }
    if (key.equals("hinhThucDauTu")) {
      return hopDongEntity.getDmHinhThucDauTuEntity() == null ? ""
          : hopDongEntity.getDmHinhThucDauTuEntity().getTen();
    }
    if (key.equals("khoanMuc")) {
      return hopDongEntity.getDmKhoanMucEntity() == null ? "" : hopDongEntity.getDmKhoanMucEntity().getMa();
    }
    if (key.equals("doiTuongKyHopDong")) {
      return hopDongEntity.getDmDoiTuongKyHopDongEntity() == null ? ""
          : hopDongEntity.getDmDoiTuongKyHopDongEntity().getTen();
    }
    if (key.equals("kyQuy")) {
      if (hopDongEntity.getCoKyQuy() != null && hopDongEntity.getCoKyQuy() == true) {
        return "Có";
      }
      return "Không";
    }
    if (key.equals("giaKyQuy")) {
      return hopDongEntity.getGiaKyQuy() == null || ObjectUtils.isEmpty(hopDongEntity.getGiaKyQuy()) ? ""
          : hopDongEntity.getGiaKyQuy().toString();
    }
    if (key.equals("ngayKyHopDong")) {
      if (hopDongEntity.getNgayKy() != null) {
        return DateUtil.formatDate(hopDongEntity.getNgayKy(), "dd/MM/yyyy");
      }
    }
    if (key.equals("ngayKetThucHopDong")) {
      if (hopDongEntity.getNgayKetThuc() != null) {
        return DateUtil.formatDate(hopDongEntity.getNgayKetThuc(), "dd/MM/yyyy");
      }
    }
    if (key.equals("giaThue") && !isDienKhoan) {
      return hopDongTramEntity.getGiaThue() == null || ObjectUtils.isEmpty(hopDongTramEntity.getGiaThue()) ? ""
          : hopDongTramEntity.getGiaThue().toString();
    }
    if (key.equals("giaDienKhoan") && isDienKhoan) {
      return hopDongTramEntity.getGiaDienKhoan() == null || ObjectUtils.isEmpty(hopDongTramEntity.getGiaDienKhoan())
          ? ""
          : hopDongTramEntity.getGiaDienKhoan().toString();
    }
    if (key.equals("thueVAT")) {
      return hopDongEntity.getThueVat() == null || ObjectUtils.isEmpty(hopDongEntity.getThueVat()) ? ""
          : hopDongEntity.getThueVat().toString();
    }
    if (key.equals("chuKyThanhToan")) {
      List<String> chukyList = new ArrayList<>();
      if (hopDongEntity.getChuKyNam() != null && hopDongEntity.getChuKyNam() != 0) {
        chukyList.add(hopDongEntity.getChuKyNam() + " năm");
      }
      if (hopDongEntity.getChuKyThang() != null && hopDongEntity.getChuKyThang() != 0) {
        chukyList.add(hopDongEntity.getChuKyThang() + " tháng");
      }
      if (hopDongEntity.getChuKyNgay() != null && hopDongEntity.getChuKyNgay() != 0) {
        chukyList.add(hopDongEntity.getChuKyNgay() + " ngày");
      }
      return chukyList.stream().map(_chuky -> _chuky).collect(Collectors.joining(" "));
    }
    if (key.equals("hinhThucThanhToan")) {
      return hopDongEntity.getDmHinhThucThanhToanEntity() == null ? ""
          : hopDongEntity.getDmHinhThucThanhToanEntity().getTen();
    }
    if (key.equals("ngayBatDauYeuCauThanhToan")) {
      return DateUtil.formatDate(hopDongTramEntity.getNgayBatDauYeuCauThanhToan(), "dd/MM/yyyy");
    }
    if (key.equals("daThanhToanDenNgay")) {
      if (hopDongTramEntity.getHopDongTramKyThanhToanEntities() != null) {
        List<HopDongTramKyThanhToanEntity> listKy = new ArrayList<>(
            hopDongTramEntity.getHopDongTramKyThanhToanEntities());
        int soKyDaThanhToan = -1;
        for (int i = 0; i < listKy.size(); i++) {
          if (listKy.get(i).getDaThanhToanNgay() != null) {
            soKyDaThanhToan += 1;
          }
        }
        if (soKyDaThanhToan > -1 && listKy.get(soKyDaThanhToan).getDaThanhToanNgay() != null) {
          return DateUtil.formatDate(listKy.get(soKyDaThanhToan).getDaThanhToanNgay(), "dd/MM/yyyy");
        }
      }
      return "";
    }
    if (key.equals("kyThanhToanKeTiep")) {
      if (hopDongTramEntity.getHopDongTramKyThanhToanEntities() != null) {
        HopDongTramKyThanhToanEntity kyCanThanhToan = hopDongTramEntity.getHopDongTramKyThanhToanEntities().stream()
            .filter(_kyThanhToan -> {
              return _kyThanhToan.getDaThanhToanNgay() == null ? true : false;
            }).findFirst().orElse(null);
        if (kyCanThanhToan != null) {
          return DateUtil.formatDate(kyCanThanhToan.getTuNgay(), "dd/MM/yyyy") + " - "
              + DateUtil.formatDate(kyCanThanhToan.getDenNgay(), "dd/MM/yyyy");
        }
      }
      return "";
    }
    if (key.equals("hoTenChuNha")) {
      return hopDongEntity.getHopDongDoiTacEntity() == null ? "" : hopDongEntity.getHopDongDoiTacEntity().getTen();
    }
    if (key.equals("diaChiLienHe")) {
      return hopDongEntity.getHopDongDoiTacEntity() == null ? "" : hopDongEntity.getHopDongDoiTacEntity().getDiaChi();
    }
    if (key.equals("soDienThoai")) {
      return hopDongEntity.getHopDongDoiTacEntity() == null ? ""
          : hopDongEntity.getHopDongDoiTacEntity().getSoDienThoai();
    }
    if (key.equals("maSoThue")) {
      return hopDongEntity.getHopDongDoiTacEntity() == null ? "" : hopDongEntity.getHopDongDoiTacEntity().getMaSoThue();
    }
    if (key.equals("cccd")) {
      return hopDongEntity.getHopDongDoiTacEntity() == null ? "" : hopDongEntity.getHopDongDoiTacEntity().getCccd();
    }
    if (key.equals("chuTaiKhoan")) {
      return hopDongEntity.getHopDongDoiTacEntity() == null ? ""
          : hopDongEntity.getHopDongDoiTacEntity().getChuTaiKhoan();
    }
    if (key.equals("soTaiKhoan")) {
      return hopDongEntity.getHopDongDoiTacEntity() == null ? ""
          : hopDongEntity.getHopDongDoiTacEntity().getSoTaiKhoan();
    }
    if (key.equals("nganHangChiNhanh")) {
      return hopDongEntity.getHopDongDoiTacEntity() == null ? ""
          : hopDongEntity.getHopDongDoiTacEntity().getNganHangChiNhanh();
    }
    // TODO with dynamic
    if (key.equals("thuePhongMay")) {
      return "Không";
    }
    if (key.equals("thuePhongMayDatMayNoCoDinh")) {
      return "Không";
    }
    if (key.equals("thueBaoVe")) {
      return "Không";
    }
    if (key.equals("thuePhongMayDatDuPhong")) {
      return "Không";
    }
    if (key.equals("thueCotAnten")) {
      return "Không";
    }
    if (key.equals("viTriAnten")) {
      return "Không";
    }
    if (key.equals("tiepDatChongSet")) {
      return "Không";
    }
    if (key.equals("htDienIndoor")) {
      return "Không";
    }
    if (hopDongTramEntity.getHopDongTramDungChungEntity() != null) {
      if (key.equals("loaiHangMucCsht")) {
        return hopDongTramEntity.getHopDongTramDungChungEntity().getDmLoaiHangMucCSHTEntity() == null ? ""
            : hopDongTramEntity.getHopDongTramDungChungEntity().getDmLoaiHangMucCSHTEntity().getTen();
      }
      if (key.equals("maTramDonViDungChung")) {
        return hopDongTramEntity.getHopDongTramDungChungEntity().getMaTramDonViDungChung();
      }
      if (key.equals("donViDungChung")) {
        return hopDongTramEntity.getHopDongTramDungChungEntity().getDmDonViDungChung() == null ? ""
            : hopDongTramEntity.getHopDongTramDungChungEntity().getDmDonViDungChung().getTen();
      }
      if (key.equals("thoiDiemPhatSinh")) {
        return hopDongTramEntity.getHopDongTramDungChungEntity().getThoiDiemPhatSinh() == null ? ""
            : DateUtil.formatDate(hopDongTramEntity.getHopDongTramDungChungEntity().getThoiDiemPhatSinh(),
                "dd/MM/yyyy");
      }
      if (key.equals("ngayLapDatThietBi")) {
        return hopDongTramEntity.getHopDongTramDungChungEntity().getNgayLapDatThietBi() == null ? ""
            : DateUtil.formatDate(hopDongTramEntity.getHopDongTramDungChungEntity().getNgayLapDatThietBi(),
                "dd/MM/yyyy");
      }
      if (key.equals("ngayBatDauDungChung")) {
        return hopDongTramEntity.getHopDongTramDungChungEntity().getNgayBatDauDungChung() == null ? ""
            : DateUtil.formatDate(hopDongTramEntity.getHopDongTramDungChungEntity().getNgayBatDauDungChung(),
                "dd/MM/yyyy");
      }
      if (key.equals("ngayKetThucDungChung")) {
        return hopDongTramEntity.getHopDongTramDungChungEntity().getNgayKetThucDungChung() == null ? ""
            : DateUtil.formatDate(hopDongTramEntity.getHopDongTramDungChungEntity().getNgayKetThucDungChung(),
                "dd/MM/yyyy");
      }
    }
    if (key.equals("ghiChu")) {
      return hopDongEntity.getGhiChu();
    }

    return "";
  }
}
