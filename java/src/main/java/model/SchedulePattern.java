package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.*;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class SchedulePattern {
    // private static final LocalDate DAY_ZERO_DATE = LocalDate.of(1899, 12, 30);
    @Getter
    private long daysOn;
    @Getter
    private long daysOff;
    @Getter
    private long daysOnAlt;
    @Getter
    private long daysOffAlt;
    @Getter
    private LocalDate startLineOne;

    // =if(OR(MOD(B$6-StartSched+$A7-1,daysTotal)<daysOn,MOD(B$6-StartSched+$A7-1+daysBack,daysTotal)<daysOnAlt),"Work","Off")
    // B6=dateToCheck A7=lineNum StartSched=startLineOne
    // daysTotal=(daysOn+daysOff+daysOnAlt+daysOffAlt)
    // daysBack=(daysOnAlt+daysOffAlt)
    // line options=daysTotal

    public boolean isWorkingDay(int lineNumber, LocalDate dateToCheck) {
        boolean working = false;
        long daysTotal = daysOn + daysOff + daysOnAlt + daysOffAlt;
        long daysBack = daysOnAlt + daysOffAlt;
        // long startLineOneNum = DAY_ZERO_DATE.until(startLineOne, ChronoUnit.DAYS);
        long dateCheckNum = startLineOne.until(dateToCheck, ChronoUnit.DAYS);

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
}
