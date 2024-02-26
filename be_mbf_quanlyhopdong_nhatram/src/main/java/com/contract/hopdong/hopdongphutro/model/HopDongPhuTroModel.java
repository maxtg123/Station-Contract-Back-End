// package com.contract.hopdong.hopdongphutro.model;

// import java.util.Objects;

// import javax.persistence.Column;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.MappedSuperclass;
// import javax.persistence.Transient;

// import com.contract.common.entity.AuditEntity;
// import com.contract.danhmuc.loaihopdongphutro.model.DmLoaiHopDongPhuTroModel;
// import com.contract.hopdong.hopdongnhatram.model.HopDongModel;
// import com.contract.hopdong.hopdongphutro.entity.HopDongPhuTroEntity;

// import lombok.AccessLevel;
// import lombok.Getter;
// import lombok.Setter;

// @Getter(AccessLevel.PUBLIC)
// @Setter(AccessLevel.PUBLIC)
// @MappedSuperclass
// public class HopDongPhuTroModel extends AuditEntity<String> {

// public static HopDongPhuTroModel fromEntity(HopDongPhuTroEntity entity,
// boolean containChild) {
// HopDongPhuTroModel model = new HopDongPhuTroModel();

// try {
// model.setId(entity.getId());
// model.setHopDongId(entity.getHopDongId());
// model.setDmPhuTroId(entity.getDmPhuTroId());
// model.setGia(entity.getGia());
// model.setHienThiThongTinChiTiet(entity.getHienThiThongTinChiTiet());

// if (containChild) {
// // if (entity.getDmLoaiHopDongPhuTroEntity() != null) {
// //
// model.setDmLoaiHopDongPhuTro(DmLoaiHopDongPhuTroModel.fromEntity(entity.getDmLoaiHopDongPhuTroEntity()));
// // }
// // if (entity.getHopDongEntity() != null) {
// // model.setHopDong(HopDongModel.fromEntity(entity.getHopDongEntity(),
// false));
// // }
// }
// } catch (Exception e) {
// System.out.println(e);
// }

// return model;
// }

// @Id
// @GeneratedValue(strategy = GenerationType.AUTO)
// protected Long id = 0L;

// @Column(name = "hop_dong_id", nullable = false)
// protected Long hopDongId;

// @Column(name = "dm_phu_tro_id", nullable = false)
// protected Integer dmPhuTroId;

// @Column(name = "gia", nullable = false)
// protected Double gia;

// @Column(name = "hien_thi_thong_tin_chi_tiet", nullable = false)
// protected Boolean hienThiThongTinChiTiet;

// @Transient
// protected DmLoaiHopDongPhuTroModel dmLoaiHopDongPhuTro;

// @Transient
// protected HopDongModel hopDong;

// @Override
// public boolean equals(Object obj) {
// if (this == obj) {
// return true;
// }
// if (obj == null || getClass() != obj.getClass()) {
// return false;
// }

// HopDongPhuTroModel other = (HopDongPhuTroModel) obj;

// return Objects.equals(hopDongId, other.hopDongId)
// && Objects.equals(dmPhuTroId, other.dmPhuTroId)
// && Objects.equals(gia, other.gia);
// }

// @Override
// public int hashCode() {
// return Objects.hash(hopDongId, dmPhuTroId, gia);
// }
// }
