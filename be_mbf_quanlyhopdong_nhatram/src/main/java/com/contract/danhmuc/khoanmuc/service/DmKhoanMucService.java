package com.contract.danhmuc.khoanmuc.service;

import com.contract.danhmuc.khoanmuc.dto.DmKhoanMucDto;
import com.contract.danhmuc.khoanmuc.model.DmKhoanMucModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DmKhoanMucService {
    List<DmKhoanMucModel> findAll();

    DmKhoanMucModel saveDm(DmKhoanMucDto dmKhoanMucDto);

    DmKhoanMucModel update(int idOld, DmKhoanMucDto dmKhoanMucDto);

    boolean delete(int idOld);
}
