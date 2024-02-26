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

import com.contract.danhmuc.loaithietbiran.model.DmLoaiThietBiRanModel;
import com.contract.danhmuc.loaithietbiran.service.DmLoaiThietBiRanService;

@Component
public class LoaiThietBiRanSeeder implements CommandLineRunner {
    @Value("${seeder.module:0}")
    private String module;

    @Autowired
    private DmLoaiThietBiRanService dmLoaiThietBiRanService;

    @Override
    public void run(String... args) throws Exception {
        if (module.equals("loai_thiet_bi_ran")) {
            seeding();
            exitProgram();
        }
    }

    private void seeding() {
        try {
            File resource = new ClassPathResource("data/excel/Danhmuc_LoaiThietBiRan_Data.xlsx").getFile();
            if (!resource.exists()) {
                throw new FileNotFoundException("File import khong ton tai!");
            }

            XSSFWorkbook workbook = new XSSFWorkbook(resource);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
                DmLoaiThietBiRanModel model = new DmLoaiThietBiRanModel();
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
                    default:
                        model.setMa("MA_THIET_BI_RAN");
                }
                model.setTen(tenCSHT.getStringCellValue());
                model.setGhiChu(ghiChu.getStringCellValue());

                dmLoaiThietBiRanService.save(model);
            }
        } catch (Exception e) {
            System.out.println("\n seeding throw exception:::" + e.getMessage() + "\n");
        }
    }

    private void exitProgram() {
        System.exit(0);
    }
}
