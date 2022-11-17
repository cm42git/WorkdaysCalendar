package com.workdays.calendar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;

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
    void get2022HolidayList() {
        int year = 2022;
        List<Holiday> expectedHolidays = new ArrayList<>();
        expectedHolidays.add(new Holiday("New Year's Day", LocalDate.of(year, 1, 1)));
        expectedHolidays.add(new Holiday("Martin Luther King Jr. Day", LocalDate.of(year, 1, 17)));
        expectedHolidays.add(new Holiday("Presidents' Day", LocalDate.of(year, 2, 21)));
        expectedHolidays.add(new Holiday("Good Friday", LocalDate.of(year, 4, 15)));
        expectedHolidays.add(new Holiday("Easter Day", LocalDate.of(year, 4, 17)));
        expectedHolidays.add(new Holiday("Memorial Day", LocalDate.of(year, 5, 30)));
        expectedHolidays.add(new Holiday("Independence Day", LocalDate.of(year, 7, 4)));
        expectedHolidays.add(new Holiday("Labor Day", LocalDate.of(year, 9, 5)));
        expectedHolidays.add(new Holiday("Thanksgiving Day", LocalDate.of(year, 11, 24)));
        expectedHolidays.add(new Holiday("Christmas Day", LocalDate.of(year, 12, 25)));

        List<Holiday> actualHolidays = holidayService.getHolidays(LocalDate.of(year, 1, 1), 0, 12);

        assertEquals(expectedHolidays,actualHolidays);
    }

    // @Test
    // void getLaborDay2022() {
    // Holiday expected = new Holiday("Labor Day", LocalDate.of(2022, 9, 5));
    // Holiday actual = holidayService.getLaborDay(LocalDate.of(2022, 9, 1));
    // assertEquals(expected, actual);
    // }

    /* @Test
    void getEasterDay2023() {
        Holiday expected = new Holiday("Easter Day", LocalDate.of(2023, 4, 9));
        Holiday actual = holidayService.getEasterDay(LocalDate.of(2023, 1, 1));
        assertEquals(expected, actual);
    }
    @Test
    void getEasterDay6554() {
        Holiday expected = new Holiday("Easter Day", LocalDate.of(6554, 4, 21));
        Holiday actual = holidayService.getEasterDay(LocalDate.of(6554, 1, 1));
        assertEquals(expected, actual);
    }

    @Test
    void testDaysOfMonthFunction() {
        LocalDate expected = LocalDate.of(2022, 9, 5);
        LocalDate actual = holidayService.getDayOfMonth(LocalDate.of(2022, 9, 1), DayOfWeek.MONDAY, 1);
        assertEquals(expected, actual);
    }

    @Test
    void testFirstWeekOfMonthSeptember2022() {
        List<LocalDate> expectDaysOfSeptember = new ArrayList<>();
        expectDaysOfSeptember.add(LocalDate.of(2022, 9, 5));
        expectDaysOfSeptember.add(LocalDate.of(2022, 9, 6));
        expectDaysOfSeptember.add(LocalDate.of(2022, 9, 7));
        expectDaysOfSeptember.add(LocalDate.of(2022, 9, 1));
        expectDaysOfSeptember.add(LocalDate.of(2022, 9, 2));
        expectDaysOfSeptember.add(LocalDate.of(2022, 9, 3));
        expectDaysOfSeptember.add(LocalDate.of(2022, 9, 4));

        List<LocalDate> actualDaysOfSeptember = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            actualDaysOfSeptember.add(holidayService.getDayOfMonth(LocalDate.of(2022, 9, 1), DayOfWeek.of(i), 1));
        }

        assertEquals(expectDaysOfSeptember, actualDaysOfSeptember);
    }

    @Test
    void testSecondWeekOfMonthSeptember2022() {
        List<LocalDate> expectDaysOfSeptember = new ArrayList<>();
        expectDaysOfSeptember.add(LocalDate.of(2022, 9, 12));
        expectDaysOfSeptember.add(LocalDate.of(2022, 9, 13));
        expectDaysOfSeptember.add(LocalDate.of(2022, 9, 14));
        expectDaysOfSeptember.add(LocalDate.of(2022, 9, 8));
        expectDaysOfSeptember.add(LocalDate.of(2022, 9, 9));
        expectDaysOfSeptember.add(LocalDate.of(2022, 9, 10));
        expectDaysOfSeptember.add(LocalDate.of(2022, 9, 11));

        List<LocalDate> actualDaysOfSeptember = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            actualDaysOfSeptember.add(holidayService.getDayOfMonth(LocalDate.of(2022, 9, 1), DayOfWeek.of(i), 2));
        }

        assertEquals(expectDaysOfSeptember, actualDaysOfSeptember);
    }

    @Test
    void testLastWeekOfMonthSeptember2022() {
        int monthNum = 9;
        List<LocalDate> expectDays = new ArrayList<>();
        expectDays.add(LocalDate.of(2022, monthNum, 25));
        expectDays.add(LocalDate.of(2022, monthNum, 26));
        expectDays.add(LocalDate.of(2022, monthNum, 27));
        expectDays.add(LocalDate.of(2022, monthNum, 28));
        expectDays.add(LocalDate.of(2022, monthNum, 29));
        expectDays.add(LocalDate.of(2022, monthNum, 30));
        expectDays.add(LocalDate.of(2022, monthNum, 24));

        List<LocalDate> actualDays = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            actualDays.add(holidayService.getDayOfMonth(LocalDate.of(2022, monthNum, 1), DayOfWeek.of(i), 5));
        }

        assertEquals(expectDays, actualDays);
    }

    @Test
    void testLastWeekOfMonthOctober2022() {
        int monthNum = 10;
        List<LocalDate> expectDays = new ArrayList<>();
        expectDays.add(LocalDate.of(2022, monthNum, 30));
        expectDays.add(LocalDate.of(2022, monthNum, 31));
        expectDays.add(LocalDate.of(2022, monthNum, 25));
        expectDays.add(LocalDate.of(2022, monthNum, 26));
        expectDays.add(LocalDate.of(2022, monthNum, 27));
        expectDays.add(LocalDate.of(2022, monthNum, 28));
        expectDays.add(LocalDate.of(2022, monthNum, 29));

        List<LocalDate> actualDays = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            actualDays.add(holidayService.getDayOfMonth(LocalDate.of(2022, monthNum, 1), DayOfWeek.of(i), 5));
        }

        assertEquals(expectDays, actualDays);
    }

    @Test
    void testLastWeekOfMonthFebruary2020() {
        int monthNum = 2;
        int yearNum = 2020;
        List<LocalDate> expectDays = new ArrayList<>();
        expectDays.add(LocalDate.of(yearNum, monthNum, 23));
        expectDays.add(LocalDate.of(yearNum, monthNum, 24));
        expectDays.add(LocalDate.of(yearNum, monthNum, 25));
        expectDays.add(LocalDate.of(yearNum, monthNum, 26));
        expectDays.add(LocalDate.of(yearNum, monthNum, 27));
        expectDays.add(LocalDate.of(yearNum, monthNum, 28));
        expectDays.add(LocalDate.of(yearNum, monthNum, 29));

        List<LocalDate> actualDays = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            actualDays.add(holidayService.getDayOfMonth(LocalDate.of(yearNum, monthNum, 1), DayOfWeek.of(i), 5));
        }

        assertEquals(expectDays, actualDays);
    } */
}
