package com.contract.danhmuc.loaihangmuccsht.controller;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.loaihangmuccsht.entity.DmLoaiHangMucCSHTEntity;
import com.contract.danhmuc.loaihangmuccsht.model.DmLoaiHangMucCSHTModel;
import com.contract.danhmuc.loaihangmuccsht.service.DmLoaiHangMucCSHTService;
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
@Tag(name = "Danh mục - Loại hạng mục CSHT")
public class DmLoaiHangMucCSHTController extends BaseController<DmLoaiHangMucCSHTModel> {
    private class DmLoaiHangMucCSHTResponse extends ResponseModel<DmLoaiHangMucCSHTModel> {
    }

    @Autowired
    private DmLoaiHangMucCSHTService dmLoaiHangMucCSHTService;

    @Operation(summary = "Danh sách danh mục loại hạng mục csht", description = "Danh sách danh mục loại hạng mục csht")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả vể danh sách tất cả danh mục hạng mục loại csht",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = DmLoaiHangMucCSHTResponse.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/loai-hang-muc-csht")
    public ResponseModel<DmLoaiHangMucCSHTModel> getAll() throws Exception {
        List<DmLoaiHangMucCSHTEntity> listEntity = dmLoaiHangMucCSHTService.findAll();
        List<DmLoaiHangMucCSHTModel> listModel = dmLoaiHangMucCSHTService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(), (long) listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục loại hạng mục csht", description = "Thêm mới danh mục loại hạng mục csht")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục loại csht vừa được tạo thành công",
                    content = @Content(schema = @Schema(implementation = DmLoaiHangMucCSHTResponse.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/loai-hang-muc-csht")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmLoaiHangMucCSHTModel> create(@RequestBody DmLoaiHangMucCSHTModel dmLoaiHangMucCSHTModel) {
        return super.success(dmLoaiHangMucCSHTService.save(dmLoaiHangMucCSHTModel));
    }

    @Operation(summary = "Cập nhật danh mục loại hạng mục csht", description = "Cập nhật danh mục loại hạng mục csht")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục loại hạng mục csht vừa được cập nhật thành công",
                    content = @Content(schema = @Schema(implementation = DmLoaiHangMucCSHTResponse.class))),
            @ApiResponse(responseCode = "400", description = "Mã hoặc tên danh mục loại csht không được để trống",
                    content = @Content(schema = @Schema(implementation = DmLoaiHangMucCSHTResponse.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/loai-hang-muc-csht/{id}")
    public ResponseModel<DmLoaiHangMucCSHTModel> update(@RequestBody DmLoaiHangMucCSHTModel dmLoaiHangMucCSHTModel, @PathVariable("id") int id) {
        dmLoaiHangMucCSHTModel.setId(id);
        return super.success(dmLoaiHangMucCSHTService.update(dmLoaiHangMucCSHTModel));
    }

    @Operation(summary = "Xóa danh mục loại hạng mục csht ", description = "Xóa danh mục loại hạng mục csht")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục loại hạng mục csht vừa được xóa thành công",
                    content = @Content(schema = @Schema(implementation = DmLoaiHangMucCSHTResponse.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/loai-hang-muc-csht/{id}")
    public ResponseModel<DmLoaiHangMucCSHTModel> delete(@PathVariable("id") Integer id) {
        try {
            dmLoaiHangMucCSHTService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }
}
