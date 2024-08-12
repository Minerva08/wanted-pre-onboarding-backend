package com.example.preboarding.service;

import com.example.preboarding.dto.request.CompanyReq;

public interface CompanyService {
    Long addCompany(CompanyReq registCom);

    void deleteCompany(Long comNum);
}
