package com.contract.danhmuc.phongdai.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.chucvu.chucvu.repository.ChucVuRepository;
import com.contract.common.exception.BadRequestException;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.common.exception.InvalidDataException;
import com.contract.common.exception.NotFoundException;
import com.contract.danhmuc.phongdai.entity.DmPhongDaiEntity;
import com.contract.danhmuc.phongdai.model.DmPhongDaiModel;
import com.contract.danhmuc.phongdai.repository.DmPhongDaiRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.nguoidung.nguoidungkhuvuc.repository.NguoiDungKhuVucRepository;
import com.contract.tram.tram.repository.TramRepository;

@Service
public class DmPhongDaiService {
    @Autowired
    private DmPhongDaiRepository dmPhongDaiRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private TramRepository tramRepository;
    @Autowired
    private NguoiDungKhuVucRepository nguoiDungKhuVucRepository;
    @Autowired
    private ChucVuRepository chucVuRepository;
    @Autowired
    private NguoiDungService nguoiDungService;

    public List<DmPhongDaiEntity> findAll() {
        return dmPhongDaiRepository.findAll();
    }

    public DmPhongDaiEntity findById(int id) {
        return dmPhongDaiRepository.findById(id).orElse(null);
    }

    public int findIdByTen(String tenPhongDai) {
        return dmPhongDaiRepository.findIdByTen(tenPhongDai);
    }

    public DmPhongDaiModel findByTen(String tenPhongDai) {
        return dmPhongDaiRepository.findByTen(tenPhongDai);
    }

    public DmPhongDaiEntity findByTenVietTat(String tenVietTat) {
        return dmPhongDaiRepository.findByTenVietTat(tenVietTat);
    }

    public List<DmPhongDaiEntity> findAllByLoai(String loai) {
        return dmPhongDaiRepository.findAllByLoai(loai);
    }

    public List<DmPhongDaiEntity> findAllByIdIn(List<Integer> ids) {
        return dmPhongDaiRepository.findAllByIdIn(ids);
    }

    public int kiemTraPhongDaiExists(String tenPhongDai) {
        return dmPhongDaiRepository.countIdByTen(tenPhongDai);
    }

    public DmPhongDaiModel save(DmPhongDaiModel model) {
        if (model.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmPhongDaiEntity entityToSave = convertModelToEntity(model);
        DmPhongDaiEntity saved = dmPhongDaiRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(model, saved, (model.getId() == null || model.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (model.getId() == null || model.getId() == 0) ? LogActionEnum.CREATE : LogActionEnum.UPDATE);
        });

        return DmPhongDaiModel.fromEntity(saved);
    }

    public DmPhongDaiModel update(DmPhongDaiModel phongDaiModel) {
        DmPhongDaiModel originalModel = findById(phongDaiModel.getId());
        if (phongDaiModel.getId() <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (phongDaiModel.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmPhongDaiEntity phongDaiToUpdate = convertModelToEntity(phongDaiModel);
        dmPhongDaiRepository.save(phongDaiToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, phongDaiToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmPhongDaiModel.fromEntity(phongDaiToUpdate);
    }

    public List<DmPhongDaiModel> convertListEntityToModel(List<DmPhongDaiEntity> listEntity) {
        List<DmPhongDaiModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmPhongDaiModel model = DmPhongDaiModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    public DmPhongDaiModel delete(Integer id) throws DeleteInUseDataException {
        if (id <= 0 || findById(id) == null) {
            throw new NotFoundException();
        }
        DmPhongDaiEntity entity = findById(id);
        if (entity == null) {
            throw new InvalidDataException();
        }
        if (checkInUsed(entity)) {
            throw new DeleteInUseDataException();
        }
        entity.setDeletedAt(new Date());

        dmPhongDaiRepository.save(entity);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, entity, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmPhongDaiEntity.fromEntity(entity);
    }

    public List<String> convertPhongDaiListBetweenHDAndPTM(List<String> phongDais) {
        List<String> result = new ArrayList<String>();
        phongDais.forEach(phongDai -> {
            switch (phongDai) {
                case "ĐVTĐ":
                    result.add("Đài viễn thông Đông HCM");
                    break;
                case "ĐVTT":
                    result.add("Đài viễn thông Tây HCM");
                    break;
                case "ĐVTĐN":
                    result.add("Đài viễn thông Đồng Nai");
                    break;
                case "ĐVTBD":
                    result.add("Đài viễn thông Bình Dương");
                    break;
                case "ĐVTCT":
                    result.add("Đài viễn thông Cần Thơ");
                    break;
                case "ĐVTTG":
                    result.add("Đài viễn thông Tiền Giang");
                    break;
                case "Đài viễn thông Tây HCM":
                    result.add("ĐVTT");
                    break;
                case "Đài viễn thông Đông HCM":
                    result.add("ĐVTĐ");
                    break;
                case "Đài viễn thông Đồng Nai":
                    result.add("ĐVTĐN");
                    break;
                case "Đài viễn thông Bình Dương":
                    result.add("ĐVTBD");
                    break;
                case "Đài viễn thông Cần Thơ":
                    result.add("ĐVTCT");
                    break;
                case "Đài viễn thông Tiền Giang":
                    result.add("ĐVTTG");
                    break;
                default:
                    break;
            }
        });
        return result;
    }

    public List<String> getAllPhongDaiPTM() {
        return new ArrayList<String>(Arrays.asList("Đài viễn thông Đông HCM",
                "Đài viễn thông Tây HCM", "Đài viễn thông Đồng Nai", "Đài viễn thông Bình Dương",
                "Đài viễn thông Cần Thơ", "Đài viễn thông Tiền Giang"));
    }

    private DmPhongDaiEntity convertModelToEntity(DmPhongDaiModel model) {
        DmPhongDaiEntity entity = new DmPhongDaiEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setTen(model.getTen());
            entity.setTenVietTat(model.getTenVietTat());
            entity.setGhiChu(model.getGhiChu());
            entity.setLoai(model.getLoai());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    private boolean checkInUsed(DmPhongDaiEntity entity) {
        long countTram = tramRepository.countByPhongDaiId(entity.getId());
        long countKhuVuc = nguoiDungKhuVucRepository.countByPhongDaiId(entity.getId());
        long countChucVu = chucVuRepository.countByPhongDaiId(entity.getId());
        if (countChucVu > 0 || countTram > 0 || countKhuVuc > 0) {
            return true;
        }
        return false;
    }
}
