package com.relx.exam.proxy;

import com.relx.exam.model.response.OfficerListResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface CompanyOfficersDetailProxy {
    @GetExchange("/TruProxyAPI/rest/Companies/v1/Officers")
    OfficerListResponse buildWebClientToGetCompanyOfficersDetails(@RequestParam String CompanyNumber);
}
