package com.workdays.calendar.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.workdays.calendar.model.Holiday;

@Component
public class RestHolidayService implements HolidayService {
    enum Ord {
        FIRST(1),
        SECOND(2),
        THIRD(3),
        FOURTH(4),
        LAST(5);

        private final int weekAdj;

        Ord(int weekAdj) {
            this.weekAdj = weekAdj;
        }

        int weekOfMonth() {
            return weekAdj;
        }
    }

    public List<Holiday> getHolidays(LocalDate monthYear, int monthsBefore, int monthsAfter) {
        List<Holiday> holidays = new ArrayList<>();
        LocalDate startMonthYear = monthYear.minusMonths(Math.max(0, monthsBefore));

        for (int i = 0; i < monthsBefore + monthsAfter; i++) {
            holidays.addAll(getHolidays(startMonthYear.plusMonths(i)));
        }

        return holidays;
    }

    public List<Holiday> getHolidays(LocalDate monthYear) {
        List<Holiday> holidays = new ArrayList<>();

        switch (monthYear.getMonth()) {
            case JANUARY -> {
                holidays.add(getNewYearsDay(monthYear));
                holidays.add(getMLKDay(monthYear));
            }
            case FEBRUARY -> holidays.add(getPresidentsDay(monthYear));
            case MARCH -> {
                if (getGoodFridayDay(monthYear).date().getMonth() == Month.MARCH) {
                    holidays.add(getGoodFridayDay(monthYear));
                }
                if (getEasterDay(monthYear).date().getMonth() == Month.MARCH) {
                    holidays.add(getEasterDay(monthYear));
                }
            }
            case APRIL -> {
                if (getGoodFridayDay(monthYear).date().getMonth() == Month.APRIL) {
                    holidays.add(getGoodFridayDay(monthYear));
                }
                if (getEasterDay(monthYear).date().getMonth() == Month.APRIL) {
                    holidays.add(getEasterDay(monthYear));
                }
            }
            case MAY -> holidays.add(getMemorialDay(monthYear));
            case JULY -> holidays.add(getIndependenceDay(monthYear));
            case SEPTEMBER -> holidays.add(getLaborDay(monthYear));
            case NOVEMBER -> holidays.add(getThanksgivingDay(monthYear));
            case DECEMBER -> holidays.add(getChristmasDay(monthYear));
            default -> {
            }
        }

        return holidays;
    }

    private Holiday getNewYearsDay(LocalDate monthYear) {
        return new Holiday("New Year's Day", LocalDate.of(monthYear.getYear(), 1, 1));
    }

    private Holiday getMLKDay(LocalDate monthYear) {
        return new Holiday("Martin Luther King Jr. Day", getDayOfMonth(monthYear, DayOfWeek.MONDAY, Ord.THIRD));
    }

    private Holiday getPresidentsDay(LocalDate monthYear) {
        return new Holiday("Presidents' Day", getDayOfMonth(monthYear, DayOfWeek.MONDAY, Ord.THIRD));
    }

    /**
     * Adapted from Laurent Longre algorithm for Excel VBA, published at
     * https://groups.google.com/group/microsoft.public.excel.worksheet.functions/msg/bae33ace3ce95b0c
     * 
     * @param monthYear only uses year part of date
     * @return Holiday data type of Easter Day
     * 
     */
    private Holiday getEasterDay(LocalDate monthYear) {
        int century, sunday, epact, golden, leapDayCorrection, syncWithMoon, N;
        int year = monthYear.getYear();

        golden = (year % 19) + 1;
        century = year / 100 + 1;
        leapDayCorrection = 3 * century / 4 - 12;
        syncWithMoon = (8 * century + 5) / 25 - 5;
        sunday = 5 * year / 4 - leapDayCorrection - 10;

        epact = (11 * golden + 20 + syncWithMoon - leapDayCorrection) % 30;
        epact += epact < 0 ? 30 : 0;
        epact += (epact == 25 && golden > 11) || epact == 24 ? 1 : 0;

        N = 44 - epact;
        N += N < 21 ? 30 : 0;
        N = N + 7 - ((sunday + N) % 7);

        return new Holiday("Easter Day", LocalDate.of(monthYear.getYear(), 3, 1).plusDays(N - 1));
    }

    private Holiday getGoodFridayDay(LocalDate monthYear) {
        return new Holiday("Good Friday", getEasterDay(monthYear).date().minusDays(2));
    }

    private Holiday getMemorialDay(LocalDate monthYear) {
        return new Holiday("Memorial Day", getDayOfMonth(monthYear, DayOfWeek.MONDAY, Ord.LAST));
    }

    private Holiday getIndependenceDay(LocalDate monthYear) {
        return new Holiday("Independence Day", LocalDate.of(monthYear.getYear(), 7, 4));
    }

    private Holiday getLaborDay(LocalDate monthYear) {
        return new Holiday("Labor Day", getDayOfMonth(monthYear, DayOfWeek.MONDAY, Ord.FIRST));
    }

    private Holiday getThanksgivingDay(LocalDate monthYear) {
        return new Holiday("Thanksgiving Day", getDayOfMonth(monthYear, DayOfWeek.THURSDAY, Ord.FOURTH));
    }

    private Holiday getChristmasDay(LocalDate monthYear) {
        return new Holiday("Christmas Day", LocalDate.of(monthYear.getYear(), 12, 25));
    }

    /**
     * @param monthYear given month and year to find day of month in
     * @param weekday   which day of week to return
     * @param ordinal   which instance of weekday (i.e. 1=first, 2=second, 3=third,
     *                  4=fourth, 5=last)
     */
    private LocalDate getDayOfMonth(LocalDate monthYear, DayOfWeek weekday, Ord week) {
        if (week != Ord.LAST) {
            DayOfWeek weekdayFirstOfMonth = monthYear.with(TemporalAdjusters.firstDayOfMonth()).getDayOfWeek();
            int dayOfMonth = (7 * (week.weekOfMonth() - 1))
                    + ((7 - weekdayFirstOfMonth.getValue() + weekday.getValue()) % 7) + 1;
            return LocalDate.of(monthYear.getYear(), monthYear.getMonthValue(), dayOfMonth);
        } else {
            LocalDate lastDay = monthYear.with(TemporalAdjusters.lastDayOfMonth());
            DayOfWeek weekdayLastOfMonth = lastDay.getDayOfWeek();
            int dayOfMonth = lastDay.getDayOfMonth() - (((weekdayLastOfMonth.getValue() - weekday.getValue()) + 7) % 7);
            return LocalDate.of(monthYear.getYear(), monthYear.getMonthValue(), dayOfMonth);
        }

    }
}
