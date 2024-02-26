package com.contract.danhmuc.donvidungchung.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.danhmuc.donvidungchung.dto.DmDonViDungChungDto;
import com.contract.danhmuc.donvidungchung.model.DmDonViDungChungModel;
import com.contract.danhmuc.donvidungchung.repository.DmDonViDungChungRepository;
import com.contract.danhmuc.donvidungchung.service.DmDonViDungChungService;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class DmDonViDungChungServiceImpl implements DmDonViDungChungService {

    @Autowired
    private DmDonViDungChungRepository dmDonViDungChungRepository;
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private LogService logService;

    @Override
    public List<DmDonViDungChungModel> findAll() {
        return dmDonViDungChungRepository.findAll() == null ? null : dmDonViDungChungRepository.findAll();
    }

    @Override
    public DmDonViDungChungModel saveDm(DmDonViDungChungDto dmDonViDungChungDto) {
        if (!dmDonViDungChungDto.getTen().isEmpty()) {
            DmDonViDungChungModel dmDonViDungChungModel = new DmDonViDungChungModel();
            dmDonViDungChungModel.setGhiChu(dmDonViDungChungDto.getGhiChu());
            dmDonViDungChungModel.setTen(dmDonViDungChungDto.getTen());
            dmDonViDungChungModel.setMaDataSite(dmDonViDungChungDto.getMaDataSite());
            dmDonViDungChungModel.setMa(dmDonViDungChungDto.getMa());
            dmDonViDungChungModel = dmDonViDungChungRepository.save(dmDonViDungChungModel);

            Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
            DmDonViDungChungModel saved = dmDonViDungChungModel;
            CompletableFuture.runAsync(() -> {
                logService.saveLogDanhMuc(dmDonViDungChungDto, saved,
                        "create",
                        nguoiDungId,
                        LogActionEnum.CREATE);
            });
            return dmDonViDungChungModel;
        }
        return null;
    }

    @Override
    public DmDonViDungChungModel update(int idOld, DmDonViDungChungDto dmDonViDungChungDto) {
        DmDonViDungChungModel dmDonViDungChungModel = dmDonViDungChungRepository.findById(idOld);
        DmDonViDungChungModel originalModel = dmDonViDungChungRepository.findById(idOld);
        if (!dmDonViDungChungDto.getTen().isEmpty()) {
            dmDonViDungChungModel.setGhiChu(dmDonViDungChungDto.getGhiChu());
            dmDonViDungChungModel.setTen(dmDonViDungChungDto.getTen());
            dmDonViDungChungModel.setMaDataSite(dmDonViDungChungDto.getMaDataSite());
            dmDonViDungChungModel.setMa(dmDonViDungChungDto.getMa());
            DmDonViDungChungModel saved = dmDonViDungChungRepository.save(dmDonViDungChungModel);

            Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
            CompletableFuture.runAsync(() -> {
                logService.saveLogDanhMuc(originalModel, saved, "update", nguoiDungId,
                        LogActionEnum.UPDATE);
            });
            return saved;
        }
        return null;
    }

    @Override
    public boolean delete(int idOld) {
        /* lay danh muc ton tai voi id nhap vao */
        DmDonViDungChungModel dmDonViDungChungModel = dmDonViDungChungRepository.findById(idOld);
        try {
            /* kiem tra danh muc khac rong */
            if (dmDonViDungChungModel != null) {
                /* cap nhat thoi gian vao truong deleted_at */
                dmDonViDungChungModel.setDeletedAt(new Date());
                /* luu thay doi sau khi cap nhat thoi gian xoa */
                dmDonViDungChungRepository.save(dmDonViDungChungModel);

                Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
                CompletableFuture.runAsync(() -> {
                    logService.saveLogDanhMuc(null, dmDonViDungChungModel, "delete", nguoiDungId,
                            LogActionEnum.DELETE);
                });
                /* ket thuc bao ket qua ra man hinh */
                return true;
            }
            /* bao ket qua khong tim thay danh muc voi id da nhap */
            return false;
        } catch (Exception exception) {
            /* tra ve cac thong tin ngoai le */
            exception.printStackTrace();
        }
        return false;
    }

}
