package com.contract.danhmuc.loaicotangten.service;

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
import com.contract.danhmuc.loaicotangten.entity.DmLoaiCotAngtenEntity;
import com.contract.danhmuc.loaicotangten.model.DmLoaiCotAngtenModel;
import com.contract.danhmuc.loaicotangten.repository.DmLoaiCotAngtenRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.tram.tram.repository.TramRepository;

@Service
public class DmLoaiCotAngtenService {
    @Autowired
    private DmLoaiCotAngtenRepository dmLoaiCotAngtenRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private TramRepository tramRepository;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmLoaiCotAngtenEntity> findAll() {
        return dmLoaiCotAngtenRepository.findAll();
    }

    public DmLoaiCotAngtenEntity findById(Integer id) {
        return dmLoaiCotAngtenRepository.findById(id).orElse(null);
    }

    public DmLoaiCotAngtenEntity findByTen(String ten) {
        return dmLoaiCotAngtenRepository.findByTen(ten);
    }

    public DmLoaiCotAngtenModel save(DmLoaiCotAngtenModel model) {
        if (model.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmLoaiCotAngtenEntity entityToSave = convertModelToEntity(model);
        DmLoaiCotAngtenEntity saved = dmLoaiCotAngtenRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(model, saved, (model.getId() == null || model.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (model.getId() == null || model.getId() == 0) ? LogActionEnum.CREATE : LogActionEnum.UPDATE);
        });

        return DmLoaiCotAngtenModel.fromEntity(saved);
    }

    public DmLoaiCotAngtenModel update(DmLoaiCotAngtenModel loaiCotAngtenModel) {
        DmLoaiCotAngtenEntity originalModel = findById(loaiCotAngtenModel.getId());
        if (loaiCotAngtenModel.getId() <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (loaiCotAngtenModel.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmLoaiCotAngtenEntity loaiCotAngtenToUpdate = convertModelToEntity(loaiCotAngtenModel);
        dmLoaiCotAngtenRepository.save(loaiCotAngtenToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, loaiCotAngtenToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmLoaiCotAngtenModel.fromEntity(loaiCotAngtenToUpdate);
    }

    public DmLoaiCotAngtenModel delete(Integer id) throws DeleteInUseDataException {
        if (id <= 0 || findById(id) == null) {
            throw new NotFoundException();
        }
        DmLoaiCotAngtenEntity dmLoaiCotAngtenToDelete = findById(id);
        if (dmLoaiCotAngtenToDelete == null) {
            throw new InvalidDataException();
        }
        if (checkInUsed(dmLoaiCotAngtenToDelete)) {
            throw new DeleteInUseDataException();
        }
        dmLoaiCotAngtenToDelete.setDeletedAt(new Date());

        dmLoaiCotAngtenRepository.save(dmLoaiCotAngtenToDelete);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, dmLoaiCotAngtenToDelete, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmLoaiCotAngtenModel.fromEntity(dmLoaiCotAngtenToDelete);
    }

    public List<DmLoaiCotAngtenModel> convertListEntityToModel(
            List<DmLoaiCotAngtenEntity> listEntity) {
        List<DmLoaiCotAngtenModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmLoaiCotAngtenModel model = DmLoaiCotAngtenModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    private DmLoaiCotAngtenEntity convertModelToEntity(DmLoaiCotAngtenModel model) {
        DmLoaiCotAngtenEntity entity = new DmLoaiCotAngtenEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setTen(model.getTen());
            entity.setMa(model.getMa());
            entity.setGhiChu(model.getGhiChu());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmLoaiCotAngtenEntity entity) {
        long countTram = tramRepository.countByLoaiCotAngtenId(entity.getId());
        if (countTram > 0) {
            return true;
        }
        return false;
    }
}
