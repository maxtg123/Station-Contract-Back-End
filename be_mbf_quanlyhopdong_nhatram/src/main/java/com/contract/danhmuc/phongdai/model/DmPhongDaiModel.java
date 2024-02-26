package com.contract.danhmuc.phongdai.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.contract.common.entity.AuditEntity;
import com.contract.danhmuc.phongdai.entity.DmPhongDaiEntity;
import com.contract.danhmuc.to.entity.DmToEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
@ToString
public class DmPhongDaiModel extends AuditEntity<String> {
    public static DmPhongDaiModel fromEntity(DmPhongDaiEntity entity) {
        DmPhongDaiModel model = new DmPhongDaiModel();
        model.setId(entity.getId());
        model.setCreatedAt(entity.getCreatedAt());
        model.setCreatedBy(entity.getCreatedBy());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setUpdatedBy(entity.getUpdatedBy());
        // model.setDeletedAt(entity.getDeletedAt());

        model.setTen(entity.getTen());
        model.setTenVietTat(entity.getTenVietTat());
        model.setGhiChu(entity.getGhiChu());
        model.setLoai(entity.getLoai());
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

    @Column(name = "loai", nullable = false)
    @NotBlank
    @NotEmpty
    @NotNull
    protected String loai;

    @Column(name = "ghi_chu")
    protected String ghiChu;

    @JsonIgnore
    @OneToMany(mappedBy = "phongDai")
    private List<DmToEntity> listDmToEntity;
}
