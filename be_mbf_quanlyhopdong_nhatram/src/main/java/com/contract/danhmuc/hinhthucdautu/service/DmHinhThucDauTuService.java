package com.contract.danhmuc.hinhthucdautu.service;

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
import com.contract.danhmuc.hinhthucdautu.entity.DmHinhThucDauTuEntity;
import com.contract.danhmuc.hinhthucdautu.model.DmHinhThucDauTuModel;
import com.contract.danhmuc.hinhthucdautu.repository.DmHinhThucDauTuRepository;
import com.contract.hopdong.hopdong.repository.HopDongRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class DmHinhThucDauTuService {
    @Autowired
    private DmHinhThucDauTuRepository dmHinhThucDauTuRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private HopDongRepository hopDongRepository;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmHinhThucDauTuEntity> findAll() {
        return dmHinhThucDauTuRepository.findAll();
    }

    public DmHinhThucDauTuEntity findByTen(String ten) {
        List<DmHinhThucDauTuEntity> dmHinhThucDauTuEntityList = dmHinhThucDauTuRepository.findAllByTen(ten);
        if (ObjectUtils.isEmpty(dmHinhThucDauTuEntityList)) {
            return null;
        }
        return dmHinhThucDauTuEntityList.get(0);
    }

    public DmHinhThucDauTuEntity findById(int id) {
        return dmHinhThucDauTuRepository.findById(id).orElse(null);
    }

    public DmHinhThucDauTuModel save(DmHinhThucDauTuModel model) {
        if (model.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmHinhThucDauTuEntity entityToSave = convertModelToEntity(model);

        DmHinhThucDauTuEntity saved = dmHinhThucDauTuRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(model, saved, (model.getId() == null || model.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (model.getId() == null || model.getId() == 0) ? LogActionEnum.CREATE : LogActionEnum.UPDATE);
        });

        return DmHinhThucDauTuModel.fromEntity(saved);
    }

    public DmHinhThucDauTuModel update(DmHinhThucDauTuModel dmLoaiCshtModel) {
        DmHinhThucDauTuEntity originalModel = findById(dmLoaiCshtModel.getId());
        if (dmLoaiCshtModel.getId() <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (dmLoaiCshtModel.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmHinhThucDauTuEntity dmHtdtToUpdate = convertModelToEntity(dmLoaiCshtModel);
        dmHinhThucDauTuRepository.save(dmHtdtToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, dmHtdtToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmHinhThucDauTuModel.fromEntity(dmHtdtToUpdate);
    }

    public DmHinhThucDauTuModel delete(Integer id) throws DeleteInUseDataException {
        if (id <= 0 || findById(id) == null) {
            throw new NotFoundException();
        }
        DmHinhThucDauTuEntity dmHinhThucDauTuToDelete = findById(id);
        if (dmHinhThucDauTuToDelete == null) {
            throw new InvalidDataException();
        }
        if (checkInUsed(dmHinhThucDauTuToDelete)) {
            throw new DeleteInUseDataException();
        }
        dmHinhThucDauTuToDelete.setDeletedAt(new Date());

        dmHinhThucDauTuRepository.save(dmHinhThucDauTuToDelete);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, dmHinhThucDauTuToDelete, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmHinhThucDauTuModel.fromEntity(dmHinhThucDauTuToDelete);
    }

    public List<DmHinhThucDauTuModel> convertListEntityToModel(
            List<DmHinhThucDauTuEntity> listEntity) {
        List<DmHinhThucDauTuModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmHinhThucDauTuModel model = DmHinhThucDauTuModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    private DmHinhThucDauTuEntity convertModelToEntity(DmHinhThucDauTuModel model) {
        DmHinhThucDauTuEntity entity = new DmHinhThucDauTuEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setTen(model.getTen());
            entity.setMa(model.getMa());
            entity.setGhiChu(model.getGhiChu());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmHinhThucDauTuEntity entity) {
        long countHD = hopDongRepository.countByHinhThucDauTuId(entity.getId());
        if (countHD > 0) {
            return true;
        }
        return false;
    }
}
