package com.contract.danhmuc.loaihopdongphutro.service;

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
import com.contract.danhmuc.loaihopdongphutro.entity.DmLoaiHopDongPhuTroEntity;
import com.contract.danhmuc.loaihopdongphutro.model.DmLoaiHopDongPhuTroModel;
import com.contract.danhmuc.loaihopdongphutro.repository.DmLoaiHopDongPhuTroRepository;
import com.contract.hopdong.hopdongtram_phutro.repository.HopDongTramPhuTroRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class DmLoaiHopDongPhuTroService {
    @Autowired
    private DmLoaiHopDongPhuTroRepository dmLoaiHopDongPhuTroRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private HopDongTramPhuTroRepository hopDongTramPhuTroRepository;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmLoaiHopDongPhuTroEntity> findAll() {
        return dmLoaiHopDongPhuTroRepository.findAll();
    }

    public DmLoaiHopDongPhuTroEntity findById(int id) {
        return dmLoaiHopDongPhuTroRepository.findById(id).orElse(null);
    }

    public DmLoaiHopDongPhuTroEntity findByTen(String ten) {
        return dmLoaiHopDongPhuTroRepository.findByTen(ten);
    }

    public DmLoaiHopDongPhuTroModel save(DmLoaiHopDongPhuTroModel hopDongPhuTroModel) {
        if (hopDongPhuTroModel.getTen().isEmpty() || hopDongPhuTroModel.getGia() < 0) {
            throw new BadRequestException();
        }
        DmLoaiHopDongPhuTroEntity entityToSave = convertModelToEntity(hopDongPhuTroModel);
        DmLoaiHopDongPhuTroEntity saved = dmLoaiHopDongPhuTroRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(hopDongPhuTroModel, saved,
                    (hopDongPhuTroModel.getId() == null || hopDongPhuTroModel.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (hopDongPhuTroModel.getId() == null || hopDongPhuTroModel.getId() == 0) ? LogActionEnum.CREATE
                            : LogActionEnum.UPDATE);
        });

        return DmLoaiHopDongPhuTroModel.fromEntity(saved);
    }

    public DmLoaiHopDongPhuTroModel update(DmLoaiHopDongPhuTroModel hopDongPhuTroModel) {
        DmLoaiHopDongPhuTroModel originalModel = findById(hopDongPhuTroModel.getId());
        if (hopDongPhuTroModel.getId() <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (hopDongPhuTroModel.getTen().isEmpty() || hopDongPhuTroModel.getGia() < 0) {
            throw new BadRequestException();
        }
        DmLoaiHopDongPhuTroEntity hopDongPhuTroToUpdate = convertModelToEntity(hopDongPhuTroModel);
        dmLoaiHopDongPhuTroRepository.save(hopDongPhuTroToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, hopDongPhuTroToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmLoaiHopDongPhuTroModel.fromEntity(hopDongPhuTroToUpdate);
    }

    public DmLoaiHopDongPhuTroModel delete(int id) {
        if (id <= 0) {
            throw new BadRequestException();
        }
        DmLoaiHopDongPhuTroEntity entityToDelete = this.findById(id);
        if (entityToDelete == null) {
            throw new InvalidDataException();
        }
        if (checkInUsed(entityToDelete)) {
            throw new DeleteInUseDataException();
        }
        entityToDelete.setDeletedAt(new Date());
        dmLoaiHopDongPhuTroRepository.save(entityToDelete);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, entityToDelete, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });
        return DmLoaiHopDongPhuTroModel.fromEntity(convertModelToEntity(entityToDelete));
    }

    public List<DmLoaiHopDongPhuTroModel> convertListEntityToModel(
            List<DmLoaiHopDongPhuTroEntity> listEntity) {
        List<DmLoaiHopDongPhuTroModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmLoaiHopDongPhuTroModel model = DmLoaiHopDongPhuTroModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    private DmLoaiHopDongPhuTroEntity convertModelToEntity(DmLoaiHopDongPhuTroModel model) {
        DmLoaiHopDongPhuTroEntity entity = new DmLoaiHopDongPhuTroEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setMa(model.getMa());
            entity.setTen(model.getTen());
            entity.setGia(model.getGia());
            entity.setGhiChu(model.getGhiChu());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmLoaiHopDongPhuTroEntity entity) {
        long count = hopDongTramPhuTroRepository.countByPhuTroId(entity.getId());
        if (count > 0) {
            return true;
        }
        return false;
    }
}
