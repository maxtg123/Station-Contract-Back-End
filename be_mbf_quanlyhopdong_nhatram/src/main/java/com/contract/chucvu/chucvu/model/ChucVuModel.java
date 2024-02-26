package com.contract.chucvu.chucvu.model;

import com.contract.chucvu.chucvu.entity.ChucVuEntity;
import com.contract.common.entity.AuditEntity;
import com.contract.danhmuc.phongdai.model.DmPhongDaiModel;
import com.contract.nguoidung.nguoidungkhuvuc.model.NguoiDungKhuVucModel;
import com.contract.chucvu.phanquyen.model.ChucVuPhanQuyenModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class ChucVuModel extends AuditEntity<String> {
    public static ChucVuModel fromEntity(ChucVuEntity entity, boolean containChild) {
        ChucVuModel model = new ChucVuModel();
        try {
            model.setId(entity.getId());
            model.setCreatedAt(entity.getCreatedAt());
            model.setCreatedBy(entity.getCreatedBy());
            model.setUpdatedAt(entity.getUpdatedAt());
            model.setUpdatedBy(entity.getUpdatedBy());

            model.setTen(entity.getTen());
            model.setPhongDaiId(entity.getPhongDaiId());
            model.setGhichu(entity.getGhichu());
            if (containChild && !ObjectUtils.isEmpty(entity.getDmPhongDaiEntity())) {
                model.setDmPhongDai(DmPhongDaiModel.fromEntity(entity.getDmPhongDaiEntity()));
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

    @Column(name = "phong_dai_id", nullable = false)
    @NotNull
    protected Integer phongDaiId;

    @Column(name = "ten", nullable = false)
    @NotBlank
    @NotEmpty
    @NotNull(message = "Tên không được trống")
    protected String ten;

    @Column(name = "ghi_chu")
    protected String ghichu;

    @Transient
    protected List<ChucVuPhanQuyenModel> chucVuPhanQuyenList;

    @Transient
    protected DmPhongDaiModel dmPhongDai;

    @Transient
    protected List<NguoiDungKhuVucModel> nguoiDungKhuVucList;
}
