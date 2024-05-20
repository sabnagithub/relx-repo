package com.relx.exam.model.response;

import lombok.Data;

import java.util.List;

@Data
public class CompanyOfficersEntity {
    private long total_results;
    private List<CompanyResponseEntity> items;

}
