package com.contract.hopdong.hopdongdamphan_tientrinh.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Comment;

import com.contract.common.entity.AuditEntity;
import com.contract.hopdong.hopdongdamphan_file.model.HopDongDamPhanFileModel;
import com.contract.hopdong.hopdongdamphan_tientrinh.entity.HopDongDamPhanTienTrinhEntity;
import com.contract.hopdong.hopdongdamphan_tientrinh.enums.TrangThaiDamPhanEnum;
import com.contract.hopdong.hopdongdamphan_tientrinh_change.model.HopDongDamPhanTienTrinhChangeModel;
import com.contract.hopdong.hopdongdamphan_tientrinh_xetduyet.model.HopDongDamPhanTienTrinhXetDuyetModel;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongDamPhanTienTrinhModel extends AuditEntity<String> {

  public static HopDongDamPhanTienTrinhModel fromEntity(HopDongDamPhanTienTrinhEntity entity, boolean containChild) {
    HopDongDamPhanTienTrinhModel model = new HopDongDamPhanTienTrinhModel();
    model.setCreatedAt(entity.getCreatedAt());
    model.setUpdatedAt(entity.getUpdatedAt());
    model.setId(entity.getId());

    model.setHopDongDamPhanId(entity.getHopDongDamPhanId());
    model.setGhiChu(entity.getGhiChu());
    model.setTrangThai(entity.getTrangThai());
    model.setNguoiDungId(entity.getNguoiDungId());

    if (containChild) {
      if (entity.getNguoiDungEntity() != null) {
        model.setNguoiDung(NguoiDungModel.fromEntity(entity.getNguoiDungEntity(), false));
      }
      try {
        if (entity.getHopDongDamPhanTienTrinhChangeEntities() != null) {
          model.setHopDongDamPhanTienTrinhChanges(entity.getHopDongDamPhanTienTrinhChangeEntities().stream()
              .map(_entity -> HopDongDamPhanTienTrinhChangeModel.fromEntity(_entity, true))
              .collect(Collectors.toList()));
        }
      } catch (Exception e) {
        // Avoid lazy loading error
        model.setHopDongDamPhanTienTrinhChanges(null);
      }
      try {
        if (entity.getHopDongDamPhanFileEntities() != null) {
          model.setHopDongDamPhanFiles(entity.getHopDongDamPhanFileEntities().stream()
              .map(_entity -> HopDongDamPhanFileModel.fromEntity(_entity, true))
              .collect(Collectors.toList()));
        }
      } catch (Exception e) {
        // Avoid lazy loading error
        model.setHopDongDamPhanFiles(null);
      }
      try {
        if (entity.getHopDongDamPhanTienTrinhXetDuyetEntities() != null) {
          model.setHopDongDamPhanTienTrinhXetDuyets(entity.getHopDongDamPhanTienTrinhXetDuyetEntities().stream()
              .map(_entity -> HopDongDamPhanTienTrinhXetDuyetModel.fromEntity(_entity, true))
              .collect(Collectors.toList()));
        }
      } catch (Exception e) {
        // Avoid lazy loading error
        model.setHopDongDamPhanFiles(null);
      }
    }
    return model;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id = 0L;

  @Column(name = "hop_dong_dam_phan_id")
  protected Long hopDongDamPhanId;

  @Column(name = "ghi_chu")
  protected String ghiChu;

  @Column(name = "trang_thai")
  @Comment("0: GUI_NOI_DUNG_DAM_PHAN, 1: TU_CHOI, 2: PHE_DUYET")
  @Enumerated(EnumType.ORDINAL)
  protected TrangThaiDamPhanEnum trangThai;

  @Column(name = "nguoi_dung_id")
  protected Long nguoiDungId;

  @Transient
  protected NguoiDungModel nguoiDung;

  @Transient
  protected List<HopDongDamPhanTienTrinhChangeModel> hopDongDamPhanTienTrinhChanges;

  @Transient
  protected List<HopDongDamPhanFileModel> hopDongDamPhanFiles;

  @Transient
  protected List<HopDongDamPhanTienTrinhXetDuyetModel> hopDongDamPhanTienTrinhXetDuyets;
}
