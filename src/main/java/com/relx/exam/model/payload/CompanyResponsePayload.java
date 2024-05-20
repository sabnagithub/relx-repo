package com.relx.exam.model.payload;

import com.relx.exam.model.response.CompanyItem;
import com.relx.exam.model.response.Officer;
import lombok.Data;

import java.util.List;

@Data
public class CompanyResponsePayload {
    private int total_results;
    private List<CompanyItem> items;
    private List<Officer> officers;
}
