package com.contract.danhmuc.doituongkyhopdong.controller;

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
import com.contract.danhmuc.doituongkyhopdong.entity.DmDoiTuongKyHopDongEntity;
import com.contract.danhmuc.doituongkyhopdong.model.DmDoiTuongKyHopDongModel;
import com.contract.danhmuc.doituongkyhopdong.service.DmDoiTuongKyHopDongService;
import com.contract.danhmuc.loaicsht.model.DmLoaiCshtModel;

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
@Tag(name = "Danh mục - Đối tượng ký hợp đồng")
public class DmDoiTuongKyHopDongController extends BaseController<DmDoiTuongKyHopDongModel> {

    private class DmDoiTuongKyHopDongResponseClass extends ResponseModel<DmDoiTuongKyHopDongModel> {
    }

    @Autowired
    private DmDoiTuongKyHopDongService dmDoiTuongKyHopDongService;

    @Operation(summary = "Danh sách danh mục đối tượng ký hợp đồng", description = "Danh sách danh mục đối tượng ký hợp đồng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả vể danh sách tất cả danh mục đối tượng ký hợp đồng", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DmDoiTuongKyHopDongResponseClass.class)))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/doi-tuong-ky-hop-dong")
    public ResponseModel<DmDoiTuongKyHopDongModel> getAllDoiTuongKyHopDong() throws Exception {
        List<DmDoiTuongKyHopDongEntity> listEntity = dmDoiTuongKyHopDongService.findAll();
        List<DmDoiTuongKyHopDongModel> listModel = dmDoiTuongKyHopDongService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(), (long) listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục đối tượng ký hợp đồng", description = "Thêm mới danh mục đối tượng ký hợp đồng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục đối tượng ký hợp đồng vừa được tạo thành công", content = @Content(schema = @Schema(implementation = DmDoiTuongKyHopDongResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/doi-tuong-ky-hop-dong")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmDoiTuongKyHopDongModel> create(
            @RequestBody DmDoiTuongKyHopDongModel doiTuongKyHopDongModel) {
        return super.success(dmDoiTuongKyHopDongService.save(doiTuongKyHopDongModel));
    }

    @Operation(summary = "Cập nhật danh mục đối tượng ký hợp đồng", description = "Cập nhật danh mục đối tượng ký hợp đồng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục đối tượng ký hợp đồng vừa được cập nhật thành công", content = @Content(schema = @Schema(implementation = DmDoiTuongKyHopDongResponseClass.class))),
            @ApiResponse(responseCode = "400", description = "Mã hoặc Tên đối tượng ký hợp đồng không được để trống", content = @Content(schema = @Schema(implementation = DmLoaiCshtModel.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/doi-tuong-ky-hop-dong/{id}")
    public ResponseModel<DmDoiTuongKyHopDongModel> update(@RequestBody DmDoiTuongKyHopDongModel doiTuongKyHopDongModel,
            @PathVariable("id") int id) {
        doiTuongKyHopDongModel.setId(id);
        return super.success(dmDoiTuongKyHopDongService.update(doiTuongKyHopDongModel));
    }

    @Operation(summary = "Xóa danh mục Đối tượng ký hợp đồng ", description = "Xóa danh mục Đối tượng ký hợp đồng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục Đối tượng ký hợp đồng vừa được xóa thành công", content = @Content(schema = @Schema(implementation = DmDoiTuongKyHopDongResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/doi-tuong-ky-hop-dong/{id}")
    public ResponseModel<DmDoiTuongKyHopDongModel> delete(@PathVariable("id") Integer id) {
        try {
            dmDoiTuongKyHopDongService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }
}