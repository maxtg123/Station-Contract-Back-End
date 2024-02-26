package com.contract.tram.tramlichsu.repository;

import com.contract.tram.tramlichsu.entity.TramLichSuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TramLichSuRepository extends JpaRepository<TramLichSuEntity, Long> {
}
