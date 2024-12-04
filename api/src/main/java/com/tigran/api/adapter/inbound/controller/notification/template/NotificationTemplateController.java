package com.tigran.api.adapter.inbound.controller.notification.template;

import com.tigran.api.adapter.inbound.controller.AbstractController;
import com.tigran.api.adapter.inbound.controller.dto.template.NotificationTemplateResponse;
import com.tigran.api.application.dto.notificatio.template.CreateNotificationTemplateRequest;
import com.tigran.api.application.dto.notificatio.template.UpdateNotificationTemplateRequest;
import com.tigran.api.domain.model.entity.notification.template.NotificationTemplate;
import com.tigran.api.domain.port.inbound.notification.template.NotificationTemplateService;
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

/**
 * Created by Tigran Melkonyan
 * Date: 11/26/24
 * Time: 4:47 PM
 */
@RestController
@RequestMapping("api/notification-templates")
@Tag(name = "Notification Template CRUD API")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
public class NotificationTemplateController extends AbstractController {

    private final NotificationTemplateService notificationTemplateService;

    public NotificationTemplateController(final NotificationTemplateService notificationTemplateService) {
        this.notificationTemplateService = notificationTemplateService;
    }

    @PostMapping
    @Operation(summary = "Create Notification Template")
    public ResponseEntity<NotificationTemplateResponse> create(
            @Valid @RequestBody final CreateNotificationTemplateRequest request) {
        NotificationTemplate template = notificationTemplateService.create(request);
        return respondOK(NotificationTemplateResponse.from(template));
    }

    @GetMapping("{id}")
    @Operation(summary = "Get Notification Template")
    public ResponseEntity<NotificationTemplateResponse> get(@PathVariable final UUID id) {
        NotificationTemplate template = notificationTemplateService.getById(id);
        return respondOK(NotificationTemplateResponse.from(template));
    }

    @PutMapping("{id}")
    @Operation(summary = "Update Notification Template")
    public ResponseEntity<NotificationTemplateResponse> update(
            @PathVariable("id") final UUID id,
            @Valid @RequestBody final UpdateNotificationTemplateRequest request) {
        NotificationTemplate template = notificationTemplateService.update(id, request);
        return respondOK(NotificationTemplateResponse.from(template));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete Notification Template")
    public ResponseEntity<?> delete(
            @PathVariable("id") final UUID id,
            @RequestParam final boolean deleteFromDb) {
        notificationTemplateService.delete(id, deleteFromDb);
        return respondEmpty();
    }

}
