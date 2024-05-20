package com.relx.exam.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OfficerListResponse {
    private String etag;
    private Links links;
    private String kind;
    private int items_per_page;
    private List<OfficerItem> items;

    @Data
    @NoArgsConstructor
    public static class Links {
        private String self;
    }

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class OfficerItem {
        private Address address;
        private String name;
        private String appointed_on;
        private String resigned_on;
        private String officer_role;
        private Links links;
        private DateOfBirth date_of_birth;
        private String occupation;
        private String country_of_residence;
        private String nationality;
    }

//    @Data
//    @NoArgsConstructor
//    public static class Address {
//        private String premises;
//        private String postal_code;
//        private String country;
//        private String locality;
//        private String address_line_1;
//    }

    @Data
    @NoArgsConstructor
    public static class DateOfBirth {
        private int month;
        private int year;
    }
}

