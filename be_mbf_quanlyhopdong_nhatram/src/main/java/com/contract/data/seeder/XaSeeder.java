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
import com.contract.danhmuc.huyen.service.DmHuyenService;
import com.contract.danhmuc.tinh.service.DmTinhService;
import com.contract.danhmuc.xa.model.DmXaModel;
import com.contract.danhmuc.xa.service.DmXaService;

@Component
public class XaSeeder implements CommandLineRunner {
    @Value("${seeder.module:0}")
    private String module;

    @Autowired
    private DmXaService dmXaService;
    @Autowired
    private DmHuyenService dmHuyenService;
    @Autowired
    private DmTinhService dmTinhService;

    @Override
    public void run(String... args) throws Exception {
        if (module.equals("xa")) {
            seeding();
            exitProgram();
        }
    }

    private void seeding() {
        try {
            File resource = new ClassPathResource("data/excel/Danhmuc_Xa_Data.xlsx").getFile();
            if (!resource.exists()) {
                throw new FileNotFoundException("File import khong ton tai!");
            }
            XSSFWorkbook workbook = new XSSFWorkbook(resource);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int soLuongXaImportThanhCong = 0;
            for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
                DmXaModel model = new DmXaModel();
                XSSFRow row = sheet.getRow(i);

                String tenTinhTP = row.getCell(0).getStringCellValue();
                String tenQuanHuyen = row.getCell(2).getStringCellValue();
                XSSFCell maPhuongXa = row.getCell(5);
                String tenPhuongXa = row.getCell(4).getStringCellValue();
                String cap = row.getCell(6).getStringCellValue();
                XSSFCell datasite = row.getCell(7);
                String ghiChu = row.getCell(8).getStringCellValue();

                switch (maPhuongXa.getCellType()) {
                    case STRING:
                        model.setMa(maPhuongXa.getStringCellValue());
                        break;
                    case NUMERIC:
                        model.setMa(String.valueOf((int) maPhuongXa.getNumericCellValue()));
                        if (model.getMa().length() == 1) {
                            model.setMa("0000" + model.getMa());
                        } else if (model.getMa().length() == 2) {
                            model.setMa("000" + model.getMa());
                        } else if (model.getMa().length() == 3) {
                            model.setMa("00" + model.getMa());
                        } else if (model.getMa().length() == 4) {
                            model.setMa("0" + model.getMa());
                        }
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

                // Kiểm tra tên quận/huyện null thì return
                if (tenQuanHuyen.isEmpty() || tenQuanHuyen == null) {
                    throw new Exception("Ten quan/huyen khong duoc null - Cell C" + (i + 1));
                } else if (dmHuyenService.kiemTraHuyenExists(tenQuanHuyen) == 0) {
                    throw new Exception(
                            "Ten quan/huyen không ton tai tren he thong - Cell C" + (i + 1));
                }

                // Kiểm tra tên phường/xã null thì return
                if (tenPhuongXa.isEmpty() || tenPhuongXa == null) {
                    switch (tenQuanHuyen) {
                        case "Huyện Bạch Long Vĩ":
                            model.setTen("*");
                            model.setMa("*");
                            break;
                        case "Huyện Cồn Cỏ":
                            model.setTen("*");
                            model.setMa("*");
                            break;
                        case "Huyện Hoàng Sa":
                            model.setTen("*");
                            model.setMa("*");
                            break;
                        case "Huyện Lý Sơn":
                            model.setTen("*");
                            model.setMa("*");
                            break;
                        case "Huyện Côn Đảo":
                            model.setTen("*");
                            model.setMa("*");
                            break;
                        default:
                            throw new Exception("Ten phuong/xa khong duoc null - Cell E" + (i + 1));
                    }
                } else {
                    model.setTen(tenPhuongXa);
                }

                // Kiểm tra tên quận/huyện
                int idQuanHuyen = 0;
                if (dmHuyenService.kiemTraHuyenExists(tenQuanHuyen) == 1) {
                    idQuanHuyen = this.getIdHuyen(tenQuanHuyen);
                } else {
                    // Nếu có nhiều hơn 1 huyện trùng tên thì xét đến tỉnh
                    if (tenTinhTP.equals("") || tenTinhTP == null) {
                        throw new Exception("Ten tinh/tp khong duoc null - Cell A" + (i + 1));
                    } else {
                        // Kiểm tra tỉnh có tồn tại?
                        if (dmTinhService.kiemTraTinhExists(tenTinhTP) > 0) {
                            int idTinhTP = this.getIdTinh(tenTinhTP);
                            if (idTinhTP > 0) {
                                idQuanHuyen = this.getIdHuyenCaseHuyenMul(tenQuanHuyen, idTinhTP);
                            }
                        } else {
                            throw new Exception(
                                    "Ten tinh/thanh pho khong ton tai tren he thong - Cell A"
                                            + (i + 1));
                        }
                    }
                }
                model.setHuyenId(idQuanHuyen);
                model.setGhiChu(ghiChu);

                // Kiểm tra xã nếu chưa tồn tại thì insert vào db
                if (dmXaService.kiemTraXaTonTai(model.getTen(), model.getHuyenId()) != null) {
                    System.out.println("Phuong/xa " + model.getTen() + " thuoc quan/huyen "
                            + tenQuanHuyen + " da ton tai tren he thong -> Khong insert");
                } else {
                    dmXaService.save(model);
                    System.out.println("\nImport thanh cong " + cap + ": " + model.toString());
                    soLuongXaImportThanhCong += 1;
                }
            }
            System.out.println("\nSo luong xa da import thanh cong: " + soLuongXaImportThanhCong);
        } catch (Exception e) {
            System.out.println("\n seeding throw exception::: " + e.getMessage() + "\n");
        }
    }

    private int getIdTinh(String tenTinh) {
        int idTinhTP = dmTinhService.findIdByTen(tenTinh);
        System.out.println("Id của tỉnh '" + tenTinh + "': " + idTinhTP);
        return idTinhTP;
    }

    private int getIdHuyen(String tenHuyen) {
        int idQuanHuyen = dmHuyenService.findIdByTen(tenHuyen);
        System.out.println("Id của huyện '" + tenHuyen + "': " + idQuanHuyen);
        return idQuanHuyen;
    }

    private int getIdHuyenCaseHuyenMul(String tenHuyen, int idTinhTP) {
        int idQuanHuyen = dmHuyenService.findIdByTenAndTinhId(tenHuyen, idTinhTP);
        System.out.println("Id của huyện '" + tenHuyen + "': " + idQuanHuyen
                + " (Trường hợp huyện có tên trùng)");
        return idQuanHuyen;
    }

    private void exitProgram() {
        System.exit(0);
    }
}
