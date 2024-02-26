package com.contract.hopdong.hopdongtram_phutro.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.loaihopdongphutro.entity.DmLoaiHopDongPhuTroEntity;
import com.contract.hopdong.hopdongtram.entity.HopDongTramEntity;
import com.contract.hopdong.hopdongtram_phutro.model.HopDongTramPhuTroModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_tram_phu_tro")
@Where(clause = "deleted_at IS NULL")
public class HopDongTramPhuTroEntity extends HopDongTramPhuTroModel {
  public static HopDongTramPhuTroEntity fromModel(HopDongTramPhuTroModel model) {
    HopDongTramPhuTroEntity entity = new HopDongTramPhuTroEntity();
    entity.setHopDongTramId(model.getHopDongTramId());
    entity.setDmPhuTroId(model.getDmPhuTroId());
    entity.setGia(model.getGia());
    entity.setHienThiThongTinChiTiet(model.getHienThiThongTinChiTiet());
    return entity;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dm_phu_tro_id", insertable = false, updatable = false)
  private DmLoaiHopDongPhuTroEntity dmLoaiHopDongPhuTroEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hop_dong_tram_id", insertable = false, updatable = false)
  private HopDongTramEntity hopDongTramEntity;
}
