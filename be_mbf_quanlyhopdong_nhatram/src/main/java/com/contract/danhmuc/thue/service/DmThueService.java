package com.contract.danhmuc.thue.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.common.exception.BadRequestException;
import com.contract.common.exception.DataExistsException;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.common.exception.NotFoundException;
import com.contract.danhmuc.thue.entity.DmThueEntity;
import com.contract.danhmuc.thue.model.DmThueModel;
import com.contract.danhmuc.thue.repository.DmThueRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class DmThueService {
    @Autowired
    private DmThueRepository dmThueRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmThueEntity> findAll() {
        return dmThueRepository.findAll();
    }

    public DmThueEntity findById(int id) {
        return dmThueRepository.findById(id).orElse(null);
    }

    public boolean findBySoThue(float soThue) {
        // Kiểm tra số thuế đã có trong cơ sở dữ liệu
        if (dmThueRepository.findBySoThue(soThue) != null) {
            return true;
        }
        return false;
    }

    public DmThueModel save(DmThueModel dmThueModel) {
        if (dmThueModel.getSoThue() < 0) {
            throw new BadRequestException();
        }
        if (findBySoThue(dmThueModel.getSoThue())) {
            throw new DataExistsException();
        }
        DmThueEntity entityToSave = convertModelToEntity(dmThueModel);
        DmThueEntity saved = dmThueRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(dmThueModel, saved,
                    (dmThueModel.getId() == null || dmThueModel.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (dmThueModel.getId() == null || dmThueModel.getId() == 0) ? LogActionEnum.CREATE
                            : LogActionEnum.UPDATE);
        });

        return DmThueModel.fromEntity(saved);
    }

    public DmThueModel update(DmThueModel dmThueModel) {
        DmThueModel originalModel = findById(dmThueModel.getId());
        if (dmThueModel.getId() <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (dmThueModel.getSoThue() < 0) {
            throw new BadRequestException();
        }
        if (findBySoThue(dmThueModel.getSoThue())) {
            throw new DataExistsException();
        }
        DmThueEntity thueToUpdate = convertModelToEntity(dmThueModel);
        dmThueRepository.save(thueToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, thueToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmThueModel.fromEntity(thueToUpdate);
    }

    public DmThueModel delete(int id) throws DeleteInUseDataException {
        if (id <= 0) {
            throw new BadRequestException();
        }
        DmThueEntity entityToDelete = this.findById(id);
        if (entityToDelete == null) {
            throw new BadRequestException();
        }
        if (checkInUsed(entityToDelete)) {
            throw new DeleteInUseDataException();
        }
        entityToDelete.setDeletedAt(new Date());
        dmThueRepository.save(entityToDelete);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, entityToDelete, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmThueModel.fromEntity(convertModelToEntity(entityToDelete));
    }

    public List<DmThueModel> convertListEntityToModel(List<DmThueEntity> listEntity) {
        List<DmThueModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmThueModel model = DmThueModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    private DmThueEntity convertModelToEntity(DmThueModel model) {
        DmThueEntity entity = new DmThueEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setSoThue(model.getSoThue());
            entity.setGhiChu(model.getGhiChu());
        }
        return entity;
    }

    private boolean checkInUsed(DmThueEntity entity) {
        return false;
    }
}
