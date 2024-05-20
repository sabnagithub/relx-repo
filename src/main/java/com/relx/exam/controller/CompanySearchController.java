package com.relx.exam.controller;

import com.relx.exam.exception.CustomException;
import com.relx.exam.model.request.CompanySearchRequest;
import com.relx.exam.model.response.CompanyOfficersEntity;
import com.relx.exam.services.TruProxyAPIServiceForCompany;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CompanySearchController {

    @Autowired
    TruProxyAPIServiceForCompany truProxyAPIServiceForCompany;

    @PostMapping("/company/search")
    public CompanyOfficersEntity searchCompany(@RequestBody CompanySearchRequest companySearchRequest) {
        return truProxyAPIServiceForCompany.getCompanyDetailsRequest(companySearchRequest);
    }
}
