package com.contract.danhmuc.huyen.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.contract.common.entity.AuditEntity;
import com.contract.danhmuc.huyen.entity.DmHuyenEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
@ToString
public class DmHuyenModel extends AuditEntity<String> {
    public static DmHuyenModel fromEntity(DmHuyenEntity entity) {
        DmHuyenModel model = new DmHuyenModel();
        model.setId(entity.getId());
        model.setCreatedAt(entity.getCreatedAt());
        model.setCreatedBy(entity.getCreatedBy());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setUpdatedBy(entity.getUpdatedBy());
        // model.setDeletedAt(entity.getDeletedAt());

        model.setTen(entity.getTen());
        model.setMa(entity.getMa());
        model.setGhiChu(entity.getGhiChu());
        model.setTinhId(entity.getTinhId());
        model.setMaDataSite(entity.getMaDataSite());
        return model;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id = 0;

    @Column(name = "ma_datasite", nullable = true)
    protected String maDataSite;

    @Column(name = "tinh_id")
    protected Integer tinhId;

    @Column(name = "ten", nullable = false)
    @NotBlank
    @NotEmpty
    @NotNull(message = "Tên không được trống")
    protected String ten;

    @Column(name = "ma", nullable = true)
    protected String ma;

    @Column(name = "ghi_chu")
    protected String ghiChu;
}
