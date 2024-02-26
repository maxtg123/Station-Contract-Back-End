package com.contract.danhmuc.tramkhuvuc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.common.exception.BadRequestException;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.common.exception.InvalidDataException;
import com.contract.common.exception.NotFoundException;
import com.contract.danhmuc.tramkhuvuc.entity.DmTramKhuVucEntity;
import com.contract.danhmuc.tramkhuvuc.model.DmTramKhuVucModel;
import com.contract.danhmuc.tramkhuvuc.repository.DmTramKhuVucRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.tram.tram.repository.TramRepository;

@Service
public class DmTramKhuVucService {
    @Autowired
    private DmTramKhuVucRepository dmTramKhuVucRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private TramRepository tramRepository;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmTramKhuVucEntity> findAll() {
        return dmTramKhuVucRepository.findAll();
    }

    public DmTramKhuVucEntity findById(int id) {
        return dmTramKhuVucRepository.findById(id).orElse(null);
    }

    public DmTramKhuVucEntity findByTen(String tenTramKhuVuc) {
        return dmTramKhuVucRepository.findByTen(tenTramKhuVuc);
    }

    public DmTramKhuVucModel save(DmTramKhuVucModel model) {
        if (model.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmTramKhuVucEntity entityToSave = convertModelToEntity(model);
        DmTramKhuVucEntity saved = dmTramKhuVucRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(model, saved, (model.getId() == null || model.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (model.getId() == null || model.getId() == 0) ? LogActionEnum.CREATE : LogActionEnum.UPDATE);
        });

        return DmTramKhuVucModel.fromEntity(saved);
    }

    public DmTramKhuVucModel update(DmTramKhuVucModel tramKhuVucModel) {
        DmTramKhuVucModel originalModel = findById(tramKhuVucModel.getId());
        if (tramKhuVucModel.getId() <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (tramKhuVucModel.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmTramKhuVucEntity tramKhuVucToUpdate = convertModelToEntity(tramKhuVucModel);
        dmTramKhuVucRepository.save(tramKhuVucToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, tramKhuVucToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmTramKhuVucModel.fromEntity(tramKhuVucToUpdate);
    }

    public List<DmTramKhuVucModel> convertListEntityToModel(List<DmTramKhuVucEntity> listEntity) {
        List<DmTramKhuVucModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmTramKhuVucModel model = DmTramKhuVucModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    public DmTramKhuVucModel delete(Integer id) throws DeleteInUseDataException {
        if (id <= 0 || findById(id) == null) {
            throw new NotFoundException();
        }
        DmTramKhuVucEntity entity = findById(id);
        if (entity == null) {
            throw new InvalidDataException();
        }
        if (checkInUsed(entity)) {
            throw new DeleteInUseDataException();
        }
        entity.setDeletedAt(new Date());

        dmTramKhuVucRepository.save(entity);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, entity, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmTramKhuVucModel.fromEntity(entity);
    }

    private DmTramKhuVucEntity convertModelToEntity(DmTramKhuVucModel model) {
        DmTramKhuVucEntity entity = new DmTramKhuVucEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setTen(model.getTen());
            entity.setMa(model.getMa());
            entity.setGhiChu(model.getGhiChu());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmTramKhuVucEntity entity) {
        long countTrams = tramRepository.countByKhuVucId(entity.getId());
        if (countTrams > 0) {
            return true;
        }
        return false;
    }
}
