package com.tigran.api.domain.port.outbound.device;

import com.tigran.api.domain.model.common.page.PageModel;
import com.tigran.api.domain.model.common.search.DeviceSearchProperties;
import com.tigran.api.domain.model.entity.common.base.ModelStatus;
import com.tigran.api.domain.model.entity.device.Device;

/**
 * Created by Tigran Melkonyan
 * Date: 11/26/24
 * Time: 4:12 PM
 */
public interface DeviceRepositoryCustom {

    PageModel<Device> searchDevices(
            final DeviceSearchProperties request,
            final ModelStatus status,
            final int page, final int size);
}
