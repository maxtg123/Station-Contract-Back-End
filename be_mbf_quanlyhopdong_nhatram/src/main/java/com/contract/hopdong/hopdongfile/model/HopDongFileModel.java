package com.contract.hopdong.hopdongfile.model;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.contract.common.entity.AuditEntity;
import com.contract.hopdong.hopdong.model.HopDongModel;
import com.contract.hopdong.hopdongfile.entity.HopDongFileEntity;
import com.contract.hopdong.hopdongfile.enums.LoaiFileEnum;
import com.contract.sys.module.model.MODULE;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongFileModel extends AuditEntity<String> {
    public static HopDongFileModel fromEntity(HopDongFileEntity entity,
            boolean containChild) {
        HopDongFileModel model = new HopDongFileModel();
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setId(entity.getId());
        model.setTen(entity.getTen());
        model.setLoai(entity.getLoai());
        model.setHopDongId(entity.getHopDongId());
        model.setHopDongTramId(entity.getHopDongTramId());
        model.setHopDongPhuLucId(entity.getHopDongPhuLucId());

        model.setPath("uploads/" + MODULE.HOP_DONG + "/" + entity.getHopDongId() + "/" + entity.getId() + "/"
                + entity.getTen());
        // fileModel.setPath("uploads" + "/" + MODULE.HOP_DONG + "/" +
        // fileEntity.getHopDongNhaTramId() + "/"
        // + fileEntity.getId() + "/" + fileEntity.getTen());

        return model;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = 0L;

    @Column(name = "ten")
    protected String ten;

    @Column(name = "loai")
    @Enumerated(EnumType.ORDINAL)
    protected LoaiFileEnum loai = LoaiFileEnum.FILE_HOP_DONG;

    @Column(name = "hop_dong_id", nullable = false)
    protected Long hopDongId;

    @Column(name = "hop_dong_phu_luc_id", nullable = true)
    protected Long hopDongPhuLucId;

    @Column(name = "hop_dong_tram_id", nullable = true)
    protected Long hopDongTramId;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected HopDongModel hopDongModel;

    @Transient
    protected String path;

    // @Override
    // public boolean equals(Object obj) {
    // if (this == obj) {
    // return true;
    // }
    // if (obj == null || getClass() != obj.getClass()) {
    // return false;
    // }

    // HopDongFileModel other = (HopDongFileModel) obj;

    // return Objects.equals(id, other.id);
    // }

    // @Override
    // public int hashCode() {
    // return Objects.hash(id);
    // }

    // @Transient
    // private MultipartFile file;
}
