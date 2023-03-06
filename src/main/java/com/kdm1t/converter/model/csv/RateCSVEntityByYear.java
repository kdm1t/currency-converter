package com.kdm1t.converter.model.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString
public class RateCSVEntityByYear {


    @CsvCustomBindByName(converter = DateCSVConverter.class, column = "Date")
    private LocalDate date;
    @CsvBindByName(column = "1 AUD")
    private Double audRate1;
    @CsvBindByName(column = "1 BGN")
    private Double bgnRate1;
    @CsvBindByName(column = "1 BRL")
    private Double brlRate1;
    @CsvBindByName(column = "1 CAD")
    private Double cadRate1;
    @CsvBindByName(column = "1 CHF")
    private Double chfRate1;
    @CsvBindByName(column = "1 CNY")
    private Double cnyRate1;
    @CsvBindByName(column = "1 DKK")
    private Double dkkRate1;
    @CsvBindByName(column = "1 EUR")
    private Double eurRate1;
    @CsvBindByName(column = "1 GBP")
    private Double gbpRate1;
    @CsvBindByName(column = "1 HKD")
    private Double hkdRate1;
    @CsvBindByName(column = "1 HRK")
    private Double hrkRate1;
    @CsvBindByName(column = "100 HUF")
    private Double hufRate100;
    @CsvBindByName(column = "1000 IDR")
    private Double idrRate1000;
    @CsvBindByName(column = "1 ILS")
    private Double ilsRate1;
    @CsvBindByName(column = "100 INR")
    private Double inrRate100;
    @CsvBindByName(column = "100 ISK")
    private Double iskRate100;
    @CsvBindByName(column = "100 JPY")
    private Double jpyRate100;
    @CsvBindByName(column = "100 KRW")
    private Double krwRate100;
    @CsvBindByName(column = "1 MXN")
    private Double mxnRate1;
    @CsvBindByName(column = "1 MYR")
    private Double myrRate1;
    @CsvBindByName(column = "1 NOK")
    private Double nokRate1;
    @CsvBindByName(column = "1 NZD")
    private Double nzdRate1;
    @CsvBindByName(column = "100 PHP")
    private Double phpRate100;
    @CsvBindByName(column = "1 PLN")
    private Double plnRate1;
    @CsvBindByName(column = "1 RON")
    private Double ronRate1;
    @CsvBindByName(column = "100 RUB")
    private Double rubRate100;
    @CsvBindByName(column = "1 SEK")
    private Double sekRate1;
    @CsvBindByName(column = "1 SGD")
    private Double sgdRate1;
    @CsvBindByName(column = "100 THB")
    private Double thbRate100;
    @CsvBindByName(column = "1 TRY")
    private Double tryRate1;
    @CsvBindByName(column = "1 USD")
    private Double usdRate1;
    @CsvBindByName(column = "1 XDR")
    private Double xdrRate1;
    @CsvBindByName(column = "1 ZAR")
    private Double zarRate1;

}
