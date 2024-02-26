package com.contract.hopdong.hopdongdamphan_tientrinh_change.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.hopdong.hopdongdamphan_tientrinh.entity.HopDongDamPhanTienTrinhEntity;
import com.contract.hopdong.hopdongdamphan_tientrinh_change.model.HopDongDamPhanTienTrinhChangeModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_dam_phan_tien_trinh_change")
@Where(clause = "deleted_at IS NULL")
public class HopDongDamPhanTienTrinhChangeEntity extends HopDongDamPhanTienTrinhChangeModel {

    public static HopDongDamPhanTienTrinhChangeEntity fromModel(HopDongDamPhanTienTrinhChangeEntity entity,
                                                                HopDongDamPhanTienTrinhChangeModel model) {
        if (entity == null) {
            entity = new HopDongDamPhanTienTrinhChangeEntity();
        }

        entity.setHopDongDamPhanTienTrinhId(model.getHopDongDamPhanTienTrinhId());
        entity.setKey(model.getKey());
        entity.setValue(model.getValue());
        entity.setTramId(model.getTramId());
        entity.setGhiChu(model.getGhiChu());
        return entity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hop_dong_dam_phan_tien_trinh_id", insertable = false, updatable = false)
    private HopDongDamPhanTienTrinhEntity hopDongDamPhanTienTrinhEntity;
}
