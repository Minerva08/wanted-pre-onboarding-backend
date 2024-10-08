package com.example.preboarding.controller;

import com.example.preboarding.domain.CompanyRole;
import com.example.preboarding.dto.JobRoleInfoDTO;
import com.example.preboarding.dto.response.CompanyPosition;
import com.example.preboarding.dto.response.JobPostInfoRes;
import com.example.preboarding.dto.response.UpdateStatusRes;
import com.example.preboarding.exception.CustomException;
import com.example.preboarding.service.CompanyRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/jobpositions")
@Tag(name = "회사별 직무 관련 API", description = "Swagger API")
public class CompanyPositionController {

    private final CompanyRoleService companyRoleService;

    @GetMapping()
    @Operation(summary = "회사의 등록된 직무 조회" ,description = "회사의 직무 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Success", content = @Content(schema = @Schema(implementation = CompanyPosition.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout", content = @Content(schema = @Schema(implementation = CustomException.class))),
    })
    public CompanyPosition getPositionList (@RequestParam(name = "comNum") Long comNum){
        try{
            List<JobRoleInfoDTO> companyPositionList = companyRoleService.getCompanyRoleList(comNum, null);
            return CompanyPosition.builder()
                    .message("회사의 직무가 조회 되었습니다")
                    .status(HttpStatus.OK.value())
                    .companyRoleList(companyPositionList)
                    .build();

        }catch (CustomException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @DeleteMapping()
    @Operation(summary = "회사의 등록된 직무 삭제" ,description = "회사의 직무 삭제 및 관련 채용 공고 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Success", content = @Content(schema = @Schema(implementation = JobPostInfoRes.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout", content = @Content(schema = @Schema(implementation = CustomException.class))),
    })
    public UpdateStatusRes deleteCompanyJobPosition (@RequestParam(name = "comNum") Long comNum, @RequestParam(name = "roleNum") Long roleNum){
        try{
            companyRoleService.deleteCompanyRole(comNum, roleNum);
            return UpdateStatusRes.builder()
                    .message("해당 직무가 정상적으로 삭제 되었습니다")
                    .status(HttpStatus.OK.value())
                    .comNum(comNum)
                    .roleNum(roleNum)
                    .build();

        }catch (CustomException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }


}
