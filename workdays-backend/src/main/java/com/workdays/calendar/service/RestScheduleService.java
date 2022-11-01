package com.workdays.calendar.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.workdays.calendar.model.SchedulePattern;
import com.workdays.calendar.model.StartEndDates;

@Component
public class RestScheduleService implements ScheduleService {

    public int lineDayNum(SchedulePattern sched, int lineNum, LocalDate dateToCheck) {
        int dateCheckNum = (int) sched.startLineOne().plusDays((long) lineNum - 1).until(dateToCheck, ChronoUnit.DAYS);
        int daysTotal = sched.daysOn() + sched.daysOff() + sched.daysOnAlt() + sched.daysOffAlt();
        return Math.floorMod(dateCheckNum, daysTotal);
    }

    public boolean isWorkingDay(SchedulePattern sched, int lineNumber, LocalDate dateToCheck) {
        boolean working = false;
        int daysTotal = sched.daysOn() + sched.daysOff() + sched.daysOnAlt() + sched.daysOffAlt();
        int daysBack = sched.daysOnAlt() + sched.daysOffAlt();
        // int startLineOneNum = DAY_ZERO_DATE.until(startLineOne, ChronoUnit.DAYS);
        int dateCheckNum = (int) sched.startLineOne().plusDays((long) lineNumber - 1).until(dateToCheck, ChronoUnit.DAYS);

        if (Math.floorMod(dateCheckNum, daysTotal) < sched.daysOn()) {
            working = true;
        }
        if ((Math.floorMod((dateCheckNum + daysBack), daysTotal) < sched.daysOnAlt())) {
            working = true;
        }

        return working;
    }

    public Map<LocalDate, Boolean> workDays(SchedulePattern sched,
            int lineNumber, LocalDate startDate, int daysToPrint) {
        Map<LocalDate, Boolean> output = new LinkedHashMap<>();

        for (int i = 0; i < daysToPrint; i++) {
            output.put(startDate.plusDays(i), isWorkingDay(sched, lineNumber, startDate.plusDays(i)));
        }

        return output;
    }

    public List<StartEndDates> listStartEnd(SchedulePattern sched, LocalDate beginMonth, int numMonths, int lineNum) {
        List<StartEndDates> output = new ArrayList<>();
        int daysTotal = sched.daysOn() + sched.daysOff() + sched.daysOnAlt() + sched.daysOffAlt();
        LocalDate firstStart = beginMonth.minusDays((long) lineDayNum(sched, lineNum, beginMonth));
        int daysInPeriodInput = (int) beginMonth.plusMonths((int) numMonths).toEpochDay()
                - (int) beginMonth.toEpochDay();
        int daysInPeriod = daysInPeriodInput + lineDayNum(sched, lineNum, beginMonth) + daysTotal;

        for (int i = 0; i < daysInPeriod; i += daysTotal) {
            LocalDate start = firstStart.plusDays(i);
            LocalDate end = firstStart.plusDays(i + sched.daysOn() - 1);
            StartEndDates startEnd = new StartEndDates(start, end);
            output.add(startEnd);

            if (sched.daysOnAlt() > 0) {
                start = end.plusDays(sched.daysOff() + 1);
                end = start.plusDays(sched.daysOnAlt() - 1);
                startEnd = new StartEndDates(start, end);
                output.add(startEnd);
            }
        }

        return output;
    }
}
