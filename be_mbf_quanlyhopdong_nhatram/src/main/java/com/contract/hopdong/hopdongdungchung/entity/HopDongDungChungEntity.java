// package com.contract.hopdong.hopdongdungchung.entity;

// import java.util.HashSet;
// import java.util.Set;

// import javax.persistence.FetchType;
// import javax.persistence.OneToMany;

// import com.contract.hopdong.hopdongdungchung.model.HopDongDungChungModel;
// import com.contract.hopdong.hopdongfile.entity.HopDongFileEntity;

// import lombok.AccessLevel;
// import lombok.Getter;
// import lombok.Setter;

// @Getter(AccessLevel.PUBLIC)
// @Setter(AccessLevel.PUBLIC)
// // @Entity
// // @Table(name = "hd_dungchung")
// // @Where(clause = "deleted_at IS NULL")
// public class HopDongDungChungEntity extends HopDongDungChungModel {
// // @OneToOne()
// // @JoinColumn(name = "hop_dong_nha_tram_id", insertable = false, updatable =
// // false)
// // private HopDongEntity hopDongEntity;

// // @ManyToOne()
// // @JoinColumn(name = "loai_hang_muc_csht_id", insertable = false, updatable
// =
// // false)
// // private DmLoaiHangMucCSHTEntity dmLoaiHangMucCSHTEntity;

// @OneToMany(mappedBy = "hopDongDungChungEntity", fetch = FetchType.LAZY)
// private Set<HopDongFileEntity> hopDongFileEntities = new HashSet<>();
// }
