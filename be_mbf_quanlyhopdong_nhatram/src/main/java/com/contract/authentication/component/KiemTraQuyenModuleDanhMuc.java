package com.contract.authentication.component;

import com.contract.chucvu.chucvu.entity.ChucVuEntity;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.nguoidung.nguoidungkhuvuc.entity.NguoiDungKhuVucEntity;
import com.contract.nguoidung.nguoidungkhuvuc.model.LoaiKhuVuc;
import com.contract.chucvu.phanquyen.entity.ChucVuPhanQuyenEntity;
import com.contract.sys.action.model.ACTION;
import com.contract.sys.module.model.MODULE;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class KiemTraQuyenModuleDanhMuc extends BaseKiemTraQuyenModule {

    private String module = MODULE.DANH_MUC.name();

    public Collection<Integer> layDanhSachPhongDaiId(String module, String action) {
        Set<Integer> listIdReturn = new HashSet<>();
        NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();
        if (nguoiDungEntity == null) {
            return listIdReturn;
        }
        List<NguoiDungKhuVucEntity> nguoiDungKhuVucEntityList;
        nguoiDungKhuVucEntityList = new ArrayList<>(nguoiDungEntity.getNguoiDungKhuVucEntityList() != null ? nguoiDungEntity.getNguoiDungKhuVucEntityList() : new ArrayList<>());
        if (nguoiDungKhuVucEntityList == null) {
            return listIdReturn;
        }
        for (int i = 0; i < nguoiDungKhuVucEntityList.size(); i++) {
            NguoiDungKhuVucEntity nguoiDungKhuVucEntity = nguoiDungKhuVucEntityList.get(i);
            if (nguoiDungKhuVucEntity == null || !LoaiKhuVuc.CHINH.equals(nguoiDungKhuVucEntity.getLoai())) {
                continue;
            }
            ChucVuEntity chucVuEntity = nguoiDungKhuVucEntity.getChucVuEntity();
            if (chucVuEntity == null) {
                continue;
            }
            List<ChucVuPhanQuyenEntity> chucVuPhanQuyenEntityList = new ArrayList<>(chucVuEntity.getListChucVuPhanQuyenEntity());
            if (chucVuPhanQuyenEntityList == null) {
                continue;
            }

            chucVuPhanQuyenEntityList.forEach(e -> {
                if (e.getModule().equals(module) && e.getAction().equals(action)) {
                    listIdReturn.add(nguoiDungKhuVucEntity.getPhongDaiId());
                }
            });
        }
        return listIdReturn;
    }

    @Override
    public List<Integer> layDanhSachIdPhongDaiDuocQuyenXem() {
        try {
            return new ArrayList(layDanhSachPhongDaiId(module, ACTION.XEM.name()));
        } catch (Exception e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> layDanhSachIdPhongDaiDuocQuyenThemMoi() {
        try {
            return new ArrayList(layDanhSachPhongDaiId(module, ACTION.THEM_MOI.name()));
        } catch (Exception e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> layDanhSachIdPhongDaiDuocQuyenCapNhat() {
        try {
            return new ArrayList(layDanhSachPhongDaiId(module, ACTION.CAP_NHAT.name()));
        } catch (Exception e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> layDanhSachIdPhongDaiDuocQuyenImport() {
        try {
            return new ArrayList(layDanhSachPhongDaiId(module, ACTION.IMPORT.name()));
        } catch (Exception e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> layDanhSachIdPhongDaiDuocQuyenXetDuyet() {
        try {
            return new ArrayList(layDanhSachPhongDaiId(module, ACTION.XET_DUYET.name()));
        } catch (Exception e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    public boolean kiemTraQuyenXem() {
        try {
            if (nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
                return true;
            }
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenXem();
            if (ObjectUtils.isEmpty(idPhongDaiList)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean kiemTraQuyenThemMoi(Object Data) {
        try {
            if (nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
                return true;
            }
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenThemMoi();
            if (ObjectUtils.isEmpty(idPhongDaiList)) {
                return false;
            } else {
                return true;
            }
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
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenCapNhat();
            if (ObjectUtils.isEmpty(idPhongDaiList)) {
                return false;
            } else {
                return true;
            }
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
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenImport();
            if (ObjectUtils.isEmpty(idPhongDaiList)) {
                return false;
            } else {
                return true;
            }
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
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenXetDuyet();
            if (ObjectUtils.isEmpty(idPhongDaiList)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean kiemTraQuyenXoa(Number id) {
        try {
            if (nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
                return true;
            }
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenCapNhat();
            if (ObjectUtils.isEmpty(idPhongDaiList)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


}
