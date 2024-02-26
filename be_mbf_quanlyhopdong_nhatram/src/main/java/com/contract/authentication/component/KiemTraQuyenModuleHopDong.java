package com.contract.authentication.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.contract.hopdong.hopdong.model.HopDongModel;
import com.contract.sys.action.model.ACTION;
import com.contract.sys.module.model.MODULE;
import com.contract.tram.tram.service.TramService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KiemTraQuyenModuleHopDong extends BaseKiemTraQuyenModule {

    private String module = MODULE.HOP_DONG.name();
    @Autowired
    private TramService tramService;

    @Override
    public List<Integer> layDanhSachIdPhongDaiDuocQuyenXem() {
        try {
            return new ArrayList(super.layDanhSachPhongDaiId(module, ACTION.XEM.name()));
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> layDanhSachIdPhongDaiDuocQuyenThemMoi() {
        try {
            return new ArrayList(super.layDanhSachPhongDaiId(module, ACTION.THEM_MOI.name()));
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> layDanhSachIdPhongDaiDuocQuyenCapNhat() {
        try {
            return new ArrayList(super.layDanhSachPhongDaiId(module, ACTION.CAP_NHAT.name()));
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> layDanhSachIdPhongDaiDuocQuyenImport() {
        try {
            return new ArrayList(super.layDanhSachPhongDaiId(module, ACTION.IMPORT.name()));
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> layDanhSachIdPhongDaiDuocQuyenXetDuyet() {
        try {
            return new ArrayList(super.layDanhSachPhongDaiId(module, ACTION.XET_DUYET.name()));
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    @Override
    public boolean kiemTraQuyenThemMoi(Object data) {
        try {
            if (nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
                return true;
            }
            ObjectMapper mapper = new ObjectMapper();
            HopDongModel hopDongModelInput = mapper.readValue((String) data, HopDongModel.class);
            if (hopDongModelInput.getHopDongTramList() == null || hopDongModelInput.getHopDongTramList().size() == 0) {
                return false;
            }
            Set<Long> tramInputIds = hopDongModelInput.getHopDongTramList().stream().map(hdTram -> hdTram.getTramId())
                    .collect(Collectors.toSet());
            Set<Integer> pdIds = tramService.findAllByIdIn(tramInputIds).stream()
                    .map(tram -> tram.getPhongDaiId())
                    .collect(Collectors.toSet());
            // check tram input and tram db
            // if (tramInputIds.size() != pdIds.size()) {
            // return false;
            // }
            // check khu vuc
            List<Integer> idPhongDaiKhuVucList = layDanhSachIdPhongDaiDuocQuyenThemMoi();
            for (Integer pdId : pdIds) {
                if (!idPhongDaiKhuVucList.contains(pdId)) {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean kiemTraQuyenCapNhat(Object data, Number id) {
        try {
            if (nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
                return true;
            }
            ObjectMapper mapper = new ObjectMapper();
            HopDongModel hopDongModelInput = mapper.readValue((String) data, HopDongModel.class);
            if (hopDongModelInput.getHopDongTramList() == null || hopDongModelInput.getHopDongTramList().size() == 0) {
                return false;
            }
            // HopDongEntity hopDongEntity = hopDongRepository.findById((Long)
            // id).orElse(null);

            Set<Long> tramInputIds = hopDongModelInput.getHopDongTramList().stream().map(hdTram -> hdTram.getTramId())
                    .collect(Collectors.toSet());
            Set<Integer> pdIds = tramService.findAllByIdIn(tramInputIds).stream()
                    .map(tram -> tram.getPhongDaiId())
                    .collect(Collectors.toSet());
            // check tram input and tram db
            // if (tramInputIds.size() != pdIds.size()) {
            // return false;
            // }
            // check khu vuc
            List<Integer> idPhongDaiKhuVucList = layDanhSachIdPhongDaiDuocQuyenThemMoi();
            for (Integer pdId : pdIds) {
                if (!idPhongDaiKhuVucList.contains(pdId)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean kiemTraQuyenImport(Object data) {
        try {
            if (nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
                return true;
            }
            HopDongModel hopDongModel = (HopDongModel) data;
            Integer phongDaiId = null;
            // if (hopDongModel.getTramId() != null) {
            // TramEntity tramEntity = tramService.findById(hopDongModel.getTramId());
            // phongDaiId = tramEntity.getPhongDaiId();
            // } else {
            // return false;
            // }
            List<Integer> idPhongDaiList = layDanhSachIdPhongDaiDuocQuyenImport();
            if (!idPhongDaiList.contains(phongDaiId)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean kiemTraQuyenXetDuyet(Object data) {
        try {
            if (nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
                return true;
            }
            ObjectMapper mapper = new ObjectMapper();
            HopDongModel hopDongModelInput = mapper.readValue((String) data, HopDongModel.class);
            if (hopDongModelInput.getHopDongTramList() == null || hopDongModelInput.getHopDongTramList().size() == 0) {
                return false;
            }

            Set<Long> tramInputIds = hopDongModelInput.getHopDongTramList().stream().map(hdTram -> hdTram.getTramId())
                    .collect(Collectors.toSet());
            Set<Integer> pdIds = tramService.findAllByIdIn(tramInputIds).stream()
                    .map(tram -> tram.getPhongDaiId())
                    .collect(Collectors.toSet());
            List<Integer> idPhongDaiKhuVucList = layDanhSachIdPhongDaiDuocQuyenXetDuyet();
            for (Integer pdId : pdIds) {
                if (!idPhongDaiKhuVucList.contains(pdId)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean kiemTraQuyenXoa(Number id) {
        try {
            // HopDongEntity hopDongEntity = hopDongService.findById((Long) id);
            // return kiemTraQuyenCapNhat(hopDongEntity, id);
            return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
