package com.contract.danhmuc.to.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.proxy.HibernateProxy;

import com.contract.common.entity.AuditEntity;
import com.contract.danhmuc.phongdai.entity.DmPhongDaiEntity;
import com.contract.danhmuc.to.entity.DmToEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
// @ToString
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class DmToModel extends AuditEntity<String> {
    public static DmToModel fromEntity(DmToEntity entity) {
        DmToModel model = new DmToModel();
        model.setId(entity.getId());
        model.setCreatedAt(entity.getCreatedAt());
        model.setCreatedBy(entity.getCreatedBy());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setUpdatedBy(entity.getUpdatedBy());
        // model.setDeletedAt(entity.getDeletedAt());

        if (entity.getPhongDai() != null && !(entity.getPhongDai() instanceof HibernateProxy)) {
            entity.getPhongDai().setTramEntitySet(null);
            entity.getPhongDai().setChucVuEntitySet(null);
            entity.getPhongDai().setNguoiDungKhuVucEntitySet(null);
            entity.getPhongDai().setListDmToEntity(null);
        }

        model.setTen(entity.getTen());
        model.setTenVietTat(entity.getTenVietTat());
        model.setGhiChu(entity.getGhiChu());
        if (!(entity.getPhongDai() instanceof HibernateProxy)) {
            model.setPhongDai(entity.getPhongDai());
        }
        model.setMaDataSite(entity.getMaDataSite());
        return model;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id = 0;

    @Column(name = "ma_datasite", nullable = true)
    protected String maDataSite;

    @Column(name = "ten_viet_tat")
    protected String tenVietTat;

    @Column(name = "ten", nullable = false)
    @NotBlank
    @NotEmpty
    @NotNull(message = "Tên không được trống")
    protected String ten;

    @Column(name = "ghi_chu")
    protected String ghiChu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phong_dai_id")
    protected DmPhongDaiEntity phongDai;
}
