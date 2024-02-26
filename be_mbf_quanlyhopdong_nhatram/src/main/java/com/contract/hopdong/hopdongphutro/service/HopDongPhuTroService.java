// package com.contract.hopdong.hopdongphutro.service;

// import com.contract.base.service.BaseService;
// import com.contract.common.exception.InvalidDataException;
// import com.contract.hopdong.hopdongphutro.entity.HopDongPhuTroEntity;
// import com.contract.hopdong.hopdongphutro.model.HopDongPhuTroModel;
// import com.contract.hopdong.hopdongphutro.repository.HopDongPhuTroRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// @Service
// public class HopDongPhuTroService extends BaseService {
// @Autowired
// private HopDongPhuTroRepository hopDongPhuTroRepository;

// public HopDongPhuTroModel create(HopDongPhuTroModel hopDongPhuTroModel) {
// HopDongPhuTroEntity toSave = new HopDongPhuTroEntity();
// toSave = convertModelToEntity(toSave, hopDongPhuTroModel);

// return save(toSave);
// }

// public Long deleteByHopDong(Long id){
// return hopDongPhuTroRepository.deleteByHopDongId(id);
// }
// public void flush(){
// hopDongPhuTroRepository.flush();
// }

// private HopDongPhuTroModel save(HopDongPhuTroEntity entity) {
// try {
// HopDongPhuTroEntity saved = hopDongPhuTroRepository.save(entity);
// return HopDongPhuTroModel.fromEntity(saved, false);
// } catch (Exception e) {
// System.out.println(e);
// throw new InvalidDataException();
// }
// }

// private HopDongPhuTroEntity convertModelToEntity(HopDongPhuTroEntity entity,
// HopDongPhuTroModel model) {
// entity.setHopDongId(model.getHopDongId());
// entity.setDmPhuTroId(model.getDmPhuTroId());
// entity.setGia(model.getGia());
// entity.setHienThiThongTinChiTiet(model.getHienThiThongTinChiTiet());

// return entity;
// }
// }
