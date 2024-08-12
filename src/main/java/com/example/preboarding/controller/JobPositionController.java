package com.example.preboarding.controller;

import com.example.preboarding.dto.request.JobPostReq;
import com.example.preboarding.dto.request.JobPostAddReq;
import com.example.preboarding.dto.response.JobPositionPostRes;
import com.example.preboarding.dto.response.JobPostInfoRes;
import com.example.preboarding.dto.response.UpdateStatusRes;
import com.example.preboarding.exception.CustomException;
import com.example.preboarding.service.JobPositionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RestController
@AllArgsConstructor
@RequestMapping("/jobpositions")
@Tag(name = "채용 공고 관련 API", description = "Swagger API")
public class JobPositionController {

    private final JobPositionsService jobPositionsService;

    @PostMapping
    @Operation(summary = "채용 공고 등록" ,description = "회사의 직무 채용 공고 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Success", content = @Content(schema = @Schema(implementation = JobPositionPostRes.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout", content = @Content(schema = @Schema(implementation = CustomException.class))),
    })
    public JobPositionPostRes registJobPositionPost(@RequestBody @NotNull JobPostAddReq registInfo){
        try{
            Long savePostNum = jobPositionsService.registPosition(registInfo);

            return JobPositionPostRes.builder()
                    .status(HttpStatus.OK.value())
                    .message("새로운 채용 공고가 등록 되었습니다.")
                    .postNum(savePostNum)
                    .build();

        }catch (CustomException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }


    @GetMapping()
    @Operation(summary = "채용 공고 검색 및 조회" ,description = "등록된 채용 공고 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Success", content = @Content(schema = @Schema(implementation = JobPostInfoRes.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout", content = @Content(schema = @Schema(implementation = CustomException.class))),
    })
    public JobPostInfoRes searchJobPost(
            @RequestParam(name = "nation", required = false) String nation,
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "comName", required = false) String comName,
            @RequestParam(name = "roleArr", required = false) String[] roleArray,
        @PageableDefault(size = 10) Pageable pageable) {
        try{
            JobPostInfoRes searchList = jobPositionsService.searchJobPostionPost(comName,nation,region,roleArray,pageable);

            searchList.setMessage("조건에 맞는 채공 공고가 검색 되었습니다");
            searchList.setStatus(HttpStatus.OK.value());
            searchList.setPageNum(pageable.getPageNumber());
            return searchList;

        }catch (CustomException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }


    @GetMapping("/{postNum}")
    @Operation(summary = "채용 공고 상세 보기" ,description = "등록된 채용 공고 상세 보기 및 사내 채용 공고 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Success", content = @Content(schema = @Schema(implementation = JobPostInfoRes.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout", content = @Content(schema = @Schema(implementation = CustomException.class))),
    })
    public JobPostInfoRes getDetail(@PathVariable("postNum") Long postNum){

        try{

            JobPostInfoRes res = jobPositionsService.getJobPostDetail(postNum);

            res.setMessage("채공 공고 상세 정보가 조회 되었습니다");
            res.setStatus(HttpStatus.OK.value());
            return res;

        }catch (CustomException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @PutMapping("/{postNum}")
    @Operation(summary = "채용 공고 수정" ,description = "등록된 채용 공고 수정 하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Success", content = @Content(schema = @Schema(implementation = UpdateStatusRes.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout", content = @Content(schema = @Schema(implementation = CustomException.class))),
    })
    public UpdateStatusRes modifyJobPost(@PathVariable("postNum") Long postNum, @RequestBody @NotNull JobPostReq modJobPost ){

        try{

            Long updatePostNum = jobPositionsService.updateJobPostInfo(postNum,modJobPost);

            return UpdateStatusRes.builder()
                    .status(HttpStatus.OK.value())
                    .message("채용 공고가 수정 되었습니다.")
                    .postNum(updatePostNum)
                    .build();

        }catch (CustomException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }


    @DeleteMapping("/{postNum}")
    @Operation(summary = "채용 공고 삭제" ,description = "등록된 채용 공고 수정 하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Success", content = @Content(schema = @Schema(implementation = UpdateStatusRes.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout", content = @Content(schema = @Schema(implementation = CustomException.class))),
    })
    public UpdateStatusRes deleteJobPost(@PathVariable("postNum") Long postNum){

        try{

            jobPositionsService.deleteJobPost(postNum);

            return UpdateStatusRes.builder()
                    .status(HttpStatus.OK.value())
                    .message("채용 공고가 삭제 되었습니다.")
                    .postNum(postNum)
                    .build();

        }catch (CustomException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

}
