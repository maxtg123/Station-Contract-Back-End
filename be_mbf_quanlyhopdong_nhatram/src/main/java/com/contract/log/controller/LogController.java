package com.contract.log.controller;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.log.entity.LogEntity;
import com.contract.log.model.LogModel;
import com.contract.log.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@ApiPrefixController
@Tag(name = "Log", description = "API quản lý log")
public class LogController extends BaseController<LogModel> {
    @Autowired
    private LogService logService;

    private class LogResponseClass extends ResponseModel<LogModel> {
    }

    @Operation(summary = "Lấy danh sách log", description = "Lấy danh sách log")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách log",
                    content = @Content(array = @ArraySchema(
                            schema = @Schema(implementation = LogResponseClass.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema())),})
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/logs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel<LogModel> getAllLog(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to,
            @RequestParam(name = "module", required = false) String module
    ) throws Exception {
        Page<LogEntity> logEntityPage = this.logService.findAll(module, from, to, size, page);
        List<LogModel> listReturn = this.logService.convertListLogEntityToModel(logEntityPage);
        return super.success(listReturn, logEntityPage.getTotalPages(), page, size,logEntityPage.getTotalElements());
    }
}
