package com.relx.exam.config;

import com.relx.exam.proxy.CompanyDetailProxy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class TruProxyCompanySearchClient {

    @Value("${company.details.url}")
    private String companyDetailsUrl;

    @Bean(name = "companyDetailsWebClient")
    public WebClient companyDetailsWebClient(WebClient.Builder builder) {
        return builder.baseUrl(companyDetailsUrl).build();
    }

    @Bean
    public CompanyDetailProxy companyDetailsClient(@Qualifier("companyDetailsWebClient") WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
        return httpServiceProxyFactory.createClient(CompanyDetailProxy.class);
    }

}
