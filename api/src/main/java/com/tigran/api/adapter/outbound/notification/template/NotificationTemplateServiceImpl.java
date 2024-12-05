package com.tigran.api.adapter.outbound.notification.template;

import com.tigran.api.application.dto.notificatio.template.CreateNotificationTemplateRequest;
import com.tigran.api.application.dto.notificatio.template.UpdateNotificationTemplateRequest;
import com.tigran.api.domain.exception.RecordConflictException;
import com.tigran.api.domain.exception.errorcode.ErrorCode;
import com.tigran.api.domain.model.entity.common.base.ModelStatus;
import com.tigran.api.domain.model.entity.notification.template.NotificationTemplate;
import com.tigran.api.domain.port.inbound.notification.template.NotificationTemplateService;
import com.tigran.api.domain.port.outbound.notification.template.NotificationTemplateRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 7:33 PM
 */
@Service
@Log4j2
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

    private final NotificationTemplateRepository repository;

    public NotificationTemplateServiceImpl(final NotificationTemplateRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public NotificationTemplate create(final CreateNotificationTemplateRequest request) {
        log.info("Creating Notification Template by request - {} ", request);
        Assert.notNull(request, "Request must not be null");
        assertDeviceNotAttached(request.getDeviceId());
        NotificationTemplate entity = request.toEntity();
        NotificationTemplate result = repository.save(entity);
        log.info("Successfully created Notification Template by request - {}, result - {}", request, result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationTemplate getById(final UUID id) {
        log.info("Retrieving Notification Template with id - {} ", id);
        Assert.notNull(id, "Id cannot be null");
        NotificationTemplate template = repository.findById(id)
                .orElseThrow(() -> new RecordConflictException(String
                        .format("Notification Template with id - %s not exists", id), ErrorCode.NOT_EXISTS_EXCEPTION));
        log.info("Successfully retrieving Notification Template with id - {}, result - {} ", id, template);
        return template;
    }

    @Override
    public NotificationTemplate getByDeviceId(final UUID deviceId) {
        log.info("Retrieving Notification Template with device id - {} ", deviceId);
        Assert.notNull(deviceId, "Id cannot be null");
        NotificationTemplate template = repository.findByDeviceId(deviceId)
                .orElseThrow(() -> new RecordConflictException(String
                        .format("Notification Template with device id - %s not exists", deviceId), ErrorCode.NOT_EXISTS_EXCEPTION));
        log.info("Successfully retrieving Notification Template with device id - {}, result - {} ", deviceId, template);
        return template;
    }

    @Override
    @Transactional
    public NotificationTemplate update(final UUID id, final UpdateNotificationTemplateRequest request) {
        log.info("Updating Notification Template with id - {} ", id);
        Assert.notNull(id, "Id cannot be null");
        Assert.notNull(request, "Request must not be null");
        NotificationTemplate result;
        NotificationTemplate entity = getEntity(id);
        entity = request.toEntity(entity);
        if (request.getDeviceId() != entity.getDeviceId()) {
            assertDeviceNotAttached(request.getDeviceId());
        }
        result = repository.save(entity);
        log.info("Successfully Notification Template with id - {}, result - {} ", id, result);
        return result;
    }

    @Override
    @Transactional
    public void delete(final UUID id, final boolean deleteFromDb) {
        log.info("Deleting Notification Template with id - {} ", id);
        Assert.notNull(id, "Id cannot be null");
        NotificationTemplate entity = getEntity(id);
        if (deleteFromDb) {
            repository.delete(entity);
            log.info("Successfully deleted Notification Template with id - {} from db", id);
        } else {
            entity.setStatus(ModelStatus.DELETED);
            repository.save(entity);
            log.info("Successfully soft deleted Notification Template with id - {} ", id);
        }
    }

    @Override
    @Transactional
    public void addReceiver(final UUID templateId, final UUID receiverId) {
        log.info("Adding receiver with id - {} to Notification Template with id - {} ", receiverId, templateId);
        NotificationTemplate template = getEntity(templateId);
        template.setReceiversIds(Collections.singleton(receiverId));
        repository.save(template);
        log.info("Successfully added receiver with id - {} to Notification Template with id - {} ", receiverId, templateId);
    }

    @Override
    @Transactional
    public void removeReceiver(final UUID templateId, final UUID receiverId) {
        log.info("Removing receiver with id - {} from Notification Template with id - {} ", templateId, receiverId);
        NotificationTemplate template = getEntity(templateId);
        boolean remove = template.getReceiversIds().remove(receiverId);
        if (remove) {
            repository.save(template);
        }
        log.info("Successfully removed receiver with id - {} from Notification Template with id - {} ", templateId, receiverId);
    }

    private NotificationTemplate getEntity(final UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecordConflictException(String
                        .format("Notification Template with id - %s not exists", id), ErrorCode.NOT_EXISTS_EXCEPTION));
    }

    private void assertDeviceNotAttached(final UUID deviceId) {
        if (repository.existsByDeviceId(deviceId)) {
            throw new RecordConflictException(String.format
                    ("Device with id - %s already attached to another Notification Template", deviceId), ErrorCode.EXISTS_EXCEPTION);
        }
    }

}
