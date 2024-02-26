// package com.contract.hopdong.hopdongdungchung.service;

// import com.contract.base.service.BaseService;
// import com.contract.common.exception.InvalidDataException;
// import com.contract.common.exception.NotFoundException;
// import com.contract.hopdong.hopdongdungchung.entity.HopDongDungChungEntity;
// import com.contract.hopdong.hopdongdungchung.model.HopDongDungChungModel;
// import
// com.contract.hopdong.hopdongdungchung.repository.HopDongDungChungRepository;
// import org.apache.commons.lang3.ObjectUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class HopDongDungChungService extends BaseService {
// @Autowired
// private HopDongDungChungRepository hopDongDungChungRepository;

// public HopDongDungChungEntity findById(Long id) {
// return hopDongDungChungRepository.findById(id).orElse(null);
// }

// public HopDongDungChungEntity findByHopDong(Long id) {
// List<HopDongDungChungEntity> hopDongDungChungEntityList =
// hopDongDungChungRepository.findAllByHopDongNhaTramId(id);
// if (ObjectUtils.isEmpty(hopDongDungChungEntityList)) {
// return null;
// }
// return hopDongDungChungEntityList.get(0);
// }

// public HopDongDungChungModel create(HopDongDungChungModel
// hopDongDungChungModel) throws Exception {
// HopDongDungChungEntity toSave = new HopDongDungChungEntity();
// toSave = convertModelToEntity(toSave, hopDongDungChungModel);
// return save(toSave);
// }

// public HopDongDungChungModel update(HopDongDungChungModel
// hopDongDungChungModel) throws NotFoundException {
// if (hopDongDungChungModel == null || (hopDongDungChungModel.getId() == null
// && hopDongDungChungModel.getHopDongNhaTramId() == null)) {
// throw new InvalidDataException();
// }
// HopDongDungChungEntity toSave = findById(hopDongDungChungModel.getId());
// if (toSave == null) {
// toSave = findByHopDong(hopDongDungChungModel.getHopDongNhaTramId());
// }
// if (toSave == null) {
// throw new NotFoundException();
// }

// toSave = convertModelToEntity(toSave, hopDongDungChungModel);
// return save(toSave);
// }

// private HopDongDungChungModel save(HopDongDungChungEntity entity) {
// try {
// HopDongDungChungEntity saved = hopDongDungChungRepository.save(entity);
// return HopDongDungChungModel.fromEntity(saved, false);
// } catch (Exception e) {
// throw new InvalidDataException();
// }
// }

// private HopDongDungChungEntity convertModelToEntity(HopDongDungChungEntity
// entity, HopDongDungChungModel model) {
// entity.setHopDongNhaTramId(model.getHopDongNhaTramId());
// entity.setLoaiHangMucCSHTId(model.getLoaiHangMucCSHTId());
// entity.setMaTramDonViDungChung(model.getMaTramDonViDungChung());
// entity.setDonViDungChung(model.getDonViDungChung());
// entity.setThoiDiemPhatSinh(model.getThoiDiemPhatSinh());
// entity.setNgayLapDatThietBi(model.getNgayLapDatThietBi());

// return entity;
// }
// }
