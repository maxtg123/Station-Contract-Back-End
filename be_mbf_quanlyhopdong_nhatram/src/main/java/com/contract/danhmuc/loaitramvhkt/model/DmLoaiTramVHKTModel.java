package com.contract.danhmuc.loaitramvhkt.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import com.contract.common.entity.AuditEntity;
import com.contract.danhmuc.loaitramvhkt.entity.DmLoaiTramVHKTEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class DmLoaiTramVHKTModel extends AuditEntity<String> {
    public static DmLoaiTramVHKTModel fromEntity(DmLoaiTramVHKTEntity entity) {
        DmLoaiTramVHKTModel model = new DmLoaiTramVHKTModel();
        model.setId(entity.getId());
        model.setCreatedAt(entity.getCreatedAt());
        model.setCreatedBy(entity.getCreatedBy());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setUpdatedBy(entity.getUpdatedBy());

        model.setMa(entity.getMa());
        model.setTen(entity.getTen());
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
    protected String ten;

    @Column(name = "ghi_chu")
    protected String ghiChu;

    @Column(name = "ma_datasite")
    protected String maDataSite;
}
