package com.kdm1t.converter.rest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class FillByCzBankRequest {

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateFrom;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateTo;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate certainDate;
    private Integer year;

}
