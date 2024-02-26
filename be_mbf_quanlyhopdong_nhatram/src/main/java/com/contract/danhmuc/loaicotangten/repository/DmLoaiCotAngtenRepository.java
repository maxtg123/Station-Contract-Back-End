package com.contract.danhmuc.loaicotangten.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contract.danhmuc.loaicotangten.entity.DmLoaiCotAngtenEntity;

public interface DmLoaiCotAngtenRepository extends JpaRepository<DmLoaiCotAngtenEntity, Integer> {
    DmLoaiCotAngtenEntity findByTen(String ten);
}
