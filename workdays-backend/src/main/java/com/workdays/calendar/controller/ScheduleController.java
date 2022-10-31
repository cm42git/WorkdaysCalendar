package com.workdays.calendar.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workdays.calendar.model.SchedulePattern;
import com.workdays.calendar.model.StartEndDates;
import com.workdays.calendar.service.ScheduleService;

@RestController
@CrossOrigin
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @GetMapping(path = "/scheduledates")
    public List<StartEndDates> listStartEnd(
            @RequestParam int daysOn,
            @RequestParam int daysOff,
            @RequestParam int daysOnAlt,
            @RequestParam int daysOffAlt,
            @RequestParam @DateTimeFormat(iso=ISO.DATE) LocalDate startLineOne,
            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate beginMonth,
            @RequestParam int numMonths,
            @RequestParam int lineNum) {
        SchedulePattern sched = new SchedulePattern(daysOn, daysOff, daysOnAlt, daysOffAlt, startLineOne);
        return scheduleService.listStartEnd(sched, beginMonth, numMonths, lineNum);
    }

}
