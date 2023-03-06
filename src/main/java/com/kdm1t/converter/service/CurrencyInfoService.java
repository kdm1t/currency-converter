package com.kdm1t.converter.service;

import com.kdm1t.converter.model.csv.RateCSVEntity;
import com.kdm1t.converter.model.entity.CurrencyInfo;
import com.kdm1t.converter.model.repository.CurrencyInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyInfoService {

    private final CurrencyInfoRepository currencyInfoRepository;

    public CurrencyInfo create(RateCSVEntity csvEntity) {
        CurrencyInfo info = new CurrencyInfo();
        info.setCurrency(csvEntity.getCurrency());
        info.setCountry(csvEntity.getCountry());
        info.setCode(csvEntity.getCode());
        return currencyInfoRepository.save(info);
    }
}
