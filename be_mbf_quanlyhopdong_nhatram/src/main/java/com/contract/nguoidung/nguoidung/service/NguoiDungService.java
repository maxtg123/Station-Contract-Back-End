package com.contract.nguoidung.nguoidung.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.contract.authentication.component.KiemTraQuyenModuleNguoiDung;
import com.contract.base.service.BaseService;
import com.contract.chucvu.chucvu.entity.ChucVuEntity;
import com.contract.chucvu.chucvu.model.ChucVuModel;
import com.contract.chucvu.phanquyen.model.ChucVuPhanQuyenModel;
import com.contract.common.exception.DataExistsException;
import com.contract.common.exception.InvalidDataException;
import com.contract.common.exception.NotFoundException;
import com.contract.log.service.LogService;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.nguoidung.nguoidung.model.LoaiNguoiDung;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;
import com.contract.nguoidung.nguoidung.model.ProfileModel;
import com.contract.nguoidung.nguoidung.model.TrangThai;
import com.contract.nguoidung.nguoidung.repository.NguoiDungRepository;
import com.contract.nguoidung.nguoidungkhuvuc.model.NguoiDungKhuVucModel;
import com.contract.nguoidung.nguoidungkhuvuc.service.NguoiDungKhuVucService;

@Service
public class NguoiDungService extends BaseService {
    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    @Autowired
    private NguoiDungKhuVucService nguoiDungKhuVucService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private KiemTraQuyenModuleNguoiDung kiemTraQuyenModuleNguoiDung;
    @Autowired
    private LogService logService;

    public List<NguoiDungEntity> findByUserName(String username) {
        return nguoiDungRepository.findByHoTen(username);
    }

    public List<NguoiDungEntity> findByEmail(String email) {
        return nguoiDungRepository.findByEmail(email);
    }

    public NguoiDungEntity findById(Long id) {
        return nguoiDungRepository.findById(id).orElse(null);
    }

    public String getUsername() {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username.toLowerCase();
    }

    public NguoiDungEntity getNguoiDung() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        NguoiDungEntity nguoiDungEntity = findByEmailAndFetchKhuVuc(email);
        return nguoiDungEntity;
    }

    public boolean kiemTraNguoiDungHienTaiLaSuperAdmin() {
        NguoiDungEntity nguoiDungEntity = getNguoiDung();

        if (LoaiNguoiDung.SUPERADMIN.equals(nguoiDungEntity.getLoaiNguoiDung())) {
            return true;
        }

        return false;
    }

    public List<NguoiDungModel> findAll() throws Exception {
        List<NguoiDungModel> listReturn = new ArrayList<>();

        if (kiemTraNguoiDungHienTaiLaSuperAdmin()) {
            List<NguoiDungEntity> nguoiDungEntityList = nguoiDungRepository.findAll();
            listReturn = convertDataToResponse(nguoiDungEntityList);
        } else {
            List<Integer> listPhongDaiId = kiemTraQuyenModuleNguoiDung.layDanhSachIdPhongDaiDuocQuyenXem();
            List<NguoiDungEntity> nguoiDungEntityList = nguoiDungRepository.findByDanhSachPhongDai(listPhongDaiId);
            listReturn = convertDataToResponse(nguoiDungEntityList);
        }

        return listReturn;
    }

    public List<NguoiDungModel> convertDataToResponse(List<NguoiDungEntity> nguoiDungEntityList) {
        List<NguoiDungModel> listReturn = new ArrayList<>();

        nguoiDungEntityList.forEach(e -> {
            NguoiDungModel nguoiDungModel = NguoiDungModel.fromEntity(e, true);
            List<NguoiDungKhuVucModel> nguoiDungKhuVucModelList = new ArrayList<>();
            if (e.getNguoiDungKhuVucEntityList() != null) {
                e.getNguoiDungKhuVucEntityList().forEach(khuvuc -> {
                    NguoiDungKhuVucModel nguoiDungKhuVucModel = NguoiDungKhuVucModel.fromEntity(khuvuc, true);
                    ChucVuEntity chucVuEntity = khuvuc.getChucVuEntity();
                    ChucVuModel chucVuModel = ChucVuModel.fromEntity(chucVuEntity, true);
                    List<ChucVuPhanQuyenModel> chucVuPhanQuyenModelList = new ArrayList<>();
                    if (chucVuEntity != null
                            && chucVuEntity.getListChucVuPhanQuyenEntity() != null) {
                        chucVuEntity.getListChucVuPhanQuyenEntity().forEach(quyen -> {
                            chucVuPhanQuyenModelList
                                    .add(ChucVuPhanQuyenModel.fromEntity(quyen, true));
                        });
                    }
                    chucVuModel.setChucVuPhanQuyenList(chucVuPhanQuyenModelList);
                    nguoiDungKhuVucModel.setChucVu(chucVuModel);
                    nguoiDungKhuVucModelList.add(nguoiDungKhuVucModel);
                });
            }
            nguoiDungModel.setNguoiDungKhuVucList(nguoiDungKhuVucModelList);
            if (!LoaiNguoiDung.SUPERADMIN.equals(e.getLoaiNguoiDung())) {
                listReturn.add(nguoiDungModel);
            }
        });

        return listReturn;
    }

    public NguoiDungEntity findByEmailAndFetchKhuVuc(String email) {
        NguoiDungEntity nguoiDungEntity = null;
        try {
            nguoiDungEntity = (NguoiDungEntity) cacheManager.getCache("users").get(email).get();
        } catch (Exception e) {
            System.out.println(e);
        }
        if (ObjectUtils.isEmpty(nguoiDungEntity)) {
            List<NguoiDungEntity> nguoiDungEntityList = nguoiDungRepository.findByEmailAndFetchKhuVuc(email);
            if (ObjectUtils.isEmpty(nguoiDungEntityList)) {
                throw new NotFoundException();
            }
            nguoiDungEntity = nguoiDungEntityList.get(0);
            cacheManager.getCache("users").put(email, nguoiDungEntity);
        }

        return nguoiDungEntity;
    }

    @Transactional
    public NguoiDungModel createNguoiDung(NguoiDungModel user) throws Exception {
        try {
            user.setMatKhau(passwordEncoder.encode("hdnt@2023$"));
        } catch (Exception e) {
            throw new InvalidDataException();
        }

        NguoiDungEntity userToSave = new NguoiDungEntity();
        userToSave = convertUserModelToUserEntity(userToSave, user);
        NguoiDungModel saved = save(userToSave);

        if (user.getNguoiDungKhuVucList() != null) {
            user.getNguoiDungKhuVucList().forEach(nguoiDungKhuVuc -> {
                try {
                    nguoiDungKhuVuc.setNguoiDungId(saved.getId());
                    nguoiDungKhuVucService.create(nguoiDungKhuVuc);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new InvalidDataException();
                }
            });
        }

        return saved;
    }

    @Transactional
    public NguoiDungModel updateNguoiDung(NguoiDungModel user, Long id) throws Exception {

        try {
            if (!ObjectUtils.isEmpty(user.getMatKhau())) {
                user.setMatKhau(passwordEncoder.encode(user.getMatKhau()));
            }
        } catch (Exception e) {
            throw new InvalidDataException();
        }

        NguoiDungEntity userToSave = findById(id);
        if (ObjectUtils.isEmpty(userToSave)) {
            throw new NotFoundException();
        }
        userToSave = convertUserModelToUserEntity(userToSave, user);
        NguoiDungModel saved = save(userToSave);

        if (user.getNguoiDungKhuVucList() != null) {
            nguoiDungKhuVucService.deleteByNguoiDungId(saved.getId());
            nguoiDungKhuVucService.flush();
            user.getNguoiDungKhuVucList().forEach(nguoiDungKhuVuc -> {
                try {
                    nguoiDungKhuVuc.setNguoiDungId(saved.getId());
                    nguoiDungKhuVucService.create(nguoiDungKhuVuc);
                } catch (Exception e) {
                    throw new InvalidDataException();
                }
            });
        }

        deleteNguoiDungCache(userToSave);
        return saved;
    }

    public NguoiDungModel createSuperAdmin(NguoiDungModel user) throws Exception {
        if (ObjectUtils.isEmpty(user.getMatKhau())) {
            throw new InvalidDataException();
        }

        boolean isSuperAdminExists = !ObjectUtils.isEmpty(this.findByEmail("superadmin@mobifone.vn"));
        if (isSuperAdminExists) {
            throw new DataExistsException();
        }

        if (!ObjectUtils.isEmpty(user.getMatKhau())) {
            user.setMatKhau(passwordEncoder.encode(user.getMatKhau()));
        }
        user.setLoaiNguoiDung(LoaiNguoiDung.SUPERADMIN);
        NguoiDungEntity userToSave = new NguoiDungEntity();
        userToSave = convertUserModelToUserEntity(userToSave, user);

        return save(userToSave);
    }

    public NguoiDungModel updateSuperAdmin(NguoiDungModel user) throws Exception {

        NguoiDungEntity nguoiDungToSave = findById(user.getId());
        if (ObjectUtils.isEmpty(nguoiDungToSave)) {
            throw new NotFoundException();
        }
        if (!ObjectUtils.isEmpty(user.getMatKhau())) {
            user.setMatKhau(passwordEncoder.encode(user.getMatKhau()));
        }
        user.setLoaiNguoiDung(LoaiNguoiDung.SUPERADMIN);
        nguoiDungToSave = convertUserModelToUserEntity(nguoiDungToSave, user);

        return save(nguoiDungToSave);
    }

    public void deleteNguoiDungCache(NguoiDungEntity nguoiDungEntity) {
        try {
            cacheManager.getCache("users").evict(nguoiDungEntity.getEmail());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public NguoiDungModel save(NguoiDungEntity user) throws Exception {
        try {
            NguoiDungEntity saved = nguoiDungRepository.save(user);
            return NguoiDungModel.fromEntity(saved, false);
        } catch (Exception e) {
            throw new InvalidDataException();
        }
    }

    public NguoiDungModel delete(Long id) throws Exception {
        NguoiDungEntity nguoiDung = findById(id);
        if (ObjectUtils.isEmpty(nguoiDung)) {
            throw new NotFoundException();
        }
        nguoiDung.setTrangThai(TrangThai.NGUNG_HOAT_DONG);
        NguoiDungModel saved = save(nguoiDung);
        deleteNguoiDungCache(nguoiDung);
        return saved;
    }

    public ProfileModel getProfile(String email) {
        List<NguoiDungEntity> nguoiDungEntityList = nguoiDungRepository.findByEmailAndFetchKhuVuc(email);

        if (ObjectUtils.isEmpty(nguoiDungEntityList)) {
            throw new NotFoundException();
        }
        NguoiDungEntity entity = nguoiDungEntityList.get(0);
        if (TrangThai.NGUNG_HOAT_DONG.equals(entity.getTrangThai())) {
            throw new NotFoundException();
        }

        ProfileModel profile = new ProfileModel();
        profile.setId(entity.getId());
        profile.setHoTen(entity.getHoTen());
        profile.setGioiTinh(entity.getGioiTinh().toString());
        profile.setEmail(entity.getEmail());
        profile.setSoDienThoai(entity.getSoDienThoai());
        profile.setTrangThai(entity.getTrangThai().toString());

        if (!ObjectUtils.isEmpty(entity.getNguoiDungKhuVucEntityList())) {
            List<NguoiDungKhuVucModel> nguoiDungKhuVucModelList = new ArrayList<>();
            entity.getNguoiDungKhuVucEntityList().forEach(khuvuc -> {
                NguoiDungKhuVucModel nguoiDungKhuVucModel = NguoiDungKhuVucModel.fromEntity(khuvuc, true);
                ChucVuEntity chucVuEntity = khuvuc.getChucVuEntity();
                ChucVuModel chucVuModel = ChucVuModel.fromEntity(chucVuEntity, true);
                List<ChucVuPhanQuyenModel> chucVuPhanQuyenModelList = new ArrayList<>();
                if (chucVuEntity != null && chucVuEntity.getListChucVuPhanQuyenEntity() != null) {
                    chucVuEntity.getListChucVuPhanQuyenEntity().forEach(quyen -> {
                        chucVuPhanQuyenModelList.add(ChucVuPhanQuyenModel.fromEntity(quyen, true));
                    });
                }
                chucVuModel.setChucVuPhanQuyenList(chucVuPhanQuyenModelList);
                nguoiDungKhuVucModel.setChucVu(chucVuModel);
                nguoiDungKhuVucModelList.add(nguoiDungKhuVucModel);
            });
            profile.setNguoiDungKhuVucList(nguoiDungKhuVucModelList);
        }

        return profile;
    }

    public List<NguoiDungEntity> findNguoiDungByListId(List<Long> ids) {
        return nguoiDungRepository.findAllById(ids);
    }

    private NguoiDungEntity convertUserModelToUserEntity(NguoiDungEntity entity,
            NguoiDungModel nguoiDungModel) {

        if (nguoiDungModel.getEmail() != null) {
            entity.setEmail(nguoiDungModel.getEmail());
        }
        if (nguoiDungModel.getHoTen() != null) {
            entity.setHoTen(nguoiDungModel.getHoTen());
        }
        if (nguoiDungModel.getMatKhau() != null) {
            entity.setMatKhau(nguoiDungModel.getMatKhau());
        }
        if (nguoiDungModel.getGioiTinh() != null) {
            entity.setGioiTinh(nguoiDungModel.getGioiTinh());
        }
        if (nguoiDungModel.getSoDienThoai() != null) {
            entity.setSoDienThoai(nguoiDungModel.getSoDienThoai());
        }
        if (nguoiDungModel.getTrangThai() != null) {
            entity.setTrangThai(nguoiDungModel.getTrangThai());
        }
        if (nguoiDungModel.getLoaiNguoiDung() != null) {
            entity.setLoaiNguoiDung(nguoiDungModel.getLoaiNguoiDung());
        }

        return entity;
    }
}
