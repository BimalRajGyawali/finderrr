package com.ncit.finder.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class DurationTillNow {
    private long years;
    private long months;
    private long days;
    private long hours;
    private long minutes;
    private long seconds;
}
