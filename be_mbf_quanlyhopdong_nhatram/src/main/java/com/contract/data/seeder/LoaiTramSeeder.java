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

import com.contract.danhmuc.loaitram.model.DmLoaiTramModel;
import com.contract.danhmuc.loaitram.service.DmLoaiTramService;

@Component
public class LoaiTramSeeder implements CommandLineRunner {
    @Value("${seeder.module:0}")
    private String module;

    @Autowired
    private DmLoaiTramService dmLoaiTramService;

    @Override
    public void run(String... args) throws Exception {
        if (module.equals("loai_tram")) {
            seeding();
            exitProgram();
        }
    }

    private void seeding() {
        try {
            File resource = new ClassPathResource("data/excel/Danhmuc_LoaiTram_Data.xlsx").getFile();
            if (!resource.exists()) {
                throw new FileNotFoundException("File import khong ton tai!");
            }

            XSSFWorkbook workbook = new XSSFWorkbook(resource);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
                DmLoaiTramModel model = new DmLoaiTramModel();
                XSSFRow row = sheet.getRow(i);

                XSSFCell tenCSHT = row.getCell(0);
                XSSFCell maCSHT = row.getCell(1);
                XSSFCell ghiChu = row.getCell(2);

                switch (maCSHT.getCellType()) {
                    case NUMERIC:
                        model.setMa(String.valueOf((int) maCSHT.getNumericCellValue()));
                        break;
                    case STRING:
                        model.setMa(maCSHT.getStringCellValue());
                        break;
                }
                model.setTen(tenCSHT.getStringCellValue());
                model.setGhiChu(ghiChu.getStringCellValue());

                dmLoaiTramService.save(model);
            }
        } catch (Exception e) {
            System.out.println("\n seeding throw exception:::" + e.getMessage() + "\n");
        }
    }

    private void exitProgram() {
        System.exit(0);
    }
}
