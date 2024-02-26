package com.contract.hopdong.hopdongphuluc.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.hopdong.hopdongfile.entity.HopDongFileEntity;
import com.contract.hopdong.hopdongphuluc.model.HopDongPhuLucModel;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_phu_luc")
@Where(clause = "deleted_at IS NULL")
public class HopDongPhuLucEntity extends HopDongPhuLucModel {

    public static HopDongPhuLucEntity fromModel(HopDongPhuLucEntity entity, HopDongPhuLucModel model) {
        if (entity == null) {
            entity = new HopDongPhuLucEntity();
        }
        entity.setHopDongId(model.getHopDongId());
        entity.setSoPhuLuc(model.getSoPhuLuc());
        entity.setNgayKy(model.getNgayKy());
        entity.setNgayHieuLuc(model.getNgayHieuLuc());
        entity.setNgayKetThuc(model.getNgayKetThuc());
        entity.setGhiChu(model.getGhiChu());
        entity.setNguoiTaoId(model.getNguoiTaoId());
        entity.setTinhTrangPhuLuc(model.getTinhTrangPhuLuc());

        return entity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_tao_id", insertable = false, updatable = false)
    private NguoiDungEntity nguoiDungEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hop_dong_id", insertable = false, updatable = false)
    private HopDongEntity hopDongEntity;

    @OneToMany(mappedBy = "hopDongPhuLucEntity", fetch = FetchType.LAZY)
    private Set<HopDongFileEntity> hopDongFileEntities = new HashSet<>();
}
