package model;

import java.time.LocalDate;

public class SchedulePattern {
    private int daysOn;
    private int daysOff;
    private int daysOnAlt;
    private int daysOffAlt;
    private LocalDate startLineOne;

//=if(OR(MOD(B$6-StartSched+$A7-1,daysTotal)<daysOn,MOD(B$6-StartSched+$A7-1+daysBack,daysTotal)<daysOnAlt),"Work","Off")

}
