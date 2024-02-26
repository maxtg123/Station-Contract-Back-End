package com.contract.tram.tramlichsu.model;

import com.contract.common.entity.AuditEntity;
import com.contract.tram.loailichsu.model.LoaiLichSuModel;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;
import com.contract.tram.tram.model.TramModel;
import com.contract.tram.tramlichsu.entity.TramLichSuEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class TramLichSuModel extends AuditEntity<String> {
    public static TramLichSuModel fromEntity(TramLichSuEntity entity, boolean containChild) {
        TramLichSuModel model = new TramLichSuModel();

        try {
            model.setId(entity.getId());
            model.setTramId(entity.getTramId());
            model.setNoiDung(entity.getNoiDung());
            model.setNguoiDungId(entity.getNguoiDungId());
            model.setLoaiId(entity.getLoaiId());
            model.setLoai(entity.getLoai());

            if (containChild
                    && entity.getTramEntity() != null
                    && entity.getNguoiDungEntity() != null
                    && entity.getLoaiLichSuEntity() != null) {
                model.setTram(entity.getTram());
                model.setNguoiDung(entity.getNguoiDung());
                model.setLoaiLichSu(entity.getLoaiLichSu());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return model;
    }

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = 0L;

    @Column(name = "tram_id", nullable = false)
    protected Long tramId;

    @Column(name = "noi_dung", nullable = false)
    protected String noiDung;

    @Column(name = "nguoi_dung_id", nullable = false)
    protected Long nguoiDungId;

    @Column(name = "loai_id", nullable = false)
    protected Integer loaiId;

    @Column(name = "loai", nullable = false)
    protected Integer loai;

    @Transient
    private TramModel tram;

    @Transient
    private NguoiDungModel nguoiDung;

    @Transient
    private LoaiLichSuModel loaiLichSu;
}
