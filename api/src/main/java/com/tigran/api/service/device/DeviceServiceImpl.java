package com.tigran.api.service.device;

import com.tigran.api.domain.entity.common.base.ModelStatus;
import com.tigran.api.domain.entity.device.Device;
import com.tigran.api.domain.model.common.page.PageModel;
import com.tigran.api.domain.model.common.search.device.DeviceSearchProperties;
import com.tigran.api.domain.model.rest.request.device.CreateDeviceRequest;
import com.tigran.api.domain.model.rest.request.device.UpdateDeviceRequest;
import com.tigran.api.domain.model.common.exceptions.RecordConflictException;
import com.tigran.api.domain.model.common.exceptions.errorcode.ErrorCode;
import com.tigran.api.repository.device.DeviceRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 11/25/24
 * Time: 7:14 PM
 */
@Service
@Log4j2
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository repository;

    public DeviceServiceImpl(final DeviceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Device create(final CreateDeviceRequest request) {
        log.info("Creating device by request - {} ", request);
        Assert.notNull(request, "Request must not be null");
        assertNotExists(request.getMacAddress(), request.getName());
        Device device = request.toEntity();
        Device result = repository.save(device);
        log.info("Successfully created device by request - {}, result - {}", request, result);
        return result;
    }

    @Override
    public Device getById(final UUID id) {
        log.info("Retrieving device with id - {} ", id);
        Assert.notNull(id, "Id cannot be null");
        Device device = repository.findById(id)
                .orElseThrow(() -> new RecordConflictException(String
                        .format("Device with id - %s not exists", id), ErrorCode.NOT_EXISTS_EXCEPTION));
        log.info("Successfully retrieving device with id - {}, result - {} ", id, device);
        return device;
    }

    @Override
    public Device update(final UUID id, final UpdateDeviceRequest request) {
        log.info("Updating device with id - {} ", id);
        Assert.notNull(id, "Id cannot be null");
        Assert.notNull(request, "Request must not be null");
        Device result;
        Device device = getById(id);
        if (!device.getName().equals(request.getName()) ||
                !device.getMacAddress().equals(request.getMacAddress())) {
            assertNotExists(request.getMacAddress(), request.getName());
        }
        device = request.toEntity(device);
        result = repository.save(device);
        log.info("Successfully updated device with id - {}, result - {} ", id, result);
        return result;
    }

    @Override
    public void delete(final UUID id, final boolean deleteFromDb) {
        log.info("Deleting device with id - {} ", id);
        Assert.notNull(id, "Id cannot be null");
        Device device = getById(id);
        if (deleteFromDb) {
            repository.delete(device);
            log.info("Successfully deleted device with id - {} from db", id);
        } else {
            device.setStatus(ModelStatus.DELETED);
            repository.save(device);
            log.info("Successfully soft deleted device with id - {} ", id);
        }
    }

    @Override
    public PageModel<Device> search(
            final DeviceSearchProperties request, final ModelStatus status,
            final int page, final int size) {
        log.info("Searching devices by request - {}", request);
        Assert.notNull(request, "Request cannot be null");
        PageModel<Device> devices = repository.searchDevices(request, status, page, size);
        log.info("Successfully retrieved devices - {}", devices);
        return new PageModel<>(devices.getItems(), devices.getTotalCount());
    }

    private void assertNotExists(final String macAddress, final String name) {
        if (repository.findByMacAddressAndName(macAddress, name).isPresent()) {
            throw new RecordConflictException(String
                    .format("Device with name - %s already exists",
                            name), ErrorCode.EXISTS_EXCEPTION);
        }
    }
}
