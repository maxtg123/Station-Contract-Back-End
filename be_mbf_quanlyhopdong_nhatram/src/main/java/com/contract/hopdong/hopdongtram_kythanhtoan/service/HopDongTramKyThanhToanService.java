package com.contract.hopdong.hopdongtram_kythanhtoan.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.hopdong.hopdongtram_kythanhtoan.entity.HopDongTramKyThanhToanEntity;
import com.contract.hopdong.hopdongtram_kythanhtoan.repository.HopDongTramKyThanhToanRepository;

@Service
public class HopDongTramKyThanhToanService {
    @Autowired
    private HopDongTramKyThanhToanRepository hopDongTramKyThanhToanRepository;

    @Autowired
    private EntityManager entityManager;

    /* find HDTKTT by id HDT */
    public List<HopDongTramKyThanhToanEntity> findByIdHopDongTram(Long id) {
        String query = "select * from hop_dong_tram_ky_thanh_toan where hop_dong_tram_id = '" + id + "'";
        Query nativeQuery = entityManager.createNativeQuery(query, HopDongTramKyThanhToanEntity.class);
        List<HopDongTramKyThanhToanEntity> list = nativeQuery.getResultList();
        return list;
    }

    /* xoa tung hop dong tram ky thanh toan */
    public void deleteListHDTTById(List<HopDongTramKyThanhToanEntity> list) {
        if (list.size() > 0) {
            for (HopDongTramKyThanhToanEntity hd : list) {
                try {
                    hopDongTramKyThanhToanRepository.deleteById(hd.getId());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
