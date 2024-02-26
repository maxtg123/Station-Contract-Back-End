package com.contract.danhmuc.to.service;

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
import com.contract.danhmuc.to.entity.DmToEntity;
import com.contract.danhmuc.to.model.DmToModel;
import com.contract.danhmuc.to.repository.DmToRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.nguoidung.nguoidungkhuvuc.repository.NguoiDungKhuVucRepository;
import com.contract.tram.tram.repository.TramRepository;

@Service
public class DmToService {
    @Autowired
    private DmToRepository dmToRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private TramRepository tramRepository;
    @Autowired
    private NguoiDungKhuVucRepository nguoiDungKhuVucRepository;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmToEntity> findAll() {
        return dmToRepository.findAllToJoinPhongDai();
    }

    public DmToEntity findById(int id) {
        return dmToRepository.findById(id).orElse(null);
    }

    public DmToEntity findByTen(String tenTo) {
        return dmToRepository.findByTen(tenTo);
    }

    public List<DmToEntity> findAllByPhongDaiId(Integer id) {
        return dmToRepository.findAllByPhongDaiId(id);
    }

    public DmToModel save(DmToModel model) {
        if (model.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmToEntity entityToSave = convertModelToEntity(model);
        DmToEntity saved = dmToRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(model, saved, (model.getId() == null || model.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (model.getId() == null || model.getId() == 0) ? LogActionEnum.CREATE : LogActionEnum.UPDATE);
        });
        return DmToModel.fromEntity(saved);
    }

    public DmToModel update(DmToModel dmToModel) {
        DmToModel originalModel = findById(dmToModel.getId());
        if (dmToModel.getId() <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (dmToModel.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmToEntity dmToToUpdate = convertModelToEntity(dmToModel);
        dmToRepository.save(dmToToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, dmToToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmToModel.fromEntity(dmToToUpdate);
    }

    public List<DmToModel> convertListEntityToModel(List<DmToEntity> listEntity) {
        List<DmToModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmToModel model = DmToModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    public DmToModel delete(Integer id) throws DeleteInUseDataException {
        if (id <= 0 || findById(id) == null) {
            throw new NotFoundException();
        }
        DmToEntity entity = findById(id);
        if (entity == null) {
            throw new InvalidDataException();
        }
        if (checkInUsed(entity)) {
            throw new DeleteInUseDataException();
        }
        entity.setDeletedAt(new Date());

        dmToRepository.save(entity);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, entity, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmToModel.fromEntity(entity);
    }

    private DmToEntity convertModelToEntity(DmToModel model) {
        DmToEntity entity = new DmToEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setTen(model.getTen());
            entity.setTenVietTat(model.getTenVietTat());
            entity.setGhiChu(model.getGhiChu());
            entity.setPhongDai(model.getPhongDai());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmToEntity entity) {
        long countTrams = tramRepository.countByToId(entity.getId());
        long countKhuVuc = nguoiDungKhuVucRepository.countByToId(entity.getId());
        if (countTrams > 0 || countKhuVuc > 0) {
            return true;
        }
        return false;
    }
}
