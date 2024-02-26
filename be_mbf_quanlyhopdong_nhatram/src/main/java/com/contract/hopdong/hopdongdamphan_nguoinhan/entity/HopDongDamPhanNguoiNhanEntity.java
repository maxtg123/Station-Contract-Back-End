package com.contract.hopdong.hopdongdamphan_nguoinhan.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.hopdong.hopdongdamphan.entity.HopDongDamPhanEntity;
import com.contract.hopdong.hopdongdamphan_nguoinhan.model.HopDongDamPhanNguoiNhanModel;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_dam_phan_nguoi_nhan")
@Where(clause = "deleted_at IS NULL")
public class HopDongDamPhanNguoiNhanEntity extends HopDongDamPhanNguoiNhanModel {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hop_dong_dam_phan_id", insertable = false, updatable = false)
  private HopDongDamPhanEntity hopDongDamPhanEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "nguoi_dung_id", insertable = false, updatable = false)
  private NguoiDungEntity nguoiDungEntity;
}
