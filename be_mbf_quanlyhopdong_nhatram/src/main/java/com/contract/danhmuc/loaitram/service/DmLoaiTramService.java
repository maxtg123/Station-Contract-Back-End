package com.contract.danhmuc.loaitram.service;

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
import com.contract.danhmuc.loaitram.entity.DmLoaiTramEntity;
import com.contract.danhmuc.loaitram.model.DmLoaiTramModel;
import com.contract.danhmuc.loaitram.repository.DmLoaiTramRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.tram.tram.repository.TramRepository;

@Service
public class DmLoaiTramService {
    @Autowired
    private DmLoaiTramRepository dmLoaiTramRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private TramRepository tramRepository;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmLoaiTramEntity> findAll() {
        return dmLoaiTramRepository.findAll();
    }

    public DmLoaiTramEntity findById(int id) {
        return dmLoaiTramRepository.findById(id).orElse(null);
    }

    public DmLoaiTramModel findByTen(String ten) {
        return dmLoaiTramRepository.findByTen(ten);
    }

    public DmLoaiTramModel save(DmLoaiTramModel model) {
        if (model.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmLoaiTramEntity entityToSave = convertModelToEntity(model);
        DmLoaiTramEntity saved = dmLoaiTramRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(model, saved, (model.getId() == null || model.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (model.getId() == null || model.getId() == 0) ? LogActionEnum.CREATE : LogActionEnum.UPDATE);
        });

        return DmLoaiTramModel.fromEntity(saved);
    }

    public DmLoaiTramModel update(DmLoaiTramModel dmLoaiTramModel) {
        DmLoaiTramModel originalModel = findById(dmLoaiTramModel.getId());
        if (dmLoaiTramModel.getId() <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (dmLoaiTramModel.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmLoaiTramEntity loaiTramToUpdate = convertModelToEntity(dmLoaiTramModel);
        dmLoaiTramRepository.save(loaiTramToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, loaiTramToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmLoaiTramModel.fromEntity(loaiTramToUpdate);
    }

    public List<DmLoaiTramModel> convertListEntityToModel(List<DmLoaiTramEntity> listEntity) {
        List<DmLoaiTramModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmLoaiTramModel model = DmLoaiTramModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    public DmLoaiTramModel delete(Integer id) throws DeleteInUseDataException {
        if (id <= 0 || findById(id) == null) {
            throw new NotFoundException();
        }
        DmLoaiTramEntity dmLoaiTramToDelete = findById(id);
        if (dmLoaiTramToDelete == null) {
            throw new InvalidDataException();
        }
        if (checkInUsed(dmLoaiTramToDelete)) {
            throw new DeleteInUseDataException();
        }
        dmLoaiTramToDelete.setDeletedAt(new Date());

        dmLoaiTramRepository.save(dmLoaiTramToDelete);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, dmLoaiTramToDelete, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmLoaiTramModel.fromEntity(dmLoaiTramToDelete);
    }

    private DmLoaiTramEntity convertModelToEntity(DmLoaiTramModel model) {
        DmLoaiTramEntity entity = new DmLoaiTramEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setTen(model.getTen());
            entity.setMa(model.getMa());
            entity.setGhiChu(model.getGhiChu());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmLoaiTramEntity entity) {
        long count = tramRepository.countByLoaiTramId(entity.getId());
        if (count > 0) {
            return true;
        }
        return false;
    }
}
