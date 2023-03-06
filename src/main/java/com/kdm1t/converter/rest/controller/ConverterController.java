package com.kdm1t.converter.rest.controller;

import com.kdm1t.converter.Tools.CollectionTools;
import com.kdm1t.converter.Tools.DateTools;
import com.kdm1t.converter.Tools.RequestTools;
import com.kdm1t.converter.model.entity.CurrencyInfo;
import com.kdm1t.converter.model.entity.Rate;
import com.kdm1t.converter.model.entity.RateDate;
import com.kdm1t.converter.model.repository.CurrencyInfoRepository;
import com.kdm1t.converter.model.repository.RateDateRepository;
import com.kdm1t.converter.model.repository.RateRepository;
import com.kdm1t.converter.rest.entity.FillByCzBankRequest;
import com.kdm1t.converter.rest.entity.RateReportRequest;
import com.kdm1t.converter.rest.entity.RateReportResponse;
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

@RestController
@RequestMapping("/rates")
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

        while (isCorrectPeriodInRequest(request.getDateFrom(), request.getDateTo())) {

            RequestTools.getRateCSVEntities(request.getDateFrom()).forEach(csvEntity ->
                    response.add(rateService.createOrUpdate(rateDateService.findOrCreate(request.getDateFrom()), csvEntity)));

            request.setDateFrom(request.getDateFrom().plusDays(1));

        }

        if (request.getCertainDate() != null) {
            RequestTools.getRateCSVEntities(request.getCertainDate()).forEach(csvEntity ->
                    response.add(rateService.createOrUpdate(rateDateService.findOrCreate(request.getCertainDate()), csvEntity)));

        }

        if (request.getYear() != null) {
            RequestTools.getRateCSVEntities(request.getYear()).forEach(csvEntityByYear ->
                    response.addAll(rateService.createOrUpdate(csvEntityByYear.getLine())));
        }

        return response;
    }

    @PostMapping(value = "/rate_report", produces = MediaType.APPLICATION_JSON_VALUE)
    private List<RateReportResponse> rateReport(@RequestBody RateReportRequest request) {
        List<RateReportResponse> responses = new ArrayList<>();

        if (!isCorrectPeriodInRequest(request.getDateFrom(), request.getDateTo())) {
            return responses;
        }

        List<RateDate> rateDates = rateDateRepository.findAllByRateDateBetween(request.getDateFrom(), request.getDateTo());
        List<CurrencyInfo> infos = currencyInfoRepository.findAllByCodeIn(request.getCodes());
        List<Rate> rates = rateRepository.findAllByCurrencyInfoInAndRateDateIn(infos, rateDates);

        rates.stream()
                .filter(CollectionTools.distinctByKey(Rate::getCurrencyInfo))
                .forEach(rate -> responses.add(new RateReportResponse(rate, rates)));

        return responses;
    }

    private boolean isCorrectPeriodInRequest(LocalDate dateFrom, LocalDate dateTo) {
        return dateFrom != null && dateTo != null && DateTools.isBeforeOrEquals(dateFrom, dateTo);
    }

}
