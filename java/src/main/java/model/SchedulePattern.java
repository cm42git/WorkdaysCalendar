package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.*;

@Data
public class SchedulePattern {
    // private static final LocalDate DAY_ZERO_DATE = LocalDate.of(1899, 12, 30);
    private final int daysOn;
    private final int daysOff;
    private final int daysOnAlt;
    private final int daysOffAlt;
    private final LocalDate startLineOne;

    // =if(OR(MOD(B$6-StartSched+$A7-1,daysTotal)<daysOn,MOD(B$6-StartSched+$A7-1+daysBack,daysTotal)<daysOnAlt),"Work","Off")
    // B6=dateToCheck A7=lineNum StartSched=startLineOne
    // daysTotal=(daysOn+daysOff+daysOnAlt+daysOffAlt)
    // daysBack=(daysOnAlt+daysOffAlt)
    // line options=daysTotal

    public int lineDayNum(int lineNum, LocalDate dateToCheck) {
        int dateCheckNum = (int) startLineOne.until(dateToCheck, ChronoUnit.DAYS);
        int daysTotal = daysOn + daysOff + daysOnAlt + daysOffAlt;
        return Math.floorMod((dateCheckNum + lineNum - 1), daysTotal);
    }

    public boolean isWorkingDay(int lineNumber, LocalDate dateToCheck) {
        boolean working = false;
        int daysTotal = daysOn + daysOff + daysOnAlt + daysOffAlt;
        int daysBack = daysOnAlt + daysOffAlt;
        // int startLineOneNum = DAY_ZERO_DATE.until(startLineOne, ChronoUnit.DAYS);
        int dateCheckNum = (int) startLineOne.until(dateToCheck, ChronoUnit.DAYS);

        if (Math.floorMod((dateCheckNum + lineNumber - 1), daysTotal) < daysOn) {
            working = true;
        }
        if ((Math.floorMod((dateCheckNum + lineNumber - 1 + daysBack), daysTotal) < daysOnAlt)) {
            working = true;
        }

        return working;
    }

    public Map<LocalDate, Boolean> workDays(int lineNumber, LocalDate startDate, int daysToPrint) {
        Map<LocalDate, Boolean> output = new LinkedHashMap<>();

        for (int i = 0; i < daysToPrint; i++) {
            output.put(startDate.plusDays(i), isWorkingDay(lineNumber, startDate.plusDays(i)));
        }

        return output;
    }

    public List<StartEndDates> listStartEnd(LocalDate beginMonth, int numMonths, int lineNum) {
        List<StartEndDates> output = new ArrayList<>();
        int daysTotal = daysOn + daysOff + daysOnAlt + daysOffAlt;
        LocalDate firstStart = beginMonth.minusDays((long) lineDayNum(lineNum, beginMonth));
        int daysInPeriodInput = (int) beginMonth.plusMonths((int) numMonths).toEpochDay()
                - (int) beginMonth.toEpochDay();
        int daysInPeriod = daysInPeriodInput + lineDayNum(lineNum, beginMonth) + daysTotal;

        for (int i = 0; i < daysInPeriod; i+=daysTotal) {
            LocalDate start = firstStart.plusDays(i);
            LocalDate end = firstStart.plusDays(i + daysOn - 1);
            StartEndDates startEnd = new StartEndDates(start, end);
            output.add(startEnd);

            if (daysOnAlt > 0) {
                start = end.plusDays(daysOff);
                end = start.plusDays(daysOnAlt - 1);
                startEnd=new StartEndDates(start, end);
                output.add(startEnd);
            }
        }

        return output;
    }
}
