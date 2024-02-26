package com.contract.hopdong.hopdongtram_dungchung.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.proxy.HibernateProxy;

import com.contract.common.entity.AuditEntity;
import com.contract.danhmuc.donvidungchung.model.DmDonViDungChungModel;
import com.contract.danhmuc.loaihangmuccsht.model.DmLoaiHangMucCSHTModel;
import com.contract.hopdong.hopdongtram.model.HopDongTramModel;
import com.contract.hopdong.hopdongtram_dungchung.entity.HopDongTramDungChungEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongTramDungChungModel extends AuditEntity<String> {
    public static HopDongTramDungChungModel fromEntity(HopDongTramDungChungEntity entity,
            boolean containChild) {
        HopDongTramDungChungModel model = new HopDongTramDungChungModel();
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setId(entity.getId());

        model.setHopDongTramId(entity.getHopDongTramId());
        model.setLoaiHangMucCSHTId(entity.getLoaiHangMucCSHTId());
        model.setDonViDungChungId(entity.getDonViDungChungId());
        model.setMaTramDonViDungChung(entity.getMaTramDonViDungChung());
        model.setThoiDiemPhatSinh(entity.getThoiDiemPhatSinh());
        model.setNgayLapDatThietBi(entity.getNgayLapDatThietBi());
        model.setNgayBatDauDungChung(entity.getNgayBatDauDungChung());
        model.setNgayKetThucDungChung(entity.getNgayKetThucDungChung());

        if (containChild) {
            if (entity.getDmLoaiHangMucCSHTEntity() != null
                    && !(entity.getDmLoaiHangMucCSHTEntity() instanceof HibernateProxy)) {
                model.setDmLoaiHangMucCSHT(
                        DmLoaiHangMucCSHTModel.fromEntity(entity.getDmLoaiHangMucCSHTEntity()));
            }
            if (entity.getDmDonViDungChungEntity() != null
                    && !(entity.getDmDonViDungChungEntity() instanceof HibernateProxy)) {
                model.setDmDonViDungChung(entity.getDmDonViDungChungEntity());
            }
        }
        return model;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = 0L;

    @Column(name = "hop_dong_tram_id")
    protected Long hopDongTramId;

    @Column(name = "loai_hang_muc_csht_id")
    protected Integer loaiHangMucCSHTId;

    @Column(name = "ma_tram_donvi_dungchung")
    protected String maTramDonViDungChung;

    @Column(name = "thoi_diem_phat_sinh")
    protected Date thoiDiemPhatSinh;

    @Column(name = "ngay_lap_dat_thiet_bi")
    protected Date ngayLapDatThietBi;

    @Column(name = "ngay_bat_dau_dung_chung")
    protected Date ngayBatDauDungChung;

    @Column(name = "ngay_ket_thuc_dung_chung")
    protected Date ngayKetThucDungChung;

    @Column(name = "don_vi_dung_chung_id")
    protected Integer donViDungChungId;

    @Transient
    protected HopDongTramModel hopDongTram;

    @Transient
    protected DmLoaiHangMucCSHTModel dmLoaiHangMucCSHT;

    @Transient
    protected DmDonViDungChungModel dmDonViDungChung;

}
