package com.relx.exam.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDetails {
    @JsonProperty("page_number")
    private int pageNumber;
    private String kind;
    @JsonProperty("total_results")
    private int totalResults;
    private List<CompanyItem> items;
    private CompanyItem companyItem;
    private Matches matches;
    private Links links;
    private Address address;
}
