package com.contract.danhmuc.thue.model;

import com.contract.common.entity.AuditEntity;
import com.contract.danhmuc.thue.entity.DmThueEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class DmThueModel extends AuditEntity<String> {
    public static DmThueModel fromEntity(DmThueEntity entity) {
        DmThueModel model = new DmThueModel();
        model.setId(entity.getId());
        model.setCreatedAt(entity.getCreatedAt());
        model.setCreatedBy(entity.getCreatedBy());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setUpdatedBy(entity.getUpdatedBy());

        model.setSoThue(entity.getSoThue());
        model.setGhiChu(entity.getGhiChu());
        return model;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id = 0;

    @Column(name = "so_thue", nullable = false)
    @NotBlank(message = "Số thuế không được để trống")
    protected float soThue;

    @Column(name = "ghi_chu", nullable = true)
    protected String ghiChu;
}