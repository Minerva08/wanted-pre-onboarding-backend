package com.example.preboarding.controller;

import com.example.preboarding.domain.Company;
import com.example.preboarding.domain.CompanyRole;
import com.example.preboarding.domain.JobPosition;
import com.example.preboarding.dto.request.CompanyReq;
import com.example.preboarding.dto.response.RegistRes;
import com.example.preboarding.dto.response.UpdateStatusRes;
import com.example.preboarding.exception.CustomException;
import com.example.preboarding.service.CompanyService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/companies")
@Tag(name = "회사 관련 API", description = "Swagger API")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    @Operation(summary = "회사 등록" ,description = "회사 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Success", content = @Content(schema = @Schema(implementation = RegistRes.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout", content = @Content(schema = @Schema(implementation = CustomException.class))),
    })
    public RegistRes  registCompnay(@RequestBody @Valid @NotNull CompanyReq registCom){
        try{
            Long comNum = companyService.addCompany(registCom);

            return RegistRes.builder()
                    .status(HttpStatus.OK.value())
                    .message("새로운 회사가 등록 되었습니다.")
                    .registNum(comNum)
                    .build();

        }catch (CustomException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/{comNum}")
    @Operation(summary = "회사 삭제" ,description = "회사 및 관련 직무 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Success", content = @Content(schema = @Schema(implementation = UpdateStatusRes.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout", content = @Content(schema = @Schema(implementation = CustomException.class))),
    })
    public UpdateStatusRes deleteCompany(@PathVariable @Valid @NotNull Long comNum){
        try{
            companyService.deleteCompany(comNum);

            return UpdateStatusRes.builder()
                    .status(HttpStatus.OK.value())
                    .message("해당 회사가 삭제 되었습니다.")
                    .comNum(comNum)
                    .build();

        }catch (CustomException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

}
