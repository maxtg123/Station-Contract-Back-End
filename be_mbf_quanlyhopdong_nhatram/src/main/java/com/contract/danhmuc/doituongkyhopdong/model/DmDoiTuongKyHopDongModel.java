package com.contract.danhmuc.doituongkyhopdong.model;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Comment;

import com.contract.common.entity.AuditEntity;
import com.contract.danhmuc.doituongkyhopdong.entity.DmDoiTuongKyHopDongEntity;
import com.contract.danhmuc.doituongkyhopdong.enums.LoaiDoiTuongEnum;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class DmDoiTuongKyHopDongModel extends AuditEntity<String> {
    public static DmDoiTuongKyHopDongModel fromEntity(DmDoiTuongKyHopDongEntity entity) {
        DmDoiTuongKyHopDongModel model = new DmDoiTuongKyHopDongModel();
        model.setId(entity.getId());
        model.setCreatedAt(entity.getCreatedAt());
        model.setCreatedBy(entity.getCreatedBy());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setUpdatedBy(entity.getUpdatedBy());

        model.setTen(entity.getTen());
        model.setMa(entity.getMa());
        model.setGhiChu(entity.getGhiChu());
        model.setMaDataSite(entity.getMaDataSite());
        model.setLoaiDoiTuong(entity.getLoaiDoiTuong());
        return model;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id = 0;

    @Column(name = "ma_datasite", nullable = true)
    protected String maDataSite;

    @Column(name = "ten", nullable = false)
    @NotBlank
    @NotEmpty
    @NotNull(message = "Tên không được trống")
    protected String ten;

    @Column(name = "ma", nullable = true)
    protected String ma;

    @Column(name = "ghi_chu")
    protected String ghiChu;

    @Column(name = "loai_doi_tuong")
    @Comment("0: PHAP_NHAN, 1: CA_NHAN: 2: KHAC")
    @Enumerated(EnumType.ORDINAL)
    protected LoaiDoiTuongEnum loaiDoiTuong;
}
