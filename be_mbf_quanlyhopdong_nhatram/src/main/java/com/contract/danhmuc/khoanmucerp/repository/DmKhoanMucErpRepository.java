package com.contract.danhmuc.khoanmucerp.repository;

import com.contract.danhmuc.khoanmucerp.model.DmKhoanMucErpModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DmKhoanMucErpRepository extends JpaRepository<DmKhoanMucErpModel, Integer> {
    DmKhoanMucErpModel findById(int idDm);

    @Transactional
    void deleteById(int idDm);
}
