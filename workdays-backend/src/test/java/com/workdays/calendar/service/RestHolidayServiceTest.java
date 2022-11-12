package com.workdays.calendar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.workdays.calendar.model.Holiday;

public class RestHolidayServiceTest {
    RestHolidayService holidayService = new RestHolidayService();

    @Test
    void getJanuary2022Holidays() {
        List<Holiday> expectedHolidays = new ArrayList<>();
        expectedHolidays.add(new Holiday("New Year's Day", LocalDate.of(2022, 1, 1)));
        expectedHolidays.add(new Holiday("Martin Luther King Jr. Day", LocalDate.of(2022, 1, 17)));

        List<Holiday> actualHolidays = holidayService.getHolidays(LocalDate.of(2022, 1, 1));

        assertEquals(expectedHolidays, actualHolidays);
    }

    @Test
    void getJanuary2024Holidays() {
        List<Holiday> expectedHolidays = new ArrayList<>();
        expectedHolidays.add(new Holiday("New Year's Day", LocalDate.of(2024, 1, 1)));
        expectedHolidays.add(new Holiday("Martin Luther King Jr. Day", LocalDate.of(2024, 1, 15)));

        List<Holiday> actualHolidays = holidayService.getHolidays(LocalDate.of(2024, 1, 1));

        assertEquals(expectedHolidays, actualHolidays);
    }
    
    @Test
    void getfirstMondayOfJanuary2022() {
        
    }
}
