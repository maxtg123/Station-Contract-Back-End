package com.contract.danhmuc.donvidungchung.repository;

import com.contract.danhmuc.donvidungchung.model.DmDonViDungChungModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DmDonViDungChungRepository extends JpaRepository<DmDonViDungChungModel, Integer> {
    DmDonViDungChungModel findById(int idDm);

    @Transactional
    void deleteById(int idDm);
}
