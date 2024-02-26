package com.contract.nguoidung.nguoidung.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDungEntity, Long> {
        List<NguoiDungEntity> findByEmail(String email);

        List<NguoiDungEntity> findByHoTen(String hoten);

        @EntityGraph(attributePaths = { "nguoiDungKhuVucEntityList", "nguoiDungKhuVucEntityList.dmPhongDaiEntity",
                        "nguoiDungKhuVucEntityList.dmToEntity",
                        "nguoiDungKhuVucEntityList.chucVuEntity",
                        "nguoiDungKhuVucEntityList.chucVuEntity.listChucVuPhanQuyenEntity" })
        @Query("select distinct c from NguoiDungEntity c left join fetch c.nguoiDungKhuVucEntityList f left join fetch f.chucVuEntity e left join fetch e.listChucVuPhanQuyenEntity where c.email = :email")
        List<NguoiDungEntity> findByEmailAndFetchKhuVuc(@Param("email") String email);

        @EntityGraph(attributePaths = { "nguoiDungKhuVucEntityList", "nguoiDungKhuVucEntityList.chucVuEntity",
                        "nguoiDungKhuVucEntityList.dmPhongDaiEntity",
                        "nguoiDungKhuVucEntityList.dmToEntity",
                        "nguoiDungKhuVucEntityList.chucVuEntity.listChucVuPhanQuyenEntity" })
        @Query("select distinct a " +
                        "from NguoiDungEntity a " +
                        "left join fetch a.nguoiDungKhuVucEntityList b left join fetch b.chucVuEntity c left join fetch c.listChucVuPhanQuyenEntity "
                        +
                        "where a.id in (select distinct e.id from NguoiDungEntity e left join e.nguoiDungKhuVucEntityList f where f.dmPhongDaiEntity.id in :ids)")
        List<NguoiDungEntity> findByDanhSachPhongDai(@Param("ids") List<Integer> ids);

        @EntityGraph(attributePaths = { "nguoiDungKhuVucEntityList", "nguoiDungKhuVucEntityList.chucVuEntity",
                        "nguoiDungKhuVucEntityList.dmPhongDaiEntity",
                        "nguoiDungKhuVucEntityList.dmToEntity",
                        "nguoiDungKhuVucEntityList.chucVuEntity.listChucVuPhanQuyenEntity" })
        @Query("select distinct a " +
                        "from NguoiDungEntity a " +
                        "left join fetch a.nguoiDungKhuVucEntityList b left join fetch b.chucVuEntity c left join fetch c.listChucVuPhanQuyenEntity "
                        +
                        "where a.id in (select distinct e.id from NguoiDungEntity e left join e.nguoiDungKhuVucEntityList f)")
        List<NguoiDungEntity> findAll();
}
