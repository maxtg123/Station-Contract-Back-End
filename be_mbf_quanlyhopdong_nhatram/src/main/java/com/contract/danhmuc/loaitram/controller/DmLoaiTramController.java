package com.contract.danhmuc.loaitram.controller;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.loaithietbiran.controller.DmLoaiThietBiRanController;
import com.contract.danhmuc.loaithietbiran.model.DmLoaiThietBiRanModel;
import com.contract.danhmuc.loaitram.entity.DmLoaiTramEntity;
import com.contract.danhmuc.loaitram.model.DmLoaiTramModel;
import com.contract.danhmuc.loaitram.service.DmLoaiTramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@ApiPrefixController
@Tag(name = "Danh mục - Loại trạm")
public class DmLoaiTramController extends BaseController<DmLoaiTramModel> {

    private class DmLoaiTramResponseClass extends ResponseModel<DmLoaiTramModel> {}

    @Autowired
    private DmLoaiTramService dmLoaiTramService;

    @Operation(summary = "Danh sách danh mục loại trạm",
        description = "Danh sách danh mục loại trạm")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Trả vể danh sách tất cả danh mục loại trạm",
        content = @Content(array = @ArraySchema(schema = @Schema(
            implementation = DmLoaiTramResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/loai-tram")
    public ResponseModel<DmLoaiTramModel> getAllLoaiTram() throws Exception {
        List<DmLoaiTramEntity> listEntity = dmLoaiTramService.findAll();
        List<DmLoaiTramModel> listModel = dmLoaiTramService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(),(long)listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục loại trạm",
        description = "Thêm mới danh mục loại trạm")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Danh mục loại trạm vừa được tạo thành công",
        content = @Content(
            schema = @Schema(implementation = DmLoaiTramResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/loai-tram")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmLoaiTramModel> create(@RequestBody DmLoaiTramModel loaiTramModel) {
        return super.success(dmLoaiTramService.save(loaiTramModel));
    }

    @Operation(summary = "Cập nhật danh mục loại trạm",
        description = "Cập nhật danh mục loại trạm")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Danh mục loại trạm vừa được cập nhật thành công",
        content = @Content(
            schema = @Schema(implementation = DmLoaiTramResponseClass.class))),
        @ApiResponse(responseCode = "400",
            description = "Mã hoặc tên trạm không được để trống",
            content = @Content(schema = @Schema(
                implementation = DmLoaiTramModel.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/loai-tram/{id}")
    public ResponseModel<DmLoaiTramModel> update(@RequestBody DmLoaiTramModel loaiTramModel,
                                  @PathVariable("id") int id) {
        loaiTramModel.setId(id);
        return super.success(dmLoaiTramService.update(loaiTramModel));
    }

    @Operation(summary = "Xóa danh mục Loại Trạm", description = "Xóa danh mục Loại Trạm")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Danh mục Loại Trạm vừa được xóa thành công",
            content = @Content(schema = @Schema(implementation = DmLoaiTramController.DmLoaiTramResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/loai-tram/{id}")
    public ResponseModel<DmLoaiTramModel> delete(@PathVariable("id") Integer id) {
        try {
            dmLoaiTramService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }
}