package com.workdays.calendar.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.workdays.calendar.model.Holiday;

public class RestHolidayService {

    public List<Holiday> getHolidays(LocalDate monthYear) {
        List<Holiday> holidays = new ArrayList<>();
        holidays.add(getNewYearsDay(monthYear));
        holidays.add(getMLKDay(monthYear));
        return holidays;
    }

    private Holiday getNewYearsDay(LocalDate monthYear) {
        return new Holiday("New Year's Day", LocalDate.of(monthYear.getYear(), 1, 1));
    }
    
    private Holiday getMLKDay(LocalDate monthYear) {
        int weekdayFirstJanuary = LocalDate.of(monthYear.getYear(), 1, 1).getDayOfWeek().getValue();
        // get the day of month for the 3rd Monday of January
        int mlkDayOfMonth = 15 + ((8 - weekdayFirstJanuary) % 7);
        return new Holiday("Martin Luther King Jr. Day", LocalDate.of(monthYear.getYear(), 1, mlkDayOfMonth));
    }

    private Holiday getPresidentsDay(LocalDate monthYear) {
        int weekdayFirstFebruary = LocalDate.of(monthYear.getYear(), 2, 1).getDayOfWeek().getValue();
        // get the day of month for the 3rd Monday of February
        int presidentsDayOfMonth = 15 + ((8 - weekdayFirstFebruary) % 7);
        return new Holiday("Presidents' Day", LocalDate.of(monthYear.getYear(), 1, presidentsDayOfMonth));
    }
    
    private Holiday getMemorialDay(LocalDate monthYear) {
        int weekdayFirstFebruary = LocalDate.of(monthYear.getYear(), 5, 31).getDayOfWeek().getValue();
        // get the day of month for the last Monday of May
        int memorialDayOfMonth = 31 - ((weekdayFirstFebruary - 1) % 7);
        return new Holiday("Memorial Day", LocalDate.of(monthYear.getYear(), 1, memorialDayOfMonth));
    }
    
    private Holiday getIndependenceDay(LocalDate monthYear) {
        return new Holiday("Independence Day", LocalDate.of(monthYear.getYear(), 7, 4));
    }
    
        private Holiday getLaborDay(LocalDate monthYear) {
            int weekdayFirstSeptember = LocalDate.of(monthYear.getYear(), 2, 1).getDayOfWeek().getValue();
            // get the day of month for the 1st Monday of September
            int mlkDayOfMonth = 15 + ((8 - weekdayFirstSeptember) % 7);
            return new Holiday("Presidents' Day", LocalDate.of(monthYear.getYear(), 1, mlkDayOfMonth));
        }
}
