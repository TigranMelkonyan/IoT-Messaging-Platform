package com.tigran.api.application.usecase.device;

import com.tigran.api.adapter.outbound.persistence.device.DeviceRepository;
import com.tigran.api.adapter.outbound.persistence.device.DeviceRepositoryCustomImpl;
import com.tigran.api.application.usecase.dto.device.CreateDeviceRequest;
import com.tigran.api.application.usecase.dto.device.UpdateDeviceRequest;
import com.tigran.api.domain.exception.RecordConflictException;
import com.tigran.api.domain.exception.errorcode.ErrorCode;
import com.tigran.api.domain.model.common.page.PageModel;
import com.tigran.api.domain.model.common.search.DeviceSearchProperties;
import com.tigran.api.domain.model.entity.common.base.ModelStatus;
import com.tigran.api.domain.model.entity.device.Device;
import com.tigran.api.domain.port.inbound.device.DeviceService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final DeviceRepositoryCustomImpl repositoryCustom;

    public DeviceServiceImpl(
            final DeviceRepository repository,
            final DeviceRepositoryCustomImpl repositoryCustom) {
        this.repository = repository;
        this.repositoryCustom = repositoryCustom;
    }

    @Override
    @Transactional
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
    @Transactional(readOnly = true)
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
    @Transactional
    public Device update(final UUID id, final UpdateDeviceRequest request) {
        log.info("Updating device with id - {} ", id);
        Assert.notNull(id, "Id cannot be null");
        Assert.notNull(request, "Request must not be null");
        Device result;
        Device device = getEntity(id);
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
    @Transactional
    public void delete(final UUID id, final boolean deleteFromDb) {
        log.info("Deleting device with id - {} ", id);
        Assert.notNull(id, "Id cannot be null");
        Device device = getEntity(id);
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
    @Transactional(readOnly = true)
    public PageModel<Device> search(
            final DeviceSearchProperties request, final ModelStatus status,
            final int page, final int size) {
        log.info("Searching devices by request - {}", request);
        Assert.notNull(request, "Request cannot be null");
        PageModel<Device> devices = repositoryCustom.searchDevices(request, status, page, size);
        log.info("Successfully retrieved devices - {}", devices);
        return new PageModel<>(devices.getItems(), devices.getTotalCount());
    }

    private Device getEntity(final UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecordConflictException(String
                        .format("Device with id - %s not exists", id), ErrorCode.NOT_EXISTS_EXCEPTION));
    }

    private void assertNotExists(final String macAddress, final String name) {
        if (repository.findByMacAddressAndName(macAddress, name).isPresent()) {
            throw new RecordConflictException(String
                    .format("Device with name - %s already exists",
                            name), ErrorCode.EXISTS_EXCEPTION);
        }
    }
}
