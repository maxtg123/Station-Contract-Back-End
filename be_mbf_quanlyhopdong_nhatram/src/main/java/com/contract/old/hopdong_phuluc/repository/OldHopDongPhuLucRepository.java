package com.contract.old.hopdong_phuluc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contract.old.hopdong_phuluc.entity.OldHopDongPhuLucEntity;

@Repository
public interface OldHopDongPhuLucRepository extends JpaRepository<OldHopDongPhuLucEntity, Long> {

}
