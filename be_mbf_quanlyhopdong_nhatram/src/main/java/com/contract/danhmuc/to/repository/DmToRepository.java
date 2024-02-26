package com.contract.danhmuc.to.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.contract.danhmuc.to.entity.DmToEntity;

public interface DmToRepository extends JpaRepository<DmToEntity, Integer> {
    DmToEntity findByTen(String tenTo);

    @Query(value = "select distinct t from DmToEntity t join fetch t.phongDai")
    List<DmToEntity> findAllToJoinPhongDai();

    List<DmToEntity> findAllByPhongDaiId(Integer id);
}
