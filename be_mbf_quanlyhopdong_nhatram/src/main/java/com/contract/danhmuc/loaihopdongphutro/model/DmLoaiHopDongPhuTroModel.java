package com.contract.danhmuc.loaihopdongphutro.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.contract.common.entity.AuditEntity;
import com.contract.danhmuc.loaihopdongphutro.entity.DmLoaiHopDongPhuTroEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class DmLoaiHopDongPhuTroModel extends AuditEntity<String> {
    public static DmLoaiHopDongPhuTroModel fromEntity(DmLoaiHopDongPhuTroEntity entity) {
        DmLoaiHopDongPhuTroModel model = new DmLoaiHopDongPhuTroModel();
        model.setId(entity.getId());
        model.setCreatedAt(entity.getCreatedAt());
        model.setCreatedBy(entity.getCreatedBy());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setUpdatedBy(entity.getUpdatedBy());

        model.setMa(entity.getMa());
        model.setTen(entity.getTen());
        model.setGia(entity.getGia());
        model.setGhiChu(entity.getGhiChu());
        model.setMaDataSite(entity.getMaDataSite());
        return model;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id = 0;

    @Column(name = "ma", nullable = true)
    protected String ma;

    @Column(name = "ten", nullable = false)
    @NotBlank(message = "Tên không được trống")
    @NotEmpty
    @NotNull
    protected String ten;

    @Column(name = "gia", nullable = false)
    @NotBlank(message = "Giá không được trống")
    @NotEmpty
    @NotNull
    protected Double gia;

    @Column(name = "ghi_chu")
    protected String ghiChu;

    @Column(name = "ma_datasite", nullable = true)
    protected String maDataSite;
}
