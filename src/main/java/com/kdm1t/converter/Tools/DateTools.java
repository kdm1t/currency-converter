package com.kdm1t.converter.Tools;

import java.time.LocalDate;

public class DateTools {

    public static boolean isBeforeOrEquals(LocalDate firstDate, LocalDate secondDate) {
        return firstDate.isBefore(secondDate) || firstDate.isEqual(secondDate);
    }
}
