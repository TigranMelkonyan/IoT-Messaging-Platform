package com.tigran.api.domain.model.rest.request.device;

import com.tigran.api.domain.entity.device.Device;
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
public class CreateDeviceRequest extends ValidatableRequest {

    @NotBlank(message = "required")
    @Size(max = 16)
    private String macAddress;

    @NotBlank(message = "required")
    @Size(max = 12)
    private String name;

    @Size(max = 12)
    private String note;

    public Device toEntity() {
        Device entity = new Device();
        entity.setMacAddress(this.macAddress);
        entity.setName(this.name);
        entity.setNote(this.note);
        return entity;
    }
}
