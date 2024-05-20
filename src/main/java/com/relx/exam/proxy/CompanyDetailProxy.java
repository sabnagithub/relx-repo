package com.relx.exam.proxy;

import com.relx.exam.model.response.CompanyDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface CompanyDetailProxy {
    @GetExchange("/TruProxyAPI/rest/Companies/v1/search")
    CompanyDetails buildWebClientToGetCompanyDetails(@RequestParam String query);

}
