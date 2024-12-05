package com.tigran.api.domain.port.inbound.threshold;

import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Created by Tigran Melkonyan
 * Date: 12/5/24
 * Time: 11:58 AM
 */
public interface TimeRangeValidator {

    boolean checkIsWithinTimeRange(final LocalTime start, final LocalTime end, final ZoneId zoneId);
}
