package com.contract.hopdong.hopdongtram_kythanhtoan.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.contract.hopdong.hopdongtram.entity.HopDongTramEntity;
import com.contract.hopdong.hopdongtram_kythanhtoan.model.HopDongTramKyThanhToanModel;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_tram_ky_thanh_toan")
public class HopDongTramKyThanhToanEntity extends HopDongTramKyThanhToanModel {

  public static HopDongTramKyThanhToanEntity fromModel(HopDongTramKyThanhToanModel model) {
    HopDongTramKyThanhToanEntity entity = new HopDongTramKyThanhToanEntity();
    entity.setHopDongTramId(model.getHopDongTramId());
    entity.setTuNgay(model.getTuNgay());
    entity.setDenNgay(model.getDenNgay());
    entity.setGiaTien(model.getGiaTien());
    entity.setDaThanhToanNgay(model.getDaThanhToanNgay());
    entity.setThanhToanBy(model.getThanhToanBy());
    return entity;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hop_dong_tram_id", insertable = false, updatable = false)
  private HopDongTramEntity hopDongTramEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "thanh_toan_by", insertable = false, updatable = false)
  private NguoiDungEntity nguoiDungEntity;
}
