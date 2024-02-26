package com.contract.danhmuc.hinhthucthanhtoan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contract.danhmuc.hinhthucthanhtoan.entity.DmHinhThucThanhToanEntity;

@Repository
public interface DmHinhThucThanhToanRepository extends JpaRepository<DmHinhThucThanhToanEntity, Integer> {
    List<DmHinhThucThanhToanEntity> findAllByTen(String ten);
}
