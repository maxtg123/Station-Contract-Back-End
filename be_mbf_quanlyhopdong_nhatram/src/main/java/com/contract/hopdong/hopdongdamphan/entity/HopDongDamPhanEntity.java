package com.contract.hopdong.hopdongdamphan.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.hopdong.hopdongdamphan.model.HopDongDamPhanModel;
import com.contract.hopdong.hopdongdamphan_nguoinhan.entity.HopDongDamPhanNguoiNhanEntity;
import com.contract.hopdong.hopdongdamphan_tientrinh.entity.HopDongDamPhanTienTrinhEntity;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_dam_phan")
@Where(clause = "deleted_at IS NULL")
public class HopDongDamPhanEntity extends HopDongDamPhanModel {
    public static HopDongDamPhanEntity fromModel(HopDongDamPhanEntity entity, HopDongDamPhanModel model) {
        if (entity == null) {
            entity = new HopDongDamPhanEntity();
        }

        entity.setHopDongId(model.getHopDongId());
        entity.setGhiChu(model.getGhiChu());
        entity.setNguoiGuiId(model.getNguoiGuiId());
        entity.setMucDoUuTien(model.getMucDoUuTien());
        entity.setFromDate(model.getFromDate());
        entity.setToDate(model.getToDate());
        return entity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_gui_id", insertable = false, updatable = false)
    private NguoiDungEntity nguoiGuiEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hop_dong_id", insertable = false, updatable = false)
    private HopDongEntity hopDongEntity;

    @OneToMany(mappedBy = "hopDongDamPhanEntity", fetch = FetchType.LAZY)
    private Set<HopDongDamPhanNguoiNhanEntity> hopDongDamPhanNguoiNhanEntities;

    @OneToMany(mappedBy = "hopDongDamPhanEntity", fetch = FetchType.LAZY)
    @OrderBy("createdAt desc")
    @JsonIgnore
    private Set<HopDongDamPhanTienTrinhEntity> hopDongDamPhanTienTrinhEntities = new HashSet<>();
}
