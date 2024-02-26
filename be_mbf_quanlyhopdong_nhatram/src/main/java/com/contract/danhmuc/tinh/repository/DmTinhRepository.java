package com.contract.danhmuc.tinh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.contract.danhmuc.tinh.entity.DmTinhEntity;

public interface DmTinhRepository extends JpaRepository<DmTinhEntity, Integer> {
    @Query(value = "Select id from DmTinhEntity where ten=:tenTinh")
    int findIdByTen(String tenTinh);

    DmTinhEntity findByMa(String maTinh);

    @Query(value = "Select count(id) from DmTinhEntity where ten=:tenTinh")
    int countIdByTen(String tenTinh);

    DmTinhEntity findByTen(String ten);
}
