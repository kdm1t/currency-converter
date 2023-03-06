package com.kdm1t.converter.model.csv;

import com.opencsv.bean.CsvBindAndJoinByName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.MultiValuedMap;

@Data
@NoArgsConstructor
@ToString
public class RateCSVEntityByYear {

    @CsvBindAndJoinByName(column = ".*", elementType = String.class)
    private MultiValuedMap<String, String> line;

}
