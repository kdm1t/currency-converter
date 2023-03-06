package com.kdm1t.converter.model.repository;

import com.kdm1t.converter.model.entity.CurrencyInfo;
import com.kdm1t.converter.model.entity.Rate;
import com.kdm1t.converter.model.entity.RateDate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends CrudRepository<Rate, Long> {
    List<Rate> findAllByRateDate(RateDate rateDate);

    List<Rate> findAllByCurrencyInfoIn(List<CurrencyInfo> currencyInfos);

    Optional<Rate> findByRateDateAndCurrencyInfo(RateDate rateDate, CurrencyInfo currencyInfo);

}
