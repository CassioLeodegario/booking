package com.hostfully.booking.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DateUtil {

    public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate){
        if(startDate == null || endDate == null) return Collections.emptyList();

        List<LocalDate> datesBetween = new ArrayList<>();
        while (!startDate.isAfter(endDate)) {
            datesBetween.add(startDate);
            startDate = startDate.plusDays(1);
        }
        return datesBetween;
    }
}
