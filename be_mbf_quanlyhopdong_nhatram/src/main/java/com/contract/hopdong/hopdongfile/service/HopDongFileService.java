package com.contract.hopdong.hopdongfile.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.contract.hopdong.hopdongtram_dungchung.entity.HopDongTramDungChungEntity;
import com.contract.hopdong.hopdongtram_phutro.entity.HopDongTramPhuTroEntity;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.contract.base.service.BaseService;
import com.contract.common.exception.InvalidDataException;
import com.contract.common.util.FileUtil;
import com.contract.hopdong.hopdong.model.LoaiFileInputDto;
import com.contract.hopdong.hopdongfile.entity.HopDongFileEntity;
import com.contract.hopdong.hopdongfile.enums.LoaiFileEnum;
import com.contract.hopdong.hopdongfile.model.HopDongFileModel;
import com.contract.hopdong.hopdongfile.repository.HopDongFileRepository;
import com.contract.hopdong.hopdongtram.entity.HopDongTramEntity;
import com.contract.sys.module.model.MODULE;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
public class HopDongFileService extends BaseService {
    @Autowired
    private HopDongFileRepository hopDongFileRepository;

    @Autowired
    private EntityManager entityManager;

    String module = MODULE.HOP_DONG.name();

    private final String rootFolder = Paths.get("uploads", module).toFile().getAbsolutePath();

    public HopDongFileModel create(HopDongFileModel fileModel, MultipartFile file)
            throws Exception {
        HopDongFileEntity toSave = HopDongFileEntity.fromModel(fileModel);
        HopDongFileModel saved = hopDongFileRepository.save(toSave);

        if (file != null) {
            saveFile(saved, file);
        }

        return saved;
    }

    public void deleteOldFile(List<Long> ids, Long hopDongId) throws Exception {
        try {
            if (ids == null || hopDongId == null) {
                return;
            }
            hopDongFileRepository.deleteAllById(ids);

            List<HopDongFileModel> hopDongFileModelList = new ArrayList<>();

            ids.forEach(id -> {
                HopDongFileModel hopDongFileModel = new HopDongFileModel();
                hopDongFileModel.setId(id);
                hopDongFileModelList.add(hopDongFileModel);
            });

            deleteListFileByHopDong(hopDongId, hopDongFileModelList);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void saveFileWithPath(String pathString, MultipartFile file) throws IOException {
        Path path = Paths.get(pathString);
        FileUtil.saveFile(path.toFile().getAbsolutePath(), path.getFileName().toString(), file);
    }

    public void deleteFolderByHopDong(Long hopDongId) throws Exception {
        if (hopDongId == null) {
            throw new InvalidDataException();
        }
        String fileFolder = hopDongId.toString();
        Path path = Paths.get(rootFolder, fileFolder);
        FileUtils.deleteDirectory(new File(path.toFile().getAbsolutePath()));
    }

    public void deleteListFileByHopDong(Long hopDongId, List<HopDongFileModel> hopDongFileModelList)
            throws IOException {
        if (hopDongId == null) {
            throw new InvalidDataException();
        }

        hopDongFileModelList.forEach(fileModel -> {
            Long fileId = fileModel.getId();
            Path path = Paths.get(rootFolder, hopDongId.toString(), fileId.toString());

            try {
                FileUtils.deleteDirectory(new File(path.toFile().getAbsolutePath()));
            } catch (IOException e) {
                System.out.println(e);
            }
        });
    }

    public void addFilesToHopDong(MultipartFile[] files,
                                  List<LoaiFileInputDto> loaiFiles, Long hopDongId, List<HopDongTramEntity> savedHopDongTrams)
            throws Exception {
        if (files != null && loaiFiles != null && files.length == loaiFiles.size()) {
            for (int i = 0; i < files.length; i++) {
                HopDongFileModel fileModel = new HopDongFileModel();
                fileModel.setTen(files[i].getOriginalFilename());
                fileModel.setHopDongId(hopDongId);
                LoaiFileEnum loaiFile = loaiFiles.get(i).getLoaiFile();
                if (loaiFile == LoaiFileEnum.FILE_DUNG_CHUNG && loaiFiles.get(i).getTramId() != null) {
                    Long tramId = loaiFiles.get(i).getTramId();
                    Long savedHDTramId = savedHopDongTrams.stream()
                            .filter(hdt -> hdt.getTramId().equals(tramId)).findFirst().orElse(null).getId();
                    if (savedHDTramId != null) {
                        fileModel.setHopDongTramId(savedHDTramId);
                    }
                }
                // TODO handle with phu luc files
                fileModel.setLoai(loaiFile);
                create(fileModel, files[i]);
            }
        }

    }

    private void saveFile(HopDongFileModel fileModel, MultipartFile file) throws Exception {

        if (fileModel.getHopDongId() == null || file == null) {
            throw new InvalidDataException();
        }

        String fileFolder = fileModel.getHopDongId().toString();
        String fileName = file.getOriginalFilename();

        Path path = Paths.get(rootFolder, fileFolder, fileModel.getId().toString());
        FileUtil.saveFile(path.toFile().getAbsolutePath(), fileName, file);
    }

    // private HopDongFileModel save(HopDongFileEntity entity) {
    // try {
    // HopDongFileEntity saved = fileRepository.save(entity);
    // return HopDongFileModel.fromEntity(saved, false);
    // } catch (Exception e) {
    // System.out.println(e);
    // throw new InvalidDataException();
    // }
    // }

    // private HopDongFileEntity convertModelToEntity(HopDongFileEntity entity,
    // HopDongFileModel model) {
    // entity.setTen(model.getTen());
    // entity.setLoai(model.getLoai());
    // entity.setHopDongNhaTramId(model.getHopDongNhaTramId());
    // entity.setHopDongPhuLucId(model.getHopDongPhuLucId());

    // return entity;
    // }

    public void addFilesToTienTrinhDamPhan(MultipartFile[] files) {

    }

    /*find HDFILE by id HD*/
    public List<HopDongTramDungChungEntity> findByIdHopDong(Long id) {
        String query = "select * from hop_dong_tram_dung_chung where hop_dong_tram_id = '" + id + "'";
        Query nativeQuery = entityManager.createNativeQuery(query);
        List<HopDongTramDungChungEntity> entity = nativeQuery.getResultList();
        return entity;
    }

    /*find HDTPT by id HDT*/
    public List<HopDongFileEntity> findFilesByIdHopDong(Long id) {
        String query = "select * from hop_dong_files where hop_dong_id = '" + id + "'";
        Query nativeQuery = entityManager.createNativeQuery(query, HopDongFileEntity.class);
        List<HopDongFileEntity> list = nativeQuery.getResultList();
        return list;
    }

    /*xoa tung hop dong phu tro*/
    public void deleteListHDTPTById(List<HopDongFileEntity> list) {
        if (list.size() > 0) {
            for (HopDongFileEntity hd : list) {
                try {
                    hopDongFileRepository.deleteById(hd.getId());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
