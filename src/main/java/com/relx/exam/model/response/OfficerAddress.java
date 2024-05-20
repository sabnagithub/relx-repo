package com.relx.exam.model.response;

import lombok.Data;

@Data
public class OfficerAddress {
    private String locality;
    private String postal_code;
    private String premises;
    private String address_line_1;
    private String country;
}
