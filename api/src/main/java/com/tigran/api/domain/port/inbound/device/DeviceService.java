package com.tigran.api.domain.port.inbound.device;

import com.tigran.api.application.usecase.dto.device.CreateDeviceRequest;
import com.tigran.api.application.usecase.dto.device.UpdateDeviceRequest;
import com.tigran.api.domain.model.common.page.PageModel;
import com.tigran.api.domain.model.common.search.DeviceSearchProperties;
import com.tigran.api.domain.model.entity.common.base.ModelStatus;
import com.tigran.api.domain.model.entity.device.Device;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 11/25/24
 * Time: 7:15 PM
 */
public interface DeviceService {

    Device create(final CreateDeviceRequest request);

    Device getById(final UUID id);

    Device update(final UUID id, final UpdateDeviceRequest request);

    void delete(final UUID id, final boolean deleteFromDb);

    PageModel<Device> search(
            final DeviceSearchProperties request,
            final ModelStatus status,
            final int page,
            final int size);

}
