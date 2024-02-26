package com.contract.danhmuc.loaiphongmayphatdien.controller;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.loaiphongmayphatdien.entity.DmLoaiPhongMayPhatDienEntity;
import com.contract.danhmuc.loaiphongmayphatdien.model.DmLoaiPhongMayPhatDienModel;
import com.contract.danhmuc.loaiphongmayphatdien.service.DmLoaiPhongMayPhatDienService;
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
@Tag(name = "Danh mục - Loại phòng máy phát điện")
public class DmLoaiPhongMayPhatDienController extends BaseController<DmLoaiPhongMayPhatDienModel> {
    private class DmLoaiPhongMayPhatDienResponseClass extends ResponseModel<DmLoaiPhongMayPhatDienModel> {
    }

    @Autowired
    private DmLoaiPhongMayPhatDienService dmLoaiPhongMayPhatDienService;

    @Operation(summary = "Danh sách danh mục loại phòng máy phát điện", description = "Danh sách danh mục loại phòng máy phát điện")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Trả vể danh sách tất cả danh mục loại Phòng máy phát điện",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DmLoaiPhongMayPhatDienResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/loai-phong-may-phat-dien")
    public ResponseModel<DmLoaiPhongMayPhatDienModel> getAll() throws Exception {
        List<DmLoaiPhongMayPhatDienEntity> listEntity = dmLoaiPhongMayPhatDienService.findAll();
        List<DmLoaiPhongMayPhatDienModel> listModel = dmLoaiPhongMayPhatDienService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(), (long) listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục loại Phòng máy phát điện", description = "Thêm mới danh mục loại Phòng máy phát điện")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Danh mục loại Phòng máy phát điện vừa được tạo thành công",
                    content = @Content(schema = @Schema(implementation = DmLoaiPhongMayPhatDienResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/loai-phong-may-phat-dien")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmLoaiPhongMayPhatDienModel> create(@RequestBody DmLoaiPhongMayPhatDienModel loaiPhongMayPhatDienModel) {
        return super.success(dmLoaiPhongMayPhatDienService.save(loaiPhongMayPhatDienModel));
    }

    @Operation(summary = "Cập nhật danh mục loại Phòng máy phát điện", description = "Cập nhật danh mục loại Phòng máy phát điện")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Danh mục loại Phòng máy phát điện vừa được cập nhật thành công",
                    content = @Content(schema = @Schema(implementation = DmLoaiPhongMayPhatDienResponseClass.class))),
            @ApiResponse(responseCode = "400",
                    description = "Mã hoặc tên loại Phòng máy phát điện không được để trống",
                    content = @Content(schema = @Schema(implementation = DmLoaiPhongMayPhatDienResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/loai-phong-may-phat-dien/{id}")
    public ResponseModel<DmLoaiPhongMayPhatDienModel> update(@RequestBody DmLoaiPhongMayPhatDienModel model, @PathVariable("id") int id) {
        model.setId(id);
        return super.success(dmLoaiPhongMayPhatDienService.update(model));
    }

    @Operation(summary = "Xóa danh mục loại Phòng máy phát điện", description = "Xóa danh mục loại Phòng máy phát điện")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục loại Phòng máy phát điện vừa được xóa thành công",
                    content = @Content(schema = @Schema(implementation = DmLoaiPhongMayPhatDienResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/loai-phong-may-phat-dien/{id}")
    public ResponseModel<DmLoaiPhongMayPhatDienModel> delete(@PathVariable("id") int id) {
        try {
            dmLoaiPhongMayPhatDienService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }
}
