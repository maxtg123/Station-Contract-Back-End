package com.contract.danhmuc.donvidungchung.controller;


import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.donvidungchung.dto.DmDonViDungChungDto;
import com.contract.danhmuc.donvidungchung.model.DmDonViDungChungModel;
import com.contract.danhmuc.donvidungchung.repository.DmDonViDungChungRepository;
import com.contract.danhmuc.donvidungchung.service.DmDonViDungChungService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@ApiPrefixController
@Tag(name = "Danh mục - Dùng chung")
public class DmDonViDungChungController extends BaseController<DmDonViDungChungModel> {
    @Autowired
    private DmDonViDungChungService dmDungChungService;

    @Autowired
    private DmDonViDungChungRepository dmDonViDungChungRepository;

    private class DmDungChungResponseClass extends ResponseModel<DmDonViDungChungModel> {
    }

    @Operation(summary = "Danh sách danh mục dùng chung", description = "Danh sách danh mục dùng chung")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả vể danh sách tất cả danh mục dùng chung",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = DmDungChungResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/dung-chung")
    public ResponseModel<DmDonViDungChungModel> getAllDungChung() throws Exception {
        List<DmDonViDungChungModel> listModel = dmDungChungService.findAll();
        return super.success(listModel, 1, 0, listModel == null ? 0 : listModel.size(), (long) listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục Dùng chung", description = "Thêm mới danh mục Dùng chung")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục dùng chung vừa được tạo thành công",
                    content = @Content(schema = @Schema(implementation = DmDungChungResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/dung-chung")
    public ResponseEntity<?> create(@RequestBody DmDonViDungChungDto dmDonViDungChungDto) {
        DmDonViDungChungModel dmDonViDungChungModel = dmDungChungService.saveDm(dmDonViDungChungDto);
        if (dmDonViDungChungModel == null) {
            return new ResponseEntity<String>("Ten danh muc khong duoc rong!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<DmDonViDungChungModel>(dmDonViDungChungModel, HttpStatus.OK);
    }

    @Operation(summary = "Cập nhật danh mục Dùng chung", description = "Cập nhật danh mục Dùng chung")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục Dùng chung vừa được cập nhật thành công",
                    content = @Content(schema = @Schema(implementation = DmDungChungResponseClass.class))),
            @ApiResponse(responseCode = "400", description = "Mã hoặc tên Dùng chung không được để trống",
                    content = @Content(schema = @Schema(implementation = DmDonViDungChungModel.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/dung-chung/{id}")
    public ResponseEntity<?> update(@RequestBody DmDonViDungChungDto dmDonViDungChungDto, @PathVariable("id") int id) {
        DmDonViDungChungModel dmDonViDungChungModel = dmDonViDungChungRepository.findById(id);
        if (dmDonViDungChungModel != null) {
            DmDonViDungChungModel dmUpdate = dmDungChungService.update(id, dmDonViDungChungDto);
            if (dmUpdate != null) {
                return new ResponseEntity<DmDonViDungChungModel>(dmUpdate, HttpStatus.OK);
            }
            return new ResponseEntity<String>("Ten danh muc khong duoc rong!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Khong tim thay id danh muc này!", HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Xóa danh mục Dùng chung", description = "Xóa danh mục Dùng chung")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục Dùng chung vừa được xóa thành công",
                    content = @Content(schema = @Schema(implementation = DmDungChungResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/dung-chung/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            boolean check = dmDungChungService.delete(id);
            if (check == true) {
                return new ResponseEntity<String>("Xoa thanh cong!", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Khong tim thay id danh muc nay!", HttpStatus.BAD_REQUEST);
            }
        } catch (DeleteInUseDataException e) {
            return new ResponseEntity<ResponseModel<DmDonViDungChungModel>>(super.error(4001, "data in use"), HttpStatus.BAD_REQUEST);
        }
    }
}
