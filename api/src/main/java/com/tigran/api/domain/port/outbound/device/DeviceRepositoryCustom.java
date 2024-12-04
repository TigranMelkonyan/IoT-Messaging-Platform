package com.tigran.api.domain.port.outbound.device;

import com.tigran.api.domain.model.common.page.PageModel;
import com.tigran.api.domain.model.common.search.DeviceOrderByOption;
import com.tigran.api.domain.model.common.search.SearchProperties;
import com.tigran.api.domain.model.entity.device.Device;

/**
 * Created by Tigran Melkonyan
 * Date: 11/26/24
 * Time: 4:12 PM
 */
public interface DeviceRepositoryCustom {

    PageModel<Device> searchDevices(
            final SearchProperties searchProperties,
            final int page,
            final int size,
            final DeviceOrderByOption orderBy);
}
