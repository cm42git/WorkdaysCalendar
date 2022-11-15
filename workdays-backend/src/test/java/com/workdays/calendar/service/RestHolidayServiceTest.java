package com.workdays.calendar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
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

    // @Test
    // void getLaborDay2022() {
    // Holiday expected = new Holiday("Labor Day", LocalDate.of(2022, 9, 5));
    // Holiday actual = holidayService.getLaborDay(LocalDate.of(2022, 9, 1));
    // assertEquals(expected, actual);
    // }

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
}
