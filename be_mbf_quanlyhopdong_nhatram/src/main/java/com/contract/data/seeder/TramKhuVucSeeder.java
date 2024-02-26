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
import com.contract.danhmuc.tramkhuvuc.model.DmTramKhuVucModel;
import com.contract.danhmuc.tramkhuvuc.service.DmTramKhuVucService;

@Component
public class TramKhuVucSeeder implements CommandLineRunner {
    @Value("${seeder.module:0}")
    private String module;

    @Autowired
    private DmTramKhuVucService dmTramKhuVucService;

    @Override
    public void run(String... args) throws Exception {
        if (module.equals("tram_khu_vuc")) {
            seeding();
            exitProgram();
        }
    }

    private void seeding() {
        try {
            File resource =
                    new ClassPathResource("data/excel/Danhmuc_TramKhuVuc_Data.xlsx").getFile();
            if (!resource.exists()) {
                throw new FileNotFoundException("File import khong ton tai!");
            }

            XSSFWorkbook workbook = new XSSFWorkbook(resource);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
                DmTramKhuVucModel model = new DmTramKhuVucModel();
                XSSFRow row = sheet.getRow(i);

                XSSFCell maTramKhuVuc = row.getCell(1);
                String tenTramKhuVuc = row.getCell(0).getStringCellValue();
                String ghiChu = row.getCell(4).getStringCellValue();

                if (tenTramKhuVuc.equals("") || tenTramKhuVuc == null) {
                    throw new Exception("Ten tram khu vuc khong duoc null - Cell A" + (i + 1));
                } else {
                    model.setTen(tenTramKhuVuc);
                }

                switch (maTramKhuVuc.getCellType()) {
                    case STRING:
                        model.setMa(maTramKhuVuc.getStringCellValue());
                        break;
                    case NUMERIC:
                        model.setMa(String.valueOf((int) maTramKhuVuc.getNumericCellValue()));
                        break;
                    default:
                        break;
                }

                if (dmTramKhuVucService.findByTen(model.getTen()) != null) {
                    System.out.println("\nTram khu vuc " + model.getTen()
                            + " da ton tai tren he thong -> Khong insert");
                } else {
                    model.setGhiChu(ghiChu);
                    dmTramKhuVucService.save(model);
                    System.out.println("\nImport thanh cong tram khu vuc " + model.toString());
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
