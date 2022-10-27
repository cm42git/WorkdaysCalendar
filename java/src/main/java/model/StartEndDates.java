package model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class StartEndDates {

    private final LocalDate start;
    private final LocalDate end;
}
