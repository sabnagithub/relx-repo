package com.relx.exam.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponsePayload {
    private int totalResults;
    private List<CompanyOfficersEntity> companyOfficersEntityList;
}
