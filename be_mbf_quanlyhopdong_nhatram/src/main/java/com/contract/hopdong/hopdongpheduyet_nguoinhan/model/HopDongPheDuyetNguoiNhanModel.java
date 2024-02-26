package com.contract.hopdong.hopdongpheduyet_nguoinhan.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.proxy.HibernateProxy;

import com.contract.common.entity.AuditEntity;
import com.contract.hopdong.hopdongpheduyet.model.HopDongPheDuyetModel;
import com.contract.hopdong.hopdongpheduyet_nguoinhan.entity.HopDongPheDuyetNguoiNhanEntity;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongPheDuyetNguoiNhanModel extends AuditEntity<String> {
  public static HopDongPheDuyetNguoiNhanModel fromEntity(HopDongPheDuyetNguoiNhanEntity entity, boolean containChild) {
    HopDongPheDuyetNguoiNhanModel model = new HopDongPheDuyetNguoiNhanModel();
    model.setCreatedAt(entity.getCreatedAt());
    model.setUpdatedAt(entity.getUpdatedAt());
    model.setId(entity.getId());
    model.setHopDongPheDuyetId(entity.getHopDongPheDuyetId());
    model.setNguoiDungId(entity.getNguoiDungId());
    if (containChild) {
      if (entity.getNguoiDungEntity() != null && !(entity.getNguoiDungEntity() instanceof HibernateProxy)) {
        model.setNguoiDung(NguoiDungModel.fromEntity(entity.getNguoiDungEntity(),
            false));
      }
    }

    return model;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id = 0L;

  @Column(name = "hop_dong_phe_duyet_id")
  protected Long hopDongPheDuyetId;

  @Column(name = "nguoi_dung_id")
  protected Long nguoiDungId;

  @Transient
  private HopDongPheDuyetModel hopDongPheDuyet;

  @Transient
  private NguoiDungModel nguoiDung;
}
