package com.contract.thongbao.controller;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.thongbao.entity.ThongBaoEntity;
import com.contract.thongbao.model.ThongBaoModel;
import com.contract.thongbao.model.TrangThaiThongBao;
import com.contract.thongbao.service.ThongBaoService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@ApiPrefixController
@Tag(name = "Thông báo", description = "API quản lý thông báo")
public class ThongBaoController extends BaseController<ThongBaoModel> {
    private class ThongBaoResponseClass extends ResponseModel<ThongBaoModel> {
    }

    @Autowired
    private ThongBaoService thongBaoService;

    @Operation(summary = "Danh sách thông báo", description = "Danh sách danh thông báo")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Trả vể danh sách tất cả thông báo",
            content = @Content(array = @ArraySchema(
                    schema = @Schema(implementation = ThongBaoResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/thong-bao")
    public ResponseModel<ThongBaoModel> getAll(
            @RequestParam(value = "nguoiGuiId", required = false) Long nguoiGuiId,
            @RequestParam(value = "module", required = false) String module,
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "trangThai", required = false) TrangThaiThongBao trangThaiThongBao,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) throws Exception {
        Page<ThongBaoEntity> thongBaoModelPage = thongBaoService.getAll(nguoiGuiId, module, action, trangThaiThongBao, page, size);
        List<ThongBaoModel> listReturn = thongBaoModelPage.getContent().stream().map(entity -> ThongBaoModel.fromEntity(entity, true)).collect(Collectors.toList());
        return super.success(listReturn, thongBaoModelPage.getTotalPages(), page != null ? page : 0, size != null ? size : (int) thongBaoModelPage.getTotalElements(), thongBaoModelPage.getTotalElements());
    }

    @Operation(summary = "Cập nhật thông báo", description = "Cập nhật thông báo")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Trả vể thông báo đã cập nhật",
            content = @Content(array = @ArraySchema(
                    schema = @Schema(implementation = ThongBaoResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/thong-bao/{id}")
    public ResponseModel<ThongBaoModel> update(@PathVariable("id") Long id) throws Exception {
        return super.success(thongBaoService.update(id));
    }

    @Operation(summary = "Số lượng thông báo chưa đọc", description = "Số lượng thông báo chưa đọc")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/thong-bao/number")
    public Long getNumberOfThongBao(@RequestParam(value = "trangThai", required = false) TrangThaiThongBao trangThaiThongBao) throws Exception {
        Long numberOfUnseen = thongBaoService.getAll(null, null, null, trangThaiThongBao, null, null).getTotalElements();
        return numberOfUnseen;
    }

}
