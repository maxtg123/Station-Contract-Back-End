package com.contract.danhmuc.khoanmuc.repository;

import com.contract.danhmuc.khoanmuc.model.DmKhoanMucModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DmKhoanMucRepository extends JpaRepository<DmKhoanMucModel, Integer> {
    DmKhoanMucModel findById(int idDm);

    @Transactional
    void deleteById(int idDm);
}
