package com.contract.danhmuc.hinhthucdautu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contract.danhmuc.hinhthucdautu.entity.DmHinhThucDauTuEntity;

public interface DmHinhThucDauTuRepository extends JpaRepository<DmHinhThucDauTuEntity, Integer> {
    List<DmHinhThucDauTuEntity> findAllByTen(String ten);
}
