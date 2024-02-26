// package com.contract.hopdong.hopdongkythanhtoan.service;

// import com.contract.base.service.BaseService;
// import com.contract.common.exception.InvalidDataException;
// import com.contract.common.exception.NotFoundException;
// import
// com.contract.hopdong.hopdongkythanhtoan.entity.HopDongKyThanhToanEntity;
// import com.contract.hopdong.hopdongkythanhtoan.model.HopDongKyThanhToanModel;
// import
// com.contract.hopdong.hopdongkythanhtoan.repository.HopDongKyThanhToanRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// @Service
// public class HopDongKyThanhToanService extends BaseService {
// @Autowired
// private HopDongKyThanhToanRepository hopDongKyThanhToanRepository;

// public Long deleteByHopDong(Long id){
// return
// hopDongKyThanhToanRepository.deleteByHopDongIdAndDaThanhToanNgayIsNull(id);
// }
// public void flush(){
// hopDongKyThanhToanRepository.flush();
// }

// public HopDongKyThanhToanModel create(HopDongKyThanhToanModel
// hopDongKyThanhToanModel) {
// HopDongKyThanhToanEntity toSave = new HopDongKyThanhToanEntity();
// toSave = convertModelToEntity(toSave, hopDongKyThanhToanModel);

// return save(toSave);
// }

// public HopDongKyThanhToanModel save(HopDongKyThanhToanEntity entity) {
// try {
// HopDongKyThanhToanEntity saved = hopDongKyThanhToanRepository.save(entity);
// return HopDongKyThanhToanModel.fromEntity(saved, false);
// } catch (Exception e) {
// System.out.println(e);
// throw new InvalidDataException();
// }
// }

// private HopDongKyThanhToanEntity
// convertModelToEntity(HopDongKyThanhToanEntity entity, HopDongKyThanhToanModel
// model) {
// entity.setHopDongId(model.getHopDongId());
// entity.setTuNgay(model.getTuNgay());
// entity.setDenNgay(model.getDenNgay());
// entity.setTien(model.getTien());
// entity.setDaThanhToanNgay(model.getDaThanhToanNgay());
// entity.setThanhToanBy(model.getThanhToanBy());
// entity.setSoTienThanhToan(model.getSoTienThanhToan());

// return entity;
// }

// public HopDongKyThanhToanEntity findKyCanThanhToan(Long hopDongId) {
// if (hopDongKyThanhToanRepository.findFirstKyCanThanhToan(hopDongId).size() >
// 0) {
// return
// hopDongKyThanhToanRepository.findFirstKyCanThanhToan(hopDongId).get(0);
// } else {
// throw new NotFoundException();
// }
// }
// }