package com.contract.data.seeder;

import com.contract.danhmuc.doituongkyhopdong.service.DmDoiTuongKyHopDongService;
import com.contract.danhmuc.hinhthucthanhtoan.model.DmHinhThucThanhToanModel;
import com.contract.danhmuc.hinhthucthanhtoan.service.DmHinhThucThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DmHinhThucThanhToanSeeder implements CommandLineRunner {
    @Value("${seeder.module:0}")
    private String module;

    @Autowired
    private DmHinhThucThanhToanService dmHinhThucThanhToanService;

    @Override
    public void run(String... args) throws Exception {
        if (module.equals("hinh_thuc_thanh_toan")) {
            seeding();
            exitProgram();
        }
    }

    private void seeding() {
        try {
            DmHinhThucThanhToanModel model = new DmHinhThucThanhToanModel();
            model.setTen("Chuyển khoản");
            dmHinhThucThanhToanService.save(model);
        } catch (Exception e) {
            System.out.println("\n seeding throw exception:::" + e.getMessage() + "\n");
        }
    }

    private void exitProgram() {
        System.exit(0);
    }
}
