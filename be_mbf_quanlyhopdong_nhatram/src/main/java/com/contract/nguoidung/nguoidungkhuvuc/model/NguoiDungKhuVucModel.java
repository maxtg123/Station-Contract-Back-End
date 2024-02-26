package com.contract.nguoidung.nguoidungkhuvuc.model;

import com.contract.chucvu.chucvu.model.ChucVuModel;
import com.contract.common.entity.AuditEntity;
import com.contract.danhmuc.phongdai.model.DmPhongDaiModel;
import com.contract.danhmuc.to.model.DmToModel;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;
import com.contract.nguoidung.nguoidungkhuvuc.entity.NguoiDungKhuVucEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class NguoiDungKhuVucModel extends AuditEntity<String> {
    public static NguoiDungKhuVucModel fromEntity(NguoiDungKhuVucEntity entity, boolean containChild) {
        NguoiDungKhuVucModel model = new NguoiDungKhuVucModel();

        try {
            model.setId(entity.getId());
            model.setCreatedAt(entity.getCreatedAt());
            model.setCreatedBy(entity.getCreatedBy());
            model.setUpdatedAt(entity.getUpdatedAt());
            model.setUpdatedBy(entity.getUpdatedBy());

            model.setNguoiDungId(entity.getNguoiDungId());
            model.setPhongDaiId(entity.getPhongDaiId());
            model.setToId(entity.getToId());
            model.setLoai(entity.getLoai());
            model.setPhongDaiChucVuId(entity.getPhongDaiChucVuId());
            if (containChild) {
                if (entity.getChucVuEntity() != null) {
                    model.setChucVu(ChucVuModel.fromEntity(entity.getChucVuEntity(), false));
                }
                if (entity.getNguoiDungEntity() != null) {
                    model.setNguoiDung(NguoiDungModel.fromEntity(entity.getNguoiDungEntity(), false));
                }
                if (entity.getDmPhongDaiEntity() != null) {
                    model.setDmPhongDai(DmPhongDaiModel.fromEntity(entity.getDmPhongDaiEntity()));
                }
                if (entity.getDmToEntity() != null) {
                    model.setDmTo(DmToModel.fromEntity(entity.getDmToEntity()));
                }
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

    @Column(name = "nguoi_dung_id", nullable = false)
    @NotNull
    protected Long nguoiDungId;

    @Column(name = "phongdai_chucvu_id", nullable = false)
    @NotNull
    protected Long phongDaiChucVuId;

    @Column(name = "phong_dai_id", nullable = false)
    @NotNull
    protected Integer phongDaiId;

    @Column(name = "to_id")
    protected Integer toId;

    @Column(name = "loai", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    protected LoaiKhuVuc loai = LoaiKhuVuc.CHINH;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected NguoiDungModel nguoiDung;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected DmPhongDaiModel dmPhongDai;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected DmToModel dmTo;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected ChucVuModel chucVu;
}
