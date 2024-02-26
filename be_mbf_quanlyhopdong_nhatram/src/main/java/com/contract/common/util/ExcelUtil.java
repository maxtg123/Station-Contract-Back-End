package com.contract.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ExcelUtil {
    public static void createDropdownList(XSSFWorkbook workbook, XSSFSheet sheet, XSSFSheet hsheet,
            List<String> listValue, int row, int column, String nameName) {
        // Put các tên danh mục vào column trong sheet danh mục ẩn
        for (int i = 0; i < listValue.size(); i++) {
            XSSFRow hideRow = hsheet.getRow(i);
            if (hideRow == null) {
                hideRow = hsheet.createRow(i);
            }
            hideRow.createCell(column).setCellValue(listValue.get(i));
        }

        // Khởi tạo name cho mỗi loại danh mục
        Name namedRange = workbook.createName();
        namedRange.setNameName(nameName);
        String colName = CellReference.convertNumToColString(column);
        namedRange
                .setRefersToFormula(hsheet.getSheetName() + "!$" + colName + "$1:$" + colName + "$" + listValue.size());

        sheet.autoSizeColumn(column); // Auto điều chỉnh độ rộng cột

        DataValidationHelper validationHelper = new XSSFDataValidationHelper(sheet);
        CellRangeAddressList addressList = new CellRangeAddressList(row, row, column, column); // Tạo dropdownlist cho
                                                                                               // một cell
        DataValidationConstraint constraint = validationHelper.createFormulaListConstraint(nameName);
        DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);

        dataValidation.setSuppressDropDownArrow(true); // Hiển thị mũi tên xổ xuống để chọn giá trị
        dataValidation.setShowErrorBox(true); // Hiển thị hộp thoại lỗi khi chọn giá trị không hợp lệ
        dataValidation.createErrorBox("Error", "Giá trị đã chọn không hợp lệ!");
        dataValidation.setEmptyCellAllowed(false); // Không cho phép ô trống trong dropdownlist
        dataValidation.setShowPromptBox(true); // Hiển thị hộp nhắc nhở khi người dùng chọn ô
        dataValidation.createPromptBox("Danh mục hệ thống", "Vui lòng chọn giá trị!"); // Tạo hộp thoại nhắc nhở khi
                                                                                       // click chuột vào cell

        sheet.addValidationData(dataValidation);
    }

    public static XSSFCellStyle setBackgroundAndBorder(XSSFCellStyle cellStyle) {
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        return cellStyle;
    }

    public static ByteArrayResource buildFile(XSSFWorkbook workbook) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return new ByteArrayResource(outputStream.toByteArray());
    }

    public static HttpHeaders getHeaders(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        return headers;
    }

    public static XSSFCellStyle setBorder(XSSFCellStyle cellStyle) {
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        return cellStyle;
    }

    public static void applyNumericFormat(Workbook outWorkbook, Row row, Cell cell, Double value, String styleFormat) {
        CellStyle style = outWorkbook.createCellStyle();
        DataFormat format = outWorkbook.createDataFormat();
        style.setDataFormat(format.getFormat(styleFormat));
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    public static void shiftCellsToLeft(Row row, int columnToDelete) {
        int lastCellNum = row.getLastCellNum();
        for (int i = columnToDelete + 1; i < lastCellNum; i++) {
            Cell sourceCell = row.getCell(i);
            if (sourceCell != null) {
                Cell targetCell = row.getCell(i - 1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                targetCell.setCellStyle(sourceCell.getCellStyle());
                // targetCell.setCellType(sourceCell.getCellType());

                switch (sourceCell.getCellType()) {
                    case BLANK:
                    case _NONE:
                        break;
                    case BOOLEAN:
                        targetCell.setCellValue(sourceCell.getBooleanCellValue());
                        break;
                    case ERROR:
                        targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
                        break;
                    case FORMULA:
                        targetCell.setCellFormula(sourceCell.getCellFormula());
                        break;
                    case NUMERIC:
                        targetCell.setCellValue(sourceCell.getNumericCellValue());
                        break;
                    case STRING:
                        targetCell.setCellValue(sourceCell.getStringCellValue());
                        break;
                }

                row.removeCell(sourceCell);
            }
        }
    }
}