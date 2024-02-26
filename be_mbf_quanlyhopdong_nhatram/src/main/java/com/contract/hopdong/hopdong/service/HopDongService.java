package com.contract.hopdong.hopdong.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;

import org.hibernate.jpa.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.contract.authentication.component.KiemTraQuyenModuleHopDong;
import com.contract.base.model.QueryResultModel;
import com.contract.common.enums.ErrorCodeEnum;
import com.contract.common.exception.BadRequestException;
import com.contract.common.exception.DataExistsException;
import com.contract.common.exception.InvalidDataException;
import com.contract.common.exception.NotFoundException;
import com.contract.common.exception.UnauthorizedException;
import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.hopdong.hopdong.enums.LoaiHopDongEnum;
import com.contract.hopdong.hopdong.enums.TinhTrangHopDongEnum;
import com.contract.hopdong.hopdong.enums.TinhTrangThanhToanEnum;
import com.contract.hopdong.hopdong.enums.TrangThaiHoatDongQueryEnum;
import com.contract.hopdong.hopdong.enums.TrangThaiHopDongEnum;
import com.contract.hopdong.hopdong.model.HopDongImportDto;
import com.contract.hopdong.hopdong.model.HopDongModel;
import com.contract.hopdong.hopdong.model.LoaiFileInputDto;
import com.contract.hopdong.hopdong.repository.HopDongRepository;
import com.contract.hopdong.hopdongdamphan.entity.HopDongDamPhanEntity;
import com.contract.hopdong.hopdongdoitac.entity.HopDongDoiTacEntity;
import com.contract.hopdong.hopdongdoitac.model.HopDongDoiTacModel;
import com.contract.hopdong.hopdongdoitac.repository.HopDongDoiTacRepository;
import com.contract.hopdong.hopdongdoitac.service.HopDongDoiTacService;
import com.contract.hopdong.hopdongfile.entity.HopDongFileEntity;
import com.contract.hopdong.hopdongfile.repository.HopDongFileRepository;
import com.contract.hopdong.hopdongfile.service.HopDongFileService;
import com.contract.hopdong.hopdongnhatram.model.ThanhToanHopDongRequest;
import com.contract.hopdong.hopdongpheduyet.entity.HopDongPheDuyetEntity;
import com.contract.hopdong.hopdongpheduyet.enums.TrangThaiPheDuyetEnum;
import com.contract.hopdong.hopdongtram.entity.HopDongTramEntity;
import com.contract.hopdong.hopdongtram.enums.TrangThaiHopDongTramEnum;
import com.contract.hopdong.hopdongtram.model.HopDongTramModel;
import com.contract.hopdong.hopdongtram.repository.HopDongTramRepository;
import com.contract.hopdong.hopdongtram.service.HopDongTramService;
import com.contract.hopdong.hopdongtram_dungchung.entity.HopDongTramDungChungEntity;
import com.contract.hopdong.hopdongtram_dungchung.model.HopDongTramDungChungModel;
import com.contract.hopdong.hopdongtram_dungchung.repository.HopDongTramDungChungRepository;
import com.contract.hopdong.hopdongtram_kythanhtoan.entity.HopDongTramKyThanhToanEntity;
import com.contract.hopdong.hopdongtram_kythanhtoan.model.HopDongTramKyThanhToanModel;
import com.contract.hopdong.hopdongtram_kythanhtoan.repository.HopDongTramKyThanhToanRepository;
import com.contract.hopdong.hopdongtram_phutro.entity.HopDongTramPhuTroEntity;
import com.contract.hopdong.hopdongtram_phutro.model.HopDongTramPhuTroModel;
import com.contract.hopdong.hopdongtram_phutro.repository.HopDongTramPhuTroRepository;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.process.enums.ProcessModuleEnum;
import com.contract.process.model.ProcessModel;
import com.contract.process.service.ProcessService;
import com.contract.tram.tram.entity.TramEntity;
import com.contract.tram.tram.model.TramModel;
import com.contract.tram.tram.service.TramService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HopDongService {
    @Autowired
    private HopDongRepository hopDongRepository;
    @Autowired
    private HopDongDoiTacRepository hopDongDoiTacRepository;
    @Autowired
    private HopDongTramRepository hopDongTramRepository;
    @Autowired
    private HopDongTramKyThanhToanRepository hopDongTramKyThanhToanRepository;
    @Autowired
    private HopDongTramPhuTroRepository hopDongTramPhuTroRepository;
    @Autowired
    private HopDongTramDungChungRepository hopDongTramDungChungRepository;
    @Autowired
    private HopDongFileService hopDongFileService;
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private KiemTraQuyenModuleHopDong kiemTraQuyenModuleHopDong;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private HopDongTramService hopDongTramService;
    @Autowired
    private HopDongFileRepository hopDongFileRepository;
    @Autowired
    private ProcessService processService;
    @Autowired
    private TramService tramService;
    @Autowired
    private HopDongDoiTacService hopDongDoiTacService;

    /**
     * @param responseType 0 pagination 1 all
     * @return
     */
    public QueryResultModel<HopDongEntity> findAll(Integer countOnly, int page, int size, Integer responseType,
            String search, String soHopDong, String soHopDongErp, Integer hinhThucDauTu,
            Integer hinhThucKyHopDong, Integer doiTuongKyHopDong, Integer phongDaiId,
            TrangThaiHoatDongQueryEnum trangThaiHopDong, LoaiHopDongEnum loaiHopDong, String maTram, Date ngayKyFrom,
            Date ngayKyTo, Date ngayKetThucFrom, Date ngayKetThucTo, Integer idTinh, Integer idHuyen, Integer idXa,
            TinhTrangHopDongEnum tinhTrangHopDong, TinhTrangThanhToanEnum tinhTrangThanhToan, Date kyThanhToanFrom,
            Date kyThanhToanTo)
            throws ParseException, ExecutionException, InterruptedException {

        List<Integer> pdIds = null;
        QueryResultModel<HopDongEntity> result = new QueryResultModel<>();
        if (!nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
            pdIds = kiemTraQuyenModuleHopDong.layDanhSachIdPhongDaiDuocQuyenXem();
        }

        if (responseType == 0) {
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            if (ngayKyFrom != null && ngayKyTo == null) {
                ngayKyTo = formatDate.parse("2200-12-31");
            }
            if (ngayKyFrom == null && ngayKyTo != null) {
                ngayKyFrom = formatDate.parse("1975-04-30");
            }
            if (ngayKetThucFrom != null && ngayKetThucTo == null) {
                ngayKetThucTo = formatDate.parse("2200-12-31");
            }
            if (ngayKetThucFrom == null && ngayKetThucTo != null) {
                ngayKetThucFrom = formatDate.parse("1975-04-30");
            }
            // pagination
            result = getListHopDong(null, pdIds, search, soHopDong, soHopDongErp, hinhThucDauTu, hinhThucKyHopDong,
                    doiTuongKyHopDong, phongDaiId, trangThaiHopDong, loaiHopDong, maTram, ngayKyFrom, ngayKyTo,
                    ngayKetThucFrom, ngayKetThucTo, idTinh, idHuyen, idXa, tinhTrangHopDong, tinhTrangThanhToan,
                    kyThanhToanFrom, kyThanhToanTo, page,
                    size, countOnly);
        } else {
            // all
            List<HopDongEntity> allHD = hopDongRepository.findAllOnlyHopDongEntity();
            result.setContent(allHD);
            result.setTotalElements((long) allHD.size());
            result.setPage(0);
            result.setSize(0);
        }
        return result;
    }

    /**
     * Create new hop dong
     *
     * @return
     * @throws Exception
     */
    @Transactional
    public HopDongModel create(String hopDongStringInput, HopDongModel hdModel, MultipartFile[] files,
            List<LoaiFileInputDto> loaiFiles) throws Exception {

        HopDongModel hopDongModelInput = null;
        if (hdModel == null) {
            // Parse Data Input
            try {
                ObjectMapper mapper = new ObjectMapper();
                hopDongModelInput = mapper.readValue(hopDongStringInput, HopDongModel.class);
            } catch (Exception e) {
                throw new InvalidDataException();
            }
        } else {
            hopDongModelInput = hdModel;
        }

        if (hopDongModelInput == null) {
            throw new InvalidDataException();
        }

        if (checkExists(null, hopDongModelInput.getSoHopDongErp())) {
            throw new DataExistsException(ErrorCodeEnum.HOP_DONG_EXIST,
                    "Số hợp đồng hoặc số hợp đồng ERP này đã tồn tại");
        }

        if (hopDongModelInput.getHopDongTramList() == null
                || hopDongModelInput.getHopDongTramList().size() == 0) {
            throw new InvalidDataException(ErrorCodeEnum.HOP_DONG_MISS_TRAM,
                    "Hợp đồng cần ít nhất thông tin về 1 trạm");
        }
        if (files != null && loaiFiles != null && files.length != loaiFiles.size()) {
            throw new InvalidDataException();
        }

        // Filter files hop dong and file dung chung di theo tram

        // Save main hop dong
        HopDongModel hopDongModel = hopDongModelInput;
        hopDongModel.setTrangThaiHopDong(TrangThaiHopDongEnum.NHAP);
        HopDongEntity hopDongSaved = hopDongRepository.save(HopDongEntity.fromModel(null, hopDongModel));
        // Save doi tac
        if (hopDongModelInput.getHopDongDoiTac() != null) {
            HopDongDoiTacModel hopDongDoiTacModel = hopDongModelInput.getHopDongDoiTac();
            hopDongDoiTacModel.setHopDongId(hopDongSaved.getId());
            hopDongDoiTacRepository.save(HopDongDoiTacEntity.fromModel(null, hopDongDoiTacModel));
        }
        // Save hop dong tram
        List<HopDongTramEntity> savedHopDongTrams = new ArrayList<>();
        for (HopDongTramModel hopDongTramModel : hopDongModelInput.getHopDongTramList()) {
            hopDongTramModel.setTrangThaiHoatDong(TrangThaiHopDongTramEnum.HOAT_DONG);
            hopDongTramModel.setHopDongId(hopDongSaved.getId());
            HopDongTramEntity hopDongTramSaved = hopDongTramRepository
                    .save(HopDongTramEntity.fromModel(null, hopDongTramModel));
            savedHopDongTrams.add(hopDongTramSaved);
            // ky thanh toan
            if (hopDongTramModel.getHopDongTramKyThanhToanList() != null
                    && hopDongTramModel.getHopDongTramKyThanhToanList().size() > 0) {
                for (HopDongTramKyThanhToanModel hopDongTramKyThanhToanModel : hopDongTramModel
                        .getHopDongTramKyThanhToanList()) {
                    hopDongTramKyThanhToanModel.setHopDongTramId(hopDongTramSaved.getId());
                    hopDongTramKyThanhToanRepository
                            .save(HopDongTramKyThanhToanEntity.fromModel(hopDongTramKyThanhToanModel));
                }
            }
            // phu tro
            if (hopDongTramModel.getHopDongTramPhuTroList() != null
                    && hopDongTramModel.getHopDongTramPhuTroList().size() > 0) {
                for (HopDongTramPhuTroModel hopDongTramPhuTroModel : hopDongTramModel
                        .getHopDongTramPhuTroList()) {
                    hopDongTramPhuTroModel.setHopDongTramId(hopDongTramSaved.getId());
                    hopDongTramPhuTroRepository
                            .save(HopDongTramPhuTroEntity.fromModel(hopDongTramPhuTroModel));
                }
            }
            // dung chung
            if (hopDongTramModel.getHopDongTramDungChung() != null) {
                HopDongTramDungChungModel hopDongTramDungChungModel = hopDongTramModel.getHopDongTramDungChung();
                hopDongTramDungChungModel.setHopDongTramId(hopDongTramSaved.getId());
                hopDongTramDungChungRepository
                        .save(HopDongTramDungChungEntity.fromModel(null, hopDongTramDungChungModel));
            }
        }

        // Save files
        // loai files === files is valid
        if (files != null && loaiFiles != null && files.length != loaiFiles.size()) {
            throw new InvalidDataException();
        }
        try {
            hopDongFileService.addFilesToHopDong(files, loaiFiles, hopDongSaved.getId(), savedHopDongTrams);
        } catch (Exception e) {
            System.out.println(e);
            hopDongFileService.deleteFolderByHopDong(hopDongSaved.getId());
            throw new InvalidDataException();
        }

        return hopDongModelInput;
    }

    /**
     * Update hop dong
     *
     * @return
     * @throws Exception
     */
    @Transactional
    public HopDongModel update(Long id, String hopDong, HopDongModel hdModel, MultipartFile[] files,
            List<LoaiFileInputDto> loaiFiles) throws Exception {
        HopDongModel hopDongModelInput = null;
        if (hdModel == null) {
            // Parse Data Input
            try {
                ObjectMapper mapper = new ObjectMapper();
                hopDongModelInput = mapper.readValue(hopDong, HopDongModel.class);
            } catch (Exception e) {
                throw new InvalidDataException();
            }
        } else {
            hopDongModelInput = hdModel;
        }

        if (hopDongModelInput == null) {
            throw new InvalidDataException();
        }
        if (files != null && loaiFiles != null && files.length != loaiFiles.size()) {
            throw new InvalidDataException();
        }
        if (checkExists(id, hopDongModelInput.getSoHopDongErp())) {
            throw new DataExistsException(ErrorCodeEnum.HOP_DONG_EXIST,
                    "Số hợp đồng hoặc số hợp đồng ERP này đã tồn tại");
        }
        if (hopDongModelInput.getHopDongTramList() == null
                || hopDongModelInput.getHopDongTramList().size() == 0) {
            throw new InvalidDataException(ErrorCodeEnum.HOP_DONG_MISS_TRAM,
                    "Hợp đồng cần ít nhất thông tin về 1 trạm");
        }

        HopDongEntity toSave = hopDongRepository.findByIdWithFetch(id);

        if (toSave == null) {
            throw new NotFoundException();
        }

        if (TrangThaiHopDongEnum.CHO_PHE_DUYET_PHU_LUC.equals(toSave.getTrangThaiHopDong())) {
            throw new InvalidDataException();
        }

        // Save hop dong - do not affect to trang thai hop dong
        hopDongModelInput.setTrangThaiHopDong(toSave.getTrangThaiHopDong());
        HopDongEntity hopDongSaved = hopDongRepository.save(HopDongEntity.fromModel(toSave, hopDongModelInput));
        // Save doi tac
        if (hopDongModelInput.getHopDongDoiTac() != null) {
            HopDongDoiTacModel hopDongDoiTacModel = hopDongModelInput.getHopDongDoiTac();
            hopDongDoiTacModel.setHopDongId(hopDongSaved.getId());
            hopDongDoiTacRepository
                    .save(HopDongDoiTacEntity.fromModel(toSave.getHopDongDoiTacEntity(), hopDongDoiTacModel));
        }
        // Save hop dong tram
        List<HopDongTramEntity> dbHopDongTramEntities = new ArrayList<>(toSave.getHopDongTramEntities());
        List<HopDongTramModel> inputHopDongTramModels = hopDongModelInput.getHopDongTramList();
        List<HopDongTramEntity> savedHopDongTrams = new ArrayList<>();
        for (int i = 0; i < inputHopDongTramModels.size(); i++) {
            HopDongTramModel inputHopDongTramModel = inputHopDongTramModels.get(i);
            HopDongTramEntity dbHopDongTramEntity;
            if (i >= dbHopDongTramEntities.size()) {
                dbHopDongTramEntity = new HopDongTramEntity();
            } else {
                dbHopDongTramEntity = dbHopDongTramEntities.get(i);
            }

            inputHopDongTramModel.setTrangThaiHoatDong(TrangThaiHopDongTramEnum.HOAT_DONG);
            inputHopDongTramModel.setHopDongId(hopDongSaved.getId());
            // dbHopDongTramEntity = null -> insert
            // dbHopDongTramEntity != null -> update
            HopDongTramEntity hopDongTramSaved = hopDongTramRepository
                    .save(HopDongTramEntity.fromModel(dbHopDongTramEntity, inputHopDongTramModel));
            savedHopDongTrams.add(hopDongTramSaved);
            // ky thanh toan
            if (inputHopDongTramModel.getHopDongTramKyThanhToanList() != null
                    && inputHopDongTramModel.getHopDongTramKyThanhToanList().size() > 0) {
                // Delete then insert new
                hopDongTramKyThanhToanRepository.deleteAllByHopDongTramId(hopDongTramSaved.getId());
                hopDongTramKyThanhToanRepository.flush();
                for (HopDongTramKyThanhToanModel hopDongTramKyThanhToanModel : inputHopDongTramModel
                        .getHopDongTramKyThanhToanList()) {
                    hopDongTramKyThanhToanModel.setHopDongTramId(hopDongTramSaved.getId());
                    hopDongTramKyThanhToanRepository
                            .save(HopDongTramKyThanhToanEntity.fromModel(hopDongTramKyThanhToanModel));
                }
            }
            // phu tro
            if (inputHopDongTramModel.getHopDongTramPhuTroList() != null
                    && inputHopDongTramModel.getHopDongTramPhuTroList().size() > 0) {
                // Delete then insert new
                hopDongTramPhuTroRepository.deleteAllByHopDongTramId(hopDongTramSaved.getId());
                hopDongTramPhuTroRepository.flush();
                for (HopDongTramPhuTroModel hopDongTramPhuTroModel : inputHopDongTramModel
                        .getHopDongTramPhuTroList()) {
                    hopDongTramPhuTroModel.setHopDongTramId(hopDongTramSaved.getId());
                    hopDongTramPhuTroRepository
                            .save(HopDongTramPhuTroEntity.fromModel(hopDongTramPhuTroModel));
                }
            }
            // dung chung
            HopDongTramDungChungEntity dbHopDongTramDungChungEntity = dbHopDongTramEntity
                    .getHopDongTramDungChungEntity();
            if (inputHopDongTramModel.getHopDongTramDungChung() != null) {
                HopDongTramDungChungModel hopDongTramDungChungModel = inputHopDongTramModel.getHopDongTramDungChung();
                hopDongTramDungChungModel.setHopDongTramId(hopDongTramSaved.getId());
                hopDongTramDungChungRepository
                        .save(HopDongTramDungChungEntity.fromModel(dbHopDongTramDungChungEntity,
                                hopDongTramDungChungModel));
            } else if (inputHopDongTramModel.getHopDongTramDungChung() == null && dbHopDongTramDungChungEntity != null
                    && dbHopDongTramDungChungEntity.getId() != null && dbHopDongTramDungChungEntity.getId() != 0) {
                hopDongTramDungChungRepository.deleteById(dbHopDongTramDungChungEntity.getId());
            }
            // if (inputHopDongTramModel != null && dbHopDongTramEntity != null) {
            // // Update dbHopDongTramEntity by inputHopDongTramModel
            // } else if (inputHopDongTramModel != null && dbHopDongTramEntity == null) {
            // // Insert new hop dong tram
            // }
        }
        if (dbHopDongTramEntities.size() > inputHopDongTramModels.size()) {
            // remove
            for (int i = dbHopDongTramEntities.size() - inputHopDongTramModels.size(); i < dbHopDongTramEntities
                    .size(); i++) {
                HopDongTramEntity dbHopDongTramEntity = dbHopDongTramEntities.get(i);
                hopDongTramService.deleteItAndChild(dbHopDongTramEntity.getId());
                // hopDongTramRepository.delete(dbHopDongTramEntity);
            }
        }
        // Handle files
        Set<Long> hdFilesIdWillDelete = new HashSet<>();
        if (toSave.getHopDongFileEntities() != null) {
            toSave.getHopDongFileEntities().forEach(_e -> {
                hdFilesIdWillDelete.add(_e.getId());
            });
        }

        if (hopDongModelInput.getHopDongFileModels() != null) {
            hopDongModelInput.getHopDongFileModels().forEach(m -> {
                if (m.getId() != null && hdFilesIdWillDelete.contains(m.getId())) {
                    hdFilesIdWillDelete.remove(m.getId());
                }
            });
        }
        if (hdFilesIdWillDelete.size() > 0) {
            hopDongFileRepository.deleteAllById(new ArrayList<>(hdFilesIdWillDelete));
        }
        // add new Files
        try {
            hopDongFileService.addFilesToHopDong(files, loaiFiles, hopDongSaved.getId(), savedHopDongTrams);
        } catch (Exception e) {
            System.out.println(e);
            hopDongFileService.deleteFolderByHopDong(hopDongSaved.getId());
            throw new InvalidDataException();
        }

        return HopDongModel.fromEntity(hopDongSaved, false);
    }

    /**
     * Get detail hop dong
     *
     * @param id
     * @return
     */
    public HopDongModel getDetail(Long id) {
        HopDongEntity hopDongEntity = hopDongRepository.findByIdWithFetch(id);
        if (hopDongEntity == null) {
            throw new NotFoundException();
        }
        // List<Integer> ids =
        // kiemTraQuyenModuleHopDong.layDanhSachIdPhongDaiDuocQuyenXem();
        // TramEntity tramEntity = hopDongEntity.getTramEntity();
        // if (ids.contains(tramEntity.getPhongDaiId())) {
        // return hopDongEntity;
        // } else {
        // throw new NotFoundException();
        // }
        if (nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
            return HopDongModel.fromEntity(hopDongEntity, true);
        }
        return HopDongModel.fromEntity(hopDongEntity, true);
    }

    public List<HopDongEntity> findHopDongByListId(List<Long> ids) {
        return hopDongRepository.findAllById(ids);
    }

    public Long importHopDong(List<HopDongImportDto> hopDongModelList) {
        if (ObjectUtils.isEmpty(hopDongModelList)) {
            throw new InvalidDataException();
        }

        // tao process
        NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();
        if (nguoiDungEntity == null) {
            throw new UnauthorizedException();
        }

        ProcessModel processModel = new ProcessModel();
        processModel.setUserId(nguoiDungEntity.getId());
        processModel.setModule(ProcessModuleEnum.HOP_DONG.name());
        processModel.setTongSo((long) hopDongModelList.size());
        ProcessModel savedProcessModel = processService.create(processModel);

        CompletableFuture.runAsync(() -> importFromExcel(hopDongModelList,
                savedProcessModel, nguoiDungEntity.getEmail(),
                nguoiDungEntity.getMatKhau()));

        return savedProcessModel.getId();
    }

    public void importFromExcel(List<HopDongImportDto> hopDongImportDtos, ProcessModel processModel, String userName,
            String password) {
        setUserNewThread(userName, password);
        try {
            for (int i = 0; i < hopDongImportDtos.size(); i++) {
                HopDongImportDto hopDongImportDto = hopDongImportDtos.get(i);
                try {
                    // Tram
                    HopDongEntity hopDongEntity = hopDongRepository
                            .findBySoHopDongErpAndFetchTram(hopDongImportDto.getSoHopDongErp());
                    if (hopDongEntity != null
                            && !hopDongEntity.getTrangThaiHopDong().equals(TrangThaiHopDongEnum.NHAP)) {
                        continue;
                    }
                    TramModel tramModelInput = hopDongImportDto.getTram();
                    TramEntity tramByMaDTXD = tramService
                            .findByMaDauTuXayDung(tramModelInput.getMaDauTuXayDung());
                    TramEntity tramByMa = tramService.findByMaTram(tramModelInput.getMaTram());
                    TramModel savedTram = null;
                    if (tramByMaDTXD == null && tramByMa == null) {
                        tramModelInput.setGhiChu("Trạm được tạo từ quá trình import hợp đồng");
                        tramModelInput.setId(null);
                        savedTram = tramService.create(tramModelInput);
                    } else {
                        Long tramId = tramByMaDTXD != null ? tramByMaDTXD.getId() : tramByMa.getId();
                        savedTram = tramService.update(tramModelInput, tramId);
                    }
                    // hop dong
                    HopDongModel hopDongModel = HopDongImportDto.convertToHopDongModelFromDto(hopDongImportDto);
                    HopDongTramModel hopDongTramModel = new HopDongTramModel();
                    hopDongTramModel.setTramId(savedTram.getId());
                    hopDongTramModel.setGiaThue(hopDongImportDto.getGiaThue());
                    hopDongTramModel.setGiaDienKhoan(hopDongImportDto.getGiaDienKhoan());
                    hopDongTramModel.setTrangThaiHoatDong(TrangThaiHopDongTramEnum.HOAT_DONG);
                    hopDongTramModel.setNgayBatDauYeuCauThanhToan(hopDongImportDto.getNgayBatDauYeuCauThanhToan());
                    hopDongTramModel.setHopDongTramDungChung(hopDongImportDto.getHopDongDungChung());
                    hopDongTramModel.setHopDongTramKyThanhToanList(hopDongImportDto.getHopDongKyThanhToanList());
                    hopDongTramModel.setHopDongTramPhuTroList(hopDongImportDto.getHopDongPhuTroList());
                    LoaiHopDongEnum loaiHopDong = hopDongImportDto.getLoaiHopDong();
                    /**
                     * * Lưu loại hợp đồng
                     */
                    if (loaiHopDong.equals(LoaiHopDongEnum.MAT_BANG)) {
                        hopDongModel.setLoaiHopDong(LoaiHopDongEnum.MAT_BANG);
                    } else if (loaiHopDong.equals(LoaiHopDongEnum.XA_HOI_HOA)) {
                        hopDongModel.setLoaiHopDong(LoaiHopDongEnum.XA_HOI_HOA);
                    } else if (loaiHopDong.equals(LoaiHopDongEnum.IBC)) {
                        hopDongModel.setLoaiHopDong(LoaiHopDongEnum.IBC);
                    }
                    if (hopDongEntity == null) {
                        // them tram vao hop dong
                        List<HopDongTramModel> tramModels = new ArrayList<>();
                        tramModels.add(hopDongTramModel);
                        hopDongModel.setHopDongTramList(tramModels);
                        create(null, hopDongModel, null, null);
                        // hopDongRepository.save(hopDongModel);
                    } else {
                        hopDongTramModel.setHopDongId(hopDongEntity.getId());
                        // get tram to update or insert
                        // hopDongRepository.findByIdWithFetch(null)
                        // save basic info
                        hopDongRepository.save(HopDongEntity.fromModel(hopDongEntity, hopDongModel));
                        // check and save info of each tram
                        HopDongEntity hopDongEntityFullFetch = hopDongRepository
                                .findByIdWithFetch(hopDongEntity.getId());
                        HopDongModel hdModelFullFetch = HopDongModel.fromEntity(hopDongEntityFullFetch, true);
                        List<HopDongTramModel> hDongTramModels = new ArrayList<>(hdModelFullFetch.getHopDongTramList());
                        if (hDongTramModels != null) {
                            Long tramId = savedTram.getId();
                            OptionalInt index = IntStream.range(0, hDongTramModels.size())
                                    .filter(_i -> hDongTramModels.get(_i).getTramId().equals(tramId))
                                    .findFirst();
                            if (index.isPresent()) {
                                hDongTramModels.set(index.getAsInt(), hopDongTramModel);
                            } else {
                                hDongTramModels.add(hopDongTramModel);
                            }

                            hopDongModel.setHopDongTramList(hDongTramModels);
                            update(hopDongEntityFullFetch.getId(), null, hopDongModel, null, null);
                        }
                    }
                    // entityManager.flush();
                    System.out.println("Import thanh cong HOPDONG: " + hopDongImportDto.getSoHopDong());
                } catch (Exception e) {
                    System.out.println("Import Error HOPDONG: " + e.getMessage());
                    System.out.println("Import Error HOPDONG: " + hopDongImportDto.getSoHopDong());
                }

            }
        } catch (Exception e) {
            System.out.println("Import Error: " + e.getMessage());
            processModel.setKetThuc(true);
            processService.update(processModel);
        }

        processModel.setKetThuc(true);
        processService.update(processModel);
    }

    public void setUserNewThread(String email, String matKhau) {
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(email,
                matKhau, new ArrayList<>());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    public HopDongEntity findById(Long id) {
        return hopDongRepository.findById(id).orElse(null);
    }

    @Transactional
    public ResponseEntity<?> deleteHopDongTypeNhapByIdHopDong(Long id) {
        HopDongEntity hopDong = hopDongRepository.findByIdWithFetch(id);
        if (hopDong != null) {
            if (hopDong.getTrangThaiHopDong() == TrangThaiHopDongEnum.NHAP) {
                try {
                    List<HopDongTramEntity> listHopDongTram = hopDongTramService.findByIdHopDong(hopDong.getId());
                    List<HopDongDoiTacEntity> listHopDongDoiTac = hopDongDoiTacService.findByIdHopDong(hopDong.getId());
                    List<HopDongFileEntity> listHopDongFiles = hopDongFileService.findFilesByIdHopDong(hopDong.getId());
                    /* kiem tra xoa cac hop dong cua hop dong tram */
                    if (listHopDongTram != null) {
                        hopDongTramService.deleteListHDTRAMById(listHopDongTram);
                    }
                    if (listHopDongDoiTac != null) {
                        hopDongDoiTacService.deleteListHDDTById(listHopDongDoiTac);
                    }
                    if (listHopDongFiles != null) {
                        hopDongFileService.deleteListHDTPTById(listHopDongFiles);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                hopDongRepository.deleteById(hopDong.getId());
                return new ResponseEntity<String>("Xoa hoan tat!", HttpStatus.OK);
            }
            return new ResponseEntity<String>("Hop dong nay khong phai hop dong NHAP!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Khong tim thay hop dong nay!", HttpStatus.BAD_REQUEST);
    }

    public QueryResultModel<HopDongEntity> getListHopDong(List<Long> hdIds, List<Integer> ids, String search,
            String soHopDong, String soHopDongErp, Integer hinhThucDauTu, Integer hinhThucKyHopDongId,
            Integer doiTuongKyHopDongId, Integer phongDaiId, TrangThaiHoatDongQueryEnum trangThaiHopDong,
            LoaiHopDongEnum loaiHopDong, String maTram, Date ngayKyFrom, Date ngayKyTo, Date ngayKetThucFrom,
            Date ngayKetThucTo, Integer idTinh, Integer idHuyen, Integer idXa, TinhTrangHopDongEnum tinhTrangHopDong,
            TinhTrangThanhToanEnum tinhTrangThanhToan, Date kyThanhToanFrom,
            Date kyThanhToanTo,
            Integer page, Integer size, Integer countOnly)
            throws ExecutionException, InterruptedException {
        NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();

        EntityGraph<HopDongEntity> entityGraph = entityManager.createEntityGraph(HopDongEntity.class);
        entityGraph.addAttributeNodes("hopDongDoiTacEntity", "hopDongTramEntities",
                "dmHinhThucKyHopDongEntity", "dmHinhThucDauTuEntity", "dmDoiTuongKyHopDongEntity",
                "dmHinhThucThanhToanEntity", "hopDongPhuLucEntities", "dmKhoanMucEntity");
        // hopDongTramEntities
        Subgraph<HopDongTramEntity> hdTramEntitySubGraph = entityGraph.addSubgraph("hopDongTramEntities",
                HopDongTramEntity.class);
        hdTramEntitySubGraph.addAttributeNodes("tramEntity", "hopDongTramDungChungEntity");
        // hopDongTrams.hopDongTramDungChungEntity
        Subgraph<HopDongTramDungChungEntity> hdTramDungChungEntitySubgraph = hdTramEntitySubGraph
                .addSubgraph("hopDongTramDungChungEntity", HopDongTramDungChungEntity.class);
        hdTramDungChungEntitySubgraph.addAttributeNodes("dmLoaiHangMucCSHTEntity");
        Subgraph<TramEntity> tramEntitySubGraph = hdTramEntitySubGraph.addSubgraph("tramEntity", TramEntity.class);
        tramEntitySubGraph.addAttributeNodes("dmPhongDaiEntity", "dmToEntity", "dmTinhEntity", "dmHuyenEntity",
                "dmXaEntity", "dmTramKhuVucEntity", "dmLoaiCshtEntity", "dmLoaiTramEntity", "dmLoaiCotAngtenEntity",
                "dmLoaiThietBiRanEntityList");

        String sqlRoot = "from HopDongEntity hopDong "
                + " left join hopDong.hopDongDoiTacEntity"
                + " left join hopDong.hopDongPhuLucEntities"
                + " left join hopDong.hopDongTramEntities hopDongTrams"
                + " left join hopDongTrams.tramEntity tram"
                + " left join tram.dmTinhEntity"
                + " left join tram.dmHuyenEntity"
                + " left join tram.dmXaEntity"
                + " left join hopDongTrams.hopDongTramDungChungEntity"
                + " left join hopDong.dmHinhThucKyHopDongEntity dmHinhThucKyHopDong"
                + " left join hopDong.dmHinhThucDauTuEntity dmHinhThucDauTu"
                + " left join hopDong.dmDoiTuongKyHopDongEntity dmDoiTuongKyHopDong";

        if (trangThaiHopDong.equals(TrangThaiHoatDongQueryEnum.CAN_PHE_DUYET)) {
            sqlRoot += " left join hopDong.hopDongPheDuyetEntities pheDuyets";
            sqlRoot += " left join pheDuyets.hopDongPheDuyetNguoiNhanEntityList pheDuyetNguoiNhans";
        } else if (trangThaiHopDong.equals(TrangThaiHoatDongQueryEnum.DA_GIAO_VIEC)
                || trangThaiHopDong.equals(TrangThaiHoatDongQueryEnum.VIEC_CAN_LAM)
                || trangThaiHopDong.equals(TrangThaiHoatDongQueryEnum.SAP_HET_HAN)) {
            sqlRoot += " left join hopDong.hopDongDamPhanEntities damPhans";
            sqlRoot += " left join damPhans.hopDongDamPhanNguoiNhanEntities damPhanNguoiNhans";
        }
        // if (!ObjectUtils.isEmpty(tinhTrangThanhToan)) {
        // sqlRoot += " left join hopDongTrams.hopDongTramKyThanhToanEntities
        // hopDongTramKyThanhToan";
        // }
        sqlRoot += " where 1=1";

        if (!ObjectUtils.isEmpty(loaiHopDong)) {
            sqlRoot += " and hopDong.loaiHopDong = :loaiHopDong ";
        }
        if (!ObjectUtils.isEmpty(tinhTrangHopDong)) {
            sqlRoot += " and hopDong.tinhTrangHopDong = :tinhTrangHopDong ";
        }
        if (hdIds != null && !ObjectUtils.isEmpty(hdIds)) {
            sqlRoot += " and hopDong.id in :hdIds";
        }

        if (!ObjectUtils.isEmpty(maTram)) {
            sqlRoot += " and tram.maTram = :maTram ";
        }
        if (!ObjectUtils.isEmpty(phongDaiId)) {
            sqlRoot += " and tram.phongDaiId = :phongDaiId";
        }
        if (!ObjectUtils.isEmpty(ids)) {
            sqlRoot += " and tram.phongDaiId in :ids";
        }
        if (!ObjectUtils.isEmpty(search)) {
            sqlRoot += " and (tram.maTram like :search or tram.maTramErp like :search or tram.maDauTuXayDung like :search or hopDong.soHopDong like :search or hopDong.soHopDongErp like :search) ";
        }
        /* tinh huyen xa */
        if (!ObjectUtils.isEmpty(idTinh)) {
            sqlRoot += " and tram.tinhId =:idTinh";
        }
        if (!ObjectUtils.isEmpty(idHuyen)) {
            sqlRoot += " and tram.huyenId =:idHuyen";
        }
        if (!ObjectUtils.isEmpty(idXa)) {
            sqlRoot += " and tram.xaId =:idXa";
        }
        // if (!ObjectUtils.isEmpty(soHopDong)) {
        // sqlRoot += " and hopDong.soHopDong like %:soHopDong% ";
        // }
        // if (!ObjectUtils.isEmpty(soHopDongErp)) {
        // sqlRoot += " and hopDong.soHopDongErp like %:soHopDongErp% ";
        // }
        if (!ObjectUtils.isEmpty(hinhThucDauTu)) {
            sqlRoot += " and dmHinhThucDauTu.id = :hinhThucDauTu ";
        }
        if (!ObjectUtils.isEmpty(hinhThucKyHopDongId)) {
            sqlRoot += " and dmHinhThucKyHopDong.id = :hinhThucKyHopDongId ";
        }
        if (!ObjectUtils.isEmpty(doiTuongKyHopDongId)) {
            sqlRoot += " and dmDoiTuongKyHopDong.id = :doiTuongKyHopDongId ";
        }
        if (!ObjectUtils.isEmpty(trangThaiHopDong)) {
            if (trangThaiHopDong.equals(TrangThaiHoatDongQueryEnum.HOAT_DONG)) {
                // CHO_PHE_DUYET_PHU_LUC, HOAT_DONG
                sqlRoot += " and hopDong.trangThaiHopDong in (2, 3)";
            } else if (trangThaiHopDong.equals(TrangThaiHoatDongQueryEnum.NHAP)) {
                // NHAP
                sqlRoot += " and hopDong.trangThaiHopDong = 0";
            } else if (trangThaiHopDong.equals(TrangThaiHoatDongQueryEnum.CHO_PHE_DUYET)) {
                // CHO_PHE_DUYET_HOP_DONG, CHO_PHE_DUYET_PHU_LUC
                sqlRoot += " and hopDong.trangThaiHopDong in (1, 2)";
                entityGraph.addAttributeNodes("hopDongPheDuyetEntities");
                Subgraph<HopDongPheDuyetEntity> hdPheDuyetEntitySubGraph = entityGraph.addSubgraph(
                        "hopDongPheDuyetEntities",
                        HopDongPheDuyetEntity.class);
                hdPheDuyetEntitySubGraph.addAttributeNodes("nguoiGuiEntity");
            } else if (trangThaiHopDong.equals(TrangThaiHoatDongQueryEnum.CAN_PHE_DUYET)) {
                sqlRoot += " and hopDong.trangThaiHopDong in (1, 2)";
                entityGraph.addAttributeNodes("hopDongPheDuyetEntities");
                Subgraph<HopDongPheDuyetEntity> hdPheDuyetEntitySubGraph = entityGraph.addSubgraph(
                        "hopDongPheDuyetEntities",
                        HopDongPheDuyetEntity.class);
                hdPheDuyetEntitySubGraph.addAttributeNodes("nguoiGuiEntity", "hopDongPheDuyetNguoiNhanEntityList");
                sqlRoot += " and pheDuyetNguoiNhans.nguoiDungId =: nguoiPheDuyetId";
                sqlRoot += " and pheDuyets.trangThaiPheDuyetMoiNhat != " + TrangThaiPheDuyetEnum.PHE_DUYET.ordinal();
            } else if (trangThaiHopDong.equals(TrangThaiHoatDongQueryEnum.SAP_HET_HAN)) {
                // CHO_PHE_DUYET_PHU_LUC, HOAT_DONG
                sqlRoot += " and hopDong.trangThaiHopDong in (2, 3) and hopDong.ngayKetThuc <= :thoiGianThongBaoSapHetHan";
                entityGraph.addAttributeNodes("hopDongDamPhanEntities");
                Subgraph<HopDongDamPhanEntity> hdDamPhanSubgraph = entityGraph.addSubgraph("hopDongDamPhanEntities",
                        HopDongDamPhanEntity.class);

                hdDamPhanSubgraph.addAttributeNodes("nguoiGuiEntity", "hopDongDamPhanNguoiNhanEntities");
            } else if (trangThaiHopDong.equals(TrangThaiHoatDongQueryEnum.DA_GIAO_VIEC)
                    || (trangThaiHopDong.equals(TrangThaiHoatDongQueryEnum.VIEC_CAN_LAM))) {
                // CHO_PHE_DUYET_PHU_LUC, HOAT_DONG
                sqlRoot += " and hopDong.trangThaiHopDong in (2, 3)";
                entityGraph.addAttributeNodes("hopDongDamPhanEntities");
                Subgraph<HopDongDamPhanEntity> hdDamPhanSubgraph = entityGraph.addSubgraph("hopDongDamPhanEntities",
                        HopDongDamPhanEntity.class);

                hdDamPhanSubgraph.addAttributeNodes("nguoiGuiEntity", "hopDongDamPhanNguoiNhanEntities");
                // 0: GUI_PHE_DUYET, 1: TU_CHOI
                sqlRoot += " and damPhans.trangThaiDamPhanMoiNhat is null";
                if (trangThaiHopDong.equals(TrangThaiHoatDongQueryEnum.VIEC_CAN_LAM)) {
                    sqlRoot += " and damPhanNguoiNhans.nguoiDungId =: nguoiDamPhanId";
                }
            }
        }
        if (!ObjectUtils.isEmpty(ngayKyFrom) && !ObjectUtils.isEmpty(ngayKyTo)) {
            sqlRoot += " and hopDong.ngayKy >= :ngayKyFrom and hopDong.ngayKy <= :ngayKyTo";
        }
        if (!ObjectUtils.isEmpty(ngayKetThucFrom) && !ObjectUtils.isEmpty(ngayKetThucTo)) {
            sqlRoot += " and hopDong.ngayKetThuc >= :ngayKetThucFrom and hopDong.ngayKetThuc <=:ngayKetThucTo ";
        }

        boolean needCheckKyThanhToanOnly = true;
        if (!ObjectUtils.isEmpty(tinhTrangThanhToan)) {
            needCheckKyThanhToanOnly = false;
            if (TinhTrangThanhToanEnum.CAN_THANH_TOAN.equals(tinhTrangThanhToan)) {
                sqlRoot += " and exists ("
                        + " select 1 from HopDongTramKyThanhToanEntity s"
                        + " where s.hopDongTramEntity = hopDongTrams and s.daThanhToanNgay is null"
                        + " and extract(month from s.tuNgay) <= :monthCanThanhToan"
                        + " and extract(year from s.tuNgay) <= :yearCanThanhToan";
                if (!ObjectUtils.isEmpty(kyThanhToanFrom) && !ObjectUtils.isEmpty(kyThanhToanTo)) {
                    sqlRoot += " and s.tuNgay <= :kyThanhToanTo and s.tuNgay >= :kyThanhToanFrom";
                } else if (!ObjectUtils.isEmpty(kyThanhToanTo)) {
                    sqlRoot += " and s.tuNgay <= :kyThanhToanTo";
                } else if (!ObjectUtils.isEmpty(kyThanhToanFrom)) {
                    sqlRoot += " and s.denNgay >= :kyThanhToanFrom";
                }
                sqlRoot += " )";

            } else if (TinhTrangThanhToanEnum.QUA_HAN.equals(tinhTrangThanhToan)) {
                sqlRoot += " and exists ("
                        + " select 1 from HopDongTramKyThanhToanEntity s where s.hopDongTramEntity = hopDongTrams and s.daThanhToanNgay is null and s.denNgay < :ngayHienTai";
                if (!ObjectUtils.isEmpty(kyThanhToanFrom) && !ObjectUtils.isEmpty(kyThanhToanTo)) {
                    sqlRoot += " and s.tuNgay <= :kyThanhToanTo and s.tuNgay >= :kyThanhToanFrom";
                } else if (!ObjectUtils.isEmpty(kyThanhToanTo)) {
                    sqlRoot += " and s.tuNgay <= :kyThanhToanTo";
                } else if (!ObjectUtils.isEmpty(kyThanhToanFrom)) {
                    sqlRoot += " and s.denNgay >= :kyThanhToanFrom";
                }
                sqlRoot += ")";
            }
        }
        if (needCheckKyThanhToanOnly
                && (!ObjectUtils.isEmpty(kyThanhToanTo) || !ObjectUtils.isEmpty(kyThanhToanFrom))) {
            sqlRoot += " and exists ("
                    + " select 1 from HopDongTramKyThanhToanEntity s where s.hopDongTramEntity = hopDongTrams";
            if (!ObjectUtils.isEmpty(kyThanhToanTo)) {
                sqlRoot += " and s.tuNgay <= :kyThanhToanTo";
            }
            if (!ObjectUtils.isEmpty(kyThanhToanFrom)) {
                sqlRoot += " and s.denNgay >= :kyThanhToanFrom";
            }
            sqlRoot += ")";
        }

        String orderSql = " order by hopDong.createdAt";

        String sqlQuery = "select distinct hopDong " + sqlRoot + orderSql;
        String sqlCount = "select count(distinct hopDong.id) " + sqlRoot + orderSql;
        int firstResult = page * size;

        TypedQuery<HopDongEntity> queryList = entityManager.createQuery(sqlQuery, HopDongEntity.class);
        queryList.setHint(QueryHints.HINT_FETCHGRAPH, entityGraph);
        queryList.setHint(QueryHints.HINT_FETCH_SIZE, 10000);
        queryList.setHint(QueryHints.HINT_READONLY, true);
        queryList.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false);
        queryList.setFirstResult(firstResult).setMaxResults(size);

        // if (!"CHUA_THANH_TOAN".equals(trangThaiThanhToan)) {

        // }

        TypedQuery<Long> queryCount = entityManager.createQuery(sqlCount, Long.class);
        queryCount.setHint(QueryHints.HINT_READONLY, true);
        if (!ObjectUtils.isEmpty(loaiHopDong)) {
            queryList.setParameter("loaiHopDong", loaiHopDong);
            queryCount.setParameter("loaiHopDong", loaiHopDong);
        }
        if (!ObjectUtils.isEmpty(tinhTrangHopDong)) {
            queryList.setParameter("tinhTrangHopDong", tinhTrangHopDong);
            queryCount.setParameter("tinhTrangHopDong", tinhTrangHopDong);
        }
        if (hdIds != null && !ObjectUtils.isEmpty(hdIds)) {
            queryList.setParameter("hdIds", hdIds);
            queryCount.setParameter("hdIds", hdIds);
        }
        if (!ObjectUtils.isEmpty(maTram)) {
            queryList.setParameter("maTram", maTram);
            queryCount.setParameter("maTram", maTram);
        }
        if (!ObjectUtils.isEmpty(phongDaiId)) {
            queryList.setParameter("phongDaiId", phongDaiId);
            queryCount.setParameter("phongDaiId", phongDaiId);
        }
        if (!ObjectUtils.isEmpty(ids)) {
            queryList.setParameter("ids", ids);
            queryCount.setParameter("ids", ids);
        }
        if (!ObjectUtils.isEmpty(search)) {
            queryList.setParameter("search", "%" + search + "%");
            queryCount.setParameter("search", "%" + search + "%");
        }
        // if (!ObjectUtils.isEmpty(soHopDong)) {
        // queryList.setParameter("soHopDong", soHopDong);
        // queryCount.setParameter("soHopDong", soHopDong);
        // }
        // if (!ObjectUtils.isEmpty(soHopDongErp)) {
        // queryList.setParameter("soHopDongErp", soHopDongErp);
        // queryCount.setParameter("soHopDongErp", soHopDongErp);
        // }
        if (!ObjectUtils.isEmpty(hinhThucDauTu)) {
            queryList.setParameter("hinhThucDauTu", hinhThucDauTu);
            queryCount.setParameter("hinhThucDauTu", hinhThucDauTu);
        }
        if (!ObjectUtils.isEmpty(hinhThucKyHopDongId)) {
            queryList.setParameter("hinhThucKyHopDongId", hinhThucKyHopDongId);
            queryCount.setParameter("hinhThucKyHopDongId", hinhThucKyHopDongId);
        }
        if (!ObjectUtils.isEmpty(doiTuongKyHopDongId)) {
            queryList.setParameter("doiTuongKyHopDongId", doiTuongKyHopDongId);
            queryCount.setParameter("doiTuongKyHopDongId", doiTuongKyHopDongId);
        }
        if (trangThaiHopDong.equals(TrangThaiHoatDongQueryEnum.CAN_PHE_DUYET)) {
            queryList.setParameter("nguoiPheDuyetId", nguoiDungEntity.getId());
            queryCount.setParameter("nguoiPheDuyetId", nguoiDungEntity.getId());
        } else if (trangThaiHopDong.equals(TrangThaiHoatDongQueryEnum.SAP_HET_HAN)) {
            LocalDate currentDate = LocalDate.now();
            LocalDate sixMonthsFuture = currentDate.plusDays(6 * 30);
            Date sixMonthsFutureAsDate = Date.from(sixMonthsFuture.atStartOfDay(ZoneId.systemDefault()).toInstant());
            queryList.setParameter("thoiGianThongBaoSapHetHan", sixMonthsFutureAsDate);
            queryCount.setParameter("thoiGianThongBaoSapHetHan", sixMonthsFutureAsDate);
        } else if (trangThaiHopDong.equals(TrangThaiHoatDongQueryEnum.VIEC_CAN_LAM)) {
            queryList.setParameter("nguoiDamPhanId", nguoiDungEntity.getId());
            queryCount.setParameter("nguoiDamPhanId", nguoiDungEntity.getId());
        }
        // if (isCanPheDuyet) {
        // queryList.setParameter("nguoiPheDuyetId", nguoiDungEntity.getId());
        // queryCount.setParameter("nguoiPheDuyetId", nguoiDungEntity.getId());
        // } else if (!ObjectUtils.isEmpty(trangThaiHopDong)) {
        // queryList.setParameter("trangThaiHopDong", trangThaiHopDong);
        // queryCount.setParameter("trangThaiHopDong", trangThaiHopDong);
        // }
        if (!ObjectUtils.isEmpty(ngayKyFrom) && !ObjectUtils.isEmpty(ngayKyTo)) {
            queryList.setParameter("ngayKyFrom", ngayKyFrom);
            queryCount.setParameter("ngayKyFrom", ngayKyFrom);

            queryList.setParameter("ngayKyTo", ngayKyTo);
            queryCount.setParameter("ngayKyTo", ngayKyTo);
        }
        if (!ObjectUtils.isEmpty(ngayKetThucFrom) && !ObjectUtils.isEmpty(ngayKetThucTo)) {
            queryList.setParameter("ngayKetThucFrom", ngayKetThucFrom);
            queryCount.setParameter("ngayKetThucFrom", ngayKetThucFrom);

            queryList.setParameter("ngayKetThucTo", ngayKetThucTo);
            queryCount.setParameter("ngayKetThucTo", ngayKetThucTo);
        }
        /* tinh huyen xa */
        if (!ObjectUtils.isEmpty(idTinh)) {
            queryList.setParameter("idTinh", idTinh);
            queryCount.setParameter("idTinh", idTinh);
        }
        if (!ObjectUtils.isEmpty(idHuyen)) {
            queryList.setParameter("idHuyen", idHuyen);
            queryCount.setParameter("idHuyen", idHuyen);
        }
        if (!ObjectUtils.isEmpty(idXa)) {
            queryList.setParameter("idXa", idXa);
            queryCount.setParameter("idXa", idXa);
        }

        // if (!ObjectUtils.isEmpty(trangThaiThanhToan)) {
        // queryList.setParameter("trangThaiThanhToan", trangThaiThanhToan);
        // queryCount.setParameter("trangThaiThanhToan", trangThaiThanhToan);
        // }
        if (!ObjectUtils.isEmpty(tinhTrangThanhToan)) {
            Date ngayHienTai = new Date();
            if (!ObjectUtils.isEmpty(kyThanhToanFrom) && !ObjectUtils.isEmpty(kyThanhToanTo)) {
                queryList.setParameter("kyThanhToanTo", kyThanhToanTo);
                queryCount.setParameter("kyThanhToanTo", kyThanhToanTo);
                queryList.setParameter("kyThanhToanFrom", kyThanhToanFrom);
                queryCount.setParameter("kyThanhToanFrom", kyThanhToanFrom);
            } else if (!ObjectUtils.isEmpty(kyThanhToanTo)) {
                queryList.setParameter("kyThanhToanTo", kyThanhToanTo);
                queryCount.setParameter("kyThanhToanTo", kyThanhToanTo);
            } else if (!ObjectUtils.isEmpty(kyThanhToanFrom)) {
                queryList.setParameter("kyThanhToanFrom", kyThanhToanFrom);
                queryCount.setParameter("kyThanhToanFrom", kyThanhToanFrom);
            }

            if (TinhTrangThanhToanEnum.CAN_THANH_TOAN.equals(tinhTrangThanhToan)) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.DATE, 15);
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR);
                queryList.setParameter("monthCanThanhToan", month + 1);
                queryCount.setParameter("monthCanThanhToan", month + 1);
                queryList.setParameter("yearCanThanhToan", year);
                queryCount.setParameter("yearCanThanhToan", year);
            }
            if (TinhTrangThanhToanEnum.QUA_HAN.equals(tinhTrangThanhToan)) {
                queryList.setParameter("ngayHienTai", ngayHienTai);
                queryCount.setParameter("ngayHienTai", ngayHienTai);
            }
            // queryList.setParameter("ngayHienTai", ngayHienTai);
            // queryCount.setParameter("ngayHienTai", ngayHienTai);

            // queryList.setParameter("ngayTuongLai", ngayTuongLai);
            // queryCount.setParameter("ngayTuongLai", ngayTuongLai);
        }
        if (needCheckKyThanhToanOnly
                && (!ObjectUtils.isEmpty(kyThanhToanTo) || !ObjectUtils.isEmpty(kyThanhToanFrom))) {
            if (!ObjectUtils.isEmpty(kyThanhToanTo)) {
                queryList.setParameter("kyThanhToanTo", kyThanhToanTo);
                queryCount.setParameter("kyThanhToanTo", kyThanhToanTo);
            }
            if (!ObjectUtils.isEmpty(kyThanhToanFrom)) {
                queryList.setParameter("kyThanhToanFrom", kyThanhToanFrom);
                queryCount.setParameter("kyThanhToanFrom", kyThanhToanFrom);
            }
        }

        boolean isCountOnly = countOnly == 1 ? true : false;

        // This fetch improvement for query join fetch
        List<HopDongEntity> hopDongList = new ArrayList<HopDongEntity>();
        if (!isCountOnly) {
            hopDongList = queryList.getResultList();
            // get ky thanh toan
            hopDongList.forEach(_hopDongEntity -> {
                _hopDongEntity.getHopDongTramEntities().forEach(_hdTramEntity -> {
                    Set<HopDongTramKyThanhToanEntity> kyThanhToans = hopDongTramKyThanhToanRepository
                            .findAllByHopDongTramId(_hdTramEntity.getId());
                    _hdTramEntity.setHopDongTramKyThanhToanEntities(kyThanhToans);
                });
            });
        }

        QueryResultModel<HopDongEntity> resultModel = new QueryResultModel<>();
        resultModel.setPage(page);
        resultModel.setSize(size);
        resultModel.setContent(hopDongList);
        resultModel.setTotalElements(queryCount.getSingleResult());
        return resultModel;
    }

    public void thanhToanHopDong(List<ThanhToanHopDongRequest> listIdHopDong) {
        if (listIdHopDong == null) {
            throw new BadRequestException();
        }
        for (ThanhToanHopDongRequest map : listIdHopDong) {
            Long hopDongTramId = map.getHopHongTramId();
            Date ngayThanhToan = map.getNgayThanhToan();

            Set<HopDongTramKyThanhToanEntity> hopDongTramKyThanhToanEntities = hopDongTramKyThanhToanRepository
                    .findAllByHopDongTramId(hopDongTramId);
            List<HopDongTramKyThanhToanEntity> listHopDongTramKyThanhToanEntities = new ArrayList<>(
                    hopDongTramKyThanhToanEntities);
            HopDongTramKyThanhToanEntity kyThanhToan = listHopDongTramKyThanhToanEntities.stream()
                    .filter(_kyThanhToanEntity -> {
                        return _kyThanhToanEntity.getDaThanhToanNgay() == null;
                    }).findFirst().orElse(null);

            if (kyThanhToan != null) {
                kyThanhToan.setDaThanhToanNgay(ngayThanhToan);
                kyThanhToan.setThanhToanBy(nguoiDungService.getNguoiDung().getId());
                hopDongTramKyThanhToanRepository.save(kyThanhToan);
            }
        }
    }

    private boolean checkExists(Long id, String soHopDongErp) {
        List<HopDongEntity> hopDongEntityList = this.hopDongRepository.findByIdAndSoHdErp(id, soHopDongErp);
        if (ObjectUtils.isEmpty(hopDongEntityList)) {
            return false;
        }
        return true;
    }
}