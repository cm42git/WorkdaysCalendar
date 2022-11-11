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
        int mlkDayOfMonth = ((8 - weekdayFirstJanuary) % 7) + 15;
        return new Holiday("Martin Luther King Jr. Day", LocalDate.of(monthYear.getYear(), 1, mlkDayOfMonth));
    }
}
