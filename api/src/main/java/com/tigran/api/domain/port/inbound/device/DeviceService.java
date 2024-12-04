package com.tigran.api.domain.port.inbound.device;

import com.tigran.api.application.dto.device.CreateDeviceRequest;
import com.tigran.api.application.dto.device.UpdateDeviceRequest;
import com.tigran.api.domain.model.common.page.PageModel;
import com.tigran.api.domain.model.common.search.DeviceOrderByOption;
import com.tigran.api.domain.model.common.search.SearchProperties;
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

    Device getByMacAddress(final String macAddress);

    Device update(final UUID id, final UpdateDeviceRequest request);

    void delete(final UUID id, final boolean deleteFromDb);

    PageModel<Device> search(
            final SearchProperties searchProperties,
            final int page,
            final int size,
            final DeviceOrderByOption orderBy);

}
