package com.contract.hopdong.hopdongtram_phutro.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.hibernate.proxy.HibernateProxy;
import com.contract.common.entity.AuditEntity;
import com.contract.danhmuc.loaihopdongphutro.model.DmLoaiHopDongPhuTroModel;
import com.contract.hopdong.hopdongtram.model.HopDongTramModel;
import com.contract.hopdong.hopdongtram_phutro.entity.HopDongTramPhuTroEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongTramPhuTroModel extends AuditEntity<String> {
  public static HopDongTramPhuTroModel fromEntity(HopDongTramPhuTroEntity entity,
      boolean containChild) {
    HopDongTramPhuTroModel model = new HopDongTramPhuTroModel();
    model.setCreatedAt(entity.getCreatedAt());
    model.setUpdatedAt(entity.getUpdatedAt());
    model.setId(entity.getId());

    model.setHopDongTramId(entity.getHopDongTramId());
    model.setDmPhuTroId(entity.getDmPhuTroId());
    model.setGia(entity.getGia());
    model.setHienThiThongTinChiTiet(entity.getHienThiThongTinChiTiet());

    if (containChild) {
      if (entity.getDmLoaiHopDongPhuTroEntity() != null
          && !(entity.getDmLoaiHopDongPhuTroEntity() instanceof HibernateProxy)) {
        model.setDmLoaiHopDongPhuTro(
            DmLoaiHopDongPhuTroModel.fromEntity(entity.getDmLoaiHopDongPhuTroEntity()));
      }
    }

    return model;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id = 0L;

  @Column(name = "hop_dong_tram_id", nullable = false)
  protected Long hopDongTramId;

  @Column(name = "dm_phu_tro_id", nullable = false)
  protected Integer dmPhuTroId;

  @Column(name = "gia", nullable = false)
  protected Double gia;

  @Column(name = "hien_thi_thong_tin_chi_tiet", nullable = false)
  protected Boolean hienThiThongTinChiTiet;

  @Transient
  protected DmLoaiHopDongPhuTroModel dmLoaiHopDongPhuTro;

  @Transient
  protected HopDongTramModel hopDongTram;

}
