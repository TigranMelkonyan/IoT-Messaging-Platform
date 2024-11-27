package com.tigran.api.repository.device.custom;

import com.tigran.api.domain.entity.common.base.ModelStatus;
import com.tigran.api.domain.entity.device.Device;
import com.tigran.api.domain.model.common.page.PageModel;
import com.tigran.api.domain.model.common.search.device.DeviceSearchProperties;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Tigran Melkonyan
 * Date: 11/26/24
 * Time: 4:12 PM
 */
public interface DeviceRepositoryCustom {

    @Transactional(readOnly = true)
    PageModel<Device> searchDevices(final DeviceSearchProperties request, final ModelStatus status,
                                    final int page, final int size);
}
