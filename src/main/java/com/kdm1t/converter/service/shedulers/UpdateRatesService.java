package com.kdm1t.converter.service.shedulers;

import com.kdm1t.converter.Tools.RequestTools;
import com.kdm1t.converter.service.RateDateService;
import com.kdm1t.converter.service.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
@Slf4j
public class UpdateRatesService {
    private final RateService rateService;
    private final RateDateService rateDateService;

    @Scheduled(cron = "${cron.interval}")
    public void updateRates() {
        log.info("updateRates method processing");
        LocalDate date = LocalDate.now();
        log.debug("Send request by current date to external service : " + date);
        RequestTools.getRateCSVEntities(date).forEach(rateCSVEntity -> {
            log.trace("RateCSVEntity processing: " + rateCSVEntity);
            rateService.createOrUpdate(rateDateService.findOrCreate(date), rateCSVEntity);
        });
    }
}
