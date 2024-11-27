package com.tigran.api.repository.device.data;

import com.tigran.api.domain.entity.device.data.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 11/25/24
 * Time: 7:13 PM
 */
@Repository
public interface DeviceDataRepository extends JpaRepository<DeviceData, UUID> {
}
