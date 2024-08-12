package com.example.preboarding.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class RoleReq {
    @NotNull
    @Schema(example = "Back-end",description = "역할 ID")
    String roleId;
    @NotNull
    @Schema(example = "백엔드",description = "역할명")
    String roleName;

}
