package com.tigran.api.application.usecase.threshold;

import com.tigran.api.domain.model.entity.device.data.DeviceData;
import com.tigran.api.domain.model.entity.notification.template.NotificationTemplate;
import com.tigran.api.domain.port.inbound.threshold.ThresholdEvaluator;
import com.tigran.api.infrastructure.util.devicedata.DeviceDataUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 9:40 PM
 */
@Service
@Log4j2
public class ThresholdEvaluatorImpl implements ThresholdEvaluator {

    @Override
    public boolean evaluateThreshold(final DeviceData data, final NotificationTemplate template) {
        if (DeviceDataUtils.isOutOfRange(data.getTemperature(), template.getTempMinThreshold(), template.getTempMaxThreshold())) {
            return true;
        }
        if (DeviceDataUtils.isOutOfRange(data.getHumidity(), template.getHumMinThreshold(), template.getHumMaxThreshold())) {
            return true;
        }
        return data.getBatteryLevel() < template.getBatteryThreshold();
    }
}
