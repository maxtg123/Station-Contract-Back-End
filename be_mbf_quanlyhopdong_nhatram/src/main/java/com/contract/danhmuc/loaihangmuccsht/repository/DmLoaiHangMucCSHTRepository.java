package com.contract.danhmuc.loaihangmuccsht.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contract.danhmuc.loaihangmuccsht.entity.DmLoaiHangMucCSHTEntity;

@Repository
public interface DmLoaiHangMucCSHTRepository extends JpaRepository<DmLoaiHangMucCSHTEntity, Integer> {
    List<DmLoaiHangMucCSHTEntity> findAllByTen(String ten);
}
