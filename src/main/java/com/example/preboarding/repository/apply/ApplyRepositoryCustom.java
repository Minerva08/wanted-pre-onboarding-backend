package com.example.preboarding.repository.apply;

import com.example.preboarding.dto.ApplyUserDTO;

import java.util.List;

public interface ApplyRepositoryCustom {
    List<ApplyUserDTO> findByApplyNum(Long applyNum);
}
