package com.contract.log.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contract.log.entity.LogEntity;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {
    @EntityGraph(attributePaths = { "nguoiDungEntity" })
    @Query("select distinct c from LogEntity c left join c.nguoiDungEntity where (:module is null or c.module = :module) and (:from is null or c.createdAt >= :from) and (:to is null or c.createdAt <= :to) order by c.createdAt desc")
    Page<LogEntity> findAllAndFetchUser(@Param("module") String module, @Param("from") Date from,
            @Param(value = "to") Date to, Pageable pageable);
}
