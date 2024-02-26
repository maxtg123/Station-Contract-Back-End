package com.contract.nguoidung.nguoidung.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.contract.common.entity.AuditEntity;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.nguoidung.nguoidungkhuvuc.model.NguoiDungKhuVucModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class NguoiDungModel extends AuditEntity<String> {
    public static NguoiDungModel fromEntity(NguoiDungEntity entity, boolean containChild) {
        NguoiDungModel model = new NguoiDungModel();

        try {
            model.setId(entity.getId());
            model.setCreatedAt(entity.getCreatedAt());
            model.setCreatedBy(entity.getCreatedBy());
            model.setUpdatedAt(entity.getUpdatedAt());
            model.setUpdatedBy(entity.getUpdatedBy());
            // model.setDeletedAt(entity.getDeletedAt());

//        model.setPhongDaiChucVuId(entity.getPhongDaiChucVuId());
            model.setEmail(entity.getEmail());
            model.setHoTen(entity.getHoTen());
            model.setGioiTinh(entity.getGioiTinh());
            model.setSoDienThoai(entity.getSoDienThoai());
            model.setTrangThai(entity.getTrangThai());
        } catch (Exception e) {
            System.out.println(e);
        }

        return model;
    }

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = 0L;

//    @Column(name = "phongdai_chucvu_id")
//    // @NotNull
//    protected Long phongDaiChucVuId;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank
    @NotEmpty
    @NotNull(message = "Email không được trống")
    protected String email;

    @Column(name = "ho_ten", nullable = false)
    @NotBlank
    @NotEmpty
    @NotNull(message = "Họ tên không được trống")
    protected String hoTen;

    @Column(name = "mat_khau", nullable = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String matKhau;

    @Column(name = "gioi_tinh", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    protected GioiTinh gioiTinh;

    @Column(name = "so_dien_thoai", nullable = true)
    protected String soDienThoai;

    @Column(name = "trang_thai", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    protected TrangThai trangThai = TrangThai.HOAT_DONG;

    @Column(name = "loai_nguoi_dung", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @JsonIgnore
    protected LoaiNguoiDung loaiNguoiDung = LoaiNguoiDung.NORMAL;

//    @Transient
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    protected ChucVuModel chucVu;

    @Transient
    protected List<NguoiDungKhuVucModel> nguoiDungKhuVucList;
}
