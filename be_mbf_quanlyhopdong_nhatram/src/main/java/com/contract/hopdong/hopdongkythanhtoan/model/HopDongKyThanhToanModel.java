// package com.contract.hopdong.hopdongkythanhtoan.model;

// import java.util.Date;
// import java.util.Objects;

// import javax.persistence.Column;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.MappedSuperclass;
// import javax.persistence.Transient;

// import com.contract.common.entity.AuditEntity;
// import
// com.contract.hopdong.hopdongkythanhtoan.entity.HopDongKyThanhToanEntity;
// import com.contract.hopdong.hopdongnhatram.model.HopDongModel;
// import com.contract.nguoidung.nguoidung.model.NguoiDungModel;

// import lombok.AccessLevel;
// import lombok.Getter;
// import lombok.Setter;

// @Getter(AccessLevel.PUBLIC)
// @Setter(AccessLevel.PUBLIC)
// @MappedSuperclass
// public class HopDongKyThanhToanModel extends AuditEntity<String> {
// public static HopDongKyThanhToanModel fromEntity(HopDongKyThanhToanEntity
// entity,
// boolean containChild) {
// HopDongKyThanhToanModel model = new HopDongKyThanhToanModel();

// try {
// model.setId(entity.getId());
// model.setHopDongId(entity.getHopDongId());
// model.setTuNgay(entity.getTuNgay());
// model.setDenNgay(entity.getDenNgay());
// model.setTien(entity.getTien());
// model.setDaThanhToanNgay(entity.getDaThanhToanNgay());
// model.setThanhToanBy(entity.getThanhToanBy());
// model.setThanhToanNgay(entity.getThanhToanNgay());
// model.setSoTienThanhToan(entity.getSoTienThanhToan());

// if (containChild) {
// // if (entity.getHopDongEntity() != null) {
// // model.setHopDong(HopDongModel.fromEntity(entity.getHopDongEntity(),
// false));
// // }
// // if (entity.getNguoiDungEntity() != null) {
// // model.setNguoiThanhToan(
// // NguoiDungModel.fromEntity(entity.getNguoiDungEntity(), false));
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

// @Column(name = "hop_dong_id")
// protected Long hopDongId;

// @Column(name = "tu_ngay")
// protected Date tuNgay;

// @Column(name = "den_ngay")
// protected Date denNgay;

// @Column(name = "tien")
// protected Double tien;

// @Column(name = "da_thanh_toan_ngay")
// protected Date daThanhToanNgay;

// @Column(name = "thanh_toan_by")
// protected Long thanhToanBy;

// @Column(name = "thanh_toan_ngay")
// protected Date thanhToanNgay;

// @Column(name = "so_tien_thanh_toan")
// protected Double soTienThanhToan;

// @Transient
// protected HopDongModel hopDong;

// @Transient
// protected NguoiDungModel nguoiThanhToan;

// @Override
// public boolean equals(Object obj) {
// if (this == obj) {
// return true;
// }
// if (obj == null || getClass() != obj.getClass()) {
// return false;
// }

// HopDongKyThanhToanModel other = (HopDongKyThanhToanModel) obj;

// return Objects.equals(hopDongId, other.hopDongId)
// && Objects.equals(tuNgay, other.tuNgay)
// && Objects.equals(denNgay, other.denNgay)
// && Objects.equals(daThanhToanNgay, other.daThanhToanNgay);
// }

// @Override
// public int hashCode() {
// return Objects.hash(hopDongId, tuNgay, denNgay, daThanhToanNgay);
// }
// }
