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
import com.contract.danhmuc.loaicotangten.model.DmLoaiCotAngtenModel;
import com.contract.danhmuc.loaicotangten.service.DmLoaiCotAngtenService;

@Component
public class LoaiCotAngtenSeeder implements CommandLineRunner {
    @Value("${seeder.module:0}")
    private String module;

    @Autowired
    private DmLoaiCotAngtenService dmLoaiCotAngtenService;

    @Override
    public void run(String... args) throws Exception {
        if (module.equals("loai_cot_angten")) {
            seeding();
            exitProgram();
        }
    }

    private void seeding() {
        try {
            File resource =
                    new ClassPathResource("data/excel/Danhmuc_LoaiCotAngten_Data.xlsx").getFile();
            if (!resource.exists()) {
                throw new FileNotFoundException("File import khong ton tai!");
            }

            XSSFWorkbook workbook = new XSSFWorkbook(resource);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
                DmLoaiCotAngtenModel model = new DmLoaiCotAngtenModel();
                XSSFRow row = sheet.getRow(i);

                XSSFCell ten = row.getCell(0);
                XSSFCell ma = row.getCell(1);
                XSSFCell datasite = row.getCell(2);
                XSSFCell ghiChu = row.getCell(3);

                switch (datasite.getCellType()) {
                    case STRING:
                        model.setMaDataSite(datasite.getStringCellValue());
                        break;
                    case NUMERIC:
                        model.setMaDataSite(String.valueOf((int) datasite.getNumericCellValue()));
                        break;
                }
                model.setTen(ten.getStringCellValue());
                model.setMa(ma.getStringCellValue());
                model.setGhiChu(ghiChu.getStringCellValue());

                dmLoaiCotAngtenService.save(model);
            }
        } catch (Exception e) {
            System.out.println("\n seeding throw exception:::" + e.getMessage() + "\n");
        }
    }

    private void exitProgram() {
        System.exit(0);
    }
}
