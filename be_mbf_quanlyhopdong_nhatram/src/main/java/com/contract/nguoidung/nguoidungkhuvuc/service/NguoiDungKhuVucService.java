package com.contract.nguoidung.nguoidungkhuvuc.service;

import com.contract.common.exception.InvalidDataException;
import com.contract.nguoidung.nguoidungkhuvuc.entity.NguoiDungKhuVucEntity;
import com.contract.nguoidung.nguoidungkhuvuc.model.NguoiDungKhuVucModel;
import com.contract.nguoidung.nguoidungkhuvuc.repository.NguoiDungKhuVucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NguoiDungKhuVucService {
    @Autowired
    private NguoiDungKhuVucRepository nguoiDungKhuVucRepository;

    public NguoiDungKhuVucModel create(NguoiDungKhuVucModel model) throws Exception {
        NguoiDungKhuVucEntity toSave = new NguoiDungKhuVucEntity();
        toSave = convertModelToEntity(toSave, model);

        return save(toSave);
    }

    public long deleteByNguoiDungId(Long id){
        return nguoiDungKhuVucRepository.deleteByNguoiDungId(id);
    }

    public void flush(){
        nguoiDungKhuVucRepository.flush();
    }

    public NguoiDungKhuVucModel save(NguoiDungKhuVucEntity entity) {
        try {
            NguoiDungKhuVucEntity saved = nguoiDungKhuVucRepository.save(entity);
            return NguoiDungKhuVucModel.fromEntity(saved, false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InvalidDataException();
        }
    }

    private NguoiDungKhuVucEntity convertModelToEntity(NguoiDungKhuVucEntity entity, NguoiDungKhuVucModel model) {

        if (model.getId() != null) {
            entity.setId(model.getId());
        }
        if (model.getNguoiDungId() != null) {
            entity.setNguoiDungId(model.getNguoiDungId());
        }
        if (model.getPhongDaiChucVuId() != null) {
            entity.setPhongDaiChucVuId(model.getPhongDaiChucVuId());
        }
        if (model.getPhongDaiId() != null) {
            entity.setPhongDaiId(model.getPhongDaiId());
        }
        if (model.getToId() != null) {
            entity.setToId(model.getToId());
        }
        if (model.getLoai() != null) {
            entity.setLoai(model.getLoai());
        }

        return entity;
    }

    public List<NguoiDungKhuVucEntity> findByNguoiDungId(Long id) {
        return nguoiDungKhuVucRepository.findByNguoiDungId(id);
    }
}
