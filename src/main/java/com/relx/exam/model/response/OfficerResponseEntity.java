package com.relx.exam.model.response;

import lombok.Data;

@Data
public class OfficerResponseEntity {
    private String name;
    private String officer_role;
    private String appointed_on;
    private Address address;
}
