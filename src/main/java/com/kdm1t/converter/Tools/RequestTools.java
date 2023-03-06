package com.kdm1t.converter.Tools;

import com.kdm1t.converter.model.csv.RateCSVEntity;
import com.kdm1t.converter.model.csv.RateCSVEntityByYear;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RequestTools {

    private static final String URL = "https://www.cnb.cz/en/financial_markets/foreign_exchange_market/exchange_rate_fixing/";
    private static final RestTemplate restTemplate = new RestTemplate();

    private static String buildUrlDaily(LocalDate date) {
        return URL + "daily.txt?date=" + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private static String buildUrlYearly(Integer year) {
        return URL + "year.txt?year=" + year;
    }

    public static List<RateCSVEntity> getRateCSVEntities(LocalDate date) {
        return restTemplate.execute(buildUrlDaily(date), HttpMethod.GET, null, clientHttpResponse -> CSVTools.parseCSV(clientHttpResponse.getBody()));
    }

    public static List<RateCSVEntityByYear> getRateCSVEntities(Integer year) {
        return restTemplate.execute(buildUrlYearly(year), HttpMethod.GET, null, clientHttpResponse -> CSVTools.parseCSVByYear(clientHttpResponse.getBody()));
    }
}
