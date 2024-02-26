package com.contract.chucvu.phanquyen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.chucvu.phanquyen.entity.ChucVuPhanQuyenEntity;
import com.contract.chucvu.phanquyen.model.ChucVuPhanQuyenModel;
import com.contract.chucvu.phanquyen.repository.ChucVuPhanQuyenRepository;
import com.contract.common.exception.InvalidDataException;

@Service
public class ChucVuPhanQuyenService {
    @Autowired
    private ChucVuPhanQuyenRepository chucVuPhanQuyenRepository;

    public ChucVuPhanQuyenModel create(ChucVuPhanQuyenModel model) throws Exception {
        ChucVuPhanQuyenEntity toSave = new ChucVuPhanQuyenEntity();
        toSave = convertModelToEntity(toSave, model);

        return save(toSave);
    }

    public ChucVuPhanQuyenModel save(ChucVuPhanQuyenEntity entity) {
        try {
            ChucVuPhanQuyenEntity saved = chucVuPhanQuyenRepository.save(entity);
            return ChucVuPhanQuyenModel.fromEntity(saved, false);
        } catch (Exception e) {
            throw new InvalidDataException();
        }
    }

    public long deleteByChucVuId(Long id) {
        return chucVuPhanQuyenRepository.deleteByPhongDaiChucVuId(id);
    }

    public void flush() {
        chucVuPhanQuyenRepository.flush();
    }

    private ChucVuPhanQuyenEntity convertModelToEntity(ChucVuPhanQuyenEntity entity, ChucVuPhanQuyenModel model) {

        if (model.getId() != null) {
            entity.setId(model.getId());
        }
        if (model.getModule() != null) {
            entity.setModule(model.getModule());
        }
        if (model.getAction() != null) {
            entity.setAction(model.getAction());
        }
        if (model.getPhongDaiChucVuId() != null) {
            entity.setPhongDaiChucVuId(model.getPhongDaiChucVuId());
        }

        return entity;
    }
}
