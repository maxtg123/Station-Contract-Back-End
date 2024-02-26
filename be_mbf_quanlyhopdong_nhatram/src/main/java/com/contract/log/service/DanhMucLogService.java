package com.contract.log.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.common.util.StringUtil;
import com.contract.log.entity.LogEntity;
import com.contract.log.enums.LogActionEnum;
import com.contract.log.model.ChangeLogModel;
import com.contract.log.model.ChangeModel;
import com.contract.log.model.ForModel;
import com.contract.log.repository.LogRepository;
import com.contract.sys.module.model.MODULE;

@Service
public class DanhMucLogService {
  @Autowired
  private LogRepository logRepository;

  public void saveDmLog(ChangeLogModel changeLogModel, Long nguoiDungId, LogActionEnum action) {
    if (action.equals(LogActionEnum.UPDATE)
        && (changeLogModel.getChanges() == null || changeLogModel.getChanges().size() == 0)) {
      return;
    }
    LogEntity logEntity = new LogEntity();
    logEntity.setModule(MODULE.DANH_MUC.name());
    logEntity.setNguoiDungId(nguoiDungId);
    logEntity.setChangeLog(new JSONObject(changeLogModel).toString());
    logEntity.setAction(action);
    logRepository.save(logEntity);
  }

  public ChangeLogModel getDmChangeLog(JSONObject fromData, JSONObject toData, String type) {
    ChangeLogModel result = new ChangeLogModel();
    List<ChangeModel> changes = new ArrayList<>();

    if (type.equals("update")) {
      if (!StringUtil.equals(fromData.optString("ten"), toData.optString("ten"))) {
        changes.add(getChange(fromData, toData, "ten"));
      }
      if (!StringUtil.equals(fromData.optString("maDataSite"), toData.optString("maDataSite"))) {
        changes.add(getChange(fromData, toData, "maDataSite"));
      }
      if (!StringUtil.equals(fromData.optString("ma"), toData.optString("ma"))) {
        changes.add(getChange(fromData, toData, "ma"));
      }
      if (!StringUtil.equals(fromData.optString("ghiChu"), toData.optString("ghiChu"))) {
        changes.add(getChange(fromData, toData, "ghiChu"));
      }
      if (!StringUtil.equals(fromData.optString("tenVietTat"), toData.optString("tenVietTat"))) {
        changes.add(getChange(fromData, toData, "tenVietTat"));
      }
    }

    result.setChanges(changes);
    result.setType(type);
    result.setVersion("0.0.0");
    ForModel forModel = new ForModel();
    forModel.setId(toData.optLong("id"));
    forModel.setName(toData.optString("ten"));
    result.setForModel(forModel);

    return result;
  }

  private ChangeModel getChange(JSONObject fromData, JSONObject toData, String name) {
    ChangeModel model = new ChangeModel();
    model.setPaths(Arrays.asList(name));
    model.setOldValue(fromData.optString(name));
    model.setValue(toData.optString(name));
    model.setType("update");

    return model;
  }
}
