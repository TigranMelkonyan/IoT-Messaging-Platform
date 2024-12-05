package com.tigran.api.domain.port.inbound.threshold;

import com.tigran.api.domain.model.entity.device.data.DeviceData;
import com.tigran.api.domain.model.entity.notification.template.NotificationTemplate;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 9:37 PM
 */
public interface ThresholdEvaluator {

    boolean evaluateThreshold(final DeviceData deviceData, final NotificationTemplate template);    
}
