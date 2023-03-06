package com.kdm1t.converter.model.repository;

import com.kdm1t.converter.model.entity.RateDate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RateDateRepository extends CrudRepository<RateDate, Long> {
    Optional<RateDate> findByRateDate(LocalDate rateDate);
    List<RateDate> findAllByRateDateBetween(LocalDate dateFrom, LocalDate dateTo);
}
