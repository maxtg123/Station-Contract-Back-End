package com.contract.chucvu.chucvu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.chucvu.chucvu.model.ChucVuModel;
import com.contract.chucvu.chucvu.service.ChucVuService;
import com.contract.common.annotation.ApiPrefixController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@ApiPrefixController
@Tag(name = "Chức vụ", description = "API quản lý chức vụ")
public class ChucVuController extends BaseController<ChucVuModel> {

    private class ChucVuResponseClass extends ResponseModel<ChucVuModel> {
    }

    @Autowired
    private ChucVuService chucVuService;

    @Operation(summary = "Danh sách chức vụ", description = "Danh sách chức vụ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả vể danh sách chức vụ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChucVuResponseClass.class)))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/chuc-vu", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel<ChucVuModel> getAllChucVu() {
        List<ChucVuModel> chucVuModelList = chucVuService.findAll();
        return super.success(chucVuModelList, 1, 0, chucVuModelList.size(), (long) chucVuModelList.size());
    }

    @Operation(summary = "Tạo mới chức vụ", description = "Tạo mới chức vụ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về chức vụ mới tạo", content = @Content(schema = @Schema(implementation = ChucVuResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleChucVu.kiemTraQuyenThemMoi(#chucVu)")
    @PostMapping(value = "/chuc-vu", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel<ChucVuModel> create(@RequestBody ChucVuModel chucVu) throws Exception {
        return super.success(chucVuService.create(chucVu));
    }

    @Operation(summary = "Cập nhật chức vụ", description = "Cập nhật chức vụ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về chức vụ mới cập nhật", content = @Content(schema = @Schema(implementation = ChucVuResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleChucVu.kiemTraQuyenCapNhat(#chucVu,#id)")
    @PutMapping(value = "/chuc-vu/{id}")
    public ResponseModel<ChucVuModel> update(@RequestBody ChucVuModel chucVu, @PathVariable Long id)
            throws Exception {
        return super.success(chucVuService.update(chucVu, id));
    }

    @Operation(summary = "Xóa chức vụ", description = "Xóa chức vụ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về chức vụ vừa xóa", content = @Content(schema = @Schema(implementation = ChucVuResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleChucVu.kiemTraQuyenXoa(#id)")
    @DeleteMapping(value = "/chuc-vu/{id}")
    public ResponseModel<ChucVuModel> delete(@PathVariable Long id) throws Exception {
        return super.success(chucVuService.delete(id));
    }
}
