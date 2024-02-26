package com.contract.danhmuc.hinhthuckyhopdong.service;

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
import com.contract.danhmuc.hinhthuckyhopdong.entity.DmHinhThucKyHopDongEntity;
import com.contract.danhmuc.hinhthuckyhopdong.model.DmHinhThucKyHopDongModel;
import com.contract.danhmuc.hinhthuckyhopdong.repository.DmHinhThucKyHopDongRepository;
import com.contract.hopdong.hopdong.repository.HopDongRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class DmHinhThucKyHopDongService {
    @Autowired
    private DmHinhThucKyHopDongRepository dmHinhThucKyHopDongRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private HopDongRepository hopDongRepository;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmHinhThucKyHopDongEntity> findAll() {
        return dmHinhThucKyHopDongRepository.findAll();
    }

    public DmHinhThucKyHopDongEntity findById(int id) {
        return dmHinhThucKyHopDongRepository.findById(id).orElse(null);
    }

    public DmHinhThucKyHopDongEntity findByTen(String ten) {
        List<DmHinhThucKyHopDongEntity> dmHinhThucKyHopDongEntityList = dmHinhThucKyHopDongRepository.findAllByTen(ten);
        if (ObjectUtils.isEmpty(dmHinhThucKyHopDongEntityList)) {
            return null;
        }
        return dmHinhThucKyHopDongEntityList.get(0);
    }

    public DmHinhThucKyHopDongModel save(DmHinhThucKyHopDongModel model) {
        if (model.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmHinhThucKyHopDongEntity entityToSave = convertModelToEntity(model);
        DmHinhThucKyHopDongEntity saved = dmHinhThucKyHopDongRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(model, saved, (model.getId() == null || model.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (model.getId() == null || model.getId() == 0) ? LogActionEnum.CREATE : LogActionEnum.UPDATE);
        });

        return DmHinhThucKyHopDongModel.fromEntity(saved);
    }

    public DmHinhThucKyHopDongModel update(DmHinhThucKyHopDongModel dmHinhThucKyHopDongModel) {
        DmHinhThucKyHopDongEntity originalModel = findById(dmHinhThucKyHopDongModel.getId());
        if (dmHinhThucKyHopDongModel.getId() <= 0
                || originalModel == null) {
            throw new NotFoundException();
        }
        if (dmHinhThucKyHopDongModel.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmHinhThucKyHopDongEntity entityToUpdate = convertModelToEntity(dmHinhThucKyHopDongModel);
        dmHinhThucKyHopDongRepository.save(entityToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, entityToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmHinhThucKyHopDongModel.fromEntity(entityToUpdate);
    }

    public List<DmHinhThucKyHopDongModel> convertListEntityToModel(
            List<DmHinhThucKyHopDongEntity> listEntity) {
        List<DmHinhThucKyHopDongModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmHinhThucKyHopDongModel model = DmHinhThucKyHopDongModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    public DmHinhThucKyHopDongModel delete(Integer id) throws DeleteInUseDataException {
        if (id <= 0 || findById(id) == null) {
            throw new NotFoundException();
        }
        DmHinhThucKyHopDongEntity dmHinhThucKyHopDongToDelete = findById(id);
        if (dmHinhThucKyHopDongToDelete == null) {
            throw new InvalidDataException();
        }
        if (checkInUsed(dmHinhThucKyHopDongToDelete)) {
            throw new DeleteInUseDataException();
        }
        dmHinhThucKyHopDongToDelete.setDeletedAt(new Date());

        dmHinhThucKyHopDongRepository.save(dmHinhThucKyHopDongToDelete);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, dmHinhThucKyHopDongToDelete, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmHinhThucKyHopDongModel.fromEntity(dmHinhThucKyHopDongToDelete);
    }

    private DmHinhThucKyHopDongEntity convertModelToEntity(DmHinhThucKyHopDongModel model) {
        DmHinhThucKyHopDongEntity entity = new DmHinhThucKyHopDongEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setTen(model.getTen());
            entity.setMa(model.getMa());
            entity.setGhiChu(model.getGhiChu());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmHinhThucKyHopDongEntity entity) {
        long countHD = hopDongRepository.countByHinhThucKyId(entity.getId());
        if (countHD > 0) {
            return true;
        }
        return false;
    }
}
