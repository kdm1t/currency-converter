package com.kdm1t.converter.service;

import com.kdm1t.converter.model.entity.RateDate;
import com.kdm1t.converter.model.repository.RateDateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class RateDateService {

    private final RateDateRepository rateDateRepository;

    public RateDate findOrCreate(LocalDate rateDate) {
        return rateDateRepository.findByRateDate(rateDate)
                .orElseGet(() -> createRateDate(rateDate));
    }

    private RateDate createRateDate(LocalDate date) {
        log.debug(String.format("RATE_DATE not found by date = %s. Creating...", date.toString()));
        RateDate rateDate = new RateDate();
        rateDate.setRateDate(date);
        return rateDateRepository.save(rateDate);
    }
}
