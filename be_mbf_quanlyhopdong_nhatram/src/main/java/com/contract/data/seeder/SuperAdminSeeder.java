package com.contract.data.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.contract.nguoidung.nguoidung.model.GioiTinh;
import com.contract.nguoidung.nguoidung.model.LoaiNguoiDung;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;
import com.contract.nguoidung.nguoidung.model.TrangThai;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Component
public class SuperAdminSeeder implements CommandLineRunner {
    @Value("${seeder.module:0}")
    private String module;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Override
    public void run(String... args) throws Exception {
        if (module.equals("superadmin")) {
            seedingNguoiDung();
            exitProgram();
        }
    }

    private void seedingNguoiDung() {
        NguoiDungModel nguoiDung = new NguoiDungModel();
        nguoiDung.setEmail("superadmin@mobifone.vn");
        nguoiDung.setHoTen("super admin");
        nguoiDung.setMatKhau("HopdongNT@3214$");
        nguoiDung.setGioiTinh(GioiTinh.NAM);
        nguoiDung.setSoDienThoai("");
        nguoiDung.setSoDienThoai("0905xxxxxx");
        nguoiDung.setTrangThai(TrangThai.HOAT_DONG);
        nguoiDung.setLoaiNguoiDung(LoaiNguoiDung.SUPERADMIN);

        try {
            nguoiDungService.createSuperAdmin(nguoiDung);
        } catch (Exception e) {
            System.out.println("seeding nguoi dung throw exception:::" + e.getMessage());
        }
    }

    private void exitProgram() {
        System.exit(0);
    }
}
