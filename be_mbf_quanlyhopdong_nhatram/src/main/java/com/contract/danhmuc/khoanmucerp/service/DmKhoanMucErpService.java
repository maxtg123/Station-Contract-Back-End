package com.contract.danhmuc.khoanmucerp.service;

import com.contract.danhmuc.khoanmucerp.dto.DmKhoanMucErpDto;
import com.contract.danhmuc.khoanmucerp.model.DmKhoanMucErpModel;

import java.util.List;

public interface DmKhoanMucErpService {
    List<DmKhoanMucErpModel> findAll();

    DmKhoanMucErpModel saveKhoanMuc(DmKhoanMucErpDto dmKhoanMucErpDto);

    DmKhoanMucErpModel updateKhoanMuc(int idOld, DmKhoanMucErpDto dmKhoanMucErpDto);

    boolean delete(int idOld);
}
