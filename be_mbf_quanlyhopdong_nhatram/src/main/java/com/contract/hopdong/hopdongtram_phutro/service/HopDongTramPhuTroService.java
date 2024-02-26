package com.contract.hopdong.hopdongtram_phutro.service;

import com.contract.hopdong.hopdongtram_phutro.entity.HopDongTramPhuTroEntity;
import com.contract.hopdong.hopdongtram_phutro.repository.HopDongTramPhuTroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class HopDongTramPhuTroService {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private HopDongTramPhuTroRepository hopDongTramPhuTroRepository;

    /*find HDTPT by id HDT*/
    public List<HopDongTramPhuTroEntity> findByIdHopDongTram(Long id) {
        String query = "select * from hop_dong_tram_phu_tro where hop_dong_tram_id='" + id + "'";
        Query nativeQuery = entityManager.createNativeQuery(query, HopDongTramPhuTroEntity.class);
        List<HopDongTramPhuTroEntity> list = nativeQuery.getResultList();
        return list;
    }

    /*xoa tung hop dong phu tro*/
    public void deleteListHDTPTById(List<HopDongTramPhuTroEntity> list) {
        if (list.size() > 0) {
            for (HopDongTramPhuTroEntity hd : list) {
                try {
                    hopDongTramPhuTroRepository.deleteById(hd.getId());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
