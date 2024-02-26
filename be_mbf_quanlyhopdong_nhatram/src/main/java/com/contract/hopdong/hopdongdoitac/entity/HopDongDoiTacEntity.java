package com.contract.hopdong.hopdongdoitac.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.hopdong.hopdongdoitac.model.HopDongDoiTacModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_doi_tac")
@Where(clause = "deleted_at IS NULL")
public class HopDongDoiTacEntity extends HopDongDoiTacModel {

    public static HopDongDoiTacEntity fromModel(HopDongDoiTacEntity entity, HopDongDoiTacModel model) {
        if (entity == null) {
            entity = new HopDongDoiTacEntity();
        }
        // HopDongDoiTacEntity entity = new HopDongDoiTacEntity();
        entity.setHopDongId(model.getHopDongId());
        entity.setTen(model.getTen());
        entity.setSoDienThoai(model.getSoDienThoai());
        entity.setCccd(model.getCccd());
        entity.setMaSoThue(model.getMaSoThue());
        entity.setDiaChi(model.getDiaChi());
        entity.setChuTaiKhoan(model.getChuTaiKhoan());
        entity.setSoTaiKhoan(model.getSoTaiKhoan());
        entity.setNganHangChiNhanh(model.getNganHangChiNhanh());
        return entity;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hop_dong_id", insertable = false, updatable = false)
    private HopDongEntity hopDongEntity;
}
