package com.kdm1t.converter.model.repository;

import com.kdm1t.converter.model.entity.CurrencyInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyInfoRepository extends CrudRepository<CurrencyInfo, Long> {
    Optional<CurrencyInfo> findByCode(String code);
    List<CurrencyInfo> findAllByCodeIn(List<String> codes);
}
