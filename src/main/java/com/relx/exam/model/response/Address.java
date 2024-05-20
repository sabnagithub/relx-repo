package com.relx.exam.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Address {
    private String premises;
    @JsonProperty("postal_code")
    private String postalCode;
    private String country;
    private String locality;
    @JsonProperty("address_line_1")
    private String addressLine1;
}
