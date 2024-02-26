package com.contract.danhmuc.khoanmucerp.service.impl;

import com.contract.danhmuc.khoanmucerp.dto.DmKhoanMucErpDto;
import com.contract.danhmuc.khoanmucerp.model.DmKhoanMucErpModel;
import com.contract.danhmuc.khoanmucerp.repository.DmKhoanMucErpRepository;
import com.contract.danhmuc.khoanmucerp.service.DmKhoanMucErpService;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DmKhoanMucErpServiceImpl implements DmKhoanMucErpService {

    @Autowired
    private LogService logService;
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private DmKhoanMucErpRepository dmKhoanMucErpRepository;

    @Override
    public List<DmKhoanMucErpModel> findAll() {
        return dmKhoanMucErpRepository.findAll() == null ? null
                : dmKhoanMucErpRepository.findAll(Sort.by(Sort.Direction.ASC, "ma"));
    }

    @Override
    public DmKhoanMucErpModel saveKhoanMuc(DmKhoanMucErpDto dmKhoanMucErpDto) {
        if (!dmKhoanMucErpDto.getTen().isEmpty() && !dmKhoanMucErpDto.getMa().isEmpty()) {
            DmKhoanMucErpModel dmKhoanMucErpModel = new DmKhoanMucErpModel();
            dmKhoanMucErpModel.setMa(dmKhoanMucErpDto.getMa());
            dmKhoanMucErpModel.setTen(dmKhoanMucErpDto.getTen());
            dmKhoanMucErpModel.setGhiChu(dmKhoanMucErpDto.getGhiChu());
            dmKhoanMucErpModel = dmKhoanMucErpRepository.save(dmKhoanMucErpModel);
            DmKhoanMucErpModel saved = dmKhoanMucErpModel;
            Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
            CompletableFuture.runAsync(() -> {
                logService.saveLogDanhMuc(dmKhoanMucErpDto, saved,
                        "create",
                        nguoiDungId,
                        LogActionEnum.CREATE);
            });

            return dmKhoanMucErpModel;
        }
        return null;
    }

    @Override
    public DmKhoanMucErpModel updateKhoanMuc(int idOld, DmKhoanMucErpDto dmKhoanMucErpDto) {
        DmKhoanMucErpModel dmKhoanMucErpModel = dmKhoanMucErpRepository.findById(idOld);
        DmKhoanMucErpModel originalModel = dmKhoanMucErpRepository.findById(idOld);
        if (!dmKhoanMucErpDto.getTen().isEmpty() && !dmKhoanMucErpDto.getMa().isEmpty()) {
            dmKhoanMucErpModel.setMa(dmKhoanMucErpDto.getMa());
            dmKhoanMucErpModel.setTen(dmKhoanMucErpDto.getTen());
            dmKhoanMucErpModel.setGhiChu(dmKhoanMucErpDto.getGhiChu());
            DmKhoanMucErpModel saved = dmKhoanMucErpRepository.save(dmKhoanMucErpModel);
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
        DmKhoanMucErpModel dmKhoanMucErpModel = dmKhoanMucErpRepository.findById(idOld);
        try {
            /* kiem tra danh muc khac rong */
            if (dmKhoanMucErpModel != null) {
                /* cap nhat thoi gian vao truong deleted_at */
                dmKhoanMucErpModel.setDeletedAt(new Date());
                /* luu thay doi sau khi cap nhat thoi gian xoa */
                dmKhoanMucErpRepository.save(dmKhoanMucErpModel);

                Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
                CompletableFuture.runAsync(() -> {
                    logService.saveLogDanhMuc(null, dmKhoanMucErpModel, "delete", nguoiDungId,
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
