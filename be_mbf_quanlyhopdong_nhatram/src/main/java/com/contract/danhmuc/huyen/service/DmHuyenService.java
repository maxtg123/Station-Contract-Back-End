package com.contract.danhmuc.huyen.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.common.exception.BadRequestException;
import com.contract.common.exception.NotFoundException;
import com.contract.danhmuc.huyen.entity.DmHuyenEntity;
import com.contract.danhmuc.huyen.model.DmHuyenModel;
import com.contract.danhmuc.huyen.repository.DmHuyenRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class DmHuyenService {
    @Autowired
    private DmHuyenRepository dmHuyenRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmHuyenEntity> findAll() {
        return dmHuyenRepository.findAll();
    }

    public DmHuyenModel findById(int id) {
        return dmHuyenRepository.findById(id).orElse(null);
    }

    public int findIdByTen(String tenHuyen) {
        // Trả về id của huyện được truyền tên vào
        return dmHuyenRepository.findIdByTen(tenHuyen);
    }

    public int findIdByTenAndTinhId(String tenHuyen, int tinhId) {
        return dmHuyenRepository.findIdByTenAndTinhId(tenHuyen, tinhId);
    }

    public DmHuyenEntity findByMa(String maQuanHuyen) {
        return dmHuyenRepository.findByMa(maQuanHuyen);
    }

    public List<DmHuyenEntity> findByTinh(int tinhId) {
        // Trả về danh sách Huyện thuộc Tỉnh
        if (tinhId <= 0) {
            throw new NotFoundException();
        }
        return dmHuyenRepository.findBytinhId(tinhId);
    }

    public DmHuyenEntity findByTen(String ten) {
        return dmHuyenRepository.findByTen(ten);
    }

    public int kiemTraHuyenExists(String tenHuyen) {
        return dmHuyenRepository.countIdByTen(tenHuyen);
    }

    public DmHuyenModel save(DmHuyenModel model) {
        if (model.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmHuyenEntity entityToSave = convertModelToEntity(model);
        DmHuyenEntity saved = dmHuyenRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(model, saved, (model.getId() == null || model.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (model.getId() == null || model.getId() == 0) ? LogActionEnum.CREATE : LogActionEnum.UPDATE);
        });

        return DmHuyenModel.fromEntity(saved);
    }

    public DmHuyenModel update(DmHuyenModel dmHuyenModel) {
        DmHuyenModel originalModel = findById(dmHuyenModel.getId());
        if (dmHuyenModel.getId() <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (dmHuyenModel.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmHuyenEntity dmHuyenToUpdate = convertModelToEntity(dmHuyenModel);
        dmHuyenRepository.save(dmHuyenToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, dmHuyenToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmHuyenModel.fromEntity(dmHuyenToUpdate);
    }

    public List<DmHuyenModel> convertListEntityToModel(List<DmHuyenEntity> listEntity) {
        List<DmHuyenModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmHuyenModel model = DmHuyenModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    private DmHuyenEntity convertModelToEntity(DmHuyenModel model) {
        DmHuyenEntity entity = new DmHuyenEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setTen(model.getTen());
            entity.setMa(model.getMa());
            entity.setGhiChu(model.getGhiChu());
            entity.setTinhId(model.getTinhId());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmHuyenEntity entity) {
        if (!ObjectUtils.isEmpty(entity.getDmXaEntitySet())
                || !ObjectUtils.isEmpty(entity.getTramEntitySet())) {
            return true;
        }
        return false;
    }
}
