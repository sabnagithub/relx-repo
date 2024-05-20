package com.relx.exam.services;

import com.relx.exam.exception.CompanyException;
import com.relx.exam.model.request.CompanySearchRequest;
import com.relx.exam.model.response.*;
import com.relx.exam.proxy.CompanyDetailProxy;
import com.relx.exam.proxy.CompanyOfficersDetailProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TruProxyAPIServiceForCompany {

    @Autowired
    CompanyDetailProxy companyDetailProxy;

    @Autowired
    CompanyOfficersDetailProxy companyOfficersDetailProxy;

    public CompanyOfficersEntity getCompanyDetailsRequest(CompanySearchRequest companySearchRequest) {
        CompanyDetails companyDetails;
        OfficerListResponse officerListResponse;
        CompanyOfficersEntity companyOfficersEntity=new CompanyOfficersEntity();
        try{
            if(companySearchRequest.getCompanyNumber() != null && !"".equals(companySearchRequest.getCompanyNumber().trim())){
                companyDetails = companyDetailProxy.buildWebClientToGetCompanyDetails(companySearchRequest.getCompanyNumber());
                officerListResponse = companyOfficersDetailProxy.buildWebClientToGetCompanyOfficersDetails(companySearchRequest.getCompanyNumber());

            }else {
                companyDetails = companyDetailProxy.buildWebClientToGetCompanyDetails(companySearchRequest.getCompanyName());
                officerListResponse = null;
            }
            List<CompanyResponseEntity> companyResponseEntityList = companyDetails.getItems().stream()
                    .map(companyItem -> buildCompanyResponseEntityDetails(companyItem,companyOfficersDetailProxy.buildWebClientToGetCompanyOfficersDetails(companyItem.getCompanyNumber())))
                    .collect(Collectors.toList());
            if(officerListResponse != null){
                companyOfficersEntity.setTotal_results(companyResponseEntityList.size());
                companyOfficersEntity.setItems(companyResponseEntityList);
            }

        }catch (CompanyException ex){
            throw ex;
        }catch (Exception e){
            throw e;
        }

        /*List<CompanyResponseEntity> componyResponseEntity = companyDetails.getItems().stream()
                .map(companyItem -> new CompanyResponseEntity())*/

        return companyOfficersEntity;
    }

    private CompanyResponseEntity buildCompanyResponseEntityDetails(CompanyItem companyItem, OfficerListResponse officerListResponse) {
        CompanyResponseEntity companyResponseEntity = new CompanyResponseEntity();
        companyResponseEntity.setCompanyNumber(companyItem.getCompanyNumber());
        companyResponseEntity.setCompanyType(companyItem.getCompanyType());
        companyResponseEntity.setTitle(companyItem.getTitle());
        companyResponseEntity.setCompanyStatus(companyItem.getCompanyStatus());
        companyResponseEntity.setDateOfCreation(companyItem.getDateOfCreation());
        companyResponseEntity.setAddress(companyItem.getAddress());
        List<OfficerResponseEntity> officerResponseEntityList = officerListResponse.getItems().stream()
                .map(off -> {
                    OfficerResponseEntity officerResponseEntity = new OfficerResponseEntity();
                    officerResponseEntity.setName(off.getName());
                    officerResponseEntity.setOfficer_role(off.getOfficer_role());
                    officerResponseEntity.setAppointed_on(off.getAppointed_on());
                    officerResponseEntity.setAddress(off.getAddress());
                    return officerResponseEntity;
                })
                .collect(Collectors.toList());
        companyResponseEntity.setOfficers(officerResponseEntityList);
        return companyResponseEntity;
    }

}
