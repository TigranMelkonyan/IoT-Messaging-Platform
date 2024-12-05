package com.tigran.api.domain.port.inbound.notification.template;

import com.tigran.api.application.dto.notificatio.template.CreateNotificationTemplateRequest;
import com.tigran.api.application.dto.notificatio.template.UpdateNotificationTemplateRequest;
import com.tigran.api.domain.model.entity.notification.template.NotificationTemplate;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 6:35 PM
 */
public interface NotificationTemplateService {

    NotificationTemplate create(final CreateNotificationTemplateRequest request);

    NotificationTemplate getById(final UUID id);
    
    NotificationTemplate getByDeviceId(final UUID id);

    NotificationTemplate update(final UUID id, final UpdateNotificationTemplateRequest request);

    void delete(final UUID id, final boolean deleteFromDb);

    void addReceiver(final UUID templateId, final UUID receiverId);
    
    void removeReceiver(final UUID templateId, final UUID receiverId);

}
