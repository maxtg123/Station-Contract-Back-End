package com.contract.data.seeder;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.contract.danhmuc.hinhthucdautu.model.DmHinhThucDauTuModel;
import com.contract.danhmuc.hinhthucdautu.service.DmHinhThucDauTuService;

@Component
public class HinhThucDauTuSeeder implements CommandLineRunner {
    @Value("${seeder.module:0}")
    private String module;

    @Autowired
    private DmHinhThucDauTuService dmHinhThucDauTuService;

    @Override
    public void run(String... args) throws Exception {
        if (module.equals("hinh_thuc_dau_tu")) {
            seeding();
            exitProgram();
        }
    }

    private void seeding() {
        try {
            File resource = new ClassPathResource("data/excel/Danhmuc_HinhThucDauTu_Data.xlsx").getFile();
            if (!resource.exists()) {
                throw new FileNotFoundException("File import khong ton tai!");
            }
            XSSFWorkbook workbook = new XSSFWorkbook(resource);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
                DmHinhThucDauTuModel model = new DmHinhThucDauTuModel();
                XSSFRow row = sheet.getRow(i);

                XSSFCell tenCSHT = row.getCell(0);
                XSSFCell maCSHT = row.getCell(1);
                XSSFCell maDatasite = row.getCell(2);
                XSSFCell ghiChu = row.getCell(3);

                switch (maDatasite.getCellType()) {
                    case STRING:
                        model.setMaDataSite(maDatasite.getStringCellValue());
                        break;
                    case NUMERIC:
                        model.setMaDataSite(String.valueOf((int) maDatasite.getNumericCellValue()));
                        break;
                }
                model.setTen(tenCSHT.getStringCellValue());
                model.setMa(maCSHT.getStringCellValue());
                model.setGhiChu(ghiChu.getStringCellValue());

                dmHinhThucDauTuService.save(model);
            }
        } catch (Exception e) {
            System.out.println("\n seeding throw exception:::" + e.getMessage() + "\n");
        }
    }

    private void exitProgram() {
        System.exit(0);
    }
}
