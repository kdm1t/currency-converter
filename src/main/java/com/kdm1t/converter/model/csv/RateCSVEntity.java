package com.kdm1t.converter.model.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class RateCSVEntity {

    @CsvBindByName(column = "Country")
    private String country;
    @CsvBindByName(column = "Currency")
    private String currency;
    @CsvBindByName(column = "Amount")
    private Integer amount;
    @CsvBindByName(column = "Code")
    private String code;
    @CsvBindByName(column = "Rate")
    private Double rate;

}
