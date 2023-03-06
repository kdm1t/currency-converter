package com.kdm1t.converter.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "options", indexes = @Index(columnList = "optionName"))
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String optionName;
    private Integer numericValue;
    private String strValue;
    private Boolean status;

}
