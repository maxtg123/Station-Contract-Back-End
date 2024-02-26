package com.contract.hopdong.hopdongdamphan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.contract.common.enums.ErrorCodeEnum;
import com.contract.common.exception.InvalidDataException;
import com.contract.common.exception.NotFoundException;
import com.contract.common.exception.UnauthorizedException;
import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.hopdong.hopdong.enums.TrangThaiHopDongEnum;
import com.contract.hopdong.hopdong.model.HopDongModel;
import com.contract.hopdong.hopdong.repository.HopDongRepository;
import com.contract.hopdong.hopdong.service.HopDongService;
import com.contract.hopdong.hopdongdamphan.entity.HopDongDamPhanEntity;
import com.contract.hopdong.hopdongdamphan.model.DamPhanTienTrinhCreateDto;
import com.contract.hopdong.hopdongdamphan.model.HopDongDamPhanCreateDto;
import com.contract.hopdong.hopdongdamphan.model.HopDongDamPhanModel;
import com.contract.hopdong.hopdongdamphan.model.XetDuyetDamPhanTienTrinhDto;
import com.contract.hopdong.hopdongdamphan.repository.HopDongDamPhanRepository;
import com.contract.hopdong.hopdongdamphan_file.service.HopDongDamPhanFileService;
import com.contract.hopdong.hopdongdamphan_nguoinhan.entity.HopDongDamPhanNguoiNhanEntity;
import com.contract.hopdong.hopdongdamphan_nguoinhan.service.HopDongDamPhanNguoiNhanService;
import com.contract.hopdong.hopdongdamphan_tientrinh.entity.HopDongDamPhanTienTrinhEntity;
import com.contract.hopdong.hopdongdamphan_tientrinh.enums.TrangThaiDamPhanEnum;
import com.contract.hopdong.hopdongdamphan_tientrinh.model.HopDongDamPhanTienTrinhModel;
import com.contract.hopdong.hopdongdamphan_tientrinh.repository.HopDongDamPhanTienTrinhRepository;
import com.contract.hopdong.hopdongdamphan_tientrinh_change.entity.HopDongDamPhanTienTrinhChangeEntity;
import com.contract.hopdong.hopdongdamphan_tientrinh_change.model.HopDongDamPhanTienTrinhChangeModel;
import com.contract.hopdong.hopdongdamphan_tientrinh_change.repository.HopDongDamPhanTienTrinhChangeRepository;
import com.contract.hopdong.hopdongdamphan_tientrinh_xetduyet.entity.HopDongDamPhanTienTrinhXetDuyetEntity;
import com.contract.hopdong.hopdongdamphan_tientrinh_xetduyet.model.HopDongDamPhanTienTrinhXetDuyetModel;
import com.contract.hopdong.hopdongdamphan_tientrinh_xetduyet.repository.HopDongDamPhanTienTrinhXetDuyetRepository;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.thongbao.service.ThongBaoService;

@Service
public class HopDongDamPhanService {
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private HopDongRepository hopDongRepository;
    @Autowired
    private HopDongService hopDongService;
    @Autowired
    private HopDongDamPhanRepository hopDongDamPhanRepository;
    @Autowired
    private ThongBaoService thongBaoService;
    @Autowired
    private HopDongDamPhanNguoiNhanService hopDongDamPhanNguoiNhanService;
    @Autowired
    private HopDongDamPhanTienTrinhRepository hopDongDamPhanTienTrinhRepository;
    @Autowired
    private HopDongDamPhanTienTrinhChangeRepository hopDongDamPhanTienTrinhChangeRepository;
    @Autowired
    private HopDongDamPhanFileService hopDongDamPhanFileService;
    @Autowired
    private HopDongDamPhanTienTrinhXetDuyetRepository xetDuyetRepository;

    @Transactional
    public List<HopDongDamPhanModel> create(HopDongDamPhanCreateDto hopDongDamPhanCreateDto) throws Exception {
        if (ObjectUtils.isEmpty(hopDongDamPhanCreateDto)) {
            throw new InvalidDataException();
        }

        List<Long> hopDongIdList = hopDongDamPhanCreateDto.getHopDongIdList();
        // TODO check permission of nguoi nhan viec dam phan
        List<Long> nguoiDamPhanIdList = hopDongDamPhanCreateDto.getNguoiDamPhanIdList();
        String ghiChu = hopDongDamPhanCreateDto.getGhiChu();
        if (ObjectUtils.isEmpty(hopDongIdList) ||
                ObjectUtils.isEmpty(nguoiDamPhanIdList)) {
            throw new InvalidDataException();
        }
        List<HopDongEntity> hopDongEntityList = hopDongService.findHopDongByListId(hopDongIdList);
        List<NguoiDungEntity> nguoiDamPhanList = nguoiDungService.findNguoiDungByListId(nguoiDamPhanIdList);
        if (ObjectUtils.isEmpty(hopDongEntityList) ||
                ObjectUtils.isEmpty(nguoiDamPhanList)) {
            throw new InvalidDataException();
        }
        hopDongEntityList.forEach(_hopDongEntity -> {
            if (_hopDongEntity.getHopDongDamPhanEntities() != null && _hopDongEntity.getHopDongDamPhanEntities().size() > 0) {
                HopDongDamPhanEntity damPhanEntity = _hopDongEntity.getHopDongDamPhanEntities().stream()
                        .filter(_damPhanEntity -> _damPhanEntity.getTrangThaiDamPhanMoiNhat() == null
                                || _damPhanEntity.getTrangThaiDamPhanMoiNhat().equals(TrangThaiDamPhanEnum.GUI_NOI_DUNG_DAM_PHAN)
                                || _damPhanEntity.getTrangThaiDamPhanMoiNhat().equals(TrangThaiDamPhanEnum.TU_CHOI))
                        .findFirst().orElse(null);
                if (damPhanEntity != null) {
                    throw new InvalidDataException(ErrorCodeEnum.HOP_DONG_DA_GIAO_DAM_PHAN,
                            "Hợp đồng này đang được giao việc đàm phán");
                }
            }
        });
        NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();
        if (nguoiDungEntity == null) {
            throw new UnauthorizedException();
        }
        List<HopDongDamPhanModel> listReturn = new ArrayList<>();

        for (int i = 0; i < hopDongEntityList.size(); i++) {
            HopDongEntity hopDongEntity = hopDongEntityList.get(i);
            if (ObjectUtils.isEmpty(hopDongEntity)) {
                continue;
            }
            if (TrangThaiHopDongEnum.NHAP.equals(hopDongEntity.getTrangThaiHopDong())) {
                continue;
            }
            // hop dong dam phan
            HopDongDamPhanModel model = new HopDongDamPhanModel();
            model.setHopDongId(hopDongEntity.getId());
            model.setGhiChu(ghiChu);
            model.setNguoiGuiId(nguoiDungEntity.getId());
            model.setMucDoUuTien(hopDongDamPhanCreateDto.getMucDoUuTien());
            model.setFromDate(hopDongDamPhanCreateDto.getFromDate());
            model.setToDate(hopDongDamPhanCreateDto.getToDate());
            HopDongDamPhanEntity savedHopDongDamPhan = hopDongDamPhanRepository
                    .save(HopDongDamPhanEntity.fromModel(null, model));
            listReturn.add(savedHopDongDamPhan);
            // hop dong dam phan nguoi nhan
            List<HopDongDamPhanNguoiNhanEntity> nguoiNhanWillSaveList = new ArrayList<>();
            nguoiDamPhanList.forEach(_nguoiDung -> {
                HopDongDamPhanNguoiNhanEntity hopDongDamPhanNguoiNhanEntity = new HopDongDamPhanNguoiNhanEntity();
                hopDongDamPhanNguoiNhanEntity.setNguoiDungId(_nguoiDung.getId());
                hopDongDamPhanNguoiNhanEntity.setHopDongDamPhanId(savedHopDongDamPhan.getId());
                nguoiNhanWillSaveList.add(hopDongDamPhanNguoiNhanEntity);
                // thong bao
                thongBaoService.createThongBaoHopDongDamPhan(HopDongModel.fromEntity(hopDongEntity,
                        false),
                        null,
                        _nguoiDung.getId(), nguoiDungEntity.getId());
            });
            hopDongDamPhanNguoiNhanService.saveAll(nguoiNhanWillSaveList);
        }

        return listReturn;
    }

    public List<HopDongDamPhanEntity> getAllByHopDong(Long hopDongId) {
        List<HopDongDamPhanEntity> result = hopDongDamPhanRepository.findAllByHopDongId(hopDongId);
        return result;
    }

    @Transactional
    public HopDongDamPhanModel createNoiDungDamPhan(Long hopDongId, Long hopDongDamPhanId,
                                                    DamPhanTienTrinhCreateDto damPhanTienTrinhCreateDto, MultipartFile[] files) throws Exception {

        NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();
        if (nguoiDungEntity == null) {
            throw new UnauthorizedException();
        }
        HopDongDamPhanEntity hopDongDamPhanEntity = hopDongDamPhanRepository.findByIdAndHopDongId(hopDongDamPhanId,
                hopDongId);

        if (hopDongDamPhanEntity == null) {
            throw new NotFoundException("Hợp đồng không tìm thấy");
        }
        // Chi có đàm phán mới được giao việc (trang thai = null) hoặc bị từ chối mới có
        // thể createNoiDungDamPhan
        if (hopDongDamPhanEntity.getTrangThaiDamPhanMoiNhat() == null
                || TrangThaiDamPhanEnum.TU_CHOI.equals(hopDongDamPhanEntity.getTrangThaiDamPhanMoiNhat())) {
            // Tao tien trinh
            HopDongDamPhanTienTrinhModel tienTrinhModel = new HopDongDamPhanTienTrinhModel();
            tienTrinhModel.setHopDongDamPhanId(hopDongDamPhanId);
            tienTrinhModel.setGhiChu(damPhanTienTrinhCreateDto.getGhiChu());
            tienTrinhModel.setTrangThai(TrangThaiDamPhanEnum.GUI_NOI_DUNG_DAM_PHAN);
            tienTrinhModel.setNguoiDungId(nguoiDungEntity.getId());
            HopDongDamPhanTienTrinhEntity savedTienTrinh = hopDongDamPhanTienTrinhRepository
                    .save(HopDongDamPhanTienTrinhEntity.fromModel(null, tienTrinhModel));
            // update dam phan
            hopDongDamPhanEntity.setTrangThaiDamPhanMoiNhat(TrangThaiDamPhanEnum.GUI_NOI_DUNG_DAM_PHAN);
            hopDongDamPhanRepository.save(hopDongDamPhanEntity);
            // save changes
            if (damPhanTienTrinhCreateDto.getNoiDungThayDoiDamPhanDtos() != null
                    && damPhanTienTrinhCreateDto.getNoiDungThayDoiDamPhanDtos().size() > 0) {
                List<HopDongDamPhanTienTrinhChangeEntity> willSaveChangesList = damPhanTienTrinhCreateDto
                        .getNoiDungThayDoiDamPhanDtos().stream()
                        .map(_noiDungDto -> {
                            HopDongDamPhanTienTrinhChangeModel _model = new HopDongDamPhanTienTrinhChangeModel();
                            _model.setHopDongDamPhanTienTrinhId(savedTienTrinh.getId());
                            _model.setKey(_noiDungDto.getKey());
                            _model.setValue(_noiDungDto.getValue());
                            _model.setTramId(_noiDungDto.getTramId());
                            _model.setGhiChu(_noiDungDto.getGhiChu());
                            return HopDongDamPhanTienTrinhChangeEntity.fromModel(null, _model);
                        }).collect(Collectors.toList());
                hopDongDamPhanTienTrinhChangeRepository.saveAll(willSaveChangesList);
            }
            // Handle Files
            hopDongDamPhanFileService.addFiles(files, hopDongId, hopDongDamPhanId, savedTienTrinh.getId());
            // thong bao
            HopDongEntity hopDongEntity = hopDongRepository.findById(hopDongId).orElse(null);
            thongBaoService.createThongBaoHopDongDamPhan(HopDongModel.fromEntity(hopDongEntity,
                    false),
                    TrangThaiDamPhanEnum.GUI_NOI_DUNG_DAM_PHAN,
                    hopDongDamPhanEntity.getNguoiGuiId(), nguoiDungEntity.getId());

            return HopDongDamPhanModel.fromEntity(hopDongDamPhanEntity, false);
        } else {
            throw new InvalidDataException();
        }
    }

    @Transactional
    public HopDongDamPhanModel xetDuyetTienTrinhDamPhan(Long hopDongId, Long hopDongDamPhanId, Long tienTrinhId,
                                                        XetDuyetDamPhanTienTrinhDto xetDuyetDamPhanTienTrinhDto) {

        NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();
        if (nguoiDungEntity == null) {
            throw new UnauthorizedException();
        }
        HopDongDamPhanEntity hopDongDamPhanEntity = hopDongDamPhanRepository.findByIdAndHopDongId(hopDongDamPhanId,
                hopDongId);

        if (hopDongDamPhanEntity == null) {
            throw new NotFoundException("Hợp đồng không tìm thấy");
        }

        // Chi có đàm phán có trạng thái đã gửi đàm phán mới có thể xét duyệt
        if (hopDongDamPhanEntity.getTrangThaiDamPhanMoiNhat() == null
                || !hopDongDamPhanEntity.getTrangThaiDamPhanMoiNhat().equals(TrangThaiDamPhanEnum.GUI_NOI_DUNG_DAM_PHAN)) {
            throw new InvalidDataException();
        }
        HopDongDamPhanTienTrinhEntity tienTrinhEntity = hopDongDamPhanTienTrinhRepository.findById(tienTrinhId)
                .orElse(null);
        if (tienTrinhEntity == null) {
            throw new NotFoundException("Hợp đồng tiến trình không tìm thấy");
        }
        // Tao tien trinh xet duyet
        TrangThaiDamPhanEnum newTrangThai = xetDuyetDamPhanTienTrinhDto.getAction().equals("tu_choi")
                ? TrangThaiDamPhanEnum.TU_CHOI
                : TrangThaiDamPhanEnum.PHE_DUYET;
        HopDongDamPhanTienTrinhXetDuyetModel xetDuyetModel = new HopDongDamPhanTienTrinhXetDuyetModel();
        xetDuyetModel.setHopDongDamPhanTienTrinhId(tienTrinhEntity.getId());
        xetDuyetModel.setGhiChu(xetDuyetDamPhanTienTrinhDto.getGhiChu());
        xetDuyetModel.setTrangThai(newTrangThai);
        xetDuyetModel.setNguoiDungId(nguoiDungEntity.getId());
        xetDuyetRepository.save(HopDongDamPhanTienTrinhXetDuyetEntity.fromModel(null, xetDuyetModel));
        // update tien trinh
        tienTrinhEntity.setTrangThai(newTrangThai);
        hopDongDamPhanTienTrinhRepository.save(tienTrinhEntity);
        // update te dam phan
        hopDongDamPhanEntity.setTrangThaiDamPhanMoiNhat(newTrangThai);
        hopDongDamPhanRepository.save(hopDongDamPhanEntity);
        // update dam phan
        hopDongDamPhanEntity.setTrangThaiDamPhanMoiNhat(newTrangThai);
        hopDongDamPhanRepository.save(hopDongDamPhanEntity);
        // thong bao
        HopDongEntity hopDongEntity = hopDongRepository.findById(hopDongId).orElse(null);
        List<HopDongDamPhanNguoiNhanEntity> nguoiNhanEntities = hopDongDamPhanNguoiNhanService
                .findAllByHopDongDamPhanId(hopDongDamPhanId);
        if (nguoiNhanEntities != null && nguoiNhanEntities.size() > 0) {
            nguoiNhanEntities.forEach(_entity -> {
                thongBaoService.createThongBaoHopDongDamPhan(HopDongModel.fromEntity(hopDongEntity,
                        false),
                        newTrangThai,
                        _entity.getNguoiDungId(), nguoiDungEntity.getId());
            });
        }
        return HopDongDamPhanModel.fromEntity(hopDongDamPhanEntity, false);
    }
}
