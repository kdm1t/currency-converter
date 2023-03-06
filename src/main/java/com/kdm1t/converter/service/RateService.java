package com.kdm1t.converter.service;

import com.kdm1t.converter.model.CurrencyCode;
import com.kdm1t.converter.model.csv.RateCSVEntity;
import com.kdm1t.converter.model.csv.RateCSVEntityByYear;
import com.kdm1t.converter.model.entity.CurrencyInfo;
import com.kdm1t.converter.model.entity.Rate;
import com.kdm1t.converter.model.entity.RateDate;
import com.kdm1t.converter.model.repository.CurrencyInfoRepository;
import com.kdm1t.converter.model.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateService {

    private final RateRepository rateRepository;
    private final CurrencyInfoRepository currencyInfoRepository;
    private final CurrencyInfoService currencyInfoService;

    public Rate createOrUpdate(RateDate rateDate, RateCSVEntity rateCSVEntity) {
        CurrencyInfo info = currencyInfoRepository.findByCode(rateCSVEntity.getCode()).orElseGet(() -> currencyInfoService.create(rateCSVEntity));
        Rate rate = rateRepository.findByRateDateAndCurrencyInfo(rateDate, info).orElse(null);
        log.debug(String.format("RATE in database by CODE = %s and RATE_DATE = %s", rateCSVEntity.getCode(), rateDate));
        if (rate == null) {
            rate = new Rate();
            rate.setRateDate(rateDate);
            rate.setCurrencyInfo(info);
        }
        rate.setAmount(rateCSVEntity.getAmount());
        rate.setRate(rateCSVEntity.getRate());
        log.debug("RATE after update/create : " + rate);
        return rateRepository.save(rate);
    }

    public List<Rate> createOrUpdate(RateDate rateDate, RateCSVEntityByYear rateCSVEntityByYear) {
        List<Rate> rates = rateRepository.findAllByRateDate(rateDate);
        List<Rate> result = new ArrayList<>();
        if (rateCSVEntityByYear.getAudRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.AUD, rateDate), 1, rateCSVEntityByYear.getAudRate1()));
        if (rateCSVEntityByYear.getBgnRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.BGN, rateDate), 1, rateCSVEntityByYear.getBgnRate1()));
        if (rateCSVEntityByYear.getBrlRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.BRL, rateDate), 1, rateCSVEntityByYear.getBrlRate1()));
        if (rateCSVEntityByYear.getCadRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.CAD, rateDate), 1, rateCSVEntityByYear.getCadRate1()));
        if (rateCSVEntityByYear.getChfRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.CHF, rateDate), 1, rateCSVEntityByYear.getChfRate1()));
        if (rateCSVEntityByYear.getCnyRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.CNY, rateDate), 1, rateCSVEntityByYear.getCnyRate1()));
        if (rateCSVEntityByYear.getDkkRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.DKK, rateDate), 1, rateCSVEntityByYear.getDkkRate1()));
        if (rateCSVEntityByYear.getEurRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.EUR, rateDate), 1, rateCSVEntityByYear.getEurRate1()));
        if (rateCSVEntityByYear.getGbpRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.GBP, rateDate), 1, rateCSVEntityByYear.getGbpRate1()));
        if (rateCSVEntityByYear.getHkdRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.HKD, rateDate), 1, rateCSVEntityByYear.getHkdRate1()));
        if (rateCSVEntityByYear.getHrkRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.HRK, rateDate), 1, rateCSVEntityByYear.getHrkRate1()));
        if (rateCSVEntityByYear.getHufRate100() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.HUF, rateDate), 100, rateCSVEntityByYear.getHufRate100()));
        if (rateCSVEntityByYear.getIdrRate1000() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.IDR, rateDate), 1000, rateCSVEntityByYear.getIdrRate1000()));
        if (rateCSVEntityByYear.getIlsRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.ILS, rateDate), 1, rateCSVEntityByYear.getIlsRate1()));
        if (rateCSVEntityByYear.getInrRate100() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.INR, rateDate), 100, rateCSVEntityByYear.getInrRate100()));
        if (rateCSVEntityByYear.getIskRate100() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.ISK, rateDate), 100, rateCSVEntityByYear.getIskRate100()));
        if (rateCSVEntityByYear.getJpyRate100() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.JPY, rateDate), 100, rateCSVEntityByYear.getJpyRate100()));
        if (rateCSVEntityByYear.getKrwRate100() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.KRW, rateDate), 100, rateCSVEntityByYear.getKrwRate100()));
        if (rateCSVEntityByYear.getMxnRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.MXN, rateDate), 1, rateCSVEntityByYear.getMxnRate1()));
        if (rateCSVEntityByYear.getMyrRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.MYR, rateDate), 1, rateCSVEntityByYear.getMyrRate1()));
        if (rateCSVEntityByYear.getNokRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.NOK, rateDate), 1, rateCSVEntityByYear.getNokRate1()));
        if (rateCSVEntityByYear.getNzdRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.NZD, rateDate), 1, rateCSVEntityByYear.getNzdRate1()));
        if (rateCSVEntityByYear.getPhpRate100() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.PHP, rateDate), 100, rateCSVEntityByYear.getPhpRate100()));
        if (rateCSVEntityByYear.getPlnRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.PLN, rateDate), 1, rateCSVEntityByYear.getPlnRate1()));
        if (rateCSVEntityByYear.getRonRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.RON, rateDate), 1, rateCSVEntityByYear.getRonRate1()));
        if (rateCSVEntityByYear.getRubRate100() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.RUB, rateDate), 100, rateCSVEntityByYear.getRubRate100()));
        if (rateCSVEntityByYear.getSekRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.SEK, rateDate), 1, rateCSVEntityByYear.getSekRate1()));
        if (rateCSVEntityByYear.getSgdRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.SGD, rateDate), 1, rateCSVEntityByYear.getSgdRate1()));
        if (rateCSVEntityByYear.getThbRate100() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.THB, rateDate), 100, rateCSVEntityByYear.getThbRate100()));
        if (rateCSVEntityByYear.getTryRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.TRY, rateDate), 1, rateCSVEntityByYear.getTryRate1()));
        if (rateCSVEntityByYear.getUsdRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.USD, rateDate), 1, rateCSVEntityByYear.getUsdRate1()));
        if (rateCSVEntityByYear.getXdrRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.XDR, rateDate), 1, rateCSVEntityByYear.getXdrRate1()));
        if (rateCSVEntityByYear.getZarRate1() != null)
            result.add(fillAndSave(getOrGenerate(rates, CurrencyCode.ZAR, rateDate), 1, rateCSVEntityByYear.getZarRate1()));

        return result;
    }

    private Rate getOrGenerate(List<Rate> rates, String code, RateDate rateDate) {
        return rates.stream()
                .filter(rate -> Objects.equals(currencyInfoRepository.findByCode(code).orElse(null), rate.getCurrencyInfo()))
                .findFirst()
                .orElseGet(() -> generate(rateDate, code));
    }

    private Rate generate(RateDate rateDate, String code) {
        Rate rate = new Rate();
        rate.setRateDate(rateDate);
        currencyInfoRepository.findByCode(code).ifPresent(rate::setCurrencyInfo);
        return rate;
    }

    private Rate fillAndSave(Rate rate, Integer amount, Double rateVal) {
        rate.setAmount(amount != null ? amount : 1);
        rate.setRate(rateVal);
        return rateRepository.save(rate);
    }
}
