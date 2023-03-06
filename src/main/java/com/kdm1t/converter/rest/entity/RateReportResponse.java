package com.kdm1t.converter.rest.entity;

import com.kdm1t.converter.model.entity.Rate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

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

    public RateReportResponse(Rate rate, List<Rate> rates) {
        this.country = rate.getCurrencyInfo().getCountry();
        this.currency = rate.getCurrencyInfo().getCurrency();
        this.code = rate.getCurrencyInfo().getCode();
        this.minValue = rates.stream()
                .filter(foundRate -> Objects.equals(foundRate.getCurrencyInfo(), rate.getCurrencyInfo()))
                .mapToDouble(Rate::divideRateByAmount)
                .min()
                .orElse(0.0);
        this.maxValue = rates.stream()
                .filter(foundRate -> Objects.equals(foundRate.getCurrencyInfo(), rate.getCurrencyInfo()))
                .mapToDouble(Rate::divideRateByAmount)
                .max()
                .orElse(0.0);
        this.midValue = (this.maxValue + this.minValue) / 2;
    }

}
