package com.contract.hopdong.hopdongtram.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.hopdong.hopdongfile.entity.HopDongFileEntity;
import com.contract.hopdong.hopdongtram.model.HopDongTramModel;
import com.contract.hopdong.hopdongtram_dungchung.entity.HopDongTramDungChungEntity;
import com.contract.hopdong.hopdongtram_kythanhtoan.entity.HopDongTramKyThanhToanEntity;
import com.contract.hopdong.hopdongtram_phutro.entity.HopDongTramPhuTroEntity;
import com.contract.tram.tram.entity.TramEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_tram")
@Where(clause = "deleted_at IS NULL")
public class HopDongTramEntity extends HopDongTramModel {

    public static HopDongTramEntity fromModel(HopDongTramEntity entity, HopDongTramModel model) {
        if (entity == null) {
            entity = new HopDongTramEntity();
        }

        entity.setHopDongId(model.getHopDongId());

        entity.setTramId(model.getTramId());
        entity.setGiaThue(model.getGiaThue());
        entity.setGiaDienKhoan(model.getGiaDienKhoan());
        entity.setTrangThaiHoatDong(model.getTrangThaiHoatDong());
        entity.setNgayBatDauYeuCauThanhToan(model.getNgayBatDauYeuCauThanhToan());
        return entity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tram_id", insertable = false, updatable = false)
    private TramEntity tramEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hop_dong_id", insertable = false, updatable = false)
    private HopDongEntity hopDongEntity;

    @OneToOne(mappedBy = "hopDongTramEntity", fetch = FetchType.LAZY)
    private HopDongTramDungChungEntity hopDongTramDungChungEntity;

    @OrderBy("tuNgay asc")
    @OneToMany(mappedBy = "hopDongTramEntity", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<HopDongTramKyThanhToanEntity> hopDongTramKyThanhToanEntities = new HashSet<>();

    @OneToMany(mappedBy = "hopDongTramEntity", fetch = FetchType.LAZY)
    @OrderBy("createdAt asc")
    @JsonIgnore
    private Set<HopDongTramPhuTroEntity> hopDongTramPhuTroEntities = new HashSet<>();

    @OneToMany(mappedBy = "hopDongTramEntity", fetch = FetchType.LAZY)
    @OrderBy("createdAt asc")
    @JsonIgnore
    private Set<HopDongFileEntity> hopDongFileEntities = new HashSet<>();
}
