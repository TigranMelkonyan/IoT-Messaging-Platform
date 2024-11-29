package com.tigran.api.adapter.inbound.controller.device;

import com.tigran.api.application.usecase.dto.device.CreateDeviceRequest;
import com.tigran.api.application.usecase.dto.device.DeviceResponse;
import com.tigran.api.application.usecase.dto.device.PageResponse;
import com.tigran.api.application.usecase.dto.device.UpdateDeviceRequest;
import com.tigran.api.domain.model.common.page.PageModel;
import com.tigran.api.domain.model.common.search.DeviceSearchProperties;
import com.tigran.api.domain.model.entity.common.base.ModelStatus;
import com.tigran.api.domain.model.entity.device.Device;
import com.tigran.api.domain.port.inbound.device.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Tigran Melkonyan
 * Date: 11/26/24
 * Time: 4:47 PM
 */
@RestController
@RequestMapping("api/admin/devices")
@Tag(name = "Admin device CRUD API")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
public class DeviceAdminController extends AbstractController {

    private final DeviceService deviceService;

    public DeviceAdminController(final DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    @Operation(summary = "Create Device")
    public ResponseEntity<DeviceResponse> create(@Valid @RequestBody final CreateDeviceRequest request) {
        Device device = deviceService.create(request);
        return respondOK(DeviceResponse.from(device));
    }

    @GetMapping("{id}")
    @Operation(summary = "Get Device")
    public ResponseEntity<DeviceResponse> get(@PathVariable final UUID id) {
        Device device = deviceService.getById(id);
        return respondOK(DeviceResponse.from(device));
    }

    @PutMapping("{id}")
    @Operation(summary = "Update Device")
    public ResponseEntity<DeviceResponse> update(
            @PathVariable("id") final UUID id,
            @Valid @RequestBody final UpdateDeviceRequest request) {
        Device device = deviceService.update(id, request);
        return respondOK(DeviceResponse.from(device));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete Device")
    public ResponseEntity<?> delete(
            @PathVariable("id") final UUID id,
            @RequestParam final boolean deleteFromDb) {
        deviceService.delete(id, deleteFromDb);
        return respondEmpty();
    }

    @GetMapping("search/{page}/{size}")
    @Operation(summary = "Search Devices with pages")
    public ResponseEntity<PageResponse<DeviceResponse>> getPagesForDevices(
            @Valid final DeviceSearchProperties searchProperties,
            @PathVariable final int page,
            @PathVariable final int size,
            @RequestParam final ModelStatus status) {
        PageModel<Device> result = deviceService.search(searchProperties, status, page, size);
        PageResponse<DeviceResponse> response = new PageResponse<>(result
                .getItems()
                .stream().map(DeviceResponse::from)
                .collect(Collectors.toList()),
                result.getTotalCount());
        return respondOK(response);
    }
}
