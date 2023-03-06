package com.kdm1t.converter.service;

import com.kdm1t.converter.model.csv.RateCSVEntity;
import com.kdm1t.converter.model.entity.CurrencyInfo;
import com.kdm1t.converter.model.entity.Rate;
import com.kdm1t.converter.model.entity.RateDate;
import com.kdm1t.converter.model.repository.CurrencyInfoRepository;
import com.kdm1t.converter.model.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateService {

    private final RateRepository rateRepository;
    private final CurrencyInfoRepository currencyInfoRepository;
    private final CurrencyInfoService currencyInfoService;
    private final RateDateService rateDateService;

    public Rate createOrUpdate(RateDate rateDate, RateCSVEntity rateCSVEntity) {
        CurrencyInfo info = currencyInfoRepository.findByCode(rateCSVEntity.getCode()).orElseGet(() -> currencyInfoService.create(rateCSVEntity));
        Rate rate = rateRepository.findByRateDateAndCurrencyInfo(rateDate, info).orElseGet(Rate::new);
        rate.setRateDate(rateDate);
        rate.setCurrencyInfo(info);
        rate.setAmount(rateCSVEntity.getAmount());
        rate.setRate(rateCSVEntity.getRate());
        return rateRepository.save(rate);
    }

    public List<Rate> createOrUpdate(MultiValuedMap<String, String> multiValuedMap) {
        String dateStr = multiValuedMap.get("Date").stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Date is not present in CSV file"));
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        RateDate rateDate = rateDateService.findOrCreate(date);
        List<Rate> rates = rateRepository.findAllByRateDate(rateDate);
        List<Rate> result = new ArrayList<>();
        for (Map.Entry<String, String> entry : multiValuedMap.entries()) {
            String header = entry.getKey();
            String value = entry.getValue();
            if (!"Date".equals(header)) {
                try {
                    Rate rate = getOrGenerate(rates, header.replaceAll("\\d", "").toUpperCase().trim(), rateDate);
                    result.add(fillAndSave(rate, NumberFormat.getInstance().parse(header).intValue(), Double.parseDouble(value)));
                } catch (ParseException | NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }

    private Rate getOrGenerate(List<Rate> rates, String code, RateDate rateDate) {
        CurrencyInfo info = currencyInfoRepository.findByCode(code).orElseGet(() -> currencyInfoService.create(code));

        return rates.stream()
                .filter(rate -> Objects.equals(info, rate.getCurrencyInfo()))
                .findFirst()
                .orElseGet(() -> generate(rateDate, info));
    }

    private Rate generate(RateDate rateDate, CurrencyInfo info) {
        Rate rate = new Rate();
        rate.setRateDate(rateDate);
        rate.setCurrencyInfo(info);
        return rate;
    }

    private Rate fillAndSave(Rate rate, Integer amount, Double rateVal) {
        rate.setAmount(amount);
        rate.setRate(rateVal);
        return rateRepository.save(rate);
    }
}
