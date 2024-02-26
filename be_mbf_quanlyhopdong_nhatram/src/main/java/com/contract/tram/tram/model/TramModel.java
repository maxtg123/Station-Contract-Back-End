package com.contract.tram.tram.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.javers.core.metamodel.annotation.DiffIgnore;

import com.contract.common.entity.AuditEntity;
import com.contract.danhmuc.huyen.model.DmHuyenModel;
import com.contract.danhmuc.loaicotangten.model.DmLoaiCotAngtenModel;
import com.contract.danhmuc.loaicsht.model.DmLoaiCshtModel;
import com.contract.danhmuc.loaithietbiran.model.DmLoaiThietBiRanModel;
import com.contract.danhmuc.loaitram.model.DmLoaiTramModel;
import com.contract.danhmuc.phongdai.model.DmPhongDaiModel;
import com.contract.danhmuc.tinh.model.DmTinhModel;
import com.contract.danhmuc.to.model.DmToModel;
import com.contract.danhmuc.tramkhuvuc.model.DmTramKhuVucModel;
import com.contract.danhmuc.xa.model.DmXaModel;
import com.contract.hopdong.hopdong.model.HopDongModel;
import com.contract.tram.tram.entity.TramEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class TramModel extends AuditEntity<String> {

    public static TramModel fromEntity(TramEntity tramEntity, boolean containChild) {
        TramModel tramModel = new TramModel();

        try {
            tramModel.setCreatedAt(tramEntity.getCreatedAt());

            tramModel.setId(tramEntity.getId());
            tramModel.setPhongDaiId(tramEntity.getPhongDaiId());
            tramModel.setToId(tramEntity.getToId());
            tramModel.setMaTram(tramEntity.getMaTram());
            tramModel.setMaTramErp(tramEntity.getMaTramErp());
            tramModel.setTen(tramEntity.getTen());
            tramModel.setTinhId(tramEntity.getTinhId());
            tramModel.setHuyenId(tramEntity.getHuyenId());
            tramModel.setXaId(tramEntity.getXaId());
            tramModel.setDiaChi(tramEntity.getDiaChi());
            tramModel.setKinhDo(tramEntity.getKinhDo());
            tramModel.setViDo(tramEntity.getViDo());
            tramModel.setKhuVucId(tramEntity.getKhuVucId());
            tramModel.setNgayPhatSong(tramEntity.getNgayPhatSong());
            tramModel.setLoaiCshtId(tramEntity.getLoaiCshtId());
            tramModel.setLoaiTramId(tramEntity.getLoaiTramId());
            tramModel.setLoaiCotAngtenId(tramEntity.getLoaiCotAngtenId());
            tramModel.setDoCaoAngten(tramEntity.getDoCaoAngten());
            tramModel.setGhiChu(tramEntity.getGhiChu());
            tramModel.setTrangThaiHoatDong(tramEntity.getTrangThaiHoatDong());
            tramModel.setSiteNameErp(tramEntity.getSiteNameErp());
            tramModel.setMaDauTuXayDung(tramEntity.getMaDauTuXayDung());
            tramModel.setDaPhatSong(tramEntity.getDaPhatSong());
            tramModel.setLuuLuong(tramEntity.getLuuLuong());
            if (containChild) {
                if (tramEntity.getDmPhongDaiEntity() != null) {
                    tramModel.setDmPhongDai(DmPhongDaiModel.fromEntity(tramEntity.getDmPhongDaiEntity()));
                }
                if (tramEntity.getDmToEntity() != null) {
                    tramModel.setDmTo(DmToModel.fromEntity(tramEntity.getDmToEntity()));
                }
                if (tramEntity.getDmTinhEntity() != null) {
                    tramModel.setDmTinh(DmTinhModel.fromEntity(tramEntity.getDmTinhEntity()));
                }
                if (tramEntity.getDmHuyenEntity() != null) {
                    tramModel.setDmHuyen(DmHuyenModel.fromEntity(tramEntity.getDmHuyenEntity()));
                }
                if (tramEntity.getDmXaEntity() != null) {
                    tramModel.setDmXa(DmXaModel.fromEntity(tramEntity.getDmXaEntity()));
                }
                if (tramEntity.getDmTramKhuVucEntity() != null) {
                    tramModel.setDmTramKhuVuc(DmTramKhuVucModel.fromEntity(tramEntity.getDmTramKhuVucEntity()));
                }
                if (tramEntity.getDmLoaiCshtEntity() != null) {
                    tramModel.setDmLoaiCsht(DmLoaiCshtModel.fromEntity(tramEntity.getDmLoaiCshtEntity()));
                }
                if (tramEntity.getDmLoaiTramEntity() != null) {
                    tramModel.setDmLoaiTram(DmLoaiTramModel.fromEntity(tramEntity.getDmLoaiTramEntity()));
                }
                if (tramEntity.getDmLoaiCotAngtenEntity() != null) {
                    tramModel
                            .setDmLoaiCotAngten(DmLoaiCotAngtenModel.fromEntity(tramEntity.getDmLoaiCotAngtenEntity()));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return tramModel;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = 0L;

    @Column(name = "phong_dai_id")
    protected Integer phongDaiId;

    @Column(name = "to_id")
    protected Integer toId;

    @Column(name = "ma_tram")
    protected String maTram;

    @Column(name = "ma_tram_erp")
    protected String maTramErp;

    @Column(name = "ten")
    protected String ten;

    @Column(name = "tinh_id")
    protected Integer tinhId;

    @Column(name = "huyen_id")
    protected Integer huyenId;

    @Column(name = "xa_id")
    protected Integer xaId;

    @Column(name = "dia_chi")
    protected String diaChi;

    @Column(name = "kinh_do")
    protected String kinhDo;

    @Column(name = "vi_do")
    protected String viDo;

    @Column(name = "khu_vuc_id")
    protected Integer khuVucId;

    @Column(name = "ngay_phat_song")
    protected Date ngayPhatSong;

    @Column(name = "loai_csht_id")
    protected Integer loaiCshtId;

    @Column(name = "loai_tram_id")
    protected Integer loaiTramId;

    @Column(name = "loai_cot_angten_id")
    protected Integer loaiCotAngtenId;

    @Column(name = "do_cao_angten")
    protected Long doCaoAngten;

    @Column(name = "ghi_chu")
    protected String ghiChu;

    @Column(name = "trang_thai_hoat_dong")
    @Enumerated(EnumType.ORDINAL)
    protected TrangThaiTram trangThaiHoatDong;

    @Column(name = "site_name_erp")
    protected String siteNameErp;

    @Column(name = "ma_dau_tu_xay_dung")
    protected String maDauTuXayDung;

    @Column(name = "da_phat_song")
    protected Boolean daPhatSong;

    @Column(name="luu_luong")
    protected Float luuLuong;

    @Transient
    @DiffIgnore
    protected List<DmLoaiThietBiRanModel> dmLoaiThietBiRanList;

    @Transient
    @DiffIgnore
    protected List<HopDongModel> hopDongList;

    @Transient
    @DiffIgnore
    protected DmPhongDaiModel dmPhongDai;

    @Transient
    @DiffIgnore
    protected DmToModel dmTo;

    @Transient
    @DiffIgnore
    protected DmTinhModel dmTinh;

    @Transient
    @DiffIgnore
    protected DmHuyenModel dmHuyen;

    @Transient
    @DiffIgnore
    protected DmXaModel dmXa;

    @Transient
    @DiffIgnore
    protected DmTramKhuVucModel dmTramKhuVuc;

    @Transient
    @DiffIgnore
    protected DmLoaiCshtModel dmLoaiCsht;

    @Transient
    @DiffIgnore
    protected DmLoaiTramModel dmLoaiTram;

    @Transient
    @DiffIgnore
    protected DmLoaiCotAngtenModel dmLoaiCotAngten;
}
