package com.contract.authentication.component;

import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.nguoidung.nguoidungkhuvuc.entity.NguoiDungKhuVucEntity;
import com.contract.nguoidung.nguoidungkhuvuc.model.NguoiDungKhuVucModel;
import com.contract.sys.action.model.ACTION;
import com.contract.sys.module.model.MODULE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class KiemTraQuyenModuleNguoiDung extends BaseKiemTraQuyenModule {

    private String module = MODULE.NGUOI_DUNG.name();

    @Autowired
    private NguoiDungService nguoiDungService;

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
            NguoiDungModel nguoiDungModel = (NguoiDungModel) data;
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenThemMoi();
            List<NguoiDungKhuVucModel> nguoiDungKhuVucModelList = nguoiDungModel.getNguoiDungKhuVucList();

            if (nguoiDungKhuVucModelList == null) {
                return false;
            }

            AtomicBoolean flag = new AtomicBoolean(false);

            nguoiDungKhuVucModelList.forEach(m -> {
                if (idPhongDaiList.contains(m.getPhongDaiId())) {
                    flag.set(true);
                }
            });

            return flag.get();
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
            NguoiDungModel nguoiDungModel = (NguoiDungModel) data;
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenCapNhat();
            List<NguoiDungKhuVucModel> nguoiDungKhuVucModelList = nguoiDungModel.getNguoiDungKhuVucList();

            if (nguoiDungKhuVucModelList == null) {
                nguoiDungKhuVucModelList = new ArrayList<>();
                NguoiDungEntity nguoiDungEntity = nguoiDungService.findById((Long) id);
                if (nguoiDungEntity.getNguoiDungKhuVucEntityList() == null) {
                    return false;
                }
                List<NguoiDungKhuVucEntity> nguoiDungKhuVucEntityList = new ArrayList<>(nguoiDungEntity.getNguoiDungKhuVucEntityList() != null ? nguoiDungEntity.getNguoiDungKhuVucEntityList() : new ArrayList<>());
                for (int i = 0; i < nguoiDungKhuVucEntityList.size(); i++) {
                    nguoiDungKhuVucModelList.add(NguoiDungKhuVucModel.fromEntity(nguoiDungKhuVucEntityList.get(i), false));
                }
            }

            AtomicBoolean flag = new AtomicBoolean(false);

            nguoiDungKhuVucModelList.forEach(m -> {
                if (idPhongDaiList.contains(m.getPhongDaiId())) {
                    flag.set(true);
                }
            });

            return flag.get();
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
            NguoiDungModel nguoiDungModel = (NguoiDungModel) data;
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenImport();
            List<NguoiDungKhuVucModel> nguoiDungKhuVucModelList = nguoiDungModel.getNguoiDungKhuVucList();

            if (nguoiDungKhuVucModelList == null) {
                return false;
            }

            AtomicBoolean flag = new AtomicBoolean(false);

            nguoiDungKhuVucModelList.forEach(m -> {
                if (idPhongDaiList.contains(m.getPhongDaiId())) {
                    flag.set(true);
                }
            });

            return flag.get();
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
            NguoiDungModel nguoiDungModel = (NguoiDungModel) data;
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenXetDuyet();
            List<NguoiDungKhuVucModel> nguoiDungKhuVucModelList = nguoiDungModel.getNguoiDungKhuVucList();

            if (nguoiDungKhuVucModelList == null) {
                return false;
            }

            AtomicBoolean flag = new AtomicBoolean(false);

            nguoiDungKhuVucModelList.forEach(m -> {
                if (idPhongDaiList.contains(m.getPhongDaiId())) {
                    flag.set(true);
                }
            });

            return flag.get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean kiemTraQuyenXoa(Number id) {
        try {
            NguoiDungEntity nguoiDungEntity = nguoiDungService.findById((Long) id);
            return kiemTraQuyenCapNhat(nguoiDungEntity, id);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
