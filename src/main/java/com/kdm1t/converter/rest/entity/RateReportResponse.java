package com.kdm1t.converter.rest.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class RateReportResponse {

    private String country;
    private String currency;
    private String code;
    private Double minValue;
    private Double midValue;
    private Double maxValue;

}
