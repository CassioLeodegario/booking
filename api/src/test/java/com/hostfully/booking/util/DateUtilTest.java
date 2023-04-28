package com.hostfully.booking.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DateUtilTest {

    @Test
    void getDatesBetween() {
        LocalDate startDate = LocalDate.now().minusDays(5);
        LocalDate endDate = LocalDate.now();

        List<LocalDate> result = DateUtil.getDatesBetween(startDate, endDate);

        List<LocalDate> expected = mockLocalDateList();
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void getDatesBetweenNullStart() {
        LocalDate startDate = null;
        LocalDate endDate = LocalDate.now();

        List<LocalDate> result = DateUtil.getDatesBetween(startDate, endDate);

        List<LocalDate> expected = Collections.emptyList();
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void getDatesBetweenNullEnd() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = null;

        List<LocalDate> result = DateUtil.getDatesBetween(startDate, endDate);

        List<LocalDate> expected = Collections.emptyList();
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    private List<LocalDate> mockLocalDateList() {
        List<LocalDate> dates = new ArrayList<>();
        IntStream.range(0, 6).forEach(n -> {
            dates.add(LocalDate.now().minusDays(n));
        });
        return dates;
    }
}
