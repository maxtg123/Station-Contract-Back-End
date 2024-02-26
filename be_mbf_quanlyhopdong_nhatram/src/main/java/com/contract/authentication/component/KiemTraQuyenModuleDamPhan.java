package com.contract.authentication.component;

import com.contract.chucvu.chucvu.service.ChucVuService;
import com.contract.sys.action.model.ACTION;
import com.contract.sys.module.model.MODULE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KiemTraQuyenModuleDamPhan extends BaseKiemTraQuyenModule {

    private String module = MODULE.DAM_PHAN.name();

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

    public List<Integer> layDanhSachIdPhongDaiDuocQuyenGiaoViec() {
        try {
            return new ArrayList(super.layDanhSachPhongDaiId(module, ACTION.GIAO_VIEC.name()));
        } catch (Exception e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    public List<Integer> layDanhSachIdPhongDaiDuocQuyenNhanViec() {
        try {
            return new ArrayList(super.layDanhSachPhongDaiId(module, ACTION.NHAN_VIEC.name()));
        } catch (Exception e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean kiemTraQuyenThemMoi(Object Data) {
        return false;
    }

    @Override
    public boolean kiemTraQuyenCapNhat(Object data, Number id) {
        return false;
    }

    @Override
    public boolean kiemTraQuyenImport(Object data) {
        return false;
    }

    @Override
    public boolean kiemTraQuyenXetDuyet(Object data) {
        return false;
    }

    @Override
    public boolean kiemTraQuyenXoa(Number id) {
        return false;
    }
}
