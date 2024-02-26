package com.contract.danhmuc.loaicsht.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.common.exception.BadRequestException;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.common.exception.InvalidDataException;
import com.contract.common.exception.NotFoundException;
import com.contract.danhmuc.loaicsht.entity.DmLoaiCshtEntity;
import com.contract.danhmuc.loaicsht.model.DmLoaiCshtModel;
import com.contract.danhmuc.loaicsht.repository.DmLoaiCshtRepository;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.tram.tram.repository.TramRepository;

@Service
public class DmLoaiCshtService {

    @Autowired
    private DmLoaiCshtRepository dmLoaiCshtRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private TramRepository tramRepository;
    @Autowired
    private NguoiDungService nguoiDungService;

    public DmLoaiCshtEntity findById(Integer id) {
        return dmLoaiCshtRepository.findById(id).orElse(null);
    }

    public List<DmLoaiCshtEntity> findAll() {
        return dmLoaiCshtRepository.findAll();
    }

    public DmLoaiCshtEntity findByTen(String ten) {
        return dmLoaiCshtRepository.findByTen(ten);
    }

    public DmLoaiCshtModel save(DmLoaiCshtModel model) {
        if (model.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmLoaiCshtEntity entityToSave = convertModelToEntity(model);
        DmLoaiCshtEntity saved = dmLoaiCshtRepository.save(entityToSave);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(model, saved, (model.getId() == null || model.getId() == 0) ? "create" : "update",
                    nguoiDungId,
                    (model.getId() == null || model.getId() == 0) ? LogActionEnum.CREATE : LogActionEnum.UPDATE);
        });

        return DmLoaiCshtModel.fromEntity(saved);
    }

    public DmLoaiCshtModel update(Integer id, DmLoaiCshtModel dmLoaiCshtModel) {
        DmLoaiCshtModel originalModel = findById(id);
        if (id <= 0 || originalModel == null) {
            throw new NotFoundException();
        }
        if (dmLoaiCshtModel.getTen().isEmpty()) {
            throw new BadRequestException();
        }
        DmLoaiCshtEntity loaiCshtToUpdate = findById(id);
        loaiCshtToUpdate.setMa(dmLoaiCshtModel.getMa());
        loaiCshtToUpdate.setTen(dmLoaiCshtModel.getTen());
        loaiCshtToUpdate.setGhiChu(dmLoaiCshtModel.getGhiChu());

        dmLoaiCshtRepository.save(loaiCshtToUpdate);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(originalModel, loaiCshtToUpdate, "update", nguoiDungId,
                    LogActionEnum.UPDATE);
        });

        return DmLoaiCshtModel.fromEntity(loaiCshtToUpdate);
    }

    public DmLoaiCshtModel delete(Integer id) throws DeleteInUseDataException {
        if (id <= 0 || findById(id) == null) {
            throw new NotFoundException();
        }
        DmLoaiCshtEntity dmLoaiCshtToDelete = findById(id);
        if (dmLoaiCshtToDelete == null) {
            throw new InvalidDataException();
        }
        if (checkInUsed(dmLoaiCshtToDelete)) {
            throw new DeleteInUseDataException();
        }
        dmLoaiCshtToDelete.setDeletedAt(new Date());

        dmLoaiCshtRepository.save(dmLoaiCshtToDelete);

        Long nguoiDungId = nguoiDungService.getNguoiDung().getId();
        CompletableFuture.runAsync(() -> {
            logService.saveLogDanhMuc(null, dmLoaiCshtToDelete, "delete", nguoiDungId,
                    LogActionEnum.DELETE);
        });

        return DmLoaiCshtModel.fromEntity(dmLoaiCshtToDelete);
    }

    public DmLoaiCshtModel checkExists(String maCSHT) {
        // Hàm kiểm tra dữ liệu đã tồn tại dưới DB theo mã cơ sở hạ tầng
        DmLoaiCshtEntity entity = dmLoaiCshtRepository.findByMa(maCSHT);
        if (entity != null) {
            System.out.println(entity.toString());
            return entity;
        }
        return null;
    }

    public List<DmLoaiCshtModel> importDuLieu(List<DmLoaiCshtModel> loaiCshtModels,
            String loaiImport) {
        List<DmLoaiCshtModel> listImportOK = new ArrayList<>();
        if (loaiImport.equals("create")) {
            // Duyệt qua các phần tử nếu chưa có dưới DB thì gọi hàm insert, nếu đã tồn tại
            // thì bỏ
            // qua
            for (DmLoaiCshtModel loaiCshtModel : loaiCshtModels) {
                // Nếu mã hoặc tên csht rỗng => ném ra ngoại lệ
                if (loaiCshtModel.getMa().isEmpty() || loaiCshtModel.getTen().isEmpty()) {
                    throw new BadRequestException();
                }
                if (checkExists(loaiCshtModel.getMa()) == null) {
                    save(loaiCshtModel);
                    // Thêm phần tử đã import thành công vào list
                    listImportOK.add(loaiCshtModel);
                }
            }
        } else if (loaiImport.equals("update")) {
            // Duyệt qua các phần tử nếu chưa có dưới DB thì gọi hàm insert, nếu đã tồn tại
            // thì cập
            // nhật lại
            for (DmLoaiCshtModel loaiCshtModel : loaiCshtModels) {
                // Nếu mã hoặc tên csht rỗng => ném ra ngoại lệ
                if (loaiCshtModel.getMa().isEmpty() || loaiCshtModel.getTen().isEmpty()) {
                    throw new BadRequestException();
                }
                DmLoaiCshtModel loaiCshtExists = checkExists(loaiCshtModel.getMa());
                if (loaiCshtExists != null) {
                    loaiCshtModel.setId(loaiCshtExists.getId());
                    update(loaiCshtExists.getId(), loaiCshtModel);
                    // Thêm phần tử đã import thành công vào list
                    listImportOK.add(loaiCshtModel);
                } else {
                    save(loaiCshtModel);
                    // Thêm phần tử đã import thành công vào list
                    listImportOK.add(loaiCshtModel);
                }
            }
        } else {
            throw new NotFoundException();
        }
        return listImportOK;
    }

    public DmLoaiCshtEntity convertModelToEntity(DmLoaiCshtModel model) {
        DmLoaiCshtEntity entity = new DmLoaiCshtEntity();
        if (model != null) {
            entity.setId(model.getId());
            entity.setTen(model.getTen());
            entity.setMa(model.getMa());
            entity.setGhiChu(model.getGhiChu());
            entity.setMaDataSite(model.getMaDataSite());
        }
        return entity;
    }

    public List<DmLoaiCshtModel> convertListEntityToModel(List<DmLoaiCshtEntity> listEntity) {
        List<DmLoaiCshtModel> listModelReturn = new ArrayList<>();
        if (listEntity != null) {
            listEntity.forEach(entity -> {
                DmLoaiCshtModel model = DmLoaiCshtModel.fromEntity(entity);
                listModelReturn.add(model);
            });
        }
        return listModelReturn;
    }

    private boolean checkInUsed(DmLoaiCshtEntity entity) {
        long countTrams = tramRepository.countByLoaiCshtId(entity.getId());
        if (countTrams > 0) {
            return true;
        }
        return false;
    }
}
