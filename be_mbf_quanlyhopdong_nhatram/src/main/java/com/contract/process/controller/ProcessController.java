package com.contract.process.controller;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.common.exception.NotFoundException;
import com.contract.process.model.ProcessModel;
import com.contract.process.service.ProcessService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@ApiPrefixController
@Tag(name = "Process", description = "API quản lý process")
public class ProcessController extends BaseController<ProcessModel> {
    @Autowired
    private ProcessService processService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/process/{id}")
    public ResponseModel<ProcessModel> getDetail(@PathVariable("id") Long id) throws Exception {
        ProcessModel processEntity = processService.findById(id);
        if (ObjectUtils.isEmpty(processEntity)) {
            throw new NotFoundException();
        }
        return super.success(processEntity);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "/process/{id}")
    public ResponseModel<ProcessModel> delete(@PathVariable("id") Long id) throws Exception {
        processService.delete(id);
        return super.success(null);
    }
}
