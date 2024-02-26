package com.contract.data.seeder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import com.contract.danhmuc.phongdai.entity.DmPhongDaiEntity;
import com.contract.danhmuc.phongdai.service.DmPhongDaiService;
import com.contract.danhmuc.to.entity.DmToEntity;
import com.contract.danhmuc.to.repository.DmToRepository;
import com.contract.danhmuc.to.service.DmToService;

@Component
public class ToSeeder implements CommandLineRunner {
    @Value("${seeder.module:0}")
    private String module;

    @Autowired
    private DmToService dmToService;

    @Autowired
    private DmToRepository dmToRepository;

    @Autowired
    private DmPhongDaiService dmPhongDaiService;


    @Override
    public void run(String... args) throws Exception {
        if (module.equals("to")) {
            seeding();
            exitProgram();
        }
    }

    private void seeding() {
        List<String> listFail = new ArrayList<String>();
        try {
            File resource = new ClassPathResource("data/excel/Danhmuc_To_Data.xlsx").getFile();
            if (!resource.exists()) {
                throw new FileNotFoundException("File import khong ton tai!");
            }

            XSSFWorkbook workbook = new XSSFWorkbook(resource);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
                // DmToModel model = new DmToModel();

                XSSFRow row = sheet.getRow(i);

                String tenTo = row.getCell(0).getStringCellValue();
                String tenVietTat = row.getCell(1).getStringCellValue();
                String tenPhongDai = row.getCell(2).getStringCellValue();
                String maDataSite = row.getCell(3).getStringCellValue();
                String ghiChu = row.getCell(4).getStringCellValue();

                if (tenTo.isEmpty() || tenTo == null) {
                    throw new Exception("Tên tổ không được null - Cell A" + (i + 1));
                }

                if (tenPhongDai.isEmpty() || tenPhongDai == null) {
                    throw new Exception("Tên phòng đài không được null - Cell C" + (i + 1));
                }

                if (dmPhongDaiService.kiemTraPhongDaiExists(tenPhongDai) > 0) {
                    DmToEntity entityToSave = new DmToEntity();
                    DmToEntity entity = dmToService.findByTen(tenTo);

                    int phongDaiId = dmPhongDaiService.findIdByTen(tenPhongDai);
                    DmPhongDaiEntity dmPhongDai = new DmPhongDaiEntity();
                    dmPhongDai.setId(phongDaiId);

                    entityToSave.setTen(tenTo);
                    entityToSave.setTenVietTat(tenVietTat);
                    entityToSave.setPhongDai(dmPhongDai);
                    entityToSave.setMaDataSite(maDataSite);
                    entityToSave.setGhiChu(ghiChu);

                    if (entity != null) {
                        entityToSave.setId(entity.getId());
                    }

                    dmToRepository.save(entityToSave);


                } else {

                }
            }
        } catch (Exception e) {
            System.out.println("\n seeding throw exception:::" + e.getMessage() + "\n");
        }

        if (listFail.size() > 0) {
            System.out.println("\nDanh sách import fail: ");
            for (String tenTo : listFail) {
                System.out.println("Tổ: " + tenTo + ". Id_phong_dai insert null");
            }
        }
    }

    private void exitProgram() {
        System.exit(0);
    }
}
