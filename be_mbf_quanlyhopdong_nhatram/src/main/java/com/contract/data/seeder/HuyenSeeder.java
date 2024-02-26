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
import com.contract.danhmuc.huyen.model.DmHuyenModel;
import com.contract.danhmuc.huyen.service.DmHuyenService;
import com.contract.danhmuc.tinh.service.DmTinhService;

@Component
public class HuyenSeeder implements CommandLineRunner {
    @Value("${seeder.module:0}")
    private String module;

    @Autowired
    private DmHuyenService dmHuyenService;
    @Autowired
    private DmTinhService dmTinhService;

    @Override
    public void run(String... args) throws Exception {
        if (module.equals("huyen")) {
            seeding();
            exitProgram();
        }
    }

    private void seeding() {
        try {
            File resource = new ClassPathResource("data/excel/Danhmuc_Huyen_Data.xlsx").getFile();
            if (!resource.exists()) {
                throw new FileNotFoundException("File import khong ton tai!");
            }

            XSSFWorkbook workbook = new XSSFWorkbook(resource);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
                DmHuyenModel model = new DmHuyenModel();
                XSSFRow row = sheet.getRow(i);

                String tenTinhTP = row.getCell(0).getStringCellValue();
                XSSFCell maQuanHuyen = row.getCell(3);
                String tenQuanHuyen = row.getCell(2).getStringCellValue();
                String cap = row.getCell(4).getStringCellValue();
                XSSFCell datasite = row.getCell(5);

                switch (maQuanHuyen.getCellType()) {
                    case NUMERIC:
                        model.setMa(String.valueOf((int) maQuanHuyen.getNumericCellValue()));
                        if (model.getMa().length() == 1) {
                            model.setMa("00" + model.getMa());
                        } else if (model.getMa().length() == 2) {
                            model.setMa("0" + model.getMa());
                        }
                        break;
                    case STRING:
                        model.setMa(maQuanHuyen.getStringCellValue());
                        break;
                }
                switch (datasite.getCellType()) {
                    case STRING:
                        model.setMaDataSite(datasite.getStringCellValue());
                        break;
                    case NUMERIC:
                        model.setMaDataSite(String.valueOf(datasite.getNumericCellValue()));
                        break;
                }

                // Kiểm tra nếu tỉnh không hợp lệ thì return
                if (tenTinhTP.equals("") || tenTinhTP == null) {
                    throw new Exception("Ten tinh/tp khong duoc null - Cell A" + (i + 1));
                } else {
                    // Kiểm tra tỉnh có tồn tại?
                    if (dmTinhService.kiemTraTinhExists(tenTinhTP) > 0) {
                        int idTinhTP = dmTinhService.findIdByTen(tenTinhTP);
                        if (idTinhTP > 0) {
                            model.setTinhId(idTinhTP);
                        }
                    } else {
                        throw new Exception(
                                "Ten tinh/thanh pho khong ton tai tren he thong - Cell A"
                                        + (i + 1));
                    }
                }
                // Kiểm tra nếu tên huyện không hợp lệ thì return
                if (tenQuanHuyen.equals("") || tenQuanHuyen == null) {
                    throw new Exception("Ten quan/huyen khong duoc null - Cell C" + (i + 1));
                } else {
                    model.setTen(tenQuanHuyen);
                }
                // Kiểm tra huyện nếu chưa tồn tại thì insert vào db
                if (dmHuyenService.findByMa(model.getMa()) != null) {
                    System.out.println("\nQuan/huyen " + model.getTen() + " co ma " + model.getMa()
                            + " da ton tai tren he thong -> Khong insert");
                } else {
                    dmHuyenService.save(model);
                    System.out.println("\nImport thanh cong " + cap + ": " + model.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("\n seeding throw exception::: " + e.getMessage() + "\n");
        }
    }

    private void exitProgram() {
        System.exit(0);
    }
}
