package com.contract.hopdong.hopdongdamphan_tientrinh_change.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.contract.common.entity.AuditEntity;
import com.contract.hopdong.hopdongdamphan_tientrinh_change.entity.HopDongDamPhanTienTrinhChangeEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongDamPhanTienTrinhChangeModel extends AuditEntity<String> {

    public static HopDongDamPhanTienTrinhChangeModel fromEntity(HopDongDamPhanTienTrinhChangeEntity entity,
                                                                boolean containChild) {
        HopDongDamPhanTienTrinhChangeModel model = new HopDongDamPhanTienTrinhChangeModel();
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setId(entity.getId());

        model.setHopDongDamPhanTienTrinhId(entity.getHopDongDamPhanTienTrinhId());
        model.setKey(entity.getKey());
        model.setValue(entity.getValue());
        model.setTramId(entity.getTramId());
        model.setGhiChu(entity.getGhiChu());

        if (containChild) {
        }

        return model;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = 0L;

    @Column(name = "hop_dong_dam_phan_tien_trinh_id")
    protected Long hopDongDamPhanTienTrinhId;

    @Column(name = "key")
    protected String key;

    @Column(name = "value")
    protected String value;

    @Column(name = "tram_id", nullable = true)
    protected Long tramId;

    @Column(name = "ghi_chu")
    protected String ghiChu;
}
