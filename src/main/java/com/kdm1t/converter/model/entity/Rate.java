package com.kdm1t.converter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"rateDate", "currencyInfo"})
@EqualsAndHashCode(exclude = {"rateDate", "currencyInfo"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Double rate;

    @ManyToOne
    RateDate rateDate;

    @ManyToOne
    CurrencyInfo currencyInfo;

    @JsonIgnore
    public Double divideRateByAmount() {
        return (this.rate / this.amount);
    }
}
