package com.tigran.api.domain.port.inbound.device.data;

import com.tigran.api.domain.model.entity.device.data.DeviceData;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 11/25/24
 * Time: 7:15 PM
 */
public interface DeviceDataService {

    DeviceData save(final DeviceData data);
    
    void deleteAllByDevice(final UUID deviceId, final boolean deleteFromDb);

    List<DeviceData> searchWithRangeByDevice(
            final UUID deviceId,
            final Instant startTime,
            final Instant endTime);

}
