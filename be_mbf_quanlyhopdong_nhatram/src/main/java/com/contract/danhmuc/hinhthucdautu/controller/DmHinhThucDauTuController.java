package com.contract.danhmuc.hinhthucdautu.controller;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.doituongkyhopdong.entity.DmDoiTuongKyHopDongEntity;
import com.contract.danhmuc.doituongkyhopdong.model.DmDoiTuongKyHopDongModel;
import com.contract.danhmuc.hinhthucdautu.entity.DmHinhThucDauTuEntity;
import com.contract.danhmuc.hinhthucdautu.model.DmHinhThucDauTuModel;
import com.contract.danhmuc.hinhthucdautu.service.DmHinhThucDauTuService;
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
@Tag(name = "Danh mục - Hình thức đầu tư")
public class DmHinhThucDauTuController extends BaseController<DmHinhThucDauTuModel> {

    private class DmHinhThucDauTuResponseClass extends ResponseModel<DmHinhThucDauTuModel> {}

    @Autowired
    private DmHinhThucDauTuService dmHinhThucDauTuService;

    @Operation(summary = "Danh sách danh mục hình thức đầu tư", description = "Danh sách danh mục hình thức đầu tư")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả vể danh sách tất cả danh mục hình thức đầu tư",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = DmHinhThucDauTuResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/hinh-thuc-dau-tu")
    public ResponseModel<DmHinhThucDauTuModel> getAllHinhThucDauTu() throws Exception{
        List<DmHinhThucDauTuEntity> listEntity = dmHinhThucDauTuService.findAll();
        List<DmHinhThucDauTuModel> listModel = dmHinhThucDauTuService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(),(long)listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục Hình thức đầu tư", description = "Thêm mới danh mục Hình thức đầu tư")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục HTĐT vừa được tạo thành công",
                    content = @Content(schema = @Schema(implementation = DmHinhThucDauTuResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/hinh-thuc-dau-tu")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmHinhThucDauTuModel> create(@RequestBody DmHinhThucDauTuModel hinhThucDauTuModel) {
        return super.success(dmHinhThucDauTuService.save(hinhThucDauTuModel));
    }

    @Operation(summary = "Cập nhật danh mục Hình thức đầu tư", description = "Cập nhật danh mục Hình thức đầu tư")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục HTĐT vừa được cập nhật thành công",
                    content = @Content(schema = @Schema(implementation = DmHinhThucDauTuResponseClass.class))),
            @ApiResponse(responseCode = "400", description = "Mã hoặc tên HTĐT không được để trống",
                    content = @Content(schema = @Schema(implementation = DmHinhThucDauTuModel.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/hinh-thuc-dau-tu/{id}")
    public ResponseModel<DmHinhThucDauTuModel> update(@RequestBody DmHinhThucDauTuModel hinhThucDauTuModel, @PathVariable("id") int id) {
        hinhThucDauTuModel.setId(id);
        return super.success(dmHinhThucDauTuService.update(hinhThucDauTuModel));
    }

    @Operation(summary = "Xóa danh mục Hình Thức Đầu Tư ", description = "Xóa danh mục Hình Thức Đầu Tư")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục Hình thức đầu tư vừa được xóa thành công",
                    content = @Content(schema = @Schema(implementation = DmHinhThucDauTuResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/hinh-thuc-dau-tu/{id}")
    public ResponseModel<DmHinhThucDauTuModel> delete(@PathVariable("id") Integer id) {
        try {
            dmHinhThucDauTuService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }
}
