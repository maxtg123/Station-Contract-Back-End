package com.contract.danhmuc.doituongkyhopdong.service;

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
import com.contract.danhmuc.doituongkyhopdong.entity.DmDoiTuongKyHopDongEntity;
import com.contract.danhmuc.doituongkyhopdong.model.DmDoiTuongKyHopDongModel;
import com.contract.danhmuc.doituongkyhopdong.repository.DmDoiTuongKyHopDongRepository;
import com.contract.hopdong.hopdong.repository.HopDongRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class DmDoiTuongKyHopDongService {
    @Autowired
    private DmDoiTuongKyHopDongRepository dmDoiTuongKyHopDongRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private HopDongRepository hopDongRepository;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmDoiTuongKyHopDongEntity> findAll() {
        return dmDoiTuongKyHopDongRepository.findAll();
    }

    public DmDoiTuongKyHopDongEntity findById(Integer id) {
        return dmDoiTuongKyHopDongRepository.findById(id).orElse(null);
    }

    public DmDoiTuongKyHopDongEntity findByTen(String ten) {
        List<DmDoiTuongKyHopDongEntity> dmDoiTuongKyHopDongEntities = dmDoiTuongKyHopDongRepository.findAllByTen(ten);
        if (ObjectUtils.isEmpty(dmDoiTuongKyHopDongEntities)) {
            return null;
        }
        return dmDoiTuongKyHopDongEntities.get(0);
    }

    public DmDoiTuongKyHopDongModel save(DmDoiTuongKyHopDongModel model) {
        if (model.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmDoiTuongKyHopDongEntity entityToSave = convertModelToEntity(model);
        DmDoiTuongKyHopDongEntity saved = dmDoiTuongKyHopDongRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(model, saved, (model.getId() == null || model.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (model.getId() == null || model.getId() == 0) ? LogActionEnum.CREATE : LogActionEnum.UPDATE);
        });
        return DmDoiTuongKyHopDongModel.fromEntity(saved);
    }

    public DmDoiTuongKyHopDongModel update(DmDoiTuongKyHopDongModel dmDoiTuongKyHopDongModel) {
        DmDoiTuongKyHopDongEntity originalModel = findById(dmDoiTuongKyHopDongModel.getId());
        if (dmDoiTuongKyHopDongModel.getId() <= 0
                || originalModel == null) {
            throw new NotFoundException();
        }
        if (dmDoiTuongKyHopDongModel.getTen().isEmpty()) {
            throw new BadRequestException();
        }

        DmDoiTuongKyHopDongEntity loaiDoiTuongKyHopDongToUpdate = convertModelToEntity(dmDoiTuongKyHopDongModel);
        dmDoiTuongKyHopDongRepository.save(loaiDoiTuongKyHopDongToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, loaiDoiTuongKyHopDongToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmDoiTuongKyHopDongModel.fromEntity(loaiDoiTuongKyHopDongToUpdate);
    }

    public DmDoiTuongKyHopDongModel delete(Integer id) throws DeleteInUseDataException {
        if (id <= 0 || findById(id) == null) {
            throw new NotFoundException();
        }
        DmDoiTuongKyHopDongEntity dmHinhDoiTuongKyHopDongToDelete = findById(id);
        if (dmHinhDoiTuongKyHopDongToDelete == null) {
            throw new InvalidDataException();
        }
        if (checkInUsed(dmHinhDoiTuongKyHopDongToDelete)) {
            throw new DeleteInUseDataException();
        }
        dmHinhDoiTuongKyHopDongToDelete.setDeletedAt(new Date());

        dmDoiTuongKyHopDongRepository.save(dmHinhDoiTuongKyHopDongToDelete);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, dmHinhDoiTuongKyHopDongToDelete, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmDoiTuongKyHopDongModel.fromEntity(dmHinhDoiTuongKyHopDongToDelete);
    }

    public List<DmDoiTuongKyHopDongModel> convertListEntityToModel(
            List<DmDoiTuongKyHopDongEntity> listEntity) {
        List<DmDoiTuongKyHopDongModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmDoiTuongKyHopDongModel model = DmDoiTuongKyHopDongModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    private DmDoiTuongKyHopDongEntity convertModelToEntity(DmDoiTuongKyHopDongModel model) {
        DmDoiTuongKyHopDongEntity entity = new DmDoiTuongKyHopDongEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setTen(model.getTen());
            entity.setMa(model.getMa());
            entity.setGhiChu(model.getGhiChu());
            entity.setMaDataSite(model.getMaDataSite());
            entity.setLoaiDoiTuong(model.getLoaiDoiTuong());
        }
        return entity;
    }

    private boolean checkInUsed(DmDoiTuongKyHopDongEntity entity) {
        long countHD = hopDongRepository.countByDoiTuongKyId(entity.getId());
        if (countHD > 0) {
            return true;
        }
        return false;
    }
}
