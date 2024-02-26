package com.contract.tram.tram.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.javers.core.metamodel.annotation.DiffIgnore;

import com.contract.danhmuc.huyen.entity.DmHuyenEntity;
import com.contract.danhmuc.loaicotangten.entity.DmLoaiCotAngtenEntity;
import com.contract.danhmuc.loaicsht.entity.DmLoaiCshtEntity;
import com.contract.danhmuc.loaithietbiran.entity.DmLoaiThietBiRanEntity;
import com.contract.danhmuc.loaitram.entity.DmLoaiTramEntity;
import com.contract.danhmuc.phongdai.entity.DmPhongDaiEntity;
import com.contract.danhmuc.tinh.entity.DmTinhEntity;
import com.contract.danhmuc.to.entity.DmToEntity;
import com.contract.danhmuc.tramkhuvuc.entity.DmTramKhuVucEntity;
import com.contract.danhmuc.xa.entity.DmXaEntity;
import com.contract.tram.tram.model.TramModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "tram")
@Where(clause = "deleted_at IS NULL")
public class TramEntity extends TramModel {
    public static TramEntity fromModel(TramEntity entity, TramModel model) {
        if (entity == null) {
            entity = new TramEntity();
        }
        entity.setPhongDaiId(model.getPhongDaiId());
        entity.setToId(model.getToId());
        entity.setMaTram(model.getMaTram());
        entity.setMaTramErp(model.getMaTramErp());
        entity.setTen(model.getTen());
        entity.setTinhId(model.getTinhId());
        entity.setHuyenId(model.getHuyenId());
        entity.setXaId(model.getXaId());
        entity.setDiaChi(model.getDiaChi());
        entity.setKinhDo(model.getKinhDo());
        entity.setViDo(model.getViDo());
        entity.setKhuVucId(model.getKhuVucId());
        entity.setNgayPhatSong(model.getNgayPhatSong());
        entity.setLoaiCshtId(model.getLoaiCshtId());
        entity.setLoaiTramId(model.getLoaiTramId());
        entity.setLoaiCotAngtenId(model.getLoaiCotAngtenId());
        entity.setDoCaoAngten(model.getDoCaoAngten());
        entity.setGhiChu(model.getGhiChu());
        entity.setTrangThaiHoatDong(model.getTrangThaiHoatDong());
        entity.setSiteNameErp(model.getSiteNameErp());
        entity.setMaDauTuXayDung(model.getMaDauTuXayDung());
        entity.setDaPhatSong(model.getDaPhatSong());

        entity.setDmLoaiThietBiRanEntityList(null);
        return entity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phong_dai_id", insertable = false, updatable = false)
    @DiffIgnore
    private DmPhongDaiEntity dmPhongDaiEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_id", insertable = false, updatable = false)
    @DiffIgnore
    private DmToEntity dmToEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tinh_id", insertable = false, updatable = false)
    @DiffIgnore
    private DmTinhEntity dmTinhEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "huyen_id", insertable = false, updatable = false)
    @DiffIgnore
    private DmHuyenEntity dmHuyenEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "xa_id", insertable = false, updatable = false)
    @DiffIgnore
    private DmXaEntity dmXaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khu_vuc_id", insertable = false, updatable = false)
    @DiffIgnore
    private DmTramKhuVucEntity dmTramKhuVucEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loai_csht_id", insertable = false, updatable = false)
    @DiffIgnore
    private DmLoaiCshtEntity dmLoaiCshtEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loai_tram_id", insertable = false, updatable = false)
    @DiffIgnore
    private DmLoaiTramEntity dmLoaiTramEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loai_cot_angten_id", insertable = false, updatable = false)
    @DiffIgnore
    private DmLoaiCotAngtenEntity dmLoaiCotAngtenEntity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tram_thietbi_ran", joinColumns = @JoinColumn(name = "tram_id"), inverseJoinColumns = @JoinColumn(name = "loai_ran_id"))
    @JsonIgnore
    private Set<DmLoaiThietBiRanEntity> dmLoaiThietBiRanEntityList;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
