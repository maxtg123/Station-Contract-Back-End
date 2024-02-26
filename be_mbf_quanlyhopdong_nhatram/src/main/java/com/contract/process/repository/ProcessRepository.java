package com.contract.process.repository;

import com.contract.process.entity.ProcessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessRepository extends JpaRepository<ProcessEntity,Long> {
}
