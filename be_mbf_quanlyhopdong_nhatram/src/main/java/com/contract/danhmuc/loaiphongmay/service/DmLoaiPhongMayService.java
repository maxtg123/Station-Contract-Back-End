package com.contract.danhmuc.loaiphongmay.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.common.exception.BadRequestException;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.common.exception.NotFoundException;
import com.contract.danhmuc.loaiphongmay.entity.DmLoaiPhongMayEntity;
import com.contract.danhmuc.loaiphongmay.model.DmLoaiPhongMayModel;
import com.contract.danhmuc.loaiphongmay.repository.DmLoaiPhongMayRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class DmLoaiPhongMayService {
    @Autowired
    private DmLoaiPhongMayRepository loaiPhongMayRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmLoaiPhongMayEntity> findAll() {
        return loaiPhongMayRepository.findAll();
    }

    public DmLoaiPhongMayEntity findById(int id) {
        return loaiPhongMayRepository.findById(id).orElse(null);
    }

    public DmLoaiPhongMayModel save(DmLoaiPhongMayModel loaiPhongMayModel) {
        if (loaiPhongMayModel.getTen().equals("")) {
            throw new BadRequestException();
        }
        DmLoaiPhongMayEntity entityToSave = convertModelToEntity(loaiPhongMayModel);
        DmLoaiPhongMayEntity saved = loaiPhongMayRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(loaiPhongMayModel, saved,
                    (loaiPhongMayModel.getId() == null || loaiPhongMayModel.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (loaiPhongMayModel.getId() == null || loaiPhongMayModel.getId() == 0) ? LogActionEnum.CREATE
                            : LogActionEnum.UPDATE);
        });

        return DmLoaiPhongMayModel.fromEntity(saved);
    }

    public DmLoaiPhongMayModel update(DmLoaiPhongMayModel loaiPhongMayModel) {
        DmLoaiPhongMayModel originalModel = findById(loaiPhongMayModel.getId());
        if (loaiPhongMayModel.getId() <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (loaiPhongMayModel.getTen().equals("")) {
            throw new BadRequestException();
        }
        DmLoaiPhongMayEntity phongMayToUpdate = convertModelToEntity(loaiPhongMayModel);
        loaiPhongMayRepository.save(phongMayToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, phongMayToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmLoaiPhongMayModel.fromEntity(phongMayToUpdate);
    }

    public DmLoaiPhongMayModel delete(int id) throws DeleteInUseDataException {
        if (id <= 0) {
            throw new BadRequestException();
        }
        DmLoaiPhongMayEntity entityToDelete = this.findById(id);
        if (entityToDelete == null) {
            throw new BadRequestException();
        }
        if (checkInUsed(entityToDelete)) {
            throw new DeleteInUseDataException();
        }
        entityToDelete.setDeletedAt(new Date());
        loaiPhongMayRepository.save(entityToDelete);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, entityToDelete, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmLoaiPhongMayModel.fromEntity(convertModelToEntity(entityToDelete));
    }

    public List<DmLoaiPhongMayModel> convertListEntityToModel(
            List<DmLoaiPhongMayEntity> listEntity) {
        List<DmLoaiPhongMayModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmLoaiPhongMayModel model = DmLoaiPhongMayModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    private DmLoaiPhongMayEntity convertModelToEntity(DmLoaiPhongMayModel model) {
        DmLoaiPhongMayEntity entity = new DmLoaiPhongMayEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setMa(model.getMa());
            entity.setTen(model.getTen());
            entity.setGhiChu(model.getGhiChu());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmLoaiPhongMayEntity entity) {
        return false;
    }
}
