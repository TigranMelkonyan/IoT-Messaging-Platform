package com.tigran.api.application.usecase.threshold;

import com.tigran.api.domain.port.inbound.threshold.TimeRangeValidator;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Tigran Melkonyan
 * Date: 12/5/24
 * Time: 12:00 PM
 */
@Service
public class TimeRangeValidatorImpl implements TimeRangeValidator {

    @Override
    public boolean checkIsWithinTimeRange(final LocalTime startTime, final LocalTime endTime, final ZoneId zoneId) {
        ZonedDateTime startDateTime = LocalDateTime.of(LocalDate.now(), startTime)
                .atZone(zoneId);
        ZonedDateTime endDateTime = LocalDateTime.of(LocalDate.now(), endTime)
                .atZone(zoneId);
        ZonedDateTime clientTime = LocalDateTime.of(LocalDate.now(), LocalTime.now())
                .atZone(zoneId);
        Instant start = startDateTime.toInstant();
        Instant end = endDateTime.toInstant();
        Instant check = clientTime.toInstant();
        if (end.isBefore(start)) {
            return check.isAfter(start) || check.isBefore(end);
        } else {
            return check.isAfter(start) && check.isBefore(end);
        }
    }
}
