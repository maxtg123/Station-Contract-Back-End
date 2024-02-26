package com.contract.hopdong.hopdongtram_dungchung.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.donvidungchung.model.DmDonViDungChungModel;
import com.contract.danhmuc.loaihangmuccsht.entity.DmLoaiHangMucCSHTEntity;
import com.contract.hopdong.hopdongtram.entity.HopDongTramEntity;
import com.contract.hopdong.hopdongtram_dungchung.model.HopDongTramDungChungModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_tram_dung_chung")
@Where(clause = "deleted_at IS NULL")

public class HopDongTramDungChungEntity extends HopDongTramDungChungModel {
    public static HopDongTramDungChungEntity fromModel(HopDongTramDungChungEntity entity,
            HopDongTramDungChungModel model) {
        if (entity == null) {
            entity = new HopDongTramDungChungEntity();
        }

        entity.setHopDongTramId(model.getHopDongTramId());
        entity.setLoaiHangMucCSHTId(model.getLoaiHangMucCSHTId());
        entity.setMaTramDonViDungChung(model.getMaTramDonViDungChung());
        entity.setThoiDiemPhatSinh(model.getThoiDiemPhatSinh());
        entity.setNgayLapDatThietBi(model.getNgayLapDatThietBi());
        entity.setNgayBatDauDungChung(model.getNgayBatDauDungChung());
        entity.setNgayKetThucDungChung(model.getNgayKetThucDungChung());
        entity.setDonViDungChungId(model.getDonViDungChungId());
        return entity;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hop_dong_tram_id", insertable = false, updatable = false)
    private HopDongTramEntity hopDongTramEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loai_hang_muc_csht_id", insertable = false, updatable = false)
    private DmLoaiHangMucCSHTEntity dmLoaiHangMucCSHTEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "don_vi_dung_chung_id", insertable = false, updatable = false)
    private DmDonViDungChungModel dmDonViDungChungEntity;

}
