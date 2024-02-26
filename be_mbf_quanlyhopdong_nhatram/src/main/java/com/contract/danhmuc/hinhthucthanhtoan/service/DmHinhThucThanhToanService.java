package com.contract.danhmuc.hinhthucthanhtoan.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.base.service.BaseService;
import com.contract.common.exception.BadRequestException;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.common.exception.InvalidDataException;
import com.contract.common.exception.NotFoundException;
import com.contract.danhmuc.hinhthucthanhtoan.entity.DmHinhThucThanhToanEntity;
import com.contract.danhmuc.hinhthucthanhtoan.model.DmHinhThucThanhToanModel;
import com.contract.danhmuc.hinhthucthanhtoan.repository.DmHinhThucThanhToanRepository;
import com.contract.hopdong.hopdong.repository.HopDongRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class DmHinhThucThanhToanService extends BaseService {
    @Autowired
    private DmHinhThucThanhToanRepository dmHinhThucThanhToanRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private HopDongRepository hopDongRepository;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmHinhThucThanhToanEntity> findAll() {
        return dmHinhThucThanhToanRepository.findAll();
    }

    public DmHinhThucThanhToanEntity findById(Integer id) {
        return dmHinhThucThanhToanRepository.findById(id).orElse(null);
    }

    public DmHinhThucThanhToanEntity findByTen(String ten) {
        List<DmHinhThucThanhToanEntity> dmHinhThucThanhToanEntities = dmHinhThucThanhToanRepository.findAllByTen(ten);
        if (ObjectUtils.isEmpty(dmHinhThucThanhToanEntities)) {
            return null;
        }
        return dmHinhThucThanhToanEntities.get(0);
    }

    public DmHinhThucThanhToanModel save(DmHinhThucThanhToanModel model) {
        if (ObjectUtils.isEmpty(model.getTen())) {
            throw new BadRequestException();
        }
        DmHinhThucThanhToanEntity entityToSave = convertModelToEntity(model);
        DmHinhThucThanhToanEntity saved = dmHinhThucThanhToanRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(model, saved, (model.getId() == null || model.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (model.getId() == null || model.getId() == 0) ? LogActionEnum.CREATE : LogActionEnum.UPDATE);
        });

        return DmHinhThucThanhToanModel.fromEntity(saved);
    }

    public DmHinhThucThanhToanModel update(DmHinhThucThanhToanModel dmHinhThucThanhToanModel) {
        DmHinhThucThanhToanEntity originalModel = findById(dmHinhThucThanhToanModel.getId());
        if (dmHinhThucThanhToanModel.getId() <= 0
                || originalModel == null) {
            throw new NotFoundException();
        }
        if (dmHinhThucThanhToanModel.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmHinhThucThanhToanEntity hinhThucThanhToanToUpdate = convertModelToEntity(dmHinhThucThanhToanModel);
        dmHinhThucThanhToanRepository.save(hinhThucThanhToanToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, hinhThucThanhToanToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmHinhThucThanhToanModel.fromEntity(hinhThucThanhToanToUpdate);
    }

    public DmHinhThucThanhToanModel delete(Integer id) throws DeleteInUseDataException {
        if (id <= 0 || findById(id) == null) {
            throw new NotFoundException();
        }
        DmHinhThucThanhToanEntity dmHinhThucThanhToanToDelete = findById(id);
        if (dmHinhThucThanhToanToDelete == null) {
            throw new InvalidDataException();
        }
        if (checkInUsed(dmHinhThucThanhToanToDelete)) {
            throw new DeleteInUseDataException();
        }
        dmHinhThucThanhToanToDelete.setDeletedAt(new Date());

        dmHinhThucThanhToanRepository.save(dmHinhThucThanhToanToDelete);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, dmHinhThucThanhToanToDelete, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmHinhThucThanhToanModel.fromEntity(dmHinhThucThanhToanToDelete);
    }

    public List<DmHinhThucThanhToanModel> convertListEntityToModel(
            List<DmHinhThucThanhToanEntity> listEntity) {
        List<DmHinhThucThanhToanModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmHinhThucThanhToanModel model = DmHinhThucThanhToanModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    private DmHinhThucThanhToanEntity convertModelToEntity(DmHinhThucThanhToanModel model) {
        DmHinhThucThanhToanEntity entity = new DmHinhThucThanhToanEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setTen(model.getTen());
            entity.setMa(model.getMa());
            entity.setGhiChu(model.getGhiChu());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmHinhThucThanhToanEntity entity) {
        long countHD = hopDongRepository.countByHinhThucThanhToanId(entity.getId());
        if (countHD > 0) {
            return true;
        }
        return false;
    }
}
