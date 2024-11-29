package com.tigran.api.adapter.outbound.persistence.device;

import com.tigran.api.domain.model.entity.device.Device;
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
public interface DeviceRepository extends JpaRepository<Device, UUID> {

    Optional<Device> findByMacAddressAndName(final String tripName, final String name);
}
