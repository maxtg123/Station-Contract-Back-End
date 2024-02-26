package com.contract.hopdong.hopdongdamphan_nguoinhan.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.proxy.HibernateProxy;

import com.contract.common.entity.AuditEntity;
import com.contract.hopdong.hopdongdamphan.model.HopDongDamPhanModel;
import com.contract.hopdong.hopdongdamphan_nguoinhan.entity.HopDongDamPhanNguoiNhanEntity;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongDamPhanNguoiNhanModel extends AuditEntity<String> {
  public static HopDongDamPhanNguoiNhanModel fromEntity(HopDongDamPhanNguoiNhanEntity entity, boolean containChild) {
    HopDongDamPhanNguoiNhanModel model = new HopDongDamPhanNguoiNhanModel();
    model.setCreatedAt(entity.getCreatedAt());
    model.setUpdatedAt(entity.getUpdatedAt());
    model.setId(entity.getId());

    model.setHopDongDamPhanId(entity.getHopDongDamPhanId());
    model.setNguoiDungId(entity.getNguoiDungId());
    if (containChild) {
      if (entity.getNguoiDungEntity() != null && !(entity.getNguoiDungEntity() instanceof HibernateProxy)) {
        model.setNguoiDung(NguoiDungModel.fromEntity(entity.getNguoiDungEntity(), false));
      }
    }

    return model;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id = 0L;

  @Column(name = "hop_dong_dam_phan_id")
  protected Long hopDongDamPhanId;

  @Column(name = "nguoi_dung_id")
  protected Long nguoiDungId;

  @Transient
  private HopDongDamPhanModel hopDongDamPhan;

  @Transient
  private NguoiDungModel nguoiDung;
}
