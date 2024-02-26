package com.contract.danhmuc.xa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.common.exception.BadRequestException;
import com.contract.common.exception.NotFoundException;
import com.contract.danhmuc.xa.entity.DmXaEntity;
import com.contract.danhmuc.xa.model.DmXaModel;
import com.contract.danhmuc.xa.repository.DmXaRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class DmXaService {
    @Autowired
    private DmXaRepository dmXaRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmXaEntity> findAll() {
        return dmXaRepository.findAll();
    }

    public DmXaEntity findById(int id) {
        return dmXaRepository.findById(id).orElse(null);
    }

    public DmXaEntity findByTen(String tenXa) {
        return dmXaRepository.findByTen(tenXa);
    }

    public List<DmXaEntity> findByHuyenId(int huyenId) {
        // Trả về danh sách Xã thuộc Huyện
        if (huyenId <= 0) {
            throw new NotFoundException();
        }
        return dmXaRepository.findByhuyenId(huyenId);
    }

    public DmXaEntity kiemTraXaTonTai(String tenXa, int huyenId) {
        return dmXaRepository.kiemTraXaTonTai(tenXa, huyenId);
    }

    public DmXaModel save(DmXaModel model) {
        if (model.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmXaEntity entityToSave = convertModelToEntity(model);
        DmXaEntity saved = dmXaRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(model, saved, (model.getId() == null || model.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (model.getId() == null || model.getId() == 0) ? LogActionEnum.CREATE : LogActionEnum.UPDATE);
        });

        return DmXaModel.fromEntity(saved);
    }

    public DmXaModel update(DmXaModel dmXaModel) {
        DmXaModel originalModel = findById(dmXaModel.getId());
        if (dmXaModel.getId() <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (dmXaModel.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmXaEntity loaiCshtToUpdate = convertModelToEntity(dmXaModel);
        dmXaRepository.save(loaiCshtToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, loaiCshtToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmXaModel.fromEntity(loaiCshtToUpdate);
    }

    public List<DmXaModel> convertListEntityToModel(List<DmXaEntity> listEntity) {
        List<DmXaModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmXaModel model = DmXaModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    private DmXaEntity convertModelToEntity(DmXaModel model) {
        DmXaEntity entity = new DmXaEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setTen(model.getTen());
            entity.setMa(model.getMa());
            entity.setGhiChu(model.getGhiChu());
            entity.setHuyenId(model.getHuyenId());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmXaEntity entity) {
        if (!ObjectUtils.isEmpty(entity.getTramEntitySet())) {
            return true;
        }
        return false;
    }
}
