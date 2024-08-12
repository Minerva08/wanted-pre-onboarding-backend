package com.example.preboarding.controller;

import com.example.preboarding.domain.User;
import com.example.preboarding.dto.ApplyInfoDTO;
import com.example.preboarding.dto.request.ApplicationReq;
import com.example.preboarding.dto.response.ApplicationInfoRes;
import com.example.preboarding.dto.response.ApplicationRes;
import com.example.preboarding.dto.response.UpdateStatusRes;
import com.example.preboarding.exception.CustomException;
import com.example.preboarding.service.UserService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Tag(name = "사용자 관련 API", description = "Swagger API")
public class UserController {
    private final UserService userService;

    @PostMapping("/application")
    @Operation(summary = "채용 공고 지원" ,description = "사용자 채용 공고 지원(1회 지원 가능)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Success", content = @Content(schema = @Schema(implementation = ApplicationRes.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout", content = @Content(schema = @Schema(implementation = CustomException.class))),
    })
    public ApplicationRes applyJobPosition(@RequestBody @Valid ApplicationReq applyRequest){
        try{
            ApplicationRes response = userService.applyJob(applyRequest);

            response.setMessage("지원 완료 되었습니다");
            response.setStatus(HttpStatus.OK.value());
            response.setUserNum(applyRequest.getUserNum());
            return response;

        }catch (CustomException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }


    @DeleteMapping("/application/{applyNum}")
    @Operation(summary = "지원 취소" ,description = "지원자 채용 공고 지원 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Success", content = @Content(schema = @Schema(implementation = UpdateStatusRes.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout", content = @Content(schema = @Schema(implementation = CustomException.class))),
    })
    public UpdateStatusRes cancelApply(@PathVariable("applyNum") @NotNull Long applyNum){
        try{
            long updateUserNum = userService.cancelApplication(applyNum);

            return UpdateStatusRes.builder()
                    .status(HttpStatus.OK.value())
                    .message("지원이 취소 되었습니다.")
                    .userNum(updateUserNum)
                    .build();

        }catch (CustomException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }


    @GetMapping("/application/{userNum}")
    @Operation(summary = "사용자의 지원 내역 조회" ,description = "지원한 공고 정보")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Success", content = @Content(schema = @Schema(implementation = ApplicationInfoRes.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout", content = @Content(schema = @Schema(implementation = CustomException.class))),
    })
    public ApplicationInfoRes getAppliedJobPosition(@PathVariable("userNum") @NotNull Long userNum){
        try{
            User user = userService.getUserInfo(userNum);
            ApplyInfoDTO applicationInfo = userService.getApplication(userNum);

            return ApplicationInfoRes.builder()
                    .status(HttpStatus.OK.value())
                    .message("지원 내역이 조회되었습니다.")
                    .applyInfo(applicationInfo)
                    .userNum(user.getUserNum())
                    .userId(user.getUserId())
                    .build();

        }catch (CustomException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

}
