package com.contract.thongbao.model;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.contract.common.entity.AuditEntity;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;
import com.contract.thongbao.entity.ThongBaoEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class ThongBaoModel extends AuditEntity<String> {
    public static ThongBaoModel fromEntity(ThongBaoEntity entity, boolean containChild) {
        ThongBaoModel model = new ThongBaoModel();

        try {
            model.setCreatedAt(entity.getCreatedAt());
            model.setCreatedBy(entity.getCreatedBy());
            model.setUpdatedAt(entity.getUpdatedAt());
            model.setUpdatedBy(entity.getUpdatedBy());

            model.setId(entity.getId());
            model.setModule(entity.getModule());
            model.setAction(entity.getAction());
            model.setNguoiNhanId(entity.getNguoiNhanId());
            model.setContent(entity.getContent());
            model.setTrangThai(entity.getTrangThai());
            model.setNguoiGuiId(entity.getNguoiGuiId());

            if (containChild) {
                if (entity.getNguoiGuiEntity() != null) {
                    model.setNguoiGui(NguoiDungModel.fromEntity(entity.getNguoiGuiEntity(),
                            false));
                }
                if (entity.getNguoiNhanEntity() != null) {
                    model.setNguoiNhan(NguoiDungModel.fromEntity(entity.getNguoiNhanEntity(),
                            false));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return model;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = 0L;

    @Column(name = "module")
    protected String module;

    @Column(name = "action")
    protected String action;

    @Column(name = "nguoi_gui_id")
    protected Long nguoiGuiId;

    @Column(name = "nguoi_nhan_id")
    protected Long nguoiNhanId;

    @Column(name = "content")
    protected String content;

    @Column(name = "trang_thai")
    @Enumerated(EnumType.ORDINAL)
    protected TrangThaiThongBao trangThai;

    @Transient
    protected NguoiDungModel nguoiGui;

    @Transient
    protected NguoiDungModel nguoiNhan;
}
