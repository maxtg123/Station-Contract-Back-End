package com.contract.hopdong.hopdong.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
import com.contract.danhmuc.phongdai.model.DmPhongDaiModel;
import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.hopdong.hopdong.repository.HopDongRepository;
import com.contract.hopdong.hopdongnhatram.model.BangKeKhaiThanhToanTrongKyRequest;
import com.contract.hopdong.hopdongtram.entity.HopDongTramEntity;
import com.contract.hopdong.hopdongtram_kythanhtoan.entity.HopDongTramKyThanhToanEntity;
import com.contract.tram.tram.entity.TramEntity;

@Service
public class ExportBangKeKhaiThanhToanService {
  @Autowired
  private HopDongRepository hopDongRepository;

  public ResponseEntity<?> exportTemplateBangKeKhaiThanhToanTrongKy(BangKeKhaiThanhToanTrongKyRequest request)
      throws Exception {
    if (request == null || request.getListHopDongTramId() == null) {
      throw new BadRequestException();
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
    String fileNameSource = "Template_export_bangKeKhaiThanhToanTrongKy.xlsx";
    long currentTimeMillis = Instant.now(Clock.systemUTC()).toEpochMilli();

    String fileNameCopy = currentTimeMillis +
        "_Template_export_bangKeKhaiThanhToanTrongKy.xlsx";
    Path fileTemplateGoc = Path.of(rootName + "/" + fileNameSource);
    Path target = Path.of(rootName + "/" + fileNameCopy);
    // Sao chép file template gốc ra một bản sao và new thành workbook
    XSSFWorkbook workbook = new XSSFWorkbook(
        Files.copy(fileTemplateGoc, target,
            StandardCopyOption.REPLACE_EXISTING).toFile());
    XSSFSheet sheet = workbook.getSheetAt(0);

    try {
      exportTableOfBangKeKhaiThanhToanTrongKy(workbook, sheet, listHopDongWithOneTram,
          listPhongDai.get(0),
          request.getKyThanhToan());
      //
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      workbook.write(outputStream);
      workbook.close();
      // Xóa file clone
      File fileDelete = new File(Path.of(rootName + "/" + fileNameCopy).toUri());
      fileDelete.delete();
      return new ResponseEntity<>(outputStream.toByteArray(),
          ExcelUtil.getHeaders("Mau export Bang ke khai thanh toan trong ky.xlsx"),
          HttpStatus.OK);
    } catch (Exception e) {
      File fileDelete = new File(Path.of(rootName + "/" + fileNameCopy).toUri());
      fileDelete.delete();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
    }
  }

  private void exportTableOfBangKeKhaiThanhToanTrongKy(XSSFWorkbook workbook,
      XSSFSheet sheet,
      List<HopDongEntity> listHopDongWithOneTram, DmPhongDaiModel phongDaiModel,
      Date kyThanhToan) throws ParseException {
    // Tên phòng đài
    sheet.getRow(1).getCell(0).setCellValue(phongDaiModel.getTen().toUpperCase());
    // Kỳ thanh toán tháng
    if (kyThanhToan != null) {
      String kyThanhToanThang = DateUtil.formatDate(kyThanhToan, "MM") + "/"
          + DateUtil.formatDate(kyThanhToan, "yyyy");
      sheet.getRow(2).getCell(0).setCellValue(
          sheet.getRow(2).getCell(0).getStringCellValue().replace("{MM/yyyy}",
              kyThanhToanThang));
    } else {
      sheet.getRow(2).getCell(0)
          .setCellValue(sheet.getRow(2).getCell(0).getStringCellValue().replace("{MM/yyyy}",
              ""));
    }
    for (int i = 0; i < listHopDongWithOneTram.size(); i++) {
      XSSFRow row = sheet.createRow(i + 6);
      XSSFCellStyle cellStyle = workbook.createCellStyle();
      List<HopDongTramEntity> hopDongTramEntities = new ArrayList<>(
          listHopDongWithOneTram.get(i).getHopDongTramEntities());
      TramEntity tramEntity = hopDongTramEntities.get(0).getTramEntity();

      cellStyle.setWrapText(true);
      row.createCell(0).setCellValue(i + 1);
      row.createCell(1).setCellValue(listHopDongWithOneTram.get(i).getSoHopDong());
      row.createCell(2).setCellValue(listHopDongWithOneTram.get(i).getSoHopDongErp());
      row.createCell(3).setCellValue(DateUtil.formatDate(listHopDongWithOneTram.get(i).getNgayKetThuc(),
          "dd/MM/yyyy"));
      row.createCell(4).setCellValue(listHopDongWithOneTram.get(i).getHopDongDoiTacEntity().getTen());
      row.createCell(5).setCellValue(tramEntity.getMaTram());
      String maTramErp = tramEntity.getMaTramErp();
      row.createCell(6).setCellValue(maTramErp);
      row.createCell(7).setCellValue(tramEntity.getSiteNameErp());
      List<HopDongTramKyThanhToanEntity> hopDongTramKyThanhToanEntities = new ArrayList<>(
          hopDongTramEntities.get(0).getHopDongTramKyThanhToanEntities());
      HopDongTramKyThanhToanEntity kyThanhToanEntity = hopDongTramKyThanhToanEntities.stream()
          .filter(_kyThanhToanEntity -> {
            return _kyThanhToanEntity.getDaThanhToanNgay() == null;
          }).findFirst().orElse(null);
      if (kyThanhToan != null) {
        row.createCell(8).setCellValue(DateUtil.formatDate(kyThanhToanEntity.getTuNgay(),
            "dd/MM/yyyy"));
        row.createCell(9).setCellValue(DateUtil.formatDate(kyThanhToanEntity.getDenNgay(),
            "dd/MM/yyyy"));
      }
      row.createCell(10).setCellValue(hopDongTramEntities.get(0).getGiaThue());
      row.createCell(11).setCellValue("");
      row.createCell(12).setCellValue("");
      if (kyThanhToanEntity.getGiaTien() != null) {
        row.createCell(13).setCellValue(kyThanhToanEntity.getGiaTien());
      } else {
        row.createCell(13);
      }
      String noiDungChi = "MB trạm " + tramEntity.getMaTram() +
          "/"
          + (maTramErp != null ? maTramErp : "") + "-"
          + DateUtil.formatDate(kyThanhToanEntity.getTuNgay(), "dd/MM/yyyy") + " đến "
          + DateUtil.formatDate(kyThanhToanEntity.getDenNgay(), "dd/MM/yyyy");
      row.createCell(14).setCellValue(noiDungChi);
      row.createCell(15).setCellValue(listHopDongWithOneTram.get(i).getHopDongDoiTacEntity().getTen());
      row.createCell(16).setCellValue(listHopDongWithOneTram.get(i).getHopDongDoiTacEntity().getSoTaiKhoan());
      row.createCell(17).setCellValue(listHopDongWithOneTram.get(i).getHopDongDoiTacEntity().getNganHangChiNhanh());
      row.createCell(18).setCellValue("");
      for (int j = 0; j <= 18; j++) {
        row.getCell(j).setCellStyle(ExcelUtil.setBorder(cellStyle));
      }
    }
  }
}
