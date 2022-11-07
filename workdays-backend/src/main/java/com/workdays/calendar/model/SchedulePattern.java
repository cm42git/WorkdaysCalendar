package com.workdays.calendar.model;

import java.time.LocalDate;

public record SchedulePattern(int daysOn, int daysOff, int daysOnAlt, int daysOffAlt, LocalDate startLineOne) {
}
