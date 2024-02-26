package com.contract.hopdong.hopdongdamphan_file.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.hopdong.hopdongdamphan_file.model.HopDongDamPhanFileModel;
import com.contract.hopdong.hopdongdamphan_tientrinh.entity.HopDongDamPhanTienTrinhEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_dam_phan_files")
@Where(clause = "deleted_at IS NULL")
public class HopDongDamPhanFileEntity extends HopDongDamPhanFileModel {
  public static HopDongDamPhanFileEntity fromModel(HopDongDamPhanFileModel model) {
    HopDongDamPhanFileEntity entity = new HopDongDamPhanFileEntity();
    entity.setTen(model.getTen());
    entity.setHopDongDamPhanId(model.getHopDongDamPhanId());
    entity.setHopDongDamPhanTienTrinhId(model.getHopDongDamPhanTienTrinhId());

    return entity;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hop_dong_dam_phan_tien_trinh_id", insertable = false, updatable = false)
  private HopDongDamPhanTienTrinhEntity hopDongDamPhanTienTrinhEntity;
}
