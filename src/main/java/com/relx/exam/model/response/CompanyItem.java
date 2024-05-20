package com.relx.exam.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyItem {
    @JsonProperty("company_status")
    private String companyStatus;
    @JsonProperty("address_snippet")
    private String addressSnippet;
    @JsonProperty("date_of_creation")
    private String dateOfCreation;
    private Matches matches;
    private String description;
    private Links links;
    @JsonProperty("company_number")
    private String companyNumber;
    private String title;
    @JsonProperty("company_type")
    private String companyType;
    private Address address;
    private String kind;
    @JsonProperty("description_identifier")
    private List<String> descriptionIdentifier;
}
