package com.contract.danhmuc.hinhthucthanhtoan.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.contract.common.entity.AuditEntity;
import com.contract.danhmuc.hinhthucthanhtoan.entity.DmHinhThucThanhToanEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class DmHinhThucThanhToanModel extends AuditEntity<String> {
    public static DmHinhThucThanhToanModel fromEntity(DmHinhThucThanhToanEntity entity) {
        DmHinhThucThanhToanModel model = new DmHinhThucThanhToanModel();
        model.setId(entity.getId());
        model.setCreatedAt(entity.getCreatedAt());
        model.setCreatedBy(entity.getCreatedBy());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setUpdatedBy(entity.getUpdatedBy());

        model.setTen(entity.getTen());
        model.setMa(entity.getMa());
        model.setGhiChu(entity.getGhiChu());
        model.setMaDataSite(entity.getMaDataSite());
        return model;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id = 0;

    @Column(name = "ma_datasite")
    protected String maDataSite;

    @Column(name = "ten")
    protected String ten;

    @Column(name = "ma")
    protected String ma;

    @Column(name = "ghi_chu")
    protected String ghiChu;
}
