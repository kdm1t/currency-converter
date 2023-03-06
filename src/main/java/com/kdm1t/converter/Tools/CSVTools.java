package com.kdm1t.converter.Tools;

import com.kdm1t.converter.model.csv.RateCSVEntity;
import com.kdm1t.converter.model.csv.RateCSVEntityByYear;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class CSVTools {

    public static List<RateCSVEntity> parseCSV(InputStream body) {
        return new CsvToBeanBuilder<RateCSVEntity>(new InputStreamReader(body))
                .withSeparator('|')
                .withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .withType(RateCSVEntity.class)
                .build()
                .parse();
    }

    public static List<RateCSVEntityByYear> parseCSVByYear(InputStream body) {
        return new CsvToBeanBuilder<RateCSVEntityByYear>(new InputStreamReader(body))
                .withSeparator('|')
                .withIgnoreLeadingWhiteSpace(true)
                .withType(RateCSVEntityByYear.class)
                .build()
                .parse();
    }
}
