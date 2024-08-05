package com.example.preboarding.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class RoleReq {
    @NotNull
    @Schema(example = "1",description = "역할 ID")
    String roleId;
    @NotNull
    @Schema(example = "1",description = "역할명")
    String roleName;

}
