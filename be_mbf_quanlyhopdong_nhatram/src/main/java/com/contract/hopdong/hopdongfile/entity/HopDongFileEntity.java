package com.contract.hopdong.hopdongfile.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.hopdong.hopdongfile.model.HopDongFileModel;
import com.contract.hopdong.hopdongphuluc.entity.HopDongPhuLucEntity;
import com.contract.hopdong.hopdongtram.entity.HopDongTramEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_files")
@Where(clause = "deleted_at IS NULL")
public class HopDongFileEntity extends HopDongFileModel {

    public static HopDongFileEntity fromModel(HopDongFileModel model) {
        HopDongFileEntity entity = new HopDongFileEntity();
        entity.setTen(model.getTen());
        entity.setLoai(model.getLoai());
        entity.setHopDongId(model.getHopDongId());
        entity.setHopDongPhuLucId(model.getHopDongPhuLucId());
        entity.setHopDongTramId(model.getHopDongTramId());
        return entity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hop_dong_id", insertable = false, updatable = false)
    private HopDongEntity hopDongEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hop_dong_phu_luc_id", insertable = false, updatable = false)
    private HopDongPhuLucEntity hopDongPhuLucEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hop_dong_tram_id", insertable = false, updatable = false)
    private HopDongTramEntity hopDongTramEntity;
}
