package com.contract.hopdong.hopdongpheduyet_nguoinhan.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.hopdong.hopdongpheduyet.entity.HopDongPheDuyetEntity;
import com.contract.hopdong.hopdongpheduyet_nguoinhan.model.HopDongPheDuyetNguoiNhanModel;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_phe_duyet_nguoi_nhan")
@Where(clause = "deleted_at IS NULL")
public class HopDongPheDuyetNguoiNhanEntity extends HopDongPheDuyetNguoiNhanModel {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hop_dong_phe_duyet_id", insertable = false, updatable = false)
  private HopDongPheDuyetEntity hopDongPheDuyetEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "nguoi_dung_id", insertable = false, updatable = false)
  private NguoiDungEntity nguoiDungEntity;
}
