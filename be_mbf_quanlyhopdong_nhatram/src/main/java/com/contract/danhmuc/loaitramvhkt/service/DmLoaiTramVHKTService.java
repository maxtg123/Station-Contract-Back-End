package com.contract.danhmuc.loaitramvhkt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.base.service.BaseService;
import com.contract.common.exception.BadRequestException;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.common.exception.NotFoundException;
import com.contract.danhmuc.loaitramvhkt.entity.DmLoaiTramVHKTEntity;
import com.contract.danhmuc.loaitramvhkt.model.DmLoaiTramVHKTModel;
import com.contract.danhmuc.loaitramvhkt.repository.DmLoaiTramVHKTRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class DmLoaiTramVHKTService extends BaseService {
    @Autowired
    private DmLoaiTramVHKTRepository dmLoaiTramVHKTRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmLoaiTramVHKTEntity> findAll() {
        return dmLoaiTramVHKTRepository.findAll();
    }

    public DmLoaiTramVHKTEntity findById(int id) {
        return dmLoaiTramVHKTRepository.findById(id).orElse(null);
    }

    public DmLoaiTramVHKTModel save(DmLoaiTramVHKTModel model) {
        if (model.getTen().equals("")) {
            throw new BadRequestException();
        }
        DmLoaiTramVHKTEntity entityToSave = convertModelToEntity(model);
        DmLoaiTramVHKTEntity saved = dmLoaiTramVHKTRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(model, saved, (model.getId() == null || model.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (model.getId() == null || model.getId() == 0) ? LogActionEnum.CREATE : LogActionEnum.UPDATE);
        });

        return DmLoaiTramVHKTModel.fromEntity(saved);
    }

    public DmLoaiTramVHKTModel update(DmLoaiTramVHKTModel model) {
        DmLoaiTramVHKTModel originalModel = findById(model.getId());
        if (model.getId() <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (model.getTen().equals("")) {
            throw new BadRequestException();
        }
        DmLoaiTramVHKTEntity toUpdate = convertModelToEntity(model);
        dmLoaiTramVHKTRepository.save(toUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, toUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmLoaiTramVHKTModel.fromEntity(toUpdate);
    }

    public DmLoaiTramVHKTModel delete(int id) throws DeleteInUseDataException {
        if (id <= 0) {
            throw new BadRequestException();
        }
        DmLoaiTramVHKTEntity entityToDelete = this.findById(id);
        if (entityToDelete == null) {
            throw new BadRequestException();
        }
        if (checkInUsed(entityToDelete)) {
            throw new DeleteInUseDataException();
        }
        entityToDelete.setDeletedAt(new Date());
        dmLoaiTramVHKTRepository.save(entityToDelete);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, entityToDelete, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmLoaiTramVHKTModel.fromEntity(convertModelToEntity(entityToDelete));
    }

    public List<DmLoaiTramVHKTModel> convertListEntityToModel(
            List<DmLoaiTramVHKTEntity> listEntity) {
        List<DmLoaiTramVHKTModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmLoaiTramVHKTModel model = DmLoaiTramVHKTModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    private DmLoaiTramVHKTEntity convertModelToEntity(DmLoaiTramVHKTModel model) {
        DmLoaiTramVHKTEntity entity = new DmLoaiTramVHKTEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setMa(model.getMa());
            entity.setTen(model.getTen());
            entity.setGhiChu(model.getGhiChu());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmLoaiTramVHKTEntity entity) {
        return false;
    }
}
