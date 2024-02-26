package com.contract.thongbao.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.contract.base.service.BaseService;
import com.contract.common.exception.InvalidDataException;
import com.contract.common.exception.NotFoundException;
import com.contract.common.exception.UnauthorizedException;
import com.contract.hopdong.hopdong.model.HopDongModel;
import com.contract.hopdong.hopdongdamphan_tientrinh.enums.TrangThaiDamPhanEnum;
import com.contract.hopdong.hopdongpheduyet.enums.TrangThaiPheDuyetEnum;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.sys.module.model.MODULE;
import com.contract.thongbao.entity.ThongBaoEntity;
import com.contract.thongbao.model.LoaiThongBaoEnum;
import com.contract.thongbao.model.ThongBaoModel;
import com.contract.thongbao.model.TrangThaiThongBao;
import com.contract.thongbao.repository.ThongBaoRepository;

@Service
public class ThongBaoService extends BaseService {
    @Autowired
    private ThongBaoRepository thongBaoRepository;
    @Autowired
    private NguoiDungService nguoiDungService;

    public Page<ThongBaoEntity> getAll(Long nguoiGuiId, String module, String action,
            TrangThaiThongBao trangThaiThongBao, Integer page, Integer size) {
        NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();
        if (nguoiDungEntity == null) {
            throw new UnauthorizedException();
        }

        Pageable pageable = null;
        if (page != null && size != null) {
            pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        } else {
            pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by("createdAt").descending());
        }

        Page<ThongBaoEntity> thongBaoEntities = thongBaoRepository.findAllWithNguoiDung(nguoiDungEntity.getId(),
                nguoiGuiId, module,
                action, trangThaiThongBao, pageable);

        return thongBaoEntities;
    }

    public ThongBaoModel update(Long id) {
        if (id == null) {
            throw new InvalidDataException();
        }
        ThongBaoEntity toSave = thongBaoRepository.findById(id).orElse(null);
        if (ObjectUtils.isEmpty(toSave)) {
            throw new NotFoundException();
        }
        toSave.setTrangThai(TrangThaiThongBao.XEM);

        return save(toSave);
    }

    public ThongBaoModel create(ThongBaoModel model) {
        ThongBaoEntity toSave = new ThongBaoEntity();
        toSave = convertModelToEntity(toSave, model);

        return save(toSave);
    }

    public ThongBaoModel save(ThongBaoEntity entity) {
        try {
            ThongBaoEntity saved = thongBaoRepository.save(entity);
            return ThongBaoModel.fromEntity(saved, false);
        } catch (Exception e) {
            System.out.println(e);
            throw new InvalidDataException();
        }
    }

    public ThongBaoModel createThongBaoHopDongXetDuyet(HopDongModel hopDongModel,
            TrangThaiPheDuyetEnum trangThaiPheDuyet,
            Long nguoiNhanId, Long nguoiGuiId) {
        if (hopDongModel == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        JSONObject hopDongObject = new JSONObject();
        hopDongObject.put("id", hopDongModel.getId());
        // TODO fix it
        // hopDongObject.put("tramId", hopDongModel.getTramId());
        hopDongObject.put("soHopDong", hopDongModel.getSoHopDong());
        hopDongObject.put("loaiHopDong", hopDongModel.getLoaiHopDong());
        hopDongObject.put("soHopDongErp", hopDongModel.getSoHopDongErp());
        jsonObject.put("data", hopDongObject);
        Map<String, Object> hopDongContent = new HashMap<>();
        hopDongContent.put("id", hopDongModel.getId());

        ThongBaoModel thongBaoModel = new ThongBaoModel();
        thongBaoModel.setModule(MODULE.HOP_DONG.name());
        String loaiThongBao = null;
        if (TrangThaiPheDuyetEnum.CHO_PHE_DUYET.equals(trangThaiPheDuyet)) {
            loaiThongBao = LoaiThongBaoEnum.XET_DUYET_CHO_PHE_DUYET.name();
        } else if (TrangThaiPheDuyetEnum.PHE_DUYET.equals(trangThaiPheDuyet)) {
            loaiThongBao = LoaiThongBaoEnum.XET_DUYET_PHE_DUYET.name();
        } else if (TrangThaiPheDuyetEnum.TU_CHOI.equals(trangThaiPheDuyet)) {
            loaiThongBao = LoaiThongBaoEnum.XET_DUYET_TU_CHOI.name();
        } else if (TrangThaiPheDuyetEnum.GUI_LAI.equals(trangThaiPheDuyet)) {
            loaiThongBao = LoaiThongBaoEnum.XET_DUYET_GUI_LAI.name();
        }
        thongBaoModel.setAction(loaiThongBao);
        if (nguoiGuiId != null) {
            thongBaoModel.setNguoiGuiId(nguoiGuiId);
        } // null is System
        thongBaoModel.setNguoiNhanId(nguoiNhanId);
        thongBaoModel.setContent(jsonObject.toString());
        thongBaoModel.setTrangThai(TrangThaiThongBao.CHUA_XEM);

        return create(thongBaoModel);
    }

    public ThongBaoModel createThongBaoHopDongDamPhan(HopDongModel hopDongModel,
            TrangThaiDamPhanEnum trangThaiDamPhan,
            Long nguoiNhanId, Long nguoiGuiId) {
        if (hopDongModel == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        JSONObject hopDongObject = new JSONObject();
        hopDongObject.put("id", hopDongModel.getId());
        // TODO fix it
        // hopDongObject.put("tramId", hopDongModel.getTramId());
        hopDongObject.put("soHopDong", hopDongModel.getSoHopDong());
        hopDongObject.put("loaiHopDong", hopDongModel.getLoaiHopDong());
        hopDongObject.put("soHopDongErp", hopDongModel.getSoHopDongErp());
        jsonObject.put("data", hopDongObject);
        Map<String, Object> hopDongContent = new HashMap<>();
        hopDongContent.put("id", hopDongModel.getId());

        ThongBaoModel thongBaoModel = new ThongBaoModel();
        thongBaoModel.setModule(MODULE.DAM_PHAN.name());
        String loaiThongBao = null;
        if (TrangThaiDamPhanEnum.GUI_NOI_DUNG_DAM_PHAN.equals(trangThaiDamPhan)) {
            loaiThongBao = LoaiThongBaoEnum.DAM_PHAN_GUI_NOI_DUNG_DAM_PHAN.name();
        } else if (TrangThaiDamPhanEnum.TU_CHOI.equals(trangThaiDamPhan)) {
            loaiThongBao = LoaiThongBaoEnum.DAM_PHAN_TU_CHOI.name();
        } else if (TrangThaiDamPhanEnum.PHE_DUYET.equals(trangThaiDamPhan)) {
            loaiThongBao = LoaiThongBaoEnum.DAM_PHAN_PHE_DUYET.name();
        } else {
            loaiThongBao = LoaiThongBaoEnum.DAM_PHAN_GIAO_VIEC.name();
        }
        thongBaoModel.setAction(loaiThongBao);
        if (nguoiGuiId != null) {
            thongBaoModel.setNguoiGuiId(nguoiGuiId);
        } // null is System
        thongBaoModel.setNguoiNhanId(nguoiNhanId);
        thongBaoModel.setContent(jsonObject.toString());
        thongBaoModel.setTrangThai(TrangThaiThongBao.CHUA_XEM);

        return create(thongBaoModel);
    }

    private ThongBaoEntity convertModelToEntity(ThongBaoEntity entity, ThongBaoModel model) {
        entity.setModule(model.getModule());
        entity.setAction(model.getAction());
        entity.setNguoiNhanId(model.getNguoiNhanId());
        entity.setContent(model.getContent());
        entity.setTrangThai(model.getTrangThai());
        entity.setNguoiGuiId(model.getNguoiGuiId());

        return entity;
    }
}
