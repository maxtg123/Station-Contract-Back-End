package com.contract.danhmuc.hinhthucthanhtoan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.hinhthucthanhtoan.entity.DmHinhThucThanhToanEntity;
import com.contract.danhmuc.hinhthucthanhtoan.model.DmHinhThucThanhToanModel;
import com.contract.danhmuc.hinhthucthanhtoan.service.DmHinhThucThanhToanService;

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
@Tag(name = "Danh mục - Hình thức thanh toán")
public class DmHinhThucThanhToanController extends BaseController<DmHinhThucThanhToanModel> {
    private class DmHinhThucThanhToanResponseClass extends ResponseModel<DmHinhThucThanhToanModel> {
    }

    @Autowired
    private DmHinhThucThanhToanService dmHinhThucThanhToanService;

    @Operation(summary = "Danh sách danh mục hình thức thanh toán", description = "Danh sách danh mục hình thức thanh toán")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả vể danh sách tất cả danh mục hình thức thanh toán", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DmHinhThucThanhToanResponseClass.class)))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/hinh-thuc-thanh-toan")
    public ResponseModel<DmHinhThucThanhToanModel> getAll() throws Exception {
        List<DmHinhThucThanhToanEntity> listEntity = dmHinhThucThanhToanService.findAll();
        List<DmHinhThucThanhToanModel> listModel = dmHinhThucThanhToanService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(), (long) listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục hình thức thanh toán", description = "Thêm mới danh mục hình thức thanh toán")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục hình thức thanh toán vừa được tạo thành công", content = @Content(schema = @Schema(implementation = DmHinhThucThanhToanResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/hinh-thuc-thanh-toan")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmHinhThucThanhToanModel> create(
            @RequestBody DmHinhThucThanhToanModel dmHinhThucThanhToanModel) {
        return super.success(dmHinhThucThanhToanService.save(dmHinhThucThanhToanModel));
    }

    @Operation(summary = "Cập nhật danh mục hình thức thanh toán", description = "Cập nhật danh mục hình thức thanh toán")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục hình thức thanh toán vừa được cập nhật thành công", content = @Content(schema = @Schema(implementation = DmHinhThucThanhToanResponseClass.class))),
            @ApiResponse(responseCode = "400", description = "Mã hoặc Tên hình thức thanh toán không được để trống", content = @Content(schema = @Schema(implementation = DmHinhThucThanhToanResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/hinh-thuc-thanh-toan/{id}")
    public ResponseModel<DmHinhThucThanhToanModel> update(
            @RequestBody DmHinhThucThanhToanModel dmHinhThucThanhToanModel, @PathVariable("id") int id) {
        dmHinhThucThanhToanModel.setId(id);
        return super.success(dmHinhThucThanhToanService.update(dmHinhThucThanhToanModel));
    }

    @Operation(summary = "Xóa danh mục hình thức thanh toán ", description = "Xóa danh mục hình thức thanh toán")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục hình thức thanh toán vừa được xóa thành công", content = @Content(schema = @Schema(implementation = DmHinhThucThanhToanResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/hinh-thuc-thanh-toan/{id}")
    public ResponseModel<DmHinhThucThanhToanModel> delete(@PathVariable("id") Integer id) {
        try {
            dmHinhThucThanhToanService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }
}
