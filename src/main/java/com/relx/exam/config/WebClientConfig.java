package com.relx.exam.config;

import com.relx.exam.exception.CompanyError;
import com.relx.exam.exception.CompanyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Value("${api.key}")
    private String apiKey;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector())
                .defaultStatusHandler(HttpStatusCode::isError, response ->
                        response.bodyToMono(CompanyError.class)
                                .flatMap(responseBody -> {
                                    return Mono.error(new CompanyException(responseBody));
                                }))
                .defaultHeaders(headers -> {
                    headers.add("x-api-key", "PwewCEztSW7XlaAKqkg4IaOsPelGynw6SN9WsbNf");
                });
    }
}
