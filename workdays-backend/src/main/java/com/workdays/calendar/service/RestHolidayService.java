package com.workdays.calendar.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
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
        return new Holiday("Presidents' Day", LocalDate.of(monthYear.getYear(), 2, presidentsDayOfMonth));
    }

    private Holiday getMemorialDay(LocalDate monthYear) {
        int weekdayLastMay = LocalDate.of(monthYear.getYear(), 5, 31).getDayOfWeek().getValue();
        // get the day of month for the last Monday of May
        int memorialDayOfMonth = 31 - ((weekdayLastMay - 1) % 7);
        return new Holiday("Memorial Day", LocalDate.of(monthYear.getYear(), 5, memorialDayOfMonth));
    }

    private Holiday getIndependenceDay(LocalDate monthYear) {
        return new Holiday("Independence Day", LocalDate.of(monthYear.getYear(), 7, 4));
    }

    private Holiday getLaborDay(LocalDate monthYear) {
        int weekdayFirstSeptember = LocalDate.of(monthYear.getYear(), monthYear.getMonthValue(), 1).getDayOfWeek()
                .getValue();
        // get the day of month for the 1st Monday of September
        int laborDayOfMonth = 1 + ((8 - weekdayFirstSeptember) % 7);
        return new Holiday("Labor Day", LocalDate.of(monthYear.getYear(), 9, laborDayOfMonth));
    }

    /**
     * @param monthYear
     * @param weekday
     * @param ordinal
     */
    public LocalDate getDayOfMonth(LocalDate monthYear, DayOfWeek weekday, int ordinal) {
        // monthYear indicates the month and year to derive the day of month from
        // weekday indicates which day of the week to return
        // ordinal indicates which instance of the day of week to return (i.e. 1=first,
        // 2=second, 3=third, 4=fourth, 5=last)
        if (ordinal > 0 && ordinal < 5) {
            DayOfWeek weekdayFirstOfMonth = monthYear.with(TemporalAdjusters.firstDayOfMonth()).getDayOfWeek();
            int dayOfMonth = (7 * (ordinal - 1)) + ((7 - weekdayFirstOfMonth.getValue() + weekday.getValue()) % 7) + 1;
            return LocalDate.of(monthYear.getYear(), monthYear.getMonthValue(), dayOfMonth);
        } else if (ordinal == 5) {
            DayOfWeek weekdayLastOfMonth = monthYear.with(TemporalAdjusters.lastDayOfMonth()).getDayOfWeek();
            int dayOfMonth = monthYear.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth()
                    - (((weekdayLastOfMonth.getValue() - weekday.getValue() + 1) + 7) % 7);
            return LocalDate.of(monthYear.getYear(), monthYear.getMonthValue(), dayOfMonth);
        }

        return LocalDate.MIN;
    }
}
