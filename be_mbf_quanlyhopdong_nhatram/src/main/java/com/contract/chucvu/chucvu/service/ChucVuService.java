package com.contract.chucvu.chucvu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.contract.authentication.component.KiemTraQuyenModuleChucVu;
import com.contract.chucvu.chucvu.entity.ChucVuEntity;
import com.contract.chucvu.chucvu.model.ChucVuModel;
import com.contract.chucvu.chucvu.repository.ChucVuRepository;
import com.contract.chucvu.phanquyen.model.ChucVuPhanQuyenModel;
import com.contract.chucvu.phanquyen.service.ChucVuPhanQuyenService;
import com.contract.common.exception.DataExistsException;
import com.contract.common.exception.InvalidDataException;
import com.contract.common.exception.NotFoundException;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.nguoidung.nguoidungkhuvuc.entity.NguoiDungKhuVucEntity;
import com.contract.nguoidung.nguoidungkhuvuc.model.NguoiDungKhuVucModel;

@Service
public class ChucVuService {
    @Autowired
    private ChucVuRepository chucVuRepository;

    @Autowired
    private ChucVuPhanQuyenService chucVuPhanQuyenService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private KiemTraQuyenModuleChucVu kiemTraQuyenModuleChucVu;

    @Autowired
    private LogService logService;

    public List<ChucVuModel> findAll() {
        List<ChucVuModel> listReturn;

        if (nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
            List<ChucVuEntity> chucVuEntityList = chucVuRepository.findAll();
            listReturn = convertDataToResponse(chucVuEntityList);
        } else {
            List<Integer> listPhongDaiId = kiemTraQuyenModuleChucVu.layDanhSachIdPhongDaiDuocQuyenXem();
            List<ChucVuEntity> chucVuEntityList = chucVuRepository.findByDanhSachPhongDai(listPhongDaiId);
            listReturn = convertDataToResponse(chucVuEntityList);
        }

        return listReturn;
    }

    public List<ChucVuModel> convertDataToResponse(List<ChucVuEntity> chucVuEntityList) {
        List<ChucVuModel> listReturn = new ArrayList<>();
        chucVuEntityList.forEach(chucvu -> {
            ChucVuModel chucVuModel = ChucVuModel.fromEntity(chucvu, true);

            List<ChucVuPhanQuyenModel> chucVuPhanQuyenModelList = new ArrayList<>();
            if (!ObjectUtils.isEmpty(chucvu.getListChucVuPhanQuyenEntity())) {
                chucvu.getListChucVuPhanQuyenEntity().forEach(e -> {
                    chucVuPhanQuyenModelList.add(ChucVuPhanQuyenModel.fromEntity(e, true));
                });
            }
            chucVuModel.setChucVuPhanQuyenList(chucVuPhanQuyenModelList);

            List<NguoiDungKhuVucModel> nguoiDungKhuVucModelList = new ArrayList<>();
            if (!ObjectUtils.isEmpty(chucvu.getNguoiDungKhuVucEntityList())) {
                chucvu.getNguoiDungKhuVucEntityList().forEach(e -> {
                    nguoiDungKhuVucModelList.add(NguoiDungKhuVucModel.fromEntity(e, true));
                });
            }
            chucVuModel.setNguoiDungKhuVucList(nguoiDungKhuVucModelList);

            listReturn.add(chucVuModel);
        });

        return listReturn;
    }

    public List<ChucVuEntity> findByTenAndPhongDaiId(String ten, Integer id) {
        return chucVuRepository.findAllByTenAndPhongDaiId(ten, id);
    }

    public ChucVuEntity findById(Long id) {
        return chucVuRepository.findById(id).orElse(null);
    }

    public void updateChucVuCache(ChucVuEntity chucVuEntity) {
        List<NguoiDungKhuVucEntity> nguoiDungKhuVucEntityList = new ArrayList<>(
                chucVuEntity.getNguoiDungKhuVucEntityList());
        nguoiDungKhuVucEntityList.forEach(e -> {
            try {
                if (e.getNguoiDungId() != null) {
                    NguoiDungEntity nguoiDungEntity = nguoiDungService.findById(e.getNguoiDungId());
                    nguoiDungService.deleteNguoiDungCache(nguoiDungEntity);
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
    }

    public ChucVuModel save(ChucVuEntity entity) {
        try {
            ChucVuEntity saved = chucVuRepository.save(entity);
            return ChucVuModel.fromEntity(saved, false);
        } catch (Exception e) {
            throw new InvalidDataException();
        }
    }

    @Transactional
    public ChucVuModel create(ChucVuModel chucvu) throws Exception {
        ChucVuEntity chucVuToSave = new ChucVuEntity();

        List<ChucVuEntity> chucVuEntityList = this.findByTenAndPhongDaiId(chucvu.getTen(), chucvu.getPhongDaiId());
        if (!ObjectUtils.isEmpty(chucVuEntityList)) {
            throw new DataExistsException();
        }

        chucVuToSave = convertModelToEntity(chucVuToSave, chucvu);
        ChucVuModel chucVuReturn = save(chucVuToSave);

        if (chucvu.getChucVuPhanQuyenList() != null) {
            chucvu.getChucVuPhanQuyenList().forEach(o -> {
                try {
                    o.setPhongDaiChucVuId(chucVuReturn.getId());
                    chucVuPhanQuyenService.create(o);
                } catch (Exception e) {
                    throw new InvalidDataException();
                }
            });
        }

        return chucVuReturn;
    }

    @Transactional
    public ChucVuModel update(ChucVuModel chucvu, Long id) throws Exception {
        ChucVuEntity chucVuToSave = findById(id);
        if (ObjectUtils.isEmpty(chucVuToSave)) {
            throw new NotFoundException();
        }

        List<ChucVuEntity> chucVuEntityList = this.findByTenAndPhongDaiId(chucvu.getTen(), chucvu.getPhongDaiId());
        if (!ObjectUtils.isEmpty(chucVuEntityList) && (!chucVuToSave.getTen().equals(chucvu.getTen())
                || !chucVuToSave.getPhongDaiId().equals(chucvu.getPhongDaiId()))) {
            throw new DataExistsException();
        }

        chucVuToSave = convertModelToEntity(chucVuToSave, chucvu);
        ChucVuModel chucVuReturn = save(chucVuToSave);

        if (chucvu.getChucVuPhanQuyenList() != null) {
            chucVuPhanQuyenService.deleteByChucVuId(chucVuReturn.getId());
            chucVuPhanQuyenService.flush();

            chucvu.getChucVuPhanQuyenList().forEach(o -> {
                try {
                    o.setPhongDaiChucVuId(chucVuReturn.getId());
                    chucVuPhanQuyenService.create(o);
                } catch (Exception e) {
                    throw new InvalidDataException();
                }
            });
        }

        updateChucVuCache(chucVuToSave);
        return chucVuReturn;
    }

    public ChucVuModel delete(Long id) throws Exception {
        ChucVuEntity entity = findById(id);

        if (ObjectUtils.isEmpty(entity)) {
            throw new NotFoundException();
        }

        if (!ObjectUtils.isEmpty(entity.getNguoiDungKhuVucEntityList())) {
            throw new DataExistsException();
        }

        entity.setDeletedAt(new Date());
        return save(entity);
    }

    private List<ChucVuEntity> findByTen(String ten) {
        return chucVuRepository.findByTen(ten);
    }

    private ChucVuEntity convertModelToEntity(ChucVuEntity entity, ChucVuModel model) {
        if (model.getTen() != null) {
            entity.setTen(model.getTen());
        }
        if (model.getPhongDaiId() != null) {
            entity.setPhongDaiId(model.getPhongDaiId());
        }
        if (model.getGhichu() != null) {
            entity.setGhichu(model.getGhichu());
        }

        if (model.getChucVuPhanQuyenList() != null) {
            entity.setChucVuPhanQuyenList(model.getChucVuPhanQuyenList());
        }

        return entity;
    }
}
