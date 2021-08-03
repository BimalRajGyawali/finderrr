package com.ncit.finder.functionality;

import com.ncit.finder.models.DurationTillNow;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LocalDateTimeParser {
    
    public static DurationTillNow parse(@NonNull LocalDateTime fromTemp) {
		LocalDateTime to = LocalDateTime.now();

		long years = fromTemp.until(to, ChronoUnit.YEARS);
		fromTemp = fromTemp.plusYears(years);

		long months = fromTemp.until(to, ChronoUnit.MONTHS);
		fromTemp = fromTemp.plusMonths(months);

		long days = fromTemp.until(to, ChronoUnit.DAYS);
		fromTemp = fromTemp.plusDays(days);

		long hours = fromTemp.until(to, ChronoUnit.HOURS);
		fromTemp = fromTemp.plusHours(hours);

		long minutes = fromTemp.until(to, ChronoUnit.MINUTES);
		fromTemp = fromTemp.plusMinutes(minutes);

		long seconds = fromTemp.until(to, ChronoUnit.SECONDS);

		return new DurationTillNow(years, months, days, hours, minutes, seconds );
	}

	public static LocalDateTime parseOrGetCurrent(String dateTimeString){
		if(dateTimeString != null && !dateTimeString.isEmpty()){
			return LocalDateTime.parse(dateTimeString);
		}
		return LocalDateTime.now();
	}
}
