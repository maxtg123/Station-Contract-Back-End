package com.contract.authentication.component;

import com.contract.sys.action.model.ACTION;
import com.contract.sys.module.model.MODULE;
import com.contract.tram.tram.entity.TramEntity;
import com.contract.tram.tram.model.TramModel;
import com.contract.tram.tram.service.TramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KiemTraQuyenModuleTram extends BaseKiemTraQuyenModule {

    private String module = MODULE.TRAM.name();

    @Autowired
    private TramService tramService;

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
            TramModel tramModel = (TramModel) data;
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenThemMoi();
            if (!idPhongDaiList.contains(tramModel.getPhongDaiId())) {
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
            TramModel tramModel = (TramModel) data;
            if (tramModel.getPhongDaiId() == null) {
                tramModel = TramModel.fromEntity(tramService.findById((Long) id), false);
            }
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenCapNhat();
            if (!idPhongDaiList.contains(tramModel.getPhongDaiId())) {
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
            TramModel tramModel = (TramModel) data;
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenImport();
            if (!idPhongDaiList.contains(tramModel.getPhongDaiId())) {
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
            TramModel tramModel = (TramModel) data;
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenXetDuyet();
            if (!idPhongDaiList.contains(tramModel.getPhongDaiId())) {
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
            TramEntity tramEntity = tramService.findById((Long) id);
            return kiemTraQuyenCapNhat(tramEntity, id);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
