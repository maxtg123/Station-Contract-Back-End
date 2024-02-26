package com.contract.danhmuc.hinhthuckyhopdong.controller;

import java.util.List;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.hinhthucdautu.controller.DmHinhThucDauTuController;
import com.contract.danhmuc.hinhthucdautu.model.DmHinhThucDauTuModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.danhmuc.hinhthuckyhopdong.entity.DmHinhThucKyHopDongEntity;
import com.contract.danhmuc.hinhthuckyhopdong.model.DmHinhThucKyHopDongModel;
import com.contract.danhmuc.hinhthuckyhopdong.service.DmHinhThucKyHopDongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@ApiPrefixController
@Tag(name = "Danh mục - Hình thức ký hợp đồng")
public class DmHinhThucKyHopDongController extends BaseController<DmHinhThucKyHopDongModel> {

    private class DmHinhThucKyHopDongResponseClass extends ResponseModel<DmHinhThucKyHopDongModel> {}

    @Autowired
    private DmHinhThucKyHopDongService dmHinhThucKyHopDongService;

    @Operation(summary = "Danh sách danh mục Hình thức ký hợp đồng",
            description = "Danh sách danh mục Hình thức ký hợp đồng")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Trả vể danh sách tất cả danh mục Hình thức ký hợp đồng",
            content = @Content(array = @ArraySchema(schema = @Schema(
                    implementation = DmHinhThucKyHopDongResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/hinh-thuc-ky-hop-dong")
    public ResponseModel<DmHinhThucKyHopDongModel> getAllHinhThucKyHopDong() throws Exception {
        List<DmHinhThucKyHopDongEntity> listEntity = dmHinhThucKyHopDongService.findAll();
        List<DmHinhThucKyHopDongModel> listModel = dmHinhThucKyHopDongService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(), (long)listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục Hình thức ký hợp đồng",
            description = "Thêm mới danh mục Hình thức ký hợp đồng")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Danh mục loại Hình thức ký hợp đồng vừa được tạo thành công",
            content = @Content(schema = @Schema(
                    implementation = DmHinhThucKyHopDongResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/hinh-thuc-ky-hop-dong")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmHinhThucKyHopDongModel> create(
            @RequestBody DmHinhThucKyHopDongModel hinhThucKyHopDongModel) {
        return super.success(dmHinhThucKyHopDongService.save(hinhThucKyHopDongModel));
    }

    @Operation(summary = "Cập nhật danh mục Hình thức ký hợp đồng",
            description = "Cập nhật danh mục Hình thức ký hợp đồng")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Danh mục Hình thức ký hợp đồng vừa được cập nhật thành công",
            content = @Content(schema = @Schema(
                    implementation = DmHinhThucKyHopDongResponseClass.class))),
            @ApiResponse(responseCode = "400",
                    description = "Mã hoặc tên Hình thức ký hợp đồng không được để trống",
                    content = @Content(schema = @Schema(
                            implementation = DmHinhThucKyHopDongModel.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/hinh-thuc-ky-hop-dong/{id}")
    public ResponseModel<DmHinhThucKyHopDongModel> update(
            @RequestBody DmHinhThucKyHopDongModel hinhThucKyHopDongModel,
            @PathVariable("id") int id) {
        hinhThucKyHopDongModel.setId(id);
        return super.success(dmHinhThucKyHopDongService.update(hinhThucKyHopDongModel));
    }

    @Operation(summary = "Xóa danh mục Hình Thức Ký Hợp Đồng", description = "Xóa danh mục Hình Thức Ký Hợp Đồng")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Danh mục Hình Thức Ký Hợp Đồng vừa được xóa thành công",
            content = @Content(schema = @Schema(implementation = DmHinhThucKyHopDongController.DmHinhThucKyHopDongResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/hinh-thuc-ky-hop-dong/{id}")
    public ResponseModel<DmHinhThucKyHopDongModel> delete(@PathVariable("id") Integer id) {
        try {
            dmHinhThucKyHopDongService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }
}
