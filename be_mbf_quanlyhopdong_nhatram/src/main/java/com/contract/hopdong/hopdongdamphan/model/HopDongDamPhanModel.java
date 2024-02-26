package com.contract.hopdong.hopdongdamphan.model;

import java.util.Date;
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
import com.contract.hopdong.hopdongdamphan.entity.HopDongDamPhanEntity;
import com.contract.hopdong.hopdongdamphan.enums.MucDoUuTienDamPhanEnum;
import com.contract.hopdong.hopdongdamphan_nguoinhan.model.HopDongDamPhanNguoiNhanModel;
import com.contract.hopdong.hopdongdamphan_tientrinh.enums.TrangThaiDamPhanEnum;
import com.contract.hopdong.hopdongdamphan_tientrinh.model.HopDongDamPhanTienTrinhModel;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongDamPhanModel extends AuditEntity<String> {
    public static HopDongDamPhanModel fromEntity(HopDongDamPhanEntity entity, boolean containChild) {
        HopDongDamPhanModel model = new HopDongDamPhanModel();

        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setId(entity.getId());

        model.setHopDongId(entity.getHopDongId());
        model.setGhiChu(entity.getGhiChu());
        model.setNguoiGuiId(entity.getNguoiGuiId());
        model.setTrangThaiDamPhanMoiNhat(entity.getTrangThaiDamPhanMoiNhat());
        model.setMucDoUuTien(entity.getMucDoUuTien());
        model.setFromDate(entity.getFromDate());
        model.setToDate(entity.getToDate());

        if (containChild) {
            if (entity.getNguoiGuiEntity() != null && !(entity.getNguoiGuiEntity() instanceof HibernateProxy)) {
                model.setNguoiGui(NguoiDungModel.fromEntity(entity.getNguoiGuiEntity(), false));
            }

            try {
                if (entity.getHopDongDamPhanNguoiNhanEntities() != null) {
                    model.setHopDongDamPhanNguoiNhanList(entity.getHopDongDamPhanNguoiNhanEntities().stream()
                            .map(_entity -> HopDongDamPhanNguoiNhanModel.fromEntity(_entity,
                                    true))
                            .collect(Collectors.toList()));
                }
            } catch (Exception e) {
                model.setHopDongDamPhanNguoiNhanList(null);
            }

            try {
                if (entity.getHopDongDamPhanTienTrinhEntities() != null) {
                    model.setHopDongDamPhanTienTrinhList(entity.getHopDongDamPhanTienTrinhEntities().stream()
                            .map(_entity -> HopDongDamPhanTienTrinhModel.fromEntity(_entity,
                                    true))
                            .collect(Collectors.toList()));
                }
            } catch (Exception e) {
                // Avoid lazy loading error
                model.setHopDongDamPhanTienTrinhList(null);
            }
        }

        return model;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = 0L;

    @Column(name = "hop_dong_id")
    protected Long hopDongId;

    @Column(name = "ghi_chu")
    protected String ghiChu;

    @Column(name = "nguoi_gui_id")
    protected Long nguoiGuiId;

    @Column(name = "trang_thai_dam_pham_moi_nhat")
    @Comment("0: GUI_NOI_DUNG_DAM_PHAN, 1: TU_CHOI, 2: PHE_DUYET")
    @Enumerated(EnumType.ORDINAL)
    protected TrangThaiDamPhanEnum trangThaiDamPhanMoiNhat;

    @Column(name = "muc_do_uu_tien")
    @Comment("0: low, 1: medium, 2: height")
    @Enumerated(EnumType.ORDINAL)
    protected MucDoUuTienDamPhanEnum mucDoUuTien;

    @Column(name = "from_date")
    protected Date fromDate;

    @Column(name = "to_date")
    protected Date toDate;

    @Transient
    protected NguoiDungModel nguoiGui;

    @Transient
    protected List<HopDongDamPhanNguoiNhanModel> hopDongDamPhanNguoiNhanList;

    @Transient
    protected List<HopDongDamPhanTienTrinhModel> hopDongDamPhanTienTrinhList;

}
