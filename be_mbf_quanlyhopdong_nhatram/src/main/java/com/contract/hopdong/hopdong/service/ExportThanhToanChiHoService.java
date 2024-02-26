package com.contract.hopdong.hopdong.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.contract.common.exception.BadRequestException;
import com.contract.common.util.DateUtil;
import com.contract.common.util.ExcelUtil;
import com.contract.common.util.StringUtil;
import com.contract.danhmuc.phongdai.model.DmPhongDaiModel;
import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.hopdong.hopdong.repository.HopDongRepository;
import com.contract.hopdong.hopdongdoitac.entity.HopDongDoiTacEntity;
import com.contract.hopdong.hopdongnhatram.model.BangKeChiHoRequest;
import com.contract.hopdong.hopdongtram.entity.HopDongTramEntity;
import com.contract.hopdong.hopdongtram_kythanhtoan.entity.HopDongTramKyThanhToanEntity;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.nguoidung.nguoidungkhuvuc.entity.NguoiDungKhuVucEntity;
import com.contract.nguoidung.nguoidungkhuvuc.service.NguoiDungKhuVucService;
import com.contract.tram.tram.entity.TramEntity;

@Service
public class ExportThanhToanChiHoService {
  @Autowired
  private HopDongRepository hopDongRepository;
  @Autowired
  private NguoiDungService nguoiDungService;
  @Autowired
  private NguoiDungKhuVucService nguoiDungKhuVucService;

  public ResponseEntity<?> exportTemplateDeNghiChiHo(BangKeChiHoRequest request) throws Exception {
    if (request == null || request.getListHopDongTramId() == null) {
      throw new BadRequestException();
    }
    Date ngayLap = request.getNgayLap();
    if (ngayLap == null) {
      ngayLap = new Date();
    }

    LinkedHashSet<DmPhongDaiModel> setPhongDai = new LinkedHashSet<>();
    List<HopDongEntity> listHopDongWithOneTram = new ArrayList<>();
    for (Long hopDongTramId : request.getListHopDongTramId()) {
      HopDongEntity hopDongEntity = hopDongRepository.findByHopDongTramIdWithFetch(hopDongTramId);
      if (hopDongEntity != null) {
        listHopDongWithOneTram.add(hopDongEntity);
        List<HopDongTramEntity> hopDongTramEntities = new ArrayList<>(hopDongEntity.getHopDongTramEntities());
        setPhongDai.add(DmPhongDaiModel.fromEntity(hopDongTramEntities.get(0).getTramEntity().getDmPhongDaiEntity()));
      }
    }

    List<DmPhongDaiModel> listPhongDai = setPhongDai.stream().toList();

    String rootName = "src/main/resources/templates/hopdong";
    String fileNameSource = "Template_export_bangKeDeNghiChiHo.xlsx";
    List<NguoiDungKhuVucEntity> listKhuVuc = nguoiDungKhuVucService
        .findByNguoiDungId(nguoiDungService.getNguoiDung().getId());
    Instant instant = Instant.now(Clock.systemUTC());
    long currentTimeMillis = instant.toEpochMilli();

    Date dateTitle = ngayLap;

    String fileNameCopy = currentTimeMillis +
        "_Template_export_de_nghi_chi_ho.xlsx";
    Path fileTemplateGoc = Path.of(rootName + "/" + fileNameSource);
    Path target = Path.of(rootName + "/" + fileNameCopy);
    // Sao chép file template gốc ra một bản sao
    File templateToExport = Files.copy(fileTemplateGoc, target,
        StandardCopyOption.REPLACE_EXISTING).toFile();
    XSSFWorkbook workbook = new XSSFWorkbook(templateToExport);
    XSSFSheet sheet = workbook.getSheetAt(0);

    try {
      exportTableDeNghiChiHo(workbook, sheet, listHopDongWithOneTram, listPhongDai.get(0),
          ngayLap);

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      workbook.write(outputStream);
      workbook.close();

      File fileDelete = new File(Path.of(rootName + "/" + fileNameCopy).toUri());
      fileDelete.delete();

      String fileNameReturn = "BANG_KE_DE_NGHI_CHI_HO T" +
          DateUtil.formatDate(dateTitle, "MM") + "."
          + DateUtil.formatDate(dateTitle, "yyyy") + " NH CTNT.xlsx";
      return new ResponseEntity<>(outputStream.toByteArray(),
          ExcelUtil.getHeaders(fileNameReturn),
          HttpStatus.OK);
    } catch (Exception e) {
      File fileDelete = new File(Path.of(rootName + "/" + fileNameCopy).toUri());
      fileDelete.delete();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
    }
  }

  private void exportTableDeNghiChiHo(XSSFWorkbook workbook, XSSFSheet sheet,
      List<HopDongEntity> listHopDongWithOneTram, DmPhongDaiModel phongDai,
      Date ngayLap) throws ParseException {
    sheet.shiftRows(10, 15, listHopDongWithOneTram.size());
    // Tên phòng đài
    sheet.getRow(1).getCell(1).setCellValue(sheet.getRow(1).getCell(1).getStringCellValue().replace("{phongDaiTen}",
        phongDai.getTen().toUpperCase()));
    // Tên viết tắt
    sheet.getRow(2).getCell(1).setCellValue(
        sheet.getRow(2).getCell(1).getStringCellValue().replace("{phongDaiTVT}",
            phongDai.getTenVietTat()));
    // Ngày lặp bảng kê
    if (ngayLap == null) {
      ngayLap = new Date();
    }
    String ngayLapBang = sheet.getRow(2).getCell(6).getStringCellValue();
    sheet.getRow(2).getCell(6).setCellValue(ngayLapBang.replace("{dd}",
        DateUtil.formatDate(ngayLap, "dd")));
    ngayLapBang = sheet.getRow(2).getCell(6).getStringCellValue();
    sheet.getRow(2).getCell(6).setCellValue(ngayLapBang.replace("{MM}",
        DateUtil.formatDate(ngayLap, "MM")));
    ngayLapBang = sheet.getRow(2).getCell(6).getStringCellValue();
    sheet.getRow(2).getCell(6).setCellValue(ngayLapBang.replace("{yyyy}",
        DateUtil.formatDate(ngayLap, "yyyy")));
    // Từ ngày đến ngày
    String tuNgayDenNgay = sheet.getRow(5).getCell(0).getStringCellValue();
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
    sheet.getRow(5).getCell(0).setCellValue(tuNgayDenNgay.replace("{tuNgay}",
        ""));
    tuNgayDenNgay = sheet.getRow(5).getCell(0).getStringCellValue();
    sheet.getRow(5).getCell(0).setCellValue(tuNgayDenNgay.replace("{denNgay}",
        ""));
    // }
    // Có hiệu lực đến ngày
    String coHieuLucDenNgay = sheet.getRow(6).getCell(0).getStringCellValue();
    // if (denNgay != null) {
    // sheet.getRow(6).getCell(0).setCellValue(
    // coHieuLucDenNgay.replace("{coHieuLucDenNgay}", DateUtil.formatDate(denNgay,
    // "dd/MM/yyyy")));
    // } else {
    // sheet.getRow(6).getCell(0).setCellValue(coHieuLucDenNgay.replace("{coHieuLucDenNgay}",
    // ""));
    // }
    sheet.getRow(6).getCell(0).setCellValue(coHieuLucDenNgay.replace("{coHieuLucDenNgay}",
        ""));

    double totalTien = 0;
    for (int i = 0; i < listHopDongWithOneTram.size(); i++) {
      XSSFRow row = sheet.createRow(i + 10);
      XSSFCellStyle cellStyle = workbook.createCellStyle();
      cellStyle.setWrapText(true);
      List<HopDongTramEntity> hopDongTramEntities = new ArrayList<>(
          listHopDongWithOneTram.get(i).getHopDongTramEntities());
      TramEntity tramEntity = hopDongTramEntities.get(0).getTramEntity();
      HopDongDoiTacEntity doiTacEntity = listHopDongWithOneTram.get(i).getHopDongDoiTacEntity();
      row.createCell(0).setCellValue(i + 1);
      row.createCell(1).setCellValue(listHopDongWithOneTram.get(i).getSoHopDong());
      row.createCell(2).setCellValue(tramEntity.getMaTram());
      row.createCell(3).setCellValue(tramEntity.getTen());
      row.createCell(4).setCellValue(tramEntity.getDiaChi());
      if (doiTacEntity != null) {
        row.createCell(5).setCellValue(doiTacEntity.getTen() == null ? "" : doiTacEntity.getTen());
      }

      // Kỳ thanh toán
      List<HopDongTramKyThanhToanEntity> hopDongTramKyThanhToanEntities = new ArrayList<>(
          hopDongTramEntities.get(0).getHopDongTramKyThanhToanEntities());
      HopDongTramKyThanhToanEntity kyThanhToan = hopDongTramKyThanhToanEntities.stream().filter(_kyThanhToanEntity -> {
        return _kyThanhToanEntity.getDaThanhToanNgay() == null;
      }).findFirst().orElse(null);

      if (kyThanhToan != null) {
        row.createCell(6).setCellValue(DateUtil.formatDate(kyThanhToan.getTuNgay(),
            "dd/MM/yyyy") + " - "
            + DateUtil.formatDate(kyThanhToan.getDenNgay(), "dd/MM/yyyy"));
        row.createCell(7).setCellValue("MB");
        // Số tiền (VNĐ)
        row.createCell(8).setCellValue(kyThanhToan.getGiaTien());
        totalTien += kyThanhToan.getGiaTien();
      } else {
        row.createCell(6).setCellValue("");
        row.createCell(7).setCellValue("MB");
        // Số tiền (VNĐ)
        row.createCell(8).setCellValue("");
      }

      // Thông tin người nhận tiền
      if (doiTacEntity != null) {
        StringBuilder nguoiNhanTien = new StringBuilder(
            doiTacEntity.getTen() == null ? "" : doiTacEntity.getTen());
        nguoiNhanTien.append(" số tài khoản " +
            doiTacEntity.getSoTaiKhoan() == null ? "" : doiTacEntity.getSoTaiKhoan());
        nguoiNhanTien.append(" tại " +
            doiTacEntity.getNganHangChiNhanh() == null ? "" : doiTacEntity.getNganHangChiNhanh());
        row.createCell(9).setCellValue(nguoiNhanTien.toString());
      } else {
        row.createCell(9).setCellValue("");
      }

      row.createCell(10).setCellValue(listHopDongWithOneTram.get(i).getGhiChu());
      // Mã hợp đồng
      row.createCell(11).setCellValue(listHopDongWithOneTram.get(i).getSoHopDongErp());
      row.createCell(12).setCellValue(tramEntity.getMaTramErp());
      for (int j = 0; j <= 12; j++) {
        row.getCell(j).setCellStyle(ExcelUtil.setBorder(cellStyle));
      }
    }
    DecimalFormat decimalFormat = new DecimalFormat("#");
    String formattedTotalTien = decimalFormat.format(totalTien);
    if (formattedTotalTien.contains(".")) {
      formattedTotalTien = formattedTotalTien.substring(0,
          formattedTotalTien.indexOf("."));
    }
    // Total tiền
    sheet.getRow(10 +
        listHopDongWithOneTram.size()).getCell(8).setCellValue(Double.parseDouble(formattedTotalTien));
    // Total tiền bằng chữ
    String totalText = sheet.getRow(11 +
        listHopDongWithOneTram.size()).getCell(1).getStringCellValue();
    sheet.getRow(11 + listHopDongWithOneTram.size()).getCell(1).setCellValue(
        totalText.replace("{totalText}",
            StringUtil.convertMoneyFromNumberToText(formattedTotalTien)));
    sheet.getRow(13 + listHopDongWithOneTram.size()).getCell(3).setCellValue(sheet.getRow(13
        + listHopDongWithOneTram.size()).getCell(3)
        .getStringCellValue().replace("{phongDaiTen}", phongDai.getTen()));
  }
}
