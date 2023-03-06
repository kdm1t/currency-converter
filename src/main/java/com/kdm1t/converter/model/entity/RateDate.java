package com.kdm1t.converter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"rates"})
@EqualsAndHashCode(exclude = {"rates"})
@Table(indexes = @Index(columnList = "rateDate"))
public class RateDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(unique = true, updatable = false)
    private LocalDate rateDate;

    @OneToMany(mappedBy = "rateDate")
    @JsonIgnore
    private List<Rate> rates;

}
