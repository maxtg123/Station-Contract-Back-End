package com.contract.data.seeder;

import java.io.File;
import java.io.FileNotFoundException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import com.contract.danhmuc.loaicsht.model.DmLoaiCshtModel;
import com.contract.danhmuc.loaicsht.repository.DmLoaiCshtRepository;
import com.contract.danhmuc.loaicsht.service.DmLoaiCshtService;

@Component
public class LoaiCshtSeeder implements CommandLineRunner {
    @Value("${seeder.module:0}")
    private String module;

    @Autowired
    private DmLoaiCshtService dmLoaiCshtService;
    @Autowired
    private DmLoaiCshtRepository dmLoaiCshtRepository;

    @Override
    public void run(String... args) throws Exception {
        if (module.equals("loai_csht")) {
            seeding();
            exitProgram();
        }
    }

    private void seeding() {
        try {
            File resource =
                    new ClassPathResource("data/excel/Danhmuc_LoaiCSHT_Data.xlsx").getFile();
            if (!resource.exists()) {
                throw new FileNotFoundException("File import khong ton tai!");
            }

            XSSFWorkbook workbook = new XSSFWorkbook(resource);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
                DmLoaiCshtModel model = new DmLoaiCshtModel();
                XSSFRow row = sheet.getRow(i);

                String tenCSHT = row.getCell(0).getStringCellValue();
                String maCSHT = row.getCell(1).getStringCellValue();
                String maDatasite = row.getCell(2).getStringCellValue();
                String ghiChu = row.getCell(3).getStringCellValue();

                model.setTen(tenCSHT);
                if (model.getTen().equals("")) {
                    continue;
                }

                model.setMa(maCSHT);
                model.setGhiChu(ghiChu);
                model.setMaDataSite(maDatasite);

                if (dmLoaiCshtRepository.findByMa(model.getMa()) != null) {
                    System.out.println("\n Loai CSHT " + model.getMa()
                            + " da ton tai -> Khong thuc hien insert tiep");
                    continue;
                }

                dmLoaiCshtRepository.save(dmLoaiCshtService.convertModelToEntity(model));
                System.out.println("\n Import thành công: " + model.getTen());
            }
        } catch (Exception e) {
            System.out.println("\n seeding throw exception:::" + e.getMessage() + "\n");
        }
    }

    private void exitProgram() {
        System.exit(0);
    }
}
