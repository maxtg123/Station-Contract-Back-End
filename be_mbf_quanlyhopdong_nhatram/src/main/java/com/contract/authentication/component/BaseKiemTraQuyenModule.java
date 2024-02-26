package com.contract.authentication.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.contract.chucvu.chucvu.entity.ChucVuEntity;
import com.contract.chucvu.phanquyen.entity.ChucVuPhanQuyenEntity;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.nguoidung.nguoidungkhuvuc.entity.NguoiDungKhuVucEntity;

public abstract class BaseKiemTraQuyenModule {
    @Autowired
    protected NguoiDungService nguoiDungService;

    public Collection<Integer> layDanhSachPhongDaiId(String module, String action) {
        Set<Integer> listIdReturn = new HashSet<>();
        NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();
        if (nguoiDungEntity == null) {
            return listIdReturn;
        }
        List<NguoiDungKhuVucEntity> nguoiDungKhuVucEntityList;
        nguoiDungKhuVucEntityList = new ArrayList<>(
                nguoiDungEntity.getNguoiDungKhuVucEntityList() != null ? nguoiDungEntity.getNguoiDungKhuVucEntityList()
                        : new ArrayList<>());
        if (nguoiDungKhuVucEntityList == null || nguoiDungKhuVucEntityList.size() == 0) {
            return listIdReturn;
        }
        for (int i = 0; i < nguoiDungKhuVucEntityList.size(); i++) {
            NguoiDungKhuVucEntity nguoiDungKhuVucEntity = nguoiDungKhuVucEntityList.get(i);
            if (nguoiDungKhuVucEntity == null) {
                continue;
            }
            ChucVuEntity chucVuEntity = nguoiDungKhuVucEntity.getChucVuEntity();
            if (chucVuEntity == null) {
                continue;
            }
            List<ChucVuPhanQuyenEntity> chucVuPhanQuyenEntityList = new ArrayList<>(
                    chucVuEntity.getListChucVuPhanQuyenEntity());
            if (ObjectUtils.isEmpty(chucVuPhanQuyenEntityList)) {
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

    public abstract List<Integer> layDanhSachIdPhongDaiDuocQuyenXem();

    public abstract List<Integer> layDanhSachIdPhongDaiDuocQuyenThemMoi();

    public abstract List<Integer> layDanhSachIdPhongDaiDuocQuyenCapNhat();

    public abstract List<Integer> layDanhSachIdPhongDaiDuocQuyenImport();

    public abstract List<Integer> layDanhSachIdPhongDaiDuocQuyenXetDuyet();

    public abstract boolean kiemTraQuyenThemMoi(Object Data);

    public abstract boolean kiemTraQuyenCapNhat(Object data, Number id);

    public abstract boolean kiemTraQuyenImport(Object data);

    public abstract boolean kiemTraQuyenXetDuyet(Object data);

    public abstract boolean kiemTraQuyenXoa(Number id);
}
