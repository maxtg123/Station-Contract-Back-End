// package com.contract.hopdong.hopdongnhatram.entity;

// import java.util.HashSet;
// import java.util.Set;

// import javax.persistence.FetchType;
// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
// import javax.persistence.NamedAttributeNode;
// import javax.persistence.NamedEntityGraph;
// import javax.persistence.OneToMany;
// import javax.persistence.OneToOne;
// import javax.persistence.OrderBy;

// import org.hibernate.annotations.Where;

// import
// com.contract.danhmuc.doituongkyhopdong.entity.DmDoiTuongKyHopDongEntity;
// import com.contract.danhmuc.hinhthucdautu.entity.DmHinhThucDauTuEntity;
// import
// com.contract.danhmuc.hinhthuckyhopdong.entity.DmHinhThucKyHopDongEntity;
// import
// com.contract.danhmuc.hinhthucthanhtoan.entity.DmHinhThucThanhToanEntity;
// import com.contract.danhmuc.loaiphongmay.entity.DmLoaiPhongMayEntity;
// import
// com.contract.danhmuc.loaiphongmayphatdien.entity.DmLoaiPhongMayPhatDienEntity;
// import com.contract.danhmuc.loaitramvhkt.entity.DmLoaiTramVHKTEntity;
// import com.contract.hopdong.hopdongdoitac.entity.HopDongDoiTacEntity;
// import com.contract.hopdong.hopdongfile.entity.HopDongFileEntity;
// import
// com.contract.hopdong.hopdongkythanhtoan.entity.HopDongKyThanhToanEntity;
// import com.contract.hopdong.hopdongnhatram.model.HopDongModel;
// import com.contract.hopdong.hopdongpheduyet.entity.HopDongPheDuyetEntity;
// import com.contract.hopdong.hopdongphuluc.entity.HopDongPhuLucEntity;
// import com.contract.hopdong.hopdongphutro.entity.HopDongPhuTroEntity;
// import
// com.contract.hopdong.hopdongtram_dungchung.entity.HopDongTramDungChungEntity;
// import com.contract.tram.tram.entity.TramEntity;
// import com.fasterxml.jackson.annotation.JsonIgnore;

// import lombok.AccessLevel;
// import lombok.Getter;
// import lombok.Setter;

// @Getter(AccessLevel.PUBLIC)
// @Setter(AccessLevel.PUBLIC)
// // @Entity
// // @Table(name = "hop_dong_nha_tram")
// @NamedEntityGraph(name = "hopdong-entity-graph", attributeNodes = {
// @NamedAttributeNode("tramEntity"),
// @NamedAttributeNode("dmHinhThucKyHopDongEntity"),
// @NamedAttributeNode("dmHinhThucDauTuEntity"),
// @NamedAttributeNode("dmDoiTuongKyHopDongEntity"),
// @NamedAttributeNode("dmHinhThucThanhToanEntity"),
// @NamedAttributeNode("hopDongDoiTacEntity"),
// @NamedAttributeNode("fileEntityList"),
// @NamedAttributeNode("hopDongPhuTroEntityList"),
// @NamedAttributeNode("hopDongKyThanhToanEntityList"),
// @NamedAttributeNode("hopDongPhuLucEntityList"),
// @NamedAttributeNode("hopDongPheDuyetEntityList") })
// @Where(clause = "deleted_at IS NULL")
// public class HopDongEntity extends HopDongModel {
// @ManyToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "tram_id", insertable = false, updatable = false)
// private TramEntity tramEntity;

// @ManyToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "hinh_thuc_ky_id", insertable = false, updatable = false)
// private DmHinhThucKyHopDongEntity dmHinhThucKyHopDongEntity;

// @ManyToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "hinh_thuc_dau_tu_id", insertable = false, updatable =
// false)
// private DmHinhThucDauTuEntity dmHinhThucDauTuEntity;

// @ManyToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "doi_tuong_ky_id", insertable = false, updatable = false)
// private DmDoiTuongKyHopDongEntity dmDoiTuongKyHopDongEntity;

// @ManyToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "hinh_thuc_thanh_toan_id", insertable = false, updatable =
// false)
// private DmHinhThucThanhToanEntity dmHinhThucThanhToanEntity;

// @ManyToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "dm_loai_phong_may_phat_dien_id", insertable = false,
// updatable = false)
// private DmLoaiPhongMayPhatDienEntity dmLoaiPhongMayPhatDienEntity;

// @ManyToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "dm_loai_tram_vhkt_id", insertable = false, updatable =
// false)
// private DmLoaiTramVHKTEntity dmLoaiTramVHKTEntity;

// @ManyToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "dm_loai_phong_may_id", insertable = false, updatable =
// false)
// private DmLoaiPhongMayEntity dmLoaiPhongMayEntity;

// @OneToOne(mappedBy = "hopDongEntity", fetch = FetchType.LAZY)
// private HopDongDoiTacEntity hopDongDoiTacEntity;

// @OneToOne(mappedBy = "hopDongEntity", fetch = FetchType.LAZY)
// private HopDongTramDungChungEntity hopDongDungChungEntity;

// @JsonIgnore
// @OrderBy("createdAt asc")
// @OneToMany(mappedBy = "hopDongEntity", fetch = FetchType.LAZY)
// private Set<HopDongFileEntity> fileEntityList = new HashSet<>();

// @JsonIgnore
// @OneToMany(mappedBy = "hopDongEntity", fetch = FetchType.LAZY)
// private Set<HopDongPhuTroEntity> hopDongPhuTroEntityList = new HashSet<>();

// @JsonIgnore
// @OrderBy("tu_ngay")
// @OneToMany(mappedBy = "hopDongEntity", fetch = FetchType.LAZY)
// private Set<HopDongKyThanhToanEntity> hopDongKyThanhToanEntityList = new
// HashSet<>();

// @JsonIgnore
// @OrderBy("createdAt")
// @OneToMany(mappedBy = "hopDongEntity", fetch = FetchType.LAZY)
// private Set<HopDongPhuLucEntity> hopDongPhuLucEntityList = new HashSet<>();

// @JsonIgnore
// @OrderBy("createdAt desc ")
// @OneToMany(mappedBy = "hopDongEntity", fetch = FetchType.LAZY)
// private Set<HopDongPheDuyetEntity> hopDongPheDuyetEntityList = new
// HashSet<>();
// }
