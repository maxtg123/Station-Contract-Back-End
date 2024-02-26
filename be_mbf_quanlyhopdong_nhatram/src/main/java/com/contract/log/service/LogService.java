package com.contract.log.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.contract.log.entity.LogEntity;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.model.ChangeLogModel;
import com.contract.log.model.LogModel;
import com.contract.log.repository.LogRepository;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private DanhMucLogService danhMucLogService;

    public Page<LogEntity> findAll(String module, Date from, Date to, Integer size, Integer page) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<LogEntity> logEntityPage = logRepository.findAllAndFetchUser(module, from, to, pageRequest);

        return logEntityPage;
    }

    public List<LogModel> convertListLogEntityToModel(Page<LogEntity> logEntityPage) {
        List<LogModel> listReturn = new ArrayList<>();
        if (logEntityPage != null) {
            logEntityPage.forEach(e -> {
                listReturn.add(LogModel.fromEntity(e, true));
            });
        }
        return listReturn;
    }

    public void saveLogDanhMuc(Object originalData, Object toData, String type, Long nguoiDungId,
            LogActionEnum action) {
        try {
            ChangeLogModel changeLogModel = danhMucLogService.getDmChangeLog(
                    originalData == null ? null : new JSONObject(originalData),
                    toData == null ? null : new JSONObject(toData), type);
            danhMucLogService.saveDmLog(changeLogModel, nguoiDungId, action);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
