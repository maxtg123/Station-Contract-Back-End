package com.contract.hopdong.hopdongpheduyet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

// import java.sql.Timestamp;
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import org.apache.commons.lang3.ObjectUtils;
// import org.json.JSONObject;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.contract.authentication.component.KiemTraQuyenModuleHopDong;
// import com.contract.base.service.BaseService;
// import com.contract.common.exception.InvalidDataException;
// import com.contract.common.exception.NotFoundException;
// import com.contract.common.exception.UnauthorizedException;
// import com.contract.hopdong.hopdongnhatram.entity.HopDongEntity;
// import com.contract.hopdong.hopdongnhatram.model.TrangThaiHopDong;
// import com.contract.hopdong.hopdongnhatram.service.HopDongService;
// import com.contract.hopdong.hopdongpheduyet.dto.PheDuyetInputDto;
// import com.contract.hopdong.hopdongpheduyet.entity.HopDongPheDuyetEntity;
// import com.contract.hopdong.hopdongpheduyet.model.HopDongPheDuyetCreateModel;
// import com.contract.hopdong.hopdongpheduyet.model.HopDongPheDuyetModel;
// import com.contract.hopdong.hopdongpheduyet.model.TrangThaiPheDuyet;
// import com.contract.hopdong.hopdongpheduyet.model.XetDuyetActionEnum;
// import
// com.contract.hopdong.hopdongpheduyet.repository.HopDongPheDuyetRepository;
// import
// com.contract.hopdong.hopdongpheduyet_nguoidung.entity.HopDongPheDuyetNguoiDungEntity;
// import
// com.contract.hopdong.hopdongpheduyet_nguoidung.model.HopDongPheDuyetNguoiDungModel;
// import
// com.contract.hopdong.hopdongpheduyet_nguoidung.model.TrangThaiNguoiDungPheDuyet;
// import
// com.contract.hopdong.hopdongpheduyet_nguoidung.service.HopDongPheDuyetNguoiDungService;
// import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
// import com.contract.nguoidung.nguoidung.model.NguoiDungModel;
// import com.contract.nguoidung.nguoidung.service.NguoiDungService;
// import com.contract.sys.action.model.ACTION;
// import com.contract.sys.module.model.MODULE;
// import com.contract.thongbao.model.ThongBaoModel;
// import com.contract.thongbao.model.TrangThaiThongBao;
// import com.contract.thongbao.service.ThongBaoService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.contract.base.service.BaseService;
import com.contract.common.exception.InvalidDataException;
import com.contract.common.exception.NotFoundException;
import com.contract.common.exception.UnauthorizedException;
import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.hopdong.hopdong.enums.TrangThaiHopDongEnum;
import com.contract.hopdong.hopdong.model.HopDongModel;
import com.contract.hopdong.hopdong.repository.HopDongRepository;
import com.contract.hopdong.hopdong.service.HopDongService;
import com.contract.hopdong.hopdongpheduyet.dto.PheDuyetInputDto;
import com.contract.hopdong.hopdongpheduyet.entity.HopDongPheDuyetEntity;
import com.contract.hopdong.hopdongpheduyet.enums.LoaiXetDuyetEnum;
import com.contract.hopdong.hopdongpheduyet.enums.TrangThaiPheDuyetEnum;
import com.contract.hopdong.hopdongpheduyet.model.HopDongPheDuyetCreateDto;
import com.contract.hopdong.hopdongpheduyet.model.HopDongPheDuyetModel;
import com.contract.hopdong.hopdongpheduyet.repository.HopDongPheDuyetRepository;
import com.contract.hopdong.hopdongpheduyet_nguoinhan.entity.HopDongPheDuyetNguoiNhanEntity;
import com.contract.hopdong.hopdongpheduyet_nguoinhan.service.HopDongPheDuyetNguoiNhanService;
import com.contract.hopdong.hopdongpheduyet_tientrinh.entity.HopDongPheDuyetTienTrinhEntity;
import com.contract.hopdong.hopdongpheduyet_tientrinh.model.HopDongPheDuyetTienTrinhModel;
import com.contract.hopdong.hopdongpheduyet_tientrinh.repository.HopDongPheDuyetTienTrinhRepository;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.thongbao.service.ThongBaoService;

@Service
public class HopDongPheDuyetService extends BaseService {
  // @Autowired
  // private HopDongPheDuyetRepository hopDongPheDuyetRepository;
  @Autowired
  private HopDongService hopDongService;
  @Autowired
  private ThongBaoService thongBaoService;
  @Autowired
  private NguoiDungService nguoiDungService;
  @Autowired
  private HopDongPheDuyetNguoiNhanService hopDongPheDuyetNguoiNhanService;
  // @Autowired
  // private KiemTraQuyenModuleHopDong kiemTraQuyenModuleHopDong;
  @Autowired
  private HopDongRepository hopDongRepository;
  @Autowired
  private HopDongPheDuyetRepository hopDongPheDuyetRepository;
  @Autowired
  private HopDongPheDuyetTienTrinhRepository hopDongPheDuyetTienTrinhRepository;
  // public HopDongPheDuyetEntity findById(Long id) {
  // return hopDongPheDuyetRepository.findById(id).orElse(null);
  // }

  // public HopDongPheDuyetEntity findByHopDong(Long id) {
  // return hopDongPheDuyetRepository.findByHopDongId(id);
  // }

  public List<HopDongPheDuyetEntity> getAllByHopDong(Long hopDongId) {
    List<HopDongPheDuyetEntity> result = hopDongPheDuyetRepository.findAllByHopDongId(hopDongId);
    return result;
  }
  // public Page<HopDongPheDuyetEntity> getDanhSachHopDongCanPheDuyet(Long
  // hopDongId,
  // List<TrangThaiPheDuyet> trangThaiPheDuyet, Integer page, Integer size) {
  // NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();
  // if (nguoiDungEntity == null) {
  // throw new UnauthorizedException();
  // }

  // Pageable pageable = null;
  // if (page != null && size != null) {
  // pageable = PageRequest.of(page, size);
  // } else {
  // pageable = Pageable.unpaged();
  // }

  // // Page<HopDongPheDuyetEntity> hopDongPheDuyetEntityList =
  // // hopDongPheDuyetRepository.findByNguoiNhan(nguoiDungEntity.getId(),
  // // trangThaiPheDuyet,
  // // hopDongId, pageable);
  // Page<HopDongPheDuyetEntity> hopDongPheDuyetEntityList = null;
  // if (!nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
  // List<Integer> ids =
  // kiemTraQuyenModuleHopDong.layDanhSachIdPhongDaiDuocQuyenXem();
  // hopDongPheDuyetEntityList = hopDongPheDuyetRepository.findByPhongDai(ids,
  // trangThaiPheDuyet, hopDongId, pageable);
  // } else {
  // hopDongPheDuyetEntityList = hopDongPheDuyetRepository
  // .adminFindByPhongDai(trangThaiPheDuyet, hopDongId, pageable);
  // }
  // return hopDongPheDuyetEntityList;
  // }

  // public List<HopDongPheDuyetModel> convertEntityToModel(
  // List<HopDongPheDuyetEntity> hopDongPheDuyetEntityList) {
  // List<HopDongPheDuyetModel> listReturn = new ArrayList<>();

  // if (hopDongPheDuyetEntityList != null) {
  // hopDongPheDuyetEntityList.forEach(entity -> {
  // HopDongPheDuyetModel hopDongPheDuyetModel =
  // HopDongPheDuyetModel.fromEntity(entity, true);
  // NguoiDungModel nguoiDuyet =
  // NguoiDungModel.fromEntity(entity.getNguoiDuyetEntity(), true);
  // List<HopDongPheDuyetNguoiDungModel> hopDongPheDuyetNguoiDungModels = new
  // ArrayList<>();
  // if (entity.getHopDongPheDuyetNguoiDungEntityList() != null) {
  // entity.getHopDongPheDuyetNguoiDungEntityList().forEach(e -> {
  // hopDongPheDuyetNguoiDungModels
  // .add(HopDongPheDuyetNguoiDungModel.fromEntity(e, true));
  // });
  // }
  // hopDongPheDuyetModel.setNguoiDuyet(nguoiDuyet);
  // hopDongPheDuyetModel.setNguoiNhanList(hopDongPheDuyetNguoiDungModels);
  // listReturn.add(hopDongPheDuyetModel);
  // });
  // }

  // return listReturn;
  // }

  // public List<HopDongPheDuyetModel> pheDuyetHopDong(
  // List<HopDongPheDuyetModel> hopDongPheDuyetModelList) {
  // List<HopDongPheDuyetModel> hopDongDaPheDuyetList = new ArrayList<>();
  // if (ObjectUtils.isEmpty(hopDongPheDuyetModelList)) {
  // throw new InvalidDataException();
  // }
  // hopDongPheDuyetModelList.forEach(hopDongPheDuyetModel -> {
  // try {
  // hopDongDaPheDuyetList.add(pheDuyet(hopDongPheDuyetModel));
  // } catch (Exception e) {
  // System.out.println(e);
  // }
  // });
  // return hopDongDaPheDuyetList;
  // }

  /**
   * Đồng ý hoặc từ chối hợp đồng đang chờ phê duyệt
   * - Thêm record trong table hop_dong_phe_duyet
   * - Thông báo
   * - Nếu đồng ý thì chuyển trạng thái của hop_dong từ cho_phe_duyet sang
   * hoat_dong
   */
  @Transactional
  public HopDongPheDuyetModel xetDuyet(PheDuyetInputDto pheDuyetInputDto) {
    NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();
    if (nguoiDungEntity == null) {
      throw new UnauthorizedException();
    }

    HopDongEntity hopDongEntity = hopDongRepository.findById(pheDuyetInputDto.getHopDongId()).orElse(null);
    if (hopDongEntity == null || TrangThaiHopDongEnum.HOAT_DONG.equals(hopDongEntity.getTrangThaiHopDong())) {
      throw new NotFoundException("Hợp đồng không tìm thấy hoặc trạng thái hợp đồng không phải là 'Chờ Phê Duyệt'");
    }
    HopDongPheDuyetEntity hopDongPheDuyetEntity = hopDongPheDuyetRepository
        .findById(pheDuyetInputDto.getHopDongPheDuyetId()).orElse(null);
    if (hopDongPheDuyetEntity == null) {
      throw new NotFoundException("Hợp đồng phê duyệt không tìm thấy");
    }
    // Add new record to hop_dong_phe_duyet_tien_trinh
    HopDongPheDuyetTienTrinhModel tienTrinhModelWillSave = new HopDongPheDuyetTienTrinhModel();
    // tienTrinhModelWillSave.setChangeLog(pheDuyetInputDto.getChangeLog());
    tienTrinhModelWillSave.setChangeLogClob(pheDuyetInputDto.getChangeLog());
    tienTrinhModelWillSave.setGhiChu(pheDuyetInputDto.getGhiChu());
    tienTrinhModelWillSave.setHopDongPheDuyetId(hopDongPheDuyetEntity.getId());
    tienTrinhModelWillSave.setTrangThaiPheDuyet(pheDuyetInputDto.getTrangThaiPheDuyet());
    tienTrinhModelWillSave.setNguoiDungId(nguoiDungEntity.getId());
    hopDongPheDuyetTienTrinhRepository.save(HopDongPheDuyetTienTrinhEntity.fromModel(null, tienTrinhModelWillSave));
    // update last phe duyet
    hopDongPheDuyetEntity.setTrangThaiPheDuyetMoiNhat(pheDuyetInputDto.getTrangThaiPheDuyet());
    hopDongPheDuyetRepository.save(hopDongPheDuyetEntity);
    // Thong bao
    thongBaoService.createThongBaoHopDongXetDuyet(hopDongEntity,
        pheDuyetInputDto.getTrangThaiPheDuyet(),
        hopDongPheDuyetEntity.getNguoiGuiId(),
        nguoiDungEntity.getId());
    // chuyen trang thai hop_dong nếu đông ý phê duỵệt
    if (TrangThaiPheDuyetEnum.PHE_DUYET.equals(pheDuyetInputDto.getTrangThaiPheDuyet())) {
      hopDongEntity.setTrangThaiHopDong(TrangThaiHopDongEnum.HOAT_DONG);
      hopDongRepository.save(hopDongEntity);
    }
    return HopDongPheDuyetModel.fromEntity(hopDongPheDuyetEntity, false);
  }

  // public boolean checkExists(Long hopDongId) {
  // return !ObjectUtils.isEmpty(
  // hopDongPheDuyetRepository.findByHopDongIdAndTrangThaiPheDuyetIsIn(hopDongId,
  // Arrays
  // .asList(TrangThaiPheDuyet.PHE_DUYET, TrangThaiPheDuyet.CHO_PHE_DUYET)));
  // }

  // /**
  // * B1: update hop dong B2: update hop dong phe duyet B3: tao thong bao
  // *
  // * @param hopDongPheDuyetModel
  // * @return
  // * @throws Exception
  // */
  // @Transactional
  // public HopDongPheDuyetModel pheDuyet(HopDongPheDuyetModel
  // hopDongPheDuyetModelInput)
  // throws Exception {
  // if (hopDongPheDuyetModelInput == null ||
  // hopDongPheDuyetModelInput.getTrangThaiPheDuyet()
  // .equals(TrangThaiPheDuyet.CHO_PHE_DUYET)) {
  // throw new InvalidDataException();
  // }
  // Long hopDongPheDuyetId = hopDongPheDuyetModelInput.getId();
  // if (hopDongPheDuyetId == null) {
  // throw new InvalidDataException();
  // }
  // HopDongPheDuyetEntity hopDongPheDuyetEntity =
  // hopDongPheDuyetRepository.findById(hopDongPheDuyetId)
  // .orElse(null);
  // if (hopDongPheDuyetEntity == null || !TrangThaiPheDuyet.CHO_PHE_DUYET
  // .equals(hopDongPheDuyetEntity.getTrangThaiPheDuyet())) {
  // throw new InvalidDataException();
  // }

  // NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();
  // if (nguoiDungEntity == null) {
  // throw new UnauthorizedException();
  // }
  // hopDongPheDuyetModelInput.setNguoiDuyetId(nguoiDungEntity.getId());

  // HopDongEntity hopDongCanPheDuyetEntity =
  // hopDongService.findById(hopDongPheDuyetEntity.getHopDongId());
  // if (hopDongCanPheDuyetEntity == null) {
  // throw new InvalidDataException();
  // }
  // hopDongPheDuyetModelInput
  // .setAction(ACTION.XET_DUYET.name() + "_" +
  // hopDongCanPheDuyetEntity.getTrangThai());

  // // HopDongModel hopDongToSave = hopDongPheDuyetModel.getHopDong();
  // // if (hopDongToSave != null) {
  // // Map<String, Object> diff = hopDongService.getMapDifference((HopDongModel)
  // // hopDongService.getThuePhuTroHopDong(hopDongCanPheDuyet).clone(),
  // // (HopDongModel)
  // // hopDongToSave.clone());
  // // hopDongPheDuyetModel.setChangeLog(new JSONObject(diff).toString());
  // // }

  // if
  // (TrangThaiPheDuyet.PHE_DUYET.equals(hopDongPheDuyetModelInput.getTrangThaiPheDuyet()))
  // {
  // // if (hopDongToSave == null) {
  // // hopDongToSave = HopDongModel.fromEntity(hopDongCanPheDuyet, false);
  // // }
  // // hopDongToSave.setTrangThai(TrangThaiHopDong.HOAT_DONG);
  // hopDongCanPheDuyetEntity.setTrangThai(TrangThaiHopDong.HOAT_DONG);
  // }
  // if
  // (TrangThaiPheDuyet.TU_CHOI.equals(hopDongPheDuyetModelInput.getTrangThaiPheDuyet()))
  // {
  // hopDongCanPheDuyetEntity.setTrangThai(TrangThaiHopDong.CHO_PHE_DUYET);
  // // hopDongToSave = HopDongModel.fromEntity(hopDongCanPheDuyet, false);
  // // hopDongToSave.setTrangThai(TrangThaiHopDong.NHAP);
  // }
  // hopDongService.save(hopDongCanPheDuyetEntity);
  // // hopDongService.update(hopDongToSave.getId(), hopDongToSave, null, null,
  // // null);

  // ThongBaoModel thongBaoModel = new ThongBaoModel();
  // thongBaoModel.setModule(MODULE.HOP_DONG.name());
  // thongBaoModel
  // .setAction(ACTION.XET_DUYET.name() + "_" +
  // hopDongCanPheDuyetEntity.getTrangThai());
  // thongBaoModel.setNguoiNhanId(hopDongPheDuyetModelInput.getNguoiGuiId());
  // thongBaoModel.setNguoiGuiId(nguoiDungEntity.getId());
  // thongBaoModel.setContent(new
  // JSONObject(hopDongCanPheDuyetEntity).toString());
  // thongBaoModel.setTrangThai(TrangThaiThongBao.CHUA_XEM);
  // thongBaoService.create(thongBaoModel);

  // return update(hopDongPheDuyetModelInput);
  // }

  @Transactional
  public List<HopDongPheDuyetModel> create(HopDongPheDuyetCreateDto hopDongPheDuyetCreateDto) throws Exception {
    if (ObjectUtils.isEmpty(hopDongPheDuyetCreateDto)) {
      throw new InvalidDataException();
    }
    List<Long> hopDongIdList = hopDongPheDuyetCreateDto.getHopDongIdList();
    // TODO check permission of nguoi phe duyet
    List<Long> nguoiPheDuyetIdList = hopDongPheDuyetCreateDto.getNguoiPheDuyetIdList();
    String ghiChu = hopDongPheDuyetCreateDto.getGhiChu();
    if (ObjectUtils.isEmpty(hopDongIdList) ||
        ObjectUtils.isEmpty(nguoiPheDuyetIdList)) {
      throw new InvalidDataException();
    }
    List<HopDongEntity> hopDongEntityList = hopDongService.findHopDongByListId(hopDongIdList);
    List<NguoiDungEntity> nguoiPheDuyetList = nguoiDungService.findNguoiDungByListId(nguoiPheDuyetIdList);
    if (ObjectUtils.isEmpty(hopDongEntityList) ||
        ObjectUtils.isEmpty(nguoiPheDuyetList)) {
      throw new InvalidDataException();
    }
    NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();
    if (nguoiDungEntity == null) {
      throw new UnauthorizedException();
    }
    List<HopDongPheDuyetModel> listReturn = new ArrayList<>();
    for (int i = 0; i < hopDongEntityList.size(); i++) {
      HopDongEntity hopDongEntity = hopDongEntityList.get(i);
      if (ObjectUtils.isEmpty(hopDongEntity)) {
        continue;
      }
      if (TrangThaiHopDongEnum.CHO_PHE_DUYET_HOP_DONG.equals(hopDongEntity.getTrangThaiHopDong())
          || TrangThaiHopDongEnum.CHO_PHE_DUYET_PHU_LUC.equals(hopDongEntity.getTrangThaiHopDong())) {
        continue;
      }
      // update trang thai hoat dong hop dong
      TrangThaiHopDongEnum trangThaiHopDongOrigin = hopDongEntity.getTrangThaiHopDong();
      hopDongEntity.setTrangThaiHopDong(trangThaiHopDongOrigin.equals(TrangThaiHopDongEnum.NHAP)
          ? TrangThaiHopDongEnum.CHO_PHE_DUYET_HOP_DONG
          : TrangThaiHopDongEnum.CHO_PHE_DUYET_PHU_LUC);
      hopDongRepository.save(hopDongEntity);
      // hop dong phe duyet
      HopDongPheDuyetModel model = new HopDongPheDuyetModel();
      model.setHopDongId(hopDongEntity.getId());
      model.setTrangThaiPheDuyetMoiNhat(TrangThaiPheDuyetEnum.CHO_PHE_DUYET);
      // model.setTrangThaiPheDuyet(TrangThaiPheDuyetEnum.CHO_PHE_DUYET);
      model.setNguoiGuiId(nguoiDungEntity.getId());
      model.setGhiChu(ghiChu);
      model.setLoaiXetDuyet(
          trangThaiHopDongOrigin.equals(TrangThaiHopDongEnum.NHAP) ? LoaiXetDuyetEnum.XET_DUYET_HOP_DONG
              : LoaiXetDuyetEnum.XET_DUYET_PHU_LUC);
      HopDongPheDuyetEntity savedHopDongPheDuyet = hopDongPheDuyetRepository
          .save(HopDongPheDuyetEntity.fromModel(null, model));
      listReturn.add(savedHopDongPheDuyet);
      // Add 1 record CHO_PHE_DUYET to hop_dong_phe_duyet_tien_trinh
      HopDongPheDuyetTienTrinhModel hopDongPheDuyetTienTrinhModel = new HopDongPheDuyetTienTrinhModel();
      hopDongPheDuyetTienTrinhModel.setHopDongPheDuyetId(savedHopDongPheDuyet.getId());
      hopDongPheDuyetTienTrinhModel.setGhiChu(ghiChu);
      hopDongPheDuyetTienTrinhModel.setTrangThaiPheDuyet(TrangThaiPheDuyetEnum.CHO_PHE_DUYET);
      hopDongPheDuyetTienTrinhModel.setNguoiDungId(nguoiDungEntity.getId());
      hopDongPheDuyetTienTrinhRepository
          .save(HopDongPheDuyetTienTrinhEntity.fromModel(null, hopDongPheDuyetTienTrinhModel));
      // hop dong phe duyet nguoi nhan
      List<HopDongPheDuyetNguoiNhanEntity> nguoiNhanWillSaveList = new ArrayList<>();
      nguoiPheDuyetList.forEach(_nguoiDung -> {
        HopDongPheDuyetNguoiNhanEntity hopDongPheDuyetNguoiNhanEntity = new HopDongPheDuyetNguoiNhanEntity();
        hopDongPheDuyetNguoiNhanEntity.setNguoiDungId(_nguoiDung.getId());
        hopDongPheDuyetNguoiNhanEntity.setHopDongPheDuyetId(savedHopDongPheDuyet.getId());
        nguoiNhanWillSaveList.add(hopDongPheDuyetNguoiNhanEntity);
        // // thong bao
        thongBaoService.createThongBaoHopDongXetDuyet(HopDongModel.fromEntity(hopDongEntity,
            false),
            TrangThaiPheDuyetEnum.CHO_PHE_DUYET,
            _nguoiDung.getId(), nguoiDungEntity.getId());
      });
      hopDongPheDuyetNguoiNhanService.saveAll(nguoiNhanWillSaveList);
    }

    return listReturn;
  }
  // @Transactional
  // public void create(HopDongPheDuyetCreateDto hopDongPheDuyetCreateDto)throws
  // Exception {
  // if (ObjectUtils.isEmpty(hopDongPheDuyetCreateDto)) {
  // throw new InvalidDataException();
  // }
  // List<Long> hopDongIdList = hopDongPheDuyetCreateDto.getHopDongIdList();
  // List<Long> nguoiPheDuyetIdList =
  // hopDongPheDuyetCreateDto.getNguoiPheDuyetIdList();
  // String ghiChu = hopDongPheDuyetCreateDto.getGhiChu();
  // if (ObjectUtils.isEmpty(hopDongIdList) ||
  // ObjectUtils.isEmpty(nguoiPheDuyetIdList)) {
  // throw new InvalidDataException();
  // }
  // List<HopDongEntity> hopDongEntityList =
  // hopDongService.findHopDongByListId(hopDongIdList);
  // List<NguoiDungEntity> nguoiPheDuyetList =
  // nguoiDungService.findNguoiDungByListId(nguoiPheDuyetIdList);
  // if (ObjectUtils.isEmpty(hopDongEntityList) ||
  // ObjectUtils.isEmpty(nguoiPheDuyetList)) {
  // throw new InvalidDataException();
  // }

  // NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();
  // if (nguoiDungEntity == null) {
  // throw new UnauthorizedException();
  // }

  // List<HopDongPheDuyetModel> listReturn = new ArrayList<>();

  // for (int i = 0; i < hopDongEntityList.size(); i++) {
  // try {
  // HopDongPheDuyetModel model = new HopDongPheDuyetModel();

  // // hop dong
  // HopDongEntity hopDongEntity = hopDongEntityList.get(i);
  // if (ObjectUtils.isEmpty(hopDongEntity)) {
  // continue;
  // }
  // if
  // (TrangThaiHopDong.CHO_PHE_DUYET.equals(hopDongEntity.getTrangThaiHopDong()))
  // {
  // continue;
  // }
  // hopDongEntity.setTrangThaiHopDong(null);(TrangThaiHopDong.CHO_PHE_DUYET);
  // hopDongRepository.save(hopDongEntity);
  // // HopDongModel hopDongModel = HopDongModel.fromEntity(hopDongEntity, false);
  // // hopDongService.update(hopDongEntity.getId(), hopDongModel, null, null,
  // // null);

  // // hop dong phe duyet
  // model.setHopDongId(hopDongEntity.getId());
  // model.setTrangThaiPheDuyet(TrangThaiPheDuyetEnum.CHO_PHE_DUYET);
  // model.setNguoiGuiId(nguoiDungEntity.getId());
  // model.setGhiChu(ghiChu);
  // model.setAction(XetDuyetActionEnum.XET_DUYET_HOP_DONG.name());
  // LocalDateTime localDateTime = LocalDateTime.now();
  // Timestamp timestamp = Timestamp.valueOf(localDateTime);
  // model.setGroupIdByTimestamp(timestamp.toString());

  // // hopDongPheDuyetRe
  // // HopDongPheDuyetEntity toSave = new HopDongPheDuyetEntity();
  // // toSave = convertModelToEntity(toSave, model);
  // // HopDongPheDuyetModel saved = save(toSave);

  // // model.setId(toSave.getId());

  // // hop dong phe duyet nguoi dung
  // // List<HopDongPheDuyetNguoiDungEntity> hopDongPheDuyetNguoiDungEntityList =
  // new ArrayList<>();
  // // List<HopDongPheDuyetNguoiDungModel> hopDongPheDuyetNguoiDungReturnList =
  // new ArrayList<>();
  // // nguoiPheDuyetList.forEach(nguoiDung -> {
  // // thong bao
  // //
  // thongBaoService.createThongBaoHopDongXetDuyet(hopDongEntity,TrangThaiPheDuyet.CHO_PHE_DUYET,
  // nguoiDung.getId(), nguoiDungEntity.getId());
  // // hop dong phe duyet nguoi dung
  // // HopDongPheDuyetNguoiDungEntity hopDongPheDuyetNguoiDungEntity = new
  // // HopDongPheDuyetNguoiDungEntity();
  // // hopDongPheDuyetNguoiDungEntity.setNguoiDungId(nguoiDung.getId());
  // // hopDongPheDuyetNguoiDungEntity.setHopDongPheDuyetId(saved.getId());
  // //
  // hopDongPheDuyetNguoiDungEntity.setTrangThai(TrangThaiNguoiDungPheDuyet.PENDING);
  // // hopDongPheDuyetNguoiDungEntityList.add(hopDongPheDuyetNguoiDungEntity);

  // // hopDongPheDuyetNguoiDungReturnList.add(HopDongPheDuyetNguoiDungModel
  // // .fromEntity(hopDongPheDuyetNguoiDungEntity, false));
  // // });
  // //
  // hopDongPheDuyetNguoiDungService.saveAll(hopDongPheDuyetNguoiDungEntityList);

  // // saved.setNguoiNhanList(hopDongPheDuyetNguoiDungReturnList);
  // // listReturn.add(saved);
  // } catch (Exception e) {
  // System.out.println(e);
  // }
  // }

  // // return listReturn;
  // }

  // public HopDongPheDuyetModel update(HopDongPheDuyetModel model) throws
  // NotFoundException {
  // if (model == null) {
  // throw new InvalidDataException();
  // }
  // HopDongPheDuyetEntity toSave = findById(model.getId());

  // if (toSave == null) {
  // throw new NotFoundException();
  // }

  // toSave = convertModelToEntity(toSave, model);
  // return save(toSave);
  // }

  // public HopDongPheDuyetModel save(HopDongPheDuyetEntity entity) {
  // try {
  // HopDongPheDuyetEntity saved = hopDongPheDuyetRepository.save(entity);
  // return HopDongPheDuyetModel.fromEntity(saved, false);
  // } catch (Exception e) {
  // System.out.println(e);
  // throw new InvalidDataException();
  // }
  // }

  // private HopDongPheDuyetEntity convertModelToEntity(HopDongPheDuyetEntity
  // entity,
  // HopDongPheDuyetModel model) {
  // entity.setHopDongId(model.getHopDongId());
  // entity.setGroupIdByTimestamp(model.getGroupIdByTimestamp());
  // entity.setTrangThaiPheDuyet(model.getTrangThaiPheDuyet());
  // entity.setGhiChu(model.getGhiChu());
  // entity.setNguoiGuiId(model.getNguoiGuiId());
  // entity.setNguoiDuyetId(model.getNguoiDuyetId());
  // entity.setChangeLog(model.getChangeLog());
  // entity.setLyDo(model.getLyDo());
  // entity.setAction(model.getAction());

  // return entity;
  // }
}
