package com.workdays.calendar.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.workdays.calendar.model.SchedulePattern;
import com.workdays.calendar.model.StartEndDates;

public interface ScheduleService {
    int lineDayNum(SchedulePattern sched, int lineNum, LocalDate dateToCheck);

    boolean isWorkingDay(SchedulePattern sched, int lineNumber, LocalDate dateToCheck);

    Map<LocalDate, Boolean> workDays(SchedulePattern sched, int lineNumber, LocalDate startDate, int daysToPrint);

    List<StartEndDates> listStartEnd(SchedulePattern sched, LocalDate beginMonth, int numMonths, int lineNum);
}
