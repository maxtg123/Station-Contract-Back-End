package com.contract.chucvu.phanquyen.model;

import com.contract.chucvu.chucvu.model.ChucVuModel;
import com.contract.common.entity.AuditEntity;
import com.contract.chucvu.phanquyen.entity.ChucVuPhanQuyenEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class ChucVuPhanQuyenModel extends AuditEntity<String> {
    public static ChucVuPhanQuyenModel fromEntity(ChucVuPhanQuyenEntity entity, boolean containChild) {
        ChucVuPhanQuyenModel model = new ChucVuPhanQuyenModel();

        try {
            model.setId(entity.getId());
            model.setCreatedAt(entity.getCreatedAt());
            model.setCreatedBy(entity.getCreatedBy());
            model.setUpdatedAt(entity.getUpdatedAt());
            model.setUpdatedBy(entity.getUpdatedBy());

            model.setModule(entity.getModule());
            model.setAction(entity.getAction());
            model.setPhongDaiChucVuId(entity.getPhongDaiChucVuId());

            if (containChild) {
                model.setChucVu(ChucVuModel.fromEntity(entity.getChucVuEntity(), false));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return model;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = 0L;

    @Column(name = "module", nullable = false)
    @NotNull
    protected String module;

    @Column(name = "action", nullable = false)
    @NotNull
    protected String action;

    @Column(name = "pd_chucvu_id", nullable = false)
    @NotNull
    protected Long phongDaiChucVuId;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected ChucVuModel chucVu;
}
