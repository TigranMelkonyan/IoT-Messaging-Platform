package com.tigran.api.adapter.inbound.controller.device.data;

import com.tigran.api.adapter.inbound.controller.AbstractController;
import com.tigran.api.application.dto.device.data.DeviceDataResponse;
import com.tigran.api.domain.model.entity.common.base.ModelStatus;
import com.tigran.api.domain.model.entity.device.data.DeviceData;
import com.tigran.api.domain.port.inbound.device.data.DeviceDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Tigran Melkonyan
 * Date: 11/26/24
 * Time: 4:47 PM
 */
@RestController
@RequestMapping("api/device-data")
@Tag(name = "Device Data API")
public class DeviceDataController extends AbstractController {

    private final DeviceDataService deviceDataService;

    public DeviceDataController(final DeviceDataService deviceDataService) {
        this.deviceDataService = deviceDataService;
    }

    @DeleteMapping("{deviceId}")
    @Operation(summary = "Delete All Device Data by Device")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<?> delete(
            @PathVariable("deviceId") final UUID id,
            @RequestParam final boolean deleteFromDb) {
        deviceDataService.deleteAllByDevice(id, deleteFromDb);
        return respondEmpty();
    }

    @GetMapping("{deviceId}/historical")
    @Operation(summary = "Search Device Data with time range")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<List<DeviceDataResponse>> getDeviceDataWithRange(
            @PathVariable final UUID deviceId,
            @RequestParam final Instant startTime,
            @RequestParam final Instant endTime,
            @RequestParam final ModelStatus status) {
        List<DeviceData> result = deviceDataService.searchWithRangeByDevice(deviceId, startTime, endTime, status);
        List<DeviceDataResponse> response = result
                .stream()
                .map(DeviceDataResponse::from)
                .collect(Collectors.toList());
        return respondOK(response);
    }
}
