package com.contract.hopdong.hopdongpheduyet_nguoinhan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.hopdong.hopdongpheduyet_nguoinhan.entity.HopDongPheDuyetNguoiNhanEntity;
import com.contract.hopdong.hopdongpheduyet_nguoinhan.repository.HopDongPheDuyetNguoiNhanRepository;

// import
// com.contract.hopdong.hopdongpheduyet_nguoidung.entity.HopDongPheDuyetNguoiDungEntity;
// import
// com.contract.hopdong.hopdongpheduyet_nguoidung.model.HopDongPheDuyetNguoiDungModel;
// import
// com.contract.hopdong.hopdongpheduyet_nguoidung.repository.HopDongPheDuyetNguoiDungRepository;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.util.ObjectUtils;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.stream.Collectors;

@Service
public class HopDongPheDuyetNguoiNhanService {
  @Autowired
  private HopDongPheDuyetNguoiNhanRepository hopDongPheDuyetNguoiNhanRepository;

  public List<HopDongPheDuyetNguoiNhanEntity> saveAll(List<HopDongPheDuyetNguoiNhanEntity> entities) {
    return hopDongPheDuyetNguoiNhanRepository.saveAll(entities);
  }

  // public List<HopDongPheDuyetNguoiDungModel>
  // update(List<HopDongPheDuyetNguoiDungModel> hopDongPheDuyetNguoiDungModels) {
  // ObjectMapper mapper = new ObjectMapper();
  // List<HopDongPheDuyetNguoiDungEntity> listSave = new ArrayList<>();
  // hopDongPheDuyetNguoiDungModels.forEach(model -> {
  // try {
  // if (model.getId() != null &&
  // !ObjectUtils.isEmpty(hopDongPheDuyetNguoiDungRepository.findById(model.getId()).orElse(null)))
  // {
  // HopDongPheDuyetNguoiDungEntity toSave = new HopDongPheDuyetNguoiDungEntity();
  // convertModelToEntity(toSave, model);
  // listSave.add(mapper.convertValue(model,
  // HopDongPheDuyetNguoiDungEntity.class));
  // }
  // } catch (Exception e) {
  // System.out.println(e);
  // }
  // });
  // List<HopDongPheDuyetNguoiDungEntity> saved = saveAll(listSave);
  // List<HopDongPheDuyetNguoiDungModel> listReturn = saved.stream().map(e ->
  // HopDongPheDuyetNguoiDungModel.fromEntity(e,
  // false)).collect(Collectors.toList());
  // return listReturn;
  // }

  // private void convertModelToEntity(HopDongPheDuyetNguoiDungEntity entity,
  // HopDongPheDuyetNguoiDungModel model) {
  // entity.setHopDongPheDuyetId(model.getHopDongPheDuyetId());
  // entity.setNguoiDungId(model.getNguoiDungId());
  // entity.setTrangThai(model.getTrangThai());
  // }
}
