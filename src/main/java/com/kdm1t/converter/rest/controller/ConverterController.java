package com.kdm1t.converter.rest.controller;

import com.kdm1t.converter.Tools.CollectionTools;
import com.kdm1t.converter.Tools.DateTools;
import com.kdm1t.converter.Tools.RequestTools;
import com.kdm1t.converter.model.csv.RateCSVEntity;
import com.kdm1t.converter.model.entity.CurrencyInfo;
import com.kdm1t.converter.model.entity.Rate;
import com.kdm1t.converter.model.entity.RateDate;
import com.kdm1t.converter.model.repository.CurrencyInfoRepository;
import com.kdm1t.converter.model.repository.RateDateRepository;
import com.kdm1t.converter.model.repository.RateRepository;
import com.kdm1t.converter.rest.entity.FillByCzBankRequest;
import com.kdm1t.converter.rest.entity.RateReportRequest;
import com.kdm1t.converter.rest.entity.RateReportResponse;
import com.kdm1t.converter.service.CurrencyInfoService;
import com.kdm1t.converter.service.RateDateService;
import com.kdm1t.converter.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/converter")
@RequiredArgsConstructor
public class ConverterController {
    private final RateService rateService;
    private final RateDateService rateDateService;
    private final RateRepository rateRepository;
    private final RateDateRepository rateDateRepository;
    private final CurrencyInfoRepository currencyInfoRepository;

    @PostMapping(value = "/fill_by_external_service", produces = MediaType.APPLICATION_JSON_VALUE)
    private List<Rate> fillByExternalService(@RequestBody FillByCzBankRequest request) {
        List<Rate> response = new ArrayList<>();

        if (isCorrectPeriodInRequest(request.getDateFrom(), request.getDateTo())) {
            List<RateCSVEntity> rateCSVEntities = new ArrayList<>();
            LocalDate dateFrom = request.getDateFrom();
            while (DateTools.isBeforeOrEquals(dateFrom, request.getDateTo())) {
                rateCSVEntities.addAll(RequestTools.getRateCSVEntities(dateFrom));
                for (RateCSVEntity rateCSVEntity : RequestTools.getRateCSVEntities(dateFrom)) {
                    response.add(rateService.createOrUpdate(rateDateService.findOrCreate(dateFrom), rateCSVEntity));
                }
                dateFrom = dateFrom.plusDays(1);
            }

        } else if (request.getCertainDate() != null) {
            RequestTools.getRateCSVEntities(request.getCertainDate()).forEach(rateCSVEntity ->
                    response.add(rateService.createOrUpdate(rateDateService.findOrCreate(request.getCertainDate()), rateCSVEntity)));

        } else if (request.getYear() != null) {
            RequestTools.getRateCSVEntities(request.getYear()).forEach(rateCSVEntityByYear ->
                    response.addAll(rateService.createOrUpdate(rateDateService.findOrCreate(rateCSVEntityByYear.getDate()), rateCSVEntityByYear)));
        }

        return response;
    }

    @PostMapping(value = "/rate_report", produces = MediaType.APPLICATION_JSON_VALUE)
    private List<RateReportResponse> rateReport(@RequestBody RateReportRequest request) {
        List<RateReportResponse> responses = new ArrayList<>();
        if (isCorrectPeriodInRequest(request.getDateFrom(), request.getDateTo())) {
            List<RateDate> rateDates = rateDateRepository.findAllByRateDateBetween(request.getDateFrom(), request.getDateTo());
            List<CurrencyInfo> infos = currencyInfoRepository.findAllByCodeIn(request.getCodes());
            List<Rate> rates = rateRepository.findAllByCurrencyInfoIn(infos).stream().filter(rate -> rateDates.contains(rate.getRateDate())).toList();
            rates.stream().filter(CollectionTools.distinctByKey(Rate::getCurrencyInfo)).forEach(rate -> {
                RateReportResponse response = new RateReportResponse();
                response.setCountry(rate.getCurrencyInfo().getCountry());
                response.setCurrency(rate.getCurrencyInfo().getCurrency());
                response.setCode(rate.getCurrencyInfo().getCode());
                response.setMinValue(rates.stream().filter(foundRate -> Objects.equals(foundRate.getCurrencyInfo(), rate.getCurrencyInfo())).mapToDouble(Rate::divideRateByAmount).min().orElse(0.0));
                response.setMaxValue(rates.stream().filter(foundRate -> Objects.equals(foundRate.getCurrencyInfo(), rate.getCurrencyInfo())).mapToDouble(Rate::divideRateByAmount).max().orElse(0.0));
                response.setMidValue((response.getMinValue() + response.getMaxValue()) / 2);
                responses.add(response);
            });
        }
        return responses;
    }

    private boolean isCorrectPeriodInRequest(LocalDate dateFrom, LocalDate dateTo) {
        return dateFrom != null && dateTo != null && DateTools.isBeforeOrEquals(dateFrom, dateTo);
    }

}
