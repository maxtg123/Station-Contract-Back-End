package com.contract.sys.action.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.contract.base.controller.BaseController;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.sys.action.model.ACTION;
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
@Tag(name = "Action trên module")
public class SysActionOnModuleController extends BaseController {

    @Operation(summary = "Danh sách các action", description = "Danh sách các action")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả vể danh sách các action",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = List.class))))})
    @GetMapping(value = "/sys/action-on-module")
    public List<Map<String, Object>> getAllSysActionOnModule() {
        List<Map<String, Object>> listReturn = new ArrayList<>();
        ACTION.getAllValue().forEach(e -> {
            Map<String, Object> map = new HashMap<>();
            map.put("key", ACTION.valueOfLabel(e));
            map.put("value", e);
            listReturn.add(map);
        });
        return listReturn;
    }

}
