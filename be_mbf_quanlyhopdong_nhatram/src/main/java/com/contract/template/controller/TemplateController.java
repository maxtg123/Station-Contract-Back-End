package com.contract.template.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.danhmuc.loaicsht.model.DmLoaiCshtModel;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin
@RestController
@ApiPrefixController
public class TemplateController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @Operation(summary = "Xuất template import",
            description = "Một số loại template: <br>"
                    + "\"LoaiCSHT\" : Template cho import danh mục loại cơ sở hạ tầng")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Template vừa được cập nhật thành công",
            content = @Content(schema = @Schema(implementation = DmLoaiCshtModel.class)))})
    @GetMapping("/template/{loaiTemplate}")
    public ResponseEntity<?> xuatTemplate(@PathVariable("loaiTemplate") String loaiTemplate) {
        String username = nguoiDungService.getUsername();
        if (username == null || username.isEmpty()) {
            return null;
        }
        String fileName = "";
        Path path = null;
        try {
            switch (loaiTemplate) {
                case "LoaiCSHT":
                    fileName = "Template_danhmuc_loaicsht.xlsx";
                    path = Paths.get("src" + File.separator + "main" + File.separator + "resources"
                            + File.separator + "templates" + File.separator + "danhmuc"
                            + File.separator + "loai_csht" + File.separator + fileName);
                    break;
                default:
                    path = Paths.get("");
            }

            File file = new File(path.toString());
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File template không tồn tại!");
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

            Files.copy(path, outputStream);
            return new ResponseEntity<>(new ByteArrayResource(outputStream.toByteArray()), headers,
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra!");
        }
    }
}
