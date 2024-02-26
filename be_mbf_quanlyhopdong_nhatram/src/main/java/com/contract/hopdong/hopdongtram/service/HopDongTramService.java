package com.contract.hopdong.hopdongtram.service;

import com.contract.hopdong.hopdongfile.service.HopDongFileService;
import com.contract.hopdong.hopdongtram_dungchung.entity.HopDongTramDungChungEntity;
import com.contract.hopdong.hopdongtram_dungchung.service.HopDongTramDungChungService;
import com.contract.hopdong.hopdongtram_kythanhtoan.entity.HopDongTramKyThanhToanEntity;
import com.contract.hopdong.hopdongtram_kythanhtoan.service.HopDongTramKyThanhToanService;
import com.contract.hopdong.hopdongtram_phutro.entity.HopDongTramPhuTroEntity;
import com.contract.hopdong.hopdongtram_phutro.service.HopDongTramPhuTroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.contract.hopdong.hopdongfile.repository.HopDongFileRepository;
import com.contract.hopdong.hopdongtram.entity.HopDongTramEntity;
import com.contract.hopdong.hopdongtram.repository.HopDongTramRepository;
import com.contract.hopdong.hopdongtram_dungchung.repository.HopDongTramDungChungRepository;
import com.contract.hopdong.hopdongtram_kythanhtoan.repository.HopDongTramKyThanhToanRepository;
import com.contract.hopdong.hopdongtram_phutro.repository.HopDongTramPhuTroRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class HopDongTramService {
    @Autowired
    private HopDongTramRepository hopDongTramRepository;
    @Autowired
    private HopDongTramPhuTroRepository hopDongTramPhuTroRepository;
    @Autowired
    private HopDongFileRepository hopDongFileRepository;
    @Autowired
    private HopDongTramKyThanhToanRepository hopDongTramKyThanhToanRepository;
    @Autowired
    private HopDongTramDungChungRepository hopDongTramDungChungRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private HopDongTramKyThanhToanService hopDongTramKyThanhToanService;
    @Autowired
    private HopDongTramDungChungService hopDongTramDungChungService;
    @Autowired
    private HopDongTramPhuTroService hopDongTramPhuTroService;

    @Transactional
    public void deleteItAndChild(Long id) {
        HopDongTramEntity entity = hopDongTramRepository.findById(id).orElse(null);
        if (entity == null) {
            return;
        }
        hopDongTramPhuTroRepository.deleteAllByHopDongTramId(entity.getId());
        hopDongTramKyThanhToanRepository.deleteAllByHopDongTramId(entity.getId());
        hopDongFileRepository.deleteAllByHopDongTramId(entity.getId());
        hopDongTramDungChungRepository.deleteAllByHopDongTramId(entity.getId());

        hopDongTramRepository.deleteById(entity.getId());
    }

    /*find HDTRAM by id HD*/
    public List<HopDongTramEntity> findByIdHopDong(Long id) {
        String query = "select * from hop_dong_tram where hop_dong_id = '" + id + "'";
        Query nativeQuery = entityManager.createNativeQuery(query, HopDongTramEntity.class);
        List<HopDongTramEntity> list = nativeQuery.getResultList();
        return list;
    }

    /*xoa tung hop dong tram*/
    public void deleteListHDTRAMById(List<HopDongTramEntity> list) {
        if (list.size() > 0) {
            for (HopDongTramEntity hdTram : list) {
                try {
                    /*xoa cac hop dong tram thanh toan*/
                    List<HopDongTramKyThanhToanEntity> listHopDongTramKyThanhToan =
                            hopDongTramKyThanhToanService.findByIdHopDongTram(hdTram.getId());
                    hopDongTramKyThanhToanService.deleteListHDTTById(listHopDongTramKyThanhToan);
                    /* xoa cac hop dong dung chung*/
                    List<HopDongTramDungChungEntity> listHopDongTramDungChung =
                            hopDongTramDungChungService.findByIdHopDongTram(hdTram.getId());
                    hopDongTramDungChungService.deleteListHDTDCById(listHopDongTramDungChung);
                    /*xoa cac hop dong phu tro*/
                    List<HopDongTramPhuTroEntity> listHopDongTramPhuTroEntity =
                            hopDongTramPhuTroService.findByIdHopDongTram(hdTram.getId());
                    hopDongTramPhuTroService.deleteListHDTPTById(listHopDongTramPhuTroEntity);
                    /*xoa hop dong tram*/
                    hopDongTramRepository.deleteById(hdTram.getId());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        }
    }
}
