package com.relx.exam.model.response;

import lombok.Data;

@Data
public class Officer {
    private String name;
    private String officer_role;
    private String appointed_on;
    private Address address;
}
