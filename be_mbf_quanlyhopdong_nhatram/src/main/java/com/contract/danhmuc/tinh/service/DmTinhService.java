package com.contract.danhmuc.tinh.service;

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
import com.contract.danhmuc.tinh.entity.DmTinhEntity;
import com.contract.danhmuc.tinh.model.DmTinhModel;
import com.contract.danhmuc.tinh.repository.DmTinhRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.tram.tram.repository.TramRepository;

@Service
public class DmTinhService {
    @Autowired
    private DmTinhRepository dmTinhRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private TramRepository tramRepository;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmTinhEntity> findAll() {
        return dmTinhRepository.findAll();
    }

    public DmTinhEntity findById(int id) {
        return dmTinhRepository.findById(id).orElse(null);
    }

    public int findIdByTen(String tenTinh) {
        return dmTinhRepository.findIdByTen(tenTinh);
    }

    public DmTinhEntity findByMa(String maTinh) {
        return dmTinhRepository.findByMa(maTinh);
    }

    public DmTinhEntity findByTen(String ten) {
        return dmTinhRepository.findByTen(ten);
    }

    public int kiemTraTinhExists(String tenTinh) {
        return dmTinhRepository.countIdByTen(tenTinh);
    }

    public DmTinhModel save(DmTinhModel model) {
        if (model.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmTinhEntity entityToSave = convertModelToEntity(model);
        DmTinhEntity saved = dmTinhRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(model, saved, (model.getId() == null || model.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (model.getId() == null || model.getId() == 0) ? LogActionEnum.CREATE : LogActionEnum.UPDATE);
        });

        return DmTinhModel.fromEntity(saved);
    }

    public DmTinhModel update(DmTinhModel dmTinhModel) {
        DmTinhModel originalModel = findById(dmTinhModel.getId());
        if (dmTinhModel.getId() <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (dmTinhModel.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmTinhEntity dmTinhToUpdate = convertModelToEntity(dmTinhModel);
        dmTinhRepository.save(dmTinhToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, dmTinhToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmTinhModel.fromEntity(dmTinhToUpdate);
    }

    public List<DmTinhModel> convertListEntityToModel(List<DmTinhEntity> listEntity) {
        List<DmTinhModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmTinhModel model = DmTinhModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    public DmTinhModel delete(Integer id) throws DeleteInUseDataException {
        if (id <= 0 || findById(id) == null) {
            throw new NotFoundException();
        }
        DmTinhEntity entity = findById(id);
        if (entity == null) {
            throw new InvalidDataException();
        }
        if (checkInUsed(entity)) {
            throw new DeleteInUseDataException();
        }
        entity.setDeletedAt(new Date());

        dmTinhRepository.save(entity);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, entity, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmTinhEntity.fromEntity(entity);
    }

    private DmTinhEntity convertModelToEntity(DmTinhModel model) {
        DmTinhEntity entity = new DmTinhEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setTen(model.getTen());
            entity.setMa(model.getMa());
            entity.setGhiChu(model.getGhiChu());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmTinhEntity entity) {
        long countTrams = tramRepository.countByTinhId(entity.getId());

        if (!ObjectUtils.isEmpty(entity.getDmHuyenEntitySet()) || countTrams > 0) {
            return true;
        }
        return false;
    }
}
