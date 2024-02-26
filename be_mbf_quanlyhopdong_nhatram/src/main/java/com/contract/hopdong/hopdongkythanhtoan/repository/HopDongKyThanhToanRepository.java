// package com.contract.hopdong.hopdongkythanhtoan.repository;

// import
// com.contract.hopdong.hopdongkythanhtoan.entity.HopDongKyThanhToanEntity;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.stereotype.Repository;

// import java.util.List;

// @Repository
// public interface HopDongKyThanhToanRepository extends
// JpaRepository<HopDongKyThanhToanEntity, Long> {
// long deleteByHopDongIdAndDaThanhToanNgayIsNull(Long id);

// @Query(value = "from HopDongKyThanhToanEntity k where k.hopDongId=:hopDongId
// and k.daThanhToanNgay is null order by k.id")
// List<HopDongKyThanhToanEntity> findFirstKyCanThanhToan(Long hopDongId);
// }
