package com.relx.exam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.relx.exam.model.request.CompanySearchRequest;
import com.relx.exam.model.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock
public class CompanySearchControllerTest {

//    @Autowired
//    private MockMvc mockMvc;

    //@MockBean
   // private TruProxyAPIServiceForCompany truProxyAPIServiceForCompany;

    @Autowired
    private ObjectMapper objectMapper;

    private CompanySearchRequest companySearchRequest;
    private CompanyOfficersEntity companyOfficersEntity;

    private CompanyDetails companyDetails;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        companySearchRequest = new CompanySearchRequest();
        companySearchRequest.setCompanyNumber("06500244");

        OfficerResponseEntity officerResponseEntity = new OfficerResponseEntity();
        officerResponseEntity.setName("BOXALL, Sarah Victoria");
        officerResponseEntity.setOfficer_role("secretary");
        officerResponseEntity.setAppointed_on("2008-02-11");

        CompanyResponseEntity companyResponseEntity = new CompanyResponseEntity();
        companyResponseEntity.setCompanyNumber("06500244");
        companyResponseEntity.setCompanyType("ltd");
        companyResponseEntity.setTitle("BBC LIMITED");
        companyResponseEntity.setCompanyStatus("active");
        companyResponseEntity.setDateOfCreation("2008-02-11");
        companyResponseEntity.setOfficers(Collections.singletonList(officerResponseEntity));

        companyOfficersEntity = new CompanyOfficersEntity();
        companyOfficersEntity.setTotal_results(1);
        companyOfficersEntity.setItems(Collections.singletonList(companyResponseEntity));
    }

    @Test
    void shoulsReturnCompanyDetailsHappyPath() throws Exception {
        //given
        stubFor(get(urlEqualTo("/TruProxyAPI/rest/Companies/v1/search?query=06500244"))
                .willReturn(WireMock
                        .aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                        .withBody(objectMapper.writeValueAsString(buildCompanyDetailsObj()))));

        stubFor(get(urlEqualTo("/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=06500244"))
                .willReturn(WireMock
                        .aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                        .withBody(objectMapper.writeValueAsString(buildOfficerListResponseObj()))));

        //when
       webTestClient.post().uri("/company/search")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(companySearchRequest), CompanySearchRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total_results").isEqualTo("1")
                .jsonPath("$.items").isNotEmpty();

       //then
        verify(1,getRequestedFor(urlEqualTo("/TruProxyAPI/rest/Companies/v1/search?query=06500244")));
        verify(1,getRequestedFor(urlEqualTo("/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=06500244")));

    }

    @Test
    void shoulReturn500IfCompaniesApiDown() throws Exception {
        //given
        stubFor(get(urlEqualTo("/TruProxyAPI/rest/Companies/v1/search?query=06500244"))
                .willReturn(
                        aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.NOT_FOUND.value())));

        //when
        webTestClient.post().uri("/company/search")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(companySearchRequest), CompanySearchRequest.class)
                .exchange()
                .expectStatus().is5xxServerError();

        //then
        verify(1,getRequestedFor(urlEqualTo("/TruProxyAPI/rest/Companies/v1/search?query=06500244")));
        verify(0,getRequestedFor(urlEqualTo("/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=06500244")));

    }

    private CompanyDetails buildCompanyDetailsObj() {
        CompanyItem companyItem=CompanyItem.builder()
                .companyNumber("some_company_number")
                .companyType("company_type")
                .build();
        return CompanyDetails.builder()
                .items(List.of(companyItem))
                .build();
    }

    private OfficerListResponse buildOfficerListResponseObj() {
        OfficerListResponse.OfficerItem officerItem= OfficerListResponse.OfficerItem.builder()
                .name("Some-office")
                .appointed_on("S0me date")
                .build();
        return OfficerListResponse.builder()
                .items(List.of(officerItem))
                .build();
    }

    @Test
    void shouldReturnBadRequestForInvalidCompanyNumber() throws Exception {
        // Given
        companySearchRequest.setCompanyNumber(""); // Invalid company number

        // When
        webTestClient.post().uri("/company/search")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(companySearchRequest), CompanySearchRequest.class)
                .exchange()
                .expectStatus().isBadRequest();
        //then
        verify(1,getRequestedFor(urlEqualTo("/TruProxyAPI/rest/Companies/v1/search?query=06500244")));
        //verify(0,getRequestedFor(urlEqualTo("/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=06500244")));

    }

    @Test
    void shouldReturnCompanyDetailsWithEmptyOfficersList() throws Exception {
        // Given
        stubFor(get(urlEqualTo("/TruProxyAPI/rest/Companies/v1/search?query=06500244"))
                .willReturn(WireMock
                        .aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                        .withBody(objectMapper.writeValueAsString(buildCompanyDetailsObj()))));

        stubFor(get(urlEqualTo("/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=06500244"))
                .willReturn(WireMock
                        .aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                        .withBody(objectMapper.writeValueAsString(new OfficerListResponse())))); // Empty officers list

        // When
        webTestClient.post().uri("/company/search")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(companySearchRequest), CompanySearchRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total_results").isEqualTo("1")
                .jsonPath("$.items").isNotEmpty()
                .jsonPath("$.items[0].officers").isEmpty();

        // Then
        verify(1, getRequestedFor(urlEqualTo("/TruProxyAPI/rest/Companies/v1/search?query=06500244")));
        verify(1, getRequestedFor(urlEqualTo("/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=06500244")));
    }

    @Test
    void shouldReturnCompanyDetailsWithMultipleOfficers() throws Exception {
        // Given
        stubFor(get(urlEqualTo("/TruProxyAPI/rest/Companies/v1/search?query=06500244"))
                .willReturn(WireMock
                        .aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                        .withBody(objectMapper.writeValueAsString(buildCompanyDetailsObj()))));

        stubFor(get(urlEqualTo("/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=06500244"))
                .willReturn(WireMock
                        .aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                        .withBody(objectMapper.writeValueAsString(buildMultipleOfficersListResponseObj())))); // Multiple officers

        // When
        webTestClient.post().uri("/company/search")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(companySearchRequest), CompanySearchRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total_results").isEqualTo("1")
                .jsonPath("$.items").isNotEmpty()
                .jsonPath("$.items[0].officers.length()").isEqualTo(2); // Check for two officers

        // Then
        verify(1, getRequestedFor(urlEqualTo("/TruProxyAPI/rest/Companies/v1/search?query=06500244")));
        verify(1, getRequestedFor(urlEqualTo("/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=06500244")));
    }

    private OfficerListResponse buildMultipleOfficersListResponseObj() {
        OfficerListResponse.OfficerItem officerItem1 = OfficerListResponse.OfficerItem.builder()
                .name("Officer One")
                .appointed_on("2008-02-11")
                .build();
        OfficerListResponse.OfficerItem officerItem2 = OfficerListResponse.OfficerItem.builder()
                .name("Officer Two")
                .appointed_on("2009-03-12")
                .build();
        return OfficerListResponse.builder()
                .items(List.of(officerItem1, officerItem2))
                .build();
    }

    @Test
    void shouldReturnNotFoundWhenCompanyNotFound() throws Exception {
        // Given
        stubFor(get(urlEqualTo("/TruProxyAPI/rest/Companies/v1/search?query=06500244"))
                .willReturn(WireMock
                        .aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.NOT_FOUND.value())));

        // When
        webTestClient.post().uri("/company/search")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(companySearchRequest), CompanySearchRequest.class)
                .exchange()
                .expectStatus().isNotFound();

        // Then
        verify(1, getRequestedFor(urlEqualTo("/TruProxyAPI/rest/Companies/v1/search?query=06500244")));
        verify(0, getRequestedFor(urlEqualTo("/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=06500244")));
    }

}