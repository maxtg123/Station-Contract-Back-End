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
import com.contract.danhmuc.phongdai.entity.DmPhongDaiEntity;
import com.contract.danhmuc.phongdai.repository.DmPhongDaiRepository;
import com.contract.danhmuc.phongdai.service.DmPhongDaiService;

@Component
public class PhongDaiSeeder implements CommandLineRunner {
    @Value("${seeder.module:0}")
    private String module;

    @Autowired
    private DmPhongDaiRepository dmPhongDaiRepository;

    @Autowired
    private DmPhongDaiService dmPhongDaiService;

    @Override
    public void run(String... args) throws Exception {
        if (module.equals("phong_dai")) {
            seeding();
            exitProgram();
        }
    }

    private void seeding() {
        try {
            File resource =
                    new ClassPathResource("data/excel/Danhmuc_PhongDai_Data.xlsx").getFile();
            if (!resource.exists()) {
                throw new FileNotFoundException("File import khong ton tai!");
            }

            XSSFWorkbook workbook = new XSSFWorkbook(resource);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {

                XSSFRow row = sheet.getRow(i);

                String tenPhongDai = row.getCell(0).getStringCellValue();
                String maDataSite = row.getCell(1).getStringCellValue();
                String tenVietTat = row.getCell(2).getStringCellValue();
                String loai = row.getCell(3).getStringCellValue();
                String ghiChu = row.getCell(4).getStringCellValue();

                if (tenPhongDai == null || tenPhongDai.isEmpty()) {
                    throw new Exception("Tên phòng đài không được null - Cell A" + (i + 1));
                }
                if (tenVietTat == null || tenVietTat.isEmpty()) {
                    throw new Exception("Ten viết tắt không được null - Cell C" + (i + 1));
                }

                DmPhongDaiEntity entityToSave = new DmPhongDaiEntity();
                DmPhongDaiEntity entity = dmPhongDaiService.findByTenVietTat(tenVietTat);

                entityToSave.setMaDataSite(maDataSite);
                entityToSave.setTenVietTat(tenVietTat);
                entityToSave.setTen(tenPhongDai);
                entityToSave.setLoai(loai);
                entityToSave.setGhiChu(ghiChu);
                if (entity != null) {
                    entityToSave.setId(entity.getId());
                }
                dmPhongDaiRepository.save(entityToSave);
                System.out.println("\nImport thanh cong phong dai " + entityToSave.toString());
            }
        } catch (Exception e) {
            System.out.println("\n seeding throw exception:::" + e.getMessage() + "\n");
        }
    }

    private void exitProgram() {
        System.exit(0);
    }
}
