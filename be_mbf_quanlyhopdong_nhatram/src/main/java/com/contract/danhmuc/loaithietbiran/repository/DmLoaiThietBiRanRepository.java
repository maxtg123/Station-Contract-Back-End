package com.contract.danhmuc.loaithietbiran.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.contract.danhmuc.loaithietbiran.entity.DmLoaiThietBiRanEntity;

public interface DmLoaiThietBiRanRepository extends JpaRepository<DmLoaiThietBiRanEntity, Integer> {
    List<DmLoaiThietBiRanEntity> findAllByTen(String ten);

    @EntityGraph(attributePaths = { "tramEntitySet" })
    @Query("select distinct a from DmLoaiThietBiRanEntity a where a.id =:id")
    DmLoaiThietBiRanEntity findByIdAndFetch(Integer id);
}
