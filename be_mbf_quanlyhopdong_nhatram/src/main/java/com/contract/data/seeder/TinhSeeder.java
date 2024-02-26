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
import com.contract.danhmuc.tinh.model.DmTinhModel;
import com.contract.danhmuc.tinh.service.DmTinhService;

@Component
public class TinhSeeder implements CommandLineRunner {
    @Value("${seeder.module:0}")
    private String module;

    @Autowired
    private DmTinhService dmTinhService;

    @Override
    public void run(String... args) throws Exception {
        if (module.equals("tinh")) {
            seeding();
            exitProgram();
        }
    }

    private void seeding() {
        try {
            File resource = new ClassPathResource("data/excel/Danhmuc_Tinh_Data.xlsx").getFile();
            if (!resource.exists()) {
                throw new FileNotFoundException("File import khong ton tai!");
            }

            XSSFWorkbook workbook = new XSSFWorkbook(resource);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
                DmTinhModel model = new DmTinhModel();
                XSSFRow row = sheet.getRow(i);

                XSSFCell maTinh = row.getCell(0);
                String tenTinh = row.getCell(1).getStringCellValue();
                XSSFCell maDatasite = row.getCell(2);
                String ghiChu = row.getCell(4).getStringCellValue();

                switch (maTinh.getCellType()) {
                    case STRING:
                        model.setMa(maTinh.getStringCellValue());
                        break;
                    case NUMERIC:
                        model.setMa(String.valueOf((int) maTinh.getNumericCellValue()));
                        break;
                }
                switch (maDatasite.getCellType()) {
                    case STRING:
                        model.setMaDataSite(maDatasite.getStringCellValue());
                        break;
                    case NUMERIC:
                        model.setMaDataSite(String.valueOf(maDatasite.getNumericCellValue()));
                        break;
                }

                if (tenTinh.equals("") || tenTinh == null) {
                    throw new Exception("Ten tinh khong duoc null - Cell B" + (i + 1));
                } else {
                    model.setTen(tenTinh);
                }

                if (dmTinhService.findByMa(model.getMa()) != null) {
                    System.out.println("\nTinh " + tenTinh + " co ma " + model.getMa()
                            + " da ton tai tren he thong -> Khong insert");
                } else {
                    model.setGhiChu(ghiChu);
                    dmTinhService.save(model);
                    System.out.println("\nImport thanh cong tinh " + model.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("\n seeding throw exception:::" + e.getMessage() + "\n");
        }
    }

    private void exitProgram() {
        System.exit(0);
    }
}
