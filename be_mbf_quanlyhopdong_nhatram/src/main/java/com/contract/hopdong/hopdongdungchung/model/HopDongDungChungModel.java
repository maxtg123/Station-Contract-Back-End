// package com.contract.hopdong.hopdongdungchung.model;

// import java.util.Date;
// import java.util.List;

// import javax.persistence.Column;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.MappedSuperclass;
// import javax.persistence.Transient;

// import com.contract.common.entity.AuditEntity;
// import com.contract.hopdong.hopdongdungchung.entity.HopDongDungChungEntity;
// import com.contract.hopdong.hopdongfile.model.HopDongFileModel;

// import lombok.AccessLevel;
// import lombok.Getter;
// import lombok.Setter;

// @Getter(AccessLevel.PUBLIC)
// @Setter(AccessLevel.PUBLIC)
// @MappedSuperclass
// public class HopDongDungChungModel extends AuditEntity<String> {
// public static HopDongDungChungModel fromEntity(HopDongDungChungEntity entity,
// boolean containChild) {
// HopDongDungChungModel model = new HopDongDungChungModel();
// try {
// model.setId(entity.getId());
// model.setHopDongNhaTramId(entity.getHopDongNhaTramId());
// model.setLoaiHangMucCSHTId(entity.getLoaiHangMucCSHTId());
// model.setMaTramDonViDungChung(entity.getMaTramDonViDungChung());
// model.setDonViDungChung(entity.getDonViDungChung());
// model.setThoiDiemPhatSinh(entity.getThoiDiemPhatSinh());
// model.setNgayLapDatThietBi(entity.getNgayLapDatThietBi());
// } catch (Exception e) {
// System.out.println(e);
// }
// return model;
// }

// @Id
// @GeneratedValue(strategy = GenerationType.AUTO)
// protected Long id = 0L;

// @Column(name = "hop_dong_nha_tram_id")
// protected Long hopDongNhaTramId;

// @Column(name = "loai_hang_muc_csht_id")
// protected Integer loaiHangMucCSHTId;

// @Column(name = "ma_tram_donvi_dungchung")
// protected String maTramDonViDungChung;

// @Column(name = "don_vi_dung_chung")
// protected String donViDungChung;

// @Column(name = "thoi_diem_phat_sinh")
// protected Date thoiDiemPhatSinh;

// @Column(name = "ngay_lap_dat_thiet_bi")
// protected Date ngayLapDatThietBi;

// @Transient
// protected List<HopDongFileModel> hopDongFileModels;
// }
