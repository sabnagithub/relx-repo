package com.relx.exam.config;

import com.relx.exam.proxy.CompanyDetailProxy;
import com.relx.exam.proxy.CompanyOfficersDetailProxy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class TruProxyCompanyOfficerSearchClient {

    @Value("${company.officers.url}")
    private String companyOfficersUrl;

    @Bean(name = "companyOfficersWebClient")
    public WebClient companyOfficersWebClient(WebClient.Builder builder) {
        return builder.baseUrl(companyOfficersUrl).build();
    }

    @Bean
    public CompanyOfficersDetailProxy companyOfficersDetailsClient(@Qualifier("companyOfficersWebClient") WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
        return httpServiceProxyFactory.createClient(CompanyOfficersDetailProxy.class);
    }
}
