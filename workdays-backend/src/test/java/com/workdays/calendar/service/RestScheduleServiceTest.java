package com.workdays.calendar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.workdays.calendar.model.SchedulePattern;
import com.workdays.calendar.model.StartEndDates;

public class RestScheduleServiceTest {
    RestScheduleService scheduleService = new RestScheduleService();

    @Test
    void testListStartEnd7On7OffJanuary2022() {
        List<StartEndDates> expectedTours = new ArrayList<>();
        expectedTours.add(new StartEndDates(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 1).plusDays(6)));
        expectedTours.add(new StartEndDates(LocalDate.of(2022, 1, 15), LocalDate.of(2022, 1, 15).plusDays(6)));
        expectedTours.add(new StartEndDates(LocalDate.of(2022, 1, 29), LocalDate.of(2022, 1, 29).plusDays(6)));
        expectedTours.add(new StartEndDates(LocalDate.of(2022, 2, 12), LocalDate.of(2022, 2, 12).plusDays(6)));

        SchedulePattern sched = new SchedulePattern(7, 7, 0, 0, LocalDate.of(2022, 1, 1));

        List<StartEndDates> actualTours = scheduleService.listStartEnd(
                sched,
                LocalDate.of(2022, 1, 1),
                1,
                1);

        assertEquals(expectedTours, actualTours);
    }
}
