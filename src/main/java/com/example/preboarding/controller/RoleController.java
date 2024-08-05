package com.example.preboarding.controller;

import com.example.preboarding.dto.request.CompanyReq;
import com.example.preboarding.dto.request.RoleReq;
import com.example.preboarding.dto.response.JobPositionPostRes;
import com.example.preboarding.dto.response.RegistRes;
import com.example.preboarding.exception.CustomException;
import com.example.preboarding.service.CompanyService;
import com.example.preboarding.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Tag(name = "직무 역할 관련 API", description = "Swagger API")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    @Operation(summary = "직무 역할 등록 " ,description = "직무 역할 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Success", content = @Content(schema = @Schema(implementation = RegistRes.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout", content = @Content(schema = @Schema(implementation = CustomException.class))),
    })
    public RegistRes registJobPositionPost(@RequestBody @Valid @NotNull RoleReq registRole){
        try{
            Long roleNum = roleService.addRole(registRole);

            return RegistRes.builder()
                    .status(HttpStatus.OK.value())
                    .message("새로운 회사가 등록 되었습니다.")
                    .registNum(roleNum)
                    .build();

        }catch (CustomException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }
}