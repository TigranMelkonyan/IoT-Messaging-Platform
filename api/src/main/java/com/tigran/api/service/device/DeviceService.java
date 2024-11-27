package com.tigran.api.service.device;

import com.tigran.api.domain.entity.common.base.ModelStatus;
import com.tigran.api.domain.entity.device.Device;
import com.tigran.api.domain.model.common.page.PageModel;
import com.tigran.api.domain.model.common.search.device.DeviceSearchProperties;
import com.tigran.api.domain.model.rest.request.device.CreateDeviceRequest;
import com.tigran.api.domain.model.rest.request.device.UpdateDeviceRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 11/25/24
 * Time: 7:15 PM
 */
public interface DeviceService {

    @Transactional
    Device create(final CreateDeviceRequest request);

    @Transactional(readOnly = true)
    Device getById(final UUID id);

    @Transactional
    Device update(final UUID id, final UpdateDeviceRequest request);

    @Transactional
    void delete(final UUID id, final boolean deleteFromDb);

    @Transactional(readOnly = true)
    PageModel<Device> search(
            final DeviceSearchProperties request,
            final ModelStatus status,
            final int page,
            final int size);

}
