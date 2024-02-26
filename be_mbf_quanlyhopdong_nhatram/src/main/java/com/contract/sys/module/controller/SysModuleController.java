package com.contract.sys.module.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.contract.base.controller.BaseController;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.sys.module.model.MODULE;
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
@Tag(name = "Module")
public class SysModuleController extends BaseController {

    @Operation(summary = "Danh sách các module", description = "Danh sách các module")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả vể danh sách các module",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = List.class))))})
    @GetMapping(value = "/sys/module")
    public List<Map<String, Object>> getAllSysModule() {
        List<Map<String, Object>> listReturn = new ArrayList<>();
        MODULE.getAllValue().forEach(e -> {
            Map<String, Object> map = new HashMap<>();
            map.put("key", MODULE.valueOfLabel(e));
            map.put("value", e);
            listReturn.add(map);
        });
        return listReturn;
    }

}
