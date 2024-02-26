package com.contract.danhmuc.loaiphongmayphatdien.service;

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
import com.contract.danhmuc.loaiphongmayphatdien.entity.DmLoaiPhongMayPhatDienEntity;
import com.contract.danhmuc.loaiphongmayphatdien.model.DmLoaiPhongMayPhatDienModel;
import com.contract.danhmuc.loaiphongmayphatdien.repository.DmLoaiPhongMayPhatDienRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class DmLoaiPhongMayPhatDienService extends BaseService {
    @Autowired
    private DmLoaiPhongMayPhatDienRepository dmLoaiPhongMayPhatDienRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmLoaiPhongMayPhatDienEntity> findAll() {
        return dmLoaiPhongMayPhatDienRepository.findAll();
    }

    public DmLoaiPhongMayPhatDienEntity findById(int id) {
        return dmLoaiPhongMayPhatDienRepository.findById(id).orElse(null);
    }

    public DmLoaiPhongMayPhatDienModel save(DmLoaiPhongMayPhatDienModel loaiPhongMayModel) {
        if (loaiPhongMayModel.getTen().equals("")) {
            throw new BadRequestException();
        }
        DmLoaiPhongMayPhatDienEntity entityToSave = convertModelToEntity(loaiPhongMayModel);
        DmLoaiPhongMayPhatDienEntity saved = dmLoaiPhongMayPhatDienRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(loaiPhongMayModel, saved,
                    (loaiPhongMayModel.getId() == null || loaiPhongMayModel.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (loaiPhongMayModel.getId() == null || loaiPhongMayModel.getId() == 0) ? LogActionEnum.CREATE
                            : LogActionEnum.UPDATE);
        });

        return DmLoaiPhongMayPhatDienModel.fromEntity(saved);
    }

    public DmLoaiPhongMayPhatDienModel update(DmLoaiPhongMayPhatDienModel loaiPhongMayModel) {
        DmLoaiPhongMayPhatDienModel originalModel = findById(loaiPhongMayModel.getId());
        if (loaiPhongMayModel.getId() <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (loaiPhongMayModel.getTen().equals("")) {
            throw new BadRequestException();
        }
        DmLoaiPhongMayPhatDienEntity phongMayToUpdate = convertModelToEntity(loaiPhongMayModel);
        dmLoaiPhongMayPhatDienRepository.save(phongMayToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, phongMayToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmLoaiPhongMayPhatDienModel.fromEntity(phongMayToUpdate);
    }

    public DmLoaiPhongMayPhatDienModel delete(int id) throws DeleteInUseDataException {
        if (id <= 0) {
            throw new BadRequestException();
        }
        DmLoaiPhongMayPhatDienEntity entityToDelete = this.findById(id);
        if (entityToDelete == null) {
            throw new BadRequestException();
        }
        if (checkInUsed(entityToDelete)) {
            throw new DeleteInUseDataException();
        }
        entityToDelete.setDeletedAt(new Date());
        dmLoaiPhongMayPhatDienRepository.save(entityToDelete);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, entityToDelete, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmLoaiPhongMayPhatDienModel.fromEntity(convertModelToEntity(entityToDelete));
    }

    public List<DmLoaiPhongMayPhatDienModel> convertListEntityToModel(
            List<DmLoaiPhongMayPhatDienEntity> listEntity) {
        List<DmLoaiPhongMayPhatDienModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmLoaiPhongMayPhatDienModel model = DmLoaiPhongMayPhatDienModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    private DmLoaiPhongMayPhatDienEntity convertModelToEntity(DmLoaiPhongMayPhatDienModel model) {
        DmLoaiPhongMayPhatDienEntity entity = new DmLoaiPhongMayPhatDienEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setMa(model.getMa());
            entity.setTen(model.getTen());
            entity.setGhiChu(model.getGhiChu());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmLoaiPhongMayPhatDienEntity entity) {
        return false;
    }
}
