package com.contract.danhmuc.donvidungchung.service;

import java.util.List;

import com.contract.danhmuc.donvidungchung.dto.DmDonViDungChungDto;
import com.contract.danhmuc.donvidungchung.model.DmDonViDungChungModel;

public interface DmDonViDungChungService {
    List<DmDonViDungChungModel> findAll();

    DmDonViDungChungModel saveDm(DmDonViDungChungDto dmDonViDungChungDto);

    DmDonViDungChungModel update(int idOld, DmDonViDungChungDto dmDonViDungChungDto);

    boolean delete(int idOld);
}
