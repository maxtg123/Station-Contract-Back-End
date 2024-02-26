package com.contract.hopdong.hopdongdoitac.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import com.contract.common.entity.AuditEntity;
import com.contract.hopdong.hopdong.model.HopDongModel;
import com.contract.hopdong.hopdongdoitac.entity.HopDongDoiTacEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongDoiTacModel extends AuditEntity<String> {

    public static HopDongDoiTacModel fromEntity(HopDongDoiTacEntity entity, boolean containChild) {
        HopDongDoiTacModel hopDongDoiTacModel = new HopDongDoiTacModel();

        try {
            hopDongDoiTacModel.setCreatedAt(entity.getCreatedAt());
            hopDongDoiTacModel.setUpdatedAt(entity.getUpdatedAt());
            hopDongDoiTacModel.setId(entity.getId());

            hopDongDoiTacModel.setHopDongId(entity.getHopDongId());
            hopDongDoiTacModel.setTen(entity.getTen());
            hopDongDoiTacModel.setSoDienThoai(entity.getSoDienThoai());
            hopDongDoiTacModel.setCccd(entity.getCccd());
            hopDongDoiTacModel.setMaSoThue(entity.getMaSoThue());
            hopDongDoiTacModel.setDiaChi(entity.getDiaChi());
            hopDongDoiTacModel.setChuTaiKhoan(entity.getChuTaiKhoan());
            hopDongDoiTacModel.setSoTaiKhoan(entity.getSoTaiKhoan());
            hopDongDoiTacModel.setNganHangChiNhanh(entity.getNganHangChiNhanh());

            if (containChild) {
                if (entity.getHopDongEntity() != null) {
                    hopDongDoiTacModel.setHopDongModel(
                            HopDongModel.fromEntity(entity.getHopDongEntity(), false));
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return hopDongDoiTacModel;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = 0L;

    @Column(name = "hop_dong_id", nullable = false)
    protected Long hopDongId;

    @Column(name = "ten")
    protected String ten;

    @Column(name = "so_dien_thoai")
    protected String soDienThoai;

    @Column(name = "cccd")
    protected String cccd;

    @Column(name = "ma_so_thue")
    protected String maSoThue;

    @Column(name = "dia_chi")
    protected String diaChi;

    @Column(name = "chu_tai_khoan")
    protected String chuTaiKhoan;

    @Column(name = "so_tai_khoan")
    protected String soTaiKhoan;

    @Column(name = "ngan_hang_chi_nhanh")
    protected String nganHangChiNhanh;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected HopDongModel hopDongModel;
}
