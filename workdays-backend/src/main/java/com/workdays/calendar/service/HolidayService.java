package com.workdays.calendar.service;

import java.time.LocalDate;
import java.util.List;

import com.workdays.calendar.model.Holiday;

public interface HolidayService {
    List<Holiday> getHolidays(LocalDate monthYear);

    List<Holiday> getHolidays(LocalDate monthYear, int monthsBefore, int monthsAfter);
}
