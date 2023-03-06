package com.kdm1t.converter.rest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RateReportRequest {
    private List<String> codes;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateFrom;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateTo;
}
