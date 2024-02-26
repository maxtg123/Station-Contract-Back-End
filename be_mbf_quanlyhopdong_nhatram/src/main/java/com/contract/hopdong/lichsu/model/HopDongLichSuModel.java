package com.contract.hopdong.lichsu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.proxy.HibernateProxy;

import com.contract.common.entity.AuditEntity;
import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.hopdong.hopdong.model.HopDongModel;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_lich_su")
public class HopDongLichSuModel extends AuditEntity<String> {
  public static HopDongLichSuModel fromEntity(HopDongLichSuModel entity, boolean containChild) {
    HopDongLichSuModel model = new HopDongLichSuModel();
    model.setId(entity.getId());
    model.setCreatedAt(entity.getCreatedAt());
    model.setUpdatedAt(entity.getUpdatedAt());

    model.setHopDongId(entity.getHopDongId());
    model.setNguoiDungId(entity.getNguoiDungId());
    model.setChangeLog(entity.getChangeLog());
    model.setVersion(entity.getVersion());

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

  @Column(name = "hop_dong_id", nullable = false)
  protected Long hopDongId;

  @Column(name = "nguoi_dung_id")
  protected Long nguoiDungId;

  @Lob
  @Column(name = "change_log", nullable = true, columnDefinition = "CLOB")
  protected String changeLog;

  @Column(name = "version")
  protected String version;

  @Transient
  protected NguoiDungModel nguoiDung;

  @Transient
  protected HopDongModel hopDong;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "nguoi_dung_id", insertable = false, updatable = false)
  @JsonIgnore
  private NguoiDungEntity nguoiDungEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hop_dong_id", insertable = false, updatable = false)
  @JsonIgnore
  private HopDongEntity hopDongEntity;
}
