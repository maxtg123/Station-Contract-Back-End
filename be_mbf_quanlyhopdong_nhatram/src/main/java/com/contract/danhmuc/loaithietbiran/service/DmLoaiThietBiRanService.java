package com.contract.danhmuc.loaithietbiran.service;

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
import com.contract.danhmuc.loaithietbiran.entity.DmLoaiThietBiRanEntity;
import com.contract.danhmuc.loaithietbiran.model.DmLoaiThietBiRanModel;
import com.contract.danhmuc.loaithietbiran.repository.DmLoaiThietBiRanRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class DmLoaiThietBiRanService {
    @Autowired
    private DmLoaiThietBiRanRepository dmLoaiThietBiRanRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmLoaiThietBiRanEntity> findAll() {
        return dmLoaiThietBiRanRepository.findAll();
    }

    public DmLoaiThietBiRanEntity findByTen(String ten) {
        List<DmLoaiThietBiRanEntity> dmLoaiThietBiRanEntityList = dmLoaiThietBiRanRepository.findAllByTen(ten);
        if (ObjectUtils.isEmpty(dmLoaiThietBiRanEntityList)) {
            return null;
        }
        return dmLoaiThietBiRanEntityList.get(0);
    }

    public DmLoaiThietBiRanEntity findById(int id) {
        return dmLoaiThietBiRanRepository.findById(id).orElse(null);
    }

    public DmLoaiThietBiRanModel save(DmLoaiThietBiRanModel model) {
        if (model.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmLoaiThietBiRanEntity entityToSave = convertModelToEntity(model);
        DmLoaiThietBiRanEntity saved = dmLoaiThietBiRanRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(model, saved, (model.getId() == null || model.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (model.getId() == null || model.getId() == 0) ? LogActionEnum.CREATE : LogActionEnum.UPDATE);
        });

        return DmLoaiThietBiRanModel.fromEntity(saved);
    }

    public DmLoaiThietBiRanModel update(DmLoaiThietBiRanModel loaiThietBiRanModel) {
        DmLoaiThietBiRanModel originalModel = findById(loaiThietBiRanModel.getId());
        if (loaiThietBiRanModel.getId() <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (loaiThietBiRanModel.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmLoaiThietBiRanEntity loaiThietBiRanToUpdate = convertModelToEntity(loaiThietBiRanModel);
        dmLoaiThietBiRanRepository.save(loaiThietBiRanToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, loaiThietBiRanToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmLoaiThietBiRanModel.fromEntity(loaiThietBiRanToUpdate);
    }

    public List<DmLoaiThietBiRanModel> convertListEntityToModel(
            List<DmLoaiThietBiRanEntity> listEntity) {
        List<DmLoaiThietBiRanModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmLoaiThietBiRanModel model = DmLoaiThietBiRanModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    public DmLoaiThietBiRanModel delete(Integer id) throws DeleteInUseDataException {
        if (id <= 0 || findById(id) == null) {
            throw new NotFoundException();
        }
        DmLoaiThietBiRanEntity dmLoaiThietBiRANToDelete = findById(id);
        if (dmLoaiThietBiRANToDelete == null) {
            throw new InvalidDataException();
        }
        if (checkInUsed(dmLoaiThietBiRANToDelete)) {
            throw new DeleteInUseDataException();
        }
        dmLoaiThietBiRANToDelete.setDeletedAt(new Date());

        dmLoaiThietBiRanRepository.save(dmLoaiThietBiRANToDelete);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, dmLoaiThietBiRANToDelete, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmLoaiThietBiRanModel.fromEntity(dmLoaiThietBiRANToDelete);
    }

    public DmLoaiThietBiRanEntity convertModelToEntity(DmLoaiThietBiRanModel model) {
        DmLoaiThietBiRanEntity entity = new DmLoaiThietBiRanEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setTen(model.getTen());
            entity.setMa(model.getMa());
            entity.setGhiChu(model.getGhiChu());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmLoaiThietBiRanEntity entity) {
        DmLoaiThietBiRanEntity _entity = dmLoaiThietBiRanRepository.findByIdAndFetch(entity.getId());
        if (!ObjectUtils.isEmpty(_entity.getTramEntitySet())) {
            return true;
        }
        return false;
    }
}
