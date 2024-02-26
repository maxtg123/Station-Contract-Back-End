package com.contract.danhmuc.loaihangmuccsht.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.common.exception.BadRequestException;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.common.exception.InvalidDataException;
import com.contract.common.exception.NotFoundException;
import com.contract.danhmuc.loaihangmuccsht.entity.DmLoaiHangMucCSHTEntity;
import com.contract.danhmuc.loaihangmuccsht.model.DmLoaiHangMucCSHTModel;
import com.contract.danhmuc.loaihangmuccsht.repository.DmLoaiHangMucCSHTRepository;
import com.contract.hopdong.hopdongtram_dungchung.repository.HopDongTramDungChungRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class DmLoaiHangMucCSHTService {
    @Autowired
    private DmLoaiHangMucCSHTRepository dmLoaiHangMucCSHTRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private HopDongTramDungChungRepository hopDongTramDungChungRepository;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmLoaiHangMucCSHTEntity> findAll() {
        return dmLoaiHangMucCSHTRepository.findAll();
    }

    public DmLoaiHangMucCSHTEntity findByTen(String ten) {
        List<DmLoaiHangMucCSHTEntity> dmLoaiHangMucCSHTEntities = dmLoaiHangMucCSHTRepository.findAllByTen(ten);
        if (ObjectUtils.isEmpty(dmLoaiHangMucCSHTEntities)) {
            return null;
        }
        return dmLoaiHangMucCSHTEntities.get(0);
    }

    public DmLoaiHangMucCSHTEntity findById(int id) {
        return dmLoaiHangMucCSHTRepository.findById(id).orElse(null);
    }

    public DmLoaiHangMucCSHTModel save(DmLoaiHangMucCSHTModel model) {
        if (model.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmLoaiHangMucCSHTEntity entityToSave = convertModelToEntity(model);

        DmLoaiHangMucCSHTEntity saved = dmLoaiHangMucCSHTRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(model, saved, (model.getId() == null || model.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (model.getId() == null || model.getId() == 0) ? LogActionEnum.CREATE : LogActionEnum.UPDATE);
        });

        return DmLoaiHangMucCSHTModel.fromEntity(saved);
    }

    public DmLoaiHangMucCSHTModel update(DmLoaiHangMucCSHTModel model) {
        DmLoaiHangMucCSHTModel originalModel = findById(model.getId());
        if (model.getId() <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (model.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmLoaiHangMucCSHTEntity toUpdate = convertModelToEntity(model);
        dmLoaiHangMucCSHTRepository.save(toUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, toUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmLoaiHangMucCSHTModel.fromEntity(toUpdate);
    }

    public DmLoaiHangMucCSHTModel delete(Integer id) throws DeleteInUseDataException {
        if (id <= 0 || findById(id) == null) {
            throw new NotFoundException();
        }
        DmLoaiHangMucCSHTEntity toDelete = findById(id);
        if (toDelete == null) {
            throw new InvalidDataException();
        }
        if (checkInUsed(toDelete)) {
            throw new DeleteInUseDataException();
        }
        toDelete.setDeletedAt(new Date());

        dmLoaiHangMucCSHTRepository.save(toDelete);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, toDelete, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmLoaiHangMucCSHTModel.fromEntity(toDelete);
    }

    public List<DmLoaiHangMucCSHTModel> convertListEntityToModel(
            List<DmLoaiHangMucCSHTEntity> listEntity) {
        List<DmLoaiHangMucCSHTModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmLoaiHangMucCSHTModel model = DmLoaiHangMucCSHTModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    private DmLoaiHangMucCSHTEntity convertModelToEntity(DmLoaiHangMucCSHTModel model) {
        DmLoaiHangMucCSHTEntity entity = new DmLoaiHangMucCSHTEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setTen(model.getTen());
            entity.setMa(model.getMa());
            entity.setGhiChu(model.getGhiChu());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmLoaiHangMucCSHTEntity entity) {
        long count = hopDongTramDungChungRepository.countByLoaiHangMucCshtId(entity.getId());
        if (count > 0) {
            return true;
        }
        return false;
    }
}
