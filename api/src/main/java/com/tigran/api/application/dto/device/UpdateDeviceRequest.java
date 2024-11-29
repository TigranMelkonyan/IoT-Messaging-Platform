package com.tigran.api.application.dto.device;

import com.tigran.api.domain.model.entity.device.Device;
import com.tigran.api.domain.model.common.validate.ValidatableRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tigran Melkonyan
 * Date: 11/25/24
 * Time: 7:19 PM
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateDeviceRequest extends ValidatableRequest {

    @NotBlank(message = "required")
    @Size(max = 16)
    private String macAddress;

    @NotBlank(message = "required")
    @Size(max = 12)
    private String name;

    @Size(max = 12)
    private String note;

    public Device toEntity(final Device device) {
        device.setMacAddress(this.macAddress);
        device.setName(this.name);
        device.setNote(this.note);
        return device;
    }
}
