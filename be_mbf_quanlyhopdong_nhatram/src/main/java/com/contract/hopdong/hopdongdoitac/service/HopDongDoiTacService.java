package com.contract.hopdong.hopdongdoitac.service;

import com.contract.hopdong.hopdongtram.entity.HopDongTramEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.base.service.BaseService;
import com.contract.hopdong.hopdongdoitac.entity.HopDongDoiTacEntity;
import com.contract.hopdong.hopdongdoitac.model.HopDongDoiTacModel;
import com.contract.hopdong.hopdongdoitac.repository.HopDongDoiTacRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class HopDongDoiTacService extends BaseService {
    @Autowired
    private HopDongDoiTacRepository hopDongDoiTacRepository;

    @Autowired
    private EntityManager entityManager;
    // public HopDongDoiTacEntity findById(Long id) {
    // return hopDongDoiTacRepository.findById(id).orElse(null);
    // }

    // public HopDongDoiTacEntity findByHopDong(Long id) {
    // List<HopDongDoiTacEntity> hopDongDoiTacEntityList =
    // hopDongDoiTacRepository.findAllByHopDongNhaTramId(id);
    // if (ObjectUtils.isEmpty(hopDongDoiTacEntityList)) {
    // return null;
    // }
    // return hopDongDoiTacEntityList.get(0);
    // }

    // public HopDongDoiTacModel create(HopDongDoiTacModel hopDongDoiTacModel)
    // throws Exception {
    // HopDongDoiTacEntity toSave = new HopDongDoiTacEntity();
    // toSave = convertModelToEntity(toSave, hopDongDoiTacModel);
    // return save(toSave);
    // }

    // public HopDongDoiTacModel update(HopDongDoiTacModel hopDongDoiTacModel)
    // throws NotFoundException {
    // if (hopDongDoiTacModel == null || (hopDongDoiTacModel.getId() == null &&
    // hopDongDoiTacModel.getHopDongNhaTramId() == null)) {
    // throw new InvalidDataException();
    // }
    // HopDongDoiTacEntity toSave = findById(hopDongDoiTacModel.getId());
    // if (toSave == null) {
    // toSave = findByHopDong(hopDongDoiTacModel.getHopDongNhaTramId());
    // }
    // if (toSave == null) {
    // throw new NotFoundException();
    // }

    // private HopDongDoiTacModel save(HopDongDoiTacEntity entity) {
    // try {
    // HopDongDoiTacEntity saved = hopDongDoiTacRepository.save(entity);
    // return HopDongDoiTacModel.fromEntity(saved, false);
    // } catch (Exception e) {
    // throw new InvalidDataException();
    // }
    // }

    public HopDongDoiTacEntity convertModelToEntity(HopDongDoiTacEntity entity,
                                                    HopDongDoiTacModel model) {
        entity.setHopDongId(model.getHopDongId());
        entity.setTen(model.getTen());
        entity.setSoDienThoai(model.getSoDienThoai());
        entity.setCccd(model.getCccd());
        entity.setMaSoThue(model.getMaSoThue());
        entity.setDiaChi(model.getDiaChi());
        entity.setChuTaiKhoan(model.getChuTaiKhoan());
        entity.setSoTaiKhoan(model.getSoTaiKhoan());
        entity.setNganHangChiNhanh(model.getNganHangChiNhanh());

        return entity;
    }

    /*find HDDTby id HD*/
    public List<HopDongDoiTacEntity> findByIdHopDong(Long id) {
        String query = "select * from hop_dong_doi_tac where hop_dong_id = '" + id + "'";
        Query nativeQuery = entityManager.createNativeQuery(query, HopDongDoiTacEntity.class);
        List<HopDongDoiTacEntity> list = nativeQuery.getResultList();
        return list;
    }

    /*xoa tung hop dong phu tro*/
    public void deleteListHDDTById(List<HopDongDoiTacEntity> list) {
        if (list.size() > 0) {
            for (HopDongDoiTacEntity hd : list) {
                try {
                    hopDongDoiTacRepository.deleteById(hd.getId());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
