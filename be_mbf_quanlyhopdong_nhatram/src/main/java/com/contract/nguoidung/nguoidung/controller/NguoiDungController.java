package com.contract.nguoidung.nguoidung.controller;

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
import com.contract.common.annotation.ApiPrefixController;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

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
@Tag(name = "Người dùng", description = "API quản lý người dùng")
public class NguoiDungController extends BaseController<NguoiDungModel> {

        private class NguoiDungResponseClass extends ResponseModel<NguoiDungModel> {
        }

        @Autowired
        private NguoiDungService nguoiDungService;

        @Operation(summary = "Lấy danh sách người dùng", description = "Lấy danh sách người dùng")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Trả về danh sách người dùng", content = @Content(array = @ArraySchema(schema = @Schema(implementation = NguoiDungResponseClass.class)))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema())), })
        @SecurityRequirement(name = "Bearer Authentication")
        @GetMapping(value = "/nguoi-dung", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseModel<NguoiDungModel> getAllNguoiDung() throws Exception {
                List<NguoiDungModel> nguoiDungModelList = this.nguoiDungService.findAll();
                return super.success(nguoiDungModelList, 1, 0, nguoiDungModelList.size(),
                                (long) nguoiDungModelList.size());
        }

        @Operation(summary = "Tạo mới người dùng", description = "Tạo mới người dùng")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Trả về người dùng mới tạo", content = @Content(schema = @Schema(implementation = NguoiDungResponseClass.class))) })
        @SecurityRequirement(name = "Bearer Authentication")
        @PreAuthorize("@kiemTraQuyenModuleNguoiDung.kiemTraQuyenThemMoi(#nguoiDung)")
        @PostMapping(value = "/nguoi-dung", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseModel<NguoiDungModel> create(@RequestBody NguoiDungModel nguoiDung) throws Exception {
                return super.success(nguoiDungService.createNguoiDung(nguoiDung));
        }

        @Operation(summary = "Cập nhật người dùng", description = "Cập nhật người dùng")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Trả về người dùng mới cập nhật", content = @Content(schema = @Schema(implementation = NguoiDungResponseClass.class))) })
        @SecurityRequirement(name = "Bearer Authentication")
        @PreAuthorize("@kiemTraQuyenModuleNguoiDung.kiemTraQuyenCapNhat(#nguoiDung,#id)")
        @PutMapping(value = "/nguoi-dung/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseModel<NguoiDungModel> update(@RequestBody NguoiDungModel nguoiDung, @PathVariable Long id)
                        throws Exception {
                return super.success(nguoiDungService.updateNguoiDung(nguoiDung, id));
        }

        @Operation(summary = "Xóa người dùng", description = "Xóa người dùng")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Trả về người dùng vừa xóa", content = @Content(schema = @Schema(implementation = NguoiDungResponseClass.class))) })
        @SecurityRequirement(name = "Bearer Authentication")
        @PreAuthorize("@kiemTraQuyenModuleNguoiDung.kiemTraQuyenXoa(#id)")
        @DeleteMapping(value = "/nguoi-dung/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseModel<NguoiDungModel> delete(@PathVariable Long id) throws Exception {
                return super.success(nguoiDungService.delete(id));
        }

        // @Operation(summary = "Xem profile người dùng", description = "Xem profile
        // người dùng")
        // @ApiResponses(value = {
        // @ApiResponse(responseCode = "200", description = "Trả về thông tin profile
        // người dùng",
        // content = @Content(array = @ArraySchema(
        // schema = @Schema(implementation = ProfileModel.class)))),
        // @ApiResponse(responseCode = "500", description = "Internal server error",
        // content = @Content(schema = @Schema())),})
        // @SecurityRequirement(name = "Bearer Authentication")
        // @GetMapping(value = "/nguoi-dung/profile", produces =
        // MediaType.APPLICATION_JSON_VALUE)
        // public ProfileModel getProfile() {
        // return nguoiDungService.getProfile(nguoiDungService.getUsername());
        // }
}
