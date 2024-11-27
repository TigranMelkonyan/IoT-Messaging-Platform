package com.tigran.api.repository.device;

import com.tigran.api.domain.entity.device.Device;
import com.tigran.api.repository.device.custom.DeviceRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 11/25/24
 * Time: 7:12 PM
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID>, DeviceRepositoryCustom {

    Optional<Device> findByMacAddressAndName(final String tripName, final String name);
}
