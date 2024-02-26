package com.contract.hopdong.hopdongpheduyet.model;

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
import org.hibernate.proxy.HibernateProxy;

import com.contract.common.entity.AuditEntity;
import com.contract.hopdong.hopdongpheduyet.entity.HopDongPheDuyetEntity;
import com.contract.hopdong.hopdongpheduyet.enums.LoaiXetDuyetEnum;
import com.contract.hopdong.hopdongpheduyet.enums.TrangThaiPheDuyetEnum;
import com.contract.hopdong.hopdongpheduyet_nguoinhan.model.HopDongPheDuyetNguoiNhanModel;
import com.contract.hopdong.hopdongpheduyet_tientrinh.model.HopDongPheDuyetTienTrinhModel;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongPheDuyetModel extends AuditEntity<String> {
  public static HopDongPheDuyetModel fromEntity(HopDongPheDuyetEntity entity, boolean containChild) {
    HopDongPheDuyetModel model = new HopDongPheDuyetModel();

    model.setCreatedAt(entity.getCreatedAt());
    model.setUpdatedAt(entity.getUpdatedAt());
    model.setId(entity.getId());
    model.setHopDongId(entity.getHopDongId());
    model.setTrangThaiPheDuyetMoiNhat(entity.getTrangThaiPheDuyetMoiNhat());
    model.setGhiChu(entity.getGhiChu());
    model.setNguoiGuiId(entity.getNguoiGuiId());
    model.setLoaiXetDuyet(entity.getLoaiXetDuyet());

    if (containChild) {
      if (entity.getNguoiGuiEntity() != null && !(entity.getNguoiGuiEntity() instanceof HibernateProxy)) {
        model.setNguoiGui(NguoiDungModel.fromEntity(entity.getNguoiGuiEntity(), false));
      }

      try {
        if (entity.getHopDongPheDuyetNguoiNhanEntityList() != null) {
          model.setHopDongPheDuyetNguoiNhanModelList(entity.getHopDongPheDuyetNguoiNhanEntityList().stream()
              .map(_entity -> HopDongPheDuyetNguoiNhanModel.fromEntity(_entity, true)).collect(Collectors.toList()));
        }
      } catch (Exception e) {
      }

      try {
        if (entity.getHopDongPheDuyetTienTrinhEnities() != null) {
          model.setHopDongPheDuyetTienTrinhList(entity.getHopDongPheDuyetTienTrinhEnities().stream()
              .map(_entity -> HopDongPheDuyetTienTrinhModel.fromEntity(_entity, true)).collect(Collectors.toList()));
        }
      } catch (Exception e) {
        // Avoid lazy loading error
        model.setHopDongPheDuyetTienTrinhList(null);
      }
    }

    return model;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id = 0L;

  @Column(name = "hop_dong_id")
  protected Long hopDongId;

  @Column(name = "trang_thai_phe_duyet")
  @Comment("0: CHO_PHE_DUYET, 1: PHE_DUYET, 2: TU_CHOI, 3: GUI_LAI, 4: UPDATE_HOP_DONG")
  @Enumerated(EnumType.ORDINAL)
  protected TrangThaiPheDuyetEnum trangThaiPheDuyetMoiNhat;

  @Column(name = "ghi_chu")
  protected String ghiChu;

  @Column(name = "nguoi_gui_id")
  protected Long nguoiGuiId;

  @Column(name = "loai_xet_duyet")
  @Comment("0: XET_DUYET_HOP_DONG; 1: XET_DUYET_PHU_LUC")
  @Enumerated(EnumType.ORDINAL)
  protected LoaiXetDuyetEnum loaiXetDuyet;

  @Transient
  protected NguoiDungModel nguoiGui;

  @Transient
  protected List<HopDongPheDuyetNguoiNhanModel> hopDongPheDuyetNguoiNhanModelList;

  @Transient
  protected List<HopDongPheDuyetTienTrinhModel> hopDongPheDuyetTienTrinhList;
}
