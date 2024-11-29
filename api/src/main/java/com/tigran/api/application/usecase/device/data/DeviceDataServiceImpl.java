package com.tigran.api.application.usecase.device.data;

import com.tigran.api.adapter.outbound.persistence.device.data.DeviceDataRepository;
import com.tigran.api.domain.model.entity.common.base.ModelStatus;
import com.tigran.api.domain.model.entity.device.data.DeviceData;
import com.tigran.api.domain.port.inbound.device.data.DeviceDataService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 11/29/24
 * Time: 4:00 PM
 */
@Service
@Log4j2
public class DeviceDataServiceImpl implements DeviceDataService {

    private final DeviceDataRepository repository;

    public DeviceDataServiceImpl(final DeviceDataRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public DeviceData save(final DeviceData data) {
        log.info("Saving device data by request - {} ", data);
        Assert.notNull(data, "Request must not be null");
        DeviceData result = repository.save(data);
        log.info("Successfully saved device data by request - {}, result - {}", data, result);
        return result;
    }

    @Override
    @Transactional
    public void deleteAllByDevice(final UUID deviceId, final boolean deleteFromDb) {
        log.info("Deleting device data by device id - {} ", deviceId);
        Assert.notNull(deviceId, "deviceId cannot be null");
        if (deleteFromDb) {
            repository.deleteAllByDeviceId(deviceId);
            log.info("Successfully deleted all device data by device id - {} from db", deviceId);
        } else {
            List<DeviceData> list = repository.findAllByDeviceId(deviceId);
            list.forEach(deviceData -> deviceData.setStatus(ModelStatus.DELETED));
            repository.saveAll(list);
            log.info("Successfully soft deleted device data by device id - {} ", deviceId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceData> searchWithRangeByDevice(
            final UUID deviceId,
            final Instant startTime,
            final Instant endTime) {
        log.info("Retrieving device data by device id - {} ", deviceId);
        List<DeviceData> result = repository.findByTimestampBetween(deviceId, startTime, endTime);
        log.info("Successfully retrieved device data by device id - {}, result - {}", deviceId, result);
        return result;
    }
}
