package com.contract.authentication.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.contract.chucvu.chucvu.entity.ChucVuEntity;
import com.contract.chucvu.chucvu.model.ChucVuModel;
import com.contract.chucvu.chucvu.service.ChucVuService;
import com.contract.sys.action.model.ACTION;
import com.contract.sys.module.model.MODULE;

@Component
public class KiemTraQuyenModuleChucVu extends BaseKiemTraQuyenModule {

    private String module = MODULE.CHUC_VU.name();

    @Autowired
    private ChucVuService chucVuService;

    @Override
    public List<Integer> layDanhSachIdPhongDaiDuocQuyenXem() {
        try {
            return new ArrayList(super.layDanhSachPhongDaiId(module, ACTION.XEM.name()));
        } catch (Exception e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> layDanhSachIdPhongDaiDuocQuyenThemMoi() {
        try {
            return new ArrayList(super.layDanhSachPhongDaiId(module, ACTION.THEM_MOI.name()));
        } catch (Exception e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> layDanhSachIdPhongDaiDuocQuyenCapNhat() {
        try {
            return new ArrayList(super.layDanhSachPhongDaiId(module, ACTION.CAP_NHAT.name()));
        } catch (Exception e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> layDanhSachIdPhongDaiDuocQuyenImport() {
        try {
            return new ArrayList(super.layDanhSachPhongDaiId(module, ACTION.IMPORT.name()));
        } catch (Exception e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> layDanhSachIdPhongDaiDuocQuyenXetDuyet() {
        try {
            return new ArrayList(super.layDanhSachPhongDaiId(module, ACTION.XET_DUYET.name()));
        } catch (Exception e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean kiemTraQuyenThemMoi(Object data) {
        try {
            if (nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
                return true;
            }
            ChucVuModel chucVuModel = (ChucVuModel) data;
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenThemMoi();
            if (!idPhongDaiList.contains(chucVuModel.getPhongDaiId())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean kiemTraQuyenCapNhat(Object data, Number id) {
        try {
            if (nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
                return true;
            }
            ChucVuModel chucVuModel = (ChucVuModel) data;
            if (chucVuModel.getPhongDaiId() == null) {
                chucVuModel = ChucVuModel.fromEntity(chucVuService.findById((Long) id), false);
            }
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenCapNhat();
            if (!idPhongDaiList.contains(chucVuModel.getPhongDaiId())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean kiemTraQuyenImport(Object data) {
        try {
            if (nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
                return true;
            }
            ChucVuModel chucVuModel = (ChucVuModel) data;
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenImport();
            if (!idPhongDaiList.contains(chucVuModel.getPhongDaiId())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean kiemTraQuyenXetDuyet(Object data) {
        try {
            if (nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
                return true;
            }
            ChucVuModel chucVuModel = (ChucVuModel) data;
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenXetDuyet();
            if (!idPhongDaiList.contains(chucVuModel.getPhongDaiId())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean kiemTraQuyenXoa(Number id) {
        try {
            ChucVuEntity chucVuEntity = chucVuService.findById((Long) id);
            return kiemTraQuyenCapNhat(chucVuEntity, id);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
