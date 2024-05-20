package com.relx.exam.services;

import com.relx.exam.model.request.CompanySearchRequest;
import com.relx.exam.model.response.*;
import com.relx.exam.proxy.CompanyDetailProxy;
import com.relx.exam.proxy.CompanyOfficersDetailProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TruProxyAPIServiceForCompanyTest {

    @Mock
    private CompanyDetailProxy companyDetailProxy;

    @Mock
    private CompanyOfficersDetailProxy companyOfficersDetailProxy;

    @InjectMocks
    private TruProxyAPIServiceForCompany truProxyAPIServiceForCompany;

    private CompanySearchRequest companySearchRequest;
    private CompanyDetails companyDetails;
    private OfficerListResponse officerListResponse;

    @BeforeEach
    void setUp() {
        companySearchRequest = new CompanySearchRequest();
        companySearchRequest.setCompanyNumber("06500244");

        CompanyItem companyItem = new CompanyItem();
        companyItem.setCompanyNumber("06500244");
        companyItem.setCompanyType("ltd");
        companyItem.setTitle("BBC LIMITED");
        companyItem.setCompanyStatus("active");
        companyItem.setDateOfCreation("2008-02-11");
        companyItem.setAddress(new Address());

        companyDetails = new CompanyDetails();
        companyDetails.setItems(Collections.singletonList(companyItem));

        OfficerListResponse.OfficerItem officerItem = new OfficerListResponse.OfficerItem();
        officerItem.setName("BOXALL, Sarah Victoria");
        officerItem.setOfficer_role("secretary");
        officerItem.setAppointed_on("2008-02-11");
        officerItem.setAddress(new Address());

        officerListResponse = new OfficerListResponse();
        officerListResponse.setItems(Collections.singletonList(officerItem));
    }

    @Test
    void testGetCompanyDetailsRequest() {
        when(companyDetailProxy.buildWebClientToGetCompanyDetails(anyString())).thenReturn(companyDetails);
        when(companyOfficersDetailProxy.buildWebClientToGetCompanyOfficersDetails(anyString())).thenReturn(officerListResponse);

        CompanyOfficersEntity result = truProxyAPIServiceForCompany.getCompanyDetailsRequest(companySearchRequest);

        assertEquals(1, result.getTotal_results());
        assertEquals("06500244", result.getItems().get(0).getCompanyNumber());
        assertEquals("BBC LIMITED", result.getItems().get(0).getTitle());
        assertEquals("BOXALL, Sarah Victoria", result.getItems().get(0).getOfficers().get(0).getName());
    }
}