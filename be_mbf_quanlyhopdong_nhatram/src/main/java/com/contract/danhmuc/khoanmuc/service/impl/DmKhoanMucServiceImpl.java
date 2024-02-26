package com.contract.danhmuc.khoanmuc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.contract.danhmuc.khoanmuc.dto.DmKhoanMucDto;
import com.contract.danhmuc.khoanmuc.model.DmKhoanMucModel;
import com.contract.danhmuc.khoanmuc.repository.DmKhoanMucRepository;
import com.contract.danhmuc.khoanmuc.service.DmKhoanMucService;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class DmKhoanMucServiceImpl implements DmKhoanMucService {
    @Autowired
    private DmKhoanMucRepository dmKhoanMucRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private NguoiDungService nguoiDungService;

    @Override
    public List<DmKhoanMucModel> findAll() {
        return dmKhoanMucRepository.findAll() == null ? null
                : dmKhoanMucRepository.findAll(Sort.by(Sort.Direction.ASC, "ma"));
    }

    @Override
    public DmKhoanMucModel saveDm(DmKhoanMucDto dmKhoanMucDto) {
        if (!dmKhoanMucDto.getTen().isEmpty() && !dmKhoanMucDto.getMa().isEmpty()) {
            DmKhoanMucModel dmKhoanMucModel = new DmKhoanMucModel();
            dmKhoanMucModel.setMa(dmKhoanMucDto.getMa());
            dmKhoanMucModel.setTen(dmKhoanMucDto.getTen());
            dmKhoanMucModel.setGhiChu(dmKhoanMucDto.getGhiChu());
            dmKhoanMucModel = dmKhoanMucRepository.save(dmKhoanMucModel);
            DmKhoanMucModel saved = dmKhoanMucModel;
            Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
            CompletableFuture.runAsync(() -> {
                logService.saveLogDanhMuc(dmKhoanMucDto, saved,
                        "create",
                        nguoiDungId,
                        LogActionEnum.CREATE);
            });

            return dmKhoanMucModel;
        }
        return null;
    }

    @Override
    public DmKhoanMucModel update(int idOld, DmKhoanMucDto dmKhoanMucDto) {
        DmKhoanMucModel dmKhoanMucModel = dmKhoanMucRepository.findById(idOld);
        DmKhoanMucModel originalModel = dmKhoanMucRepository.findById(idOld);
        if (!dmKhoanMucDto.getTen().isEmpty() && !dmKhoanMucDto.getMa().isEmpty()) {
            dmKhoanMucModel.setMa(dmKhoanMucDto.getMa());
            dmKhoanMucModel.setTen(dmKhoanMucDto.getTen());
            dmKhoanMucModel.setGhiChu(dmKhoanMucDto.getGhiChu());
            DmKhoanMucModel saved = dmKhoanMucRepository.save(dmKhoanMucModel);
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
        DmKhoanMucModel dmKhoanMucModel = dmKhoanMucRepository.findById(idOld);
        try {
            /* kiem tra danh muc khac rong */
            if (dmKhoanMucModel != null) {
                /* cap nhat thoi gian vao truong deleted_at */
                dmKhoanMucModel.setDeletedAt(new Date());
                /* luu thay doi sau khi cap nhat thoi gian xoa */
                dmKhoanMucRepository.save(dmKhoanMucModel);

                Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
                CompletableFuture.runAsync(() -> {
                    logService.saveLogDanhMuc(null, dmKhoanMucModel, "delete", nguoiDungId,
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
