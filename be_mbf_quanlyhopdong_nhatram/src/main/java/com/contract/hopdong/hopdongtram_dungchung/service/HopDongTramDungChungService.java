package com.contract.hopdong.hopdongtram_dungchung.service;

import com.contract.hopdong.hopdongtram_dungchung.entity.HopDongTramDungChungEntity;
import com.contract.hopdong.hopdongtram_dungchung.repository.HopDongTramDungChungRepository;
import com.contract.hopdong.hopdongtram_kythanhtoan.entity.HopDongTramKyThanhToanEntity;
import com.contract.hopdong.hopdongtram_phutro.entity.HopDongTramPhuTroEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class HopDongTramDungChungService {

    @Autowired
    private HopDongTramDungChungRepository tramDungChungRepository;

    @Autowired
    private EntityManager entityManager;

    /*find list HDTDC by id HDT*/
    public List<HopDongTramDungChungEntity> findByIdHopDongTram(Long id) {
        String query = "select * from hop_dong_tram_dung_chung where hop_dong_tram_id = '" + id + "'";
        Query nativeQuery = entityManager.createNativeQuery(query, HopDongTramDungChungEntity.class);
        List<HopDongTramDungChungEntity> list = nativeQuery.getResultList();
        return list;
    }

    /*xoa tung hop dong tram dung chung*/
    public void deleteListHDTDCById(List<HopDongTramDungChungEntity> list) {
        if (list.size() > 0) {
            for (HopDongTramDungChungEntity hd : list) {
                try {
                    tramDungChungRepository.deleteById(hd.getId());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

}
