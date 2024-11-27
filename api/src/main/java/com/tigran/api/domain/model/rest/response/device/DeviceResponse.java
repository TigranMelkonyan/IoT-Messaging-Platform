package com.tigran.api.domain.model.rest.response.device;

import com.tigran.api.domain.entity.device.Device;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 11/26/24
 * Time: 4:44 PM
 */
@Data
@AllArgsConstructor
public class DeviceResponse {

    private UUID id;
    private String macAddress;
    private String name;
    private String note;

    public static DeviceResponse from(Device device) {
        return new DeviceResponse(device.getId(), device.getMacAddress(),
                device.getName(), device.getNote());
    }
}
