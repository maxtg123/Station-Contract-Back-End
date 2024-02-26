package com.contract.hopdong.hopdongphuluc.model;

import java.util.Date;
import java.util.List;

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
import com.contract.hopdong.hopdongfile.model.HopDongFileModel;
import com.contract.hopdong.hopdongphuluc.entity.HopDongPhuLucEntity;
import com.contract.hopdong.hopdongphuluc.enums.TinhTrangPhuLucEnum;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongPhuLucModel extends AuditEntity<String> {
    public static HopDongPhuLucModel fromEntity(HopDongPhuLucEntity entity, boolean containChild) {
        HopDongPhuLucModel model = new HopDongPhuLucModel();
        model.setId(entity.getId());
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());

        model.setId(entity.getId());
        model.setHopDongId(entity.getHopDongId());
        model.setSoPhuLuc(entity.getSoPhuLuc());
        model.setNgayKy(entity.getNgayKy());
        model.setNgayHieuLuc(entity.getNgayHieuLuc());
        model.setNgayKetThuc(entity.getNgayKetThuc());
        model.setGhiChu(entity.getGhiChu());
        model.setNguoiTaoId(entity.getNguoiTaoId());
        model.setTinhTrangPhuLuc(entity.getTinhTrangPhuLuc());

        if (containChild) {

        }
        return model;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = 0L;

    @Column(name = "hop_dong_id")
    protected Long hopDongId;

    @Column(name = "ngay_ky")
    protected Date ngayKy;

    @Column(name = "ngay_hieu_luc")
    protected Date ngayHieuLuc;

    @Column(name = "ngay_ket_thuc")
    protected Date ngayKetThuc;

    @Column(name = "ghi_chu")
    protected String ghiChu;

    @Column(name = "nguoi_tao_id")
    protected Long nguoiTaoId;

    @Column(name = "tinh_trang")
    @Comment("0: ngừng hoạt động; 1: hoạt động")
    @Enumerated(EnumType.ORDINAL)
    protected TinhTrangPhuLucEnum tinhTrangPhuLuc;

    @Column(name = "so_phu_luc", nullable = false)
    protected String soPhuLuc;

    @Transient
    protected NguoiDungModel nguoiTao;

    @Transient
    protected List<HopDongFileModel> hopDongFileModels;
}
