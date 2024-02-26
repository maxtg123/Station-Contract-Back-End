package com.contract.danhmuc.loaitramvhkt.controller;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.loaitramvhkt.entity.DmLoaiTramVHKTEntity;
import com.contract.danhmuc.loaitramvhkt.model.DmLoaiTramVHKTModel;
import com.contract.danhmuc.loaitramvhkt.service.DmLoaiTramVHKTService;
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
@Tag(name = "Danh mục - Loại trạm VHKT")
public class DmLoaiTramVHKTController extends BaseController<DmLoaiTramVHKTModel> {

    private class DmLoaiTramVHKTResponseClass extends ResponseModel<DmLoaiTramVHKTModel> {
    }

    @Autowired
    private DmLoaiTramVHKTService dmLoaiTramVHKTService;

    @Operation(summary = "Danh sách danh mục loại trạm VHKT", description = "Danh sách danh mục loại trạm VHKT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Trả vể danh sách tất cả danh mục loại trạm VHKT",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DmLoaiTramVHKTResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/loai-tram-vhkt")
    public ResponseModel<DmLoaiTramVHKTModel> getAll() throws Exception {
        List<DmLoaiTramVHKTEntity> listEntity = dmLoaiTramVHKTService.findAll();
        List<DmLoaiTramVHKTModel> listModel = dmLoaiTramVHKTService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(), (long) listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục loại trạm vhkt", description = "Thêm mới danh mục loại trạm vhkt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Danh mục loại trạm vhkt vừa được tạo thành công",
                    content = @Content(schema = @Schema(implementation = DmLoaiTramVHKTResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/loai-tram-vhkt")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmLoaiTramVHKTModel> create(@RequestBody DmLoaiTramVHKTModel model) {
        return super.success(dmLoaiTramVHKTService.save(model));
    }

    @Operation(summary = "Cập nhật danh mục loại trạm vhkt", description = "Cập nhật danh mục loại trạm vhkt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Danh mục loại trạm vhkt vừa được cập nhật thành công",
                    content = @Content(schema = @Schema(implementation = DmLoaiTramVHKTResponseClass.class))),
            @ApiResponse(responseCode = "400",
                    description = "Mã hoặc tên loại loại trạm vhkt không được để trống",
                    content = @Content(schema = @Schema(implementation = DmLoaiTramVHKTResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/loai-tram-vhkt/{id}")
    public ResponseModel<DmLoaiTramVHKTModel> update(@RequestBody DmLoaiTramVHKTModel model, @PathVariable("id") int id) {
        model.setId(id);
        return super.success(dmLoaiTramVHKTService.update(model));
    }

    @Operation(summary = "Xóa danh mục loại trạm vhkt", description = "Xóa danh mục loại trạm vhkt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục loại trạm vhkt vừa được xóa thành công",
                    content = @Content(schema = @Schema(implementation = DmLoaiTramVHKTResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/loai-tram-vhkt/{id}")
    public ResponseModel<DmLoaiTramVHKTModel> delete(@PathVariable("id") int id) {
        try {
            dmLoaiTramVHKTService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }
}
