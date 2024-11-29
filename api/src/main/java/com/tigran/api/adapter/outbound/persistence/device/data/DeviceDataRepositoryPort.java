package com.tigran.api.adapter.outbound.persistence.device.data;

import com.tigran.api.domain.model.entity.device.data.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 11/25/24
 * Time: 7:13 PM
 */
@Repository
public interface DeviceDataRepositoryPort extends JpaRepository<DeviceData, UUID> {
}
