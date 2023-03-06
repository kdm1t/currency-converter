package com.kdm1t.converter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"rates"})
@EqualsAndHashCode(exclude = {"rates"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(indexes = @Index(columnList = "code"))
public class CurrencyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String country;

    private String currency;

    @Column(unique = true)
    private String code;

    @OneToMany(mappedBy = "currencyInfo")
    @JsonIgnore
    List<Rate> rates;
}
