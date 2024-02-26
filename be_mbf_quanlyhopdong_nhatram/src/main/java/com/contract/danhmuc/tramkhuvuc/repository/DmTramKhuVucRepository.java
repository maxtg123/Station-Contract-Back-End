package com.contract.danhmuc.tramkhuvuc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contract.danhmuc.tramkhuvuc.entity.DmTramKhuVucEntity;

public interface DmTramKhuVucRepository extends JpaRepository<DmTramKhuVucEntity, Integer> {
    DmTramKhuVucEntity findByTen(String tenTramKhuVuc);
}
