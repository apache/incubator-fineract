package org.apache.fineract.notification.eventandlistener.group;

import org.apache.fineract.infrastructure.security.service.BasicAuthTenantDetailsService;
import org.apache.fineract.notification.eventandlistener.Listener;
import org.apache.fineract.notification.eventandlistener.notification.NotificationEvent;
import org.apache.fineract.useradministration.service.PermissionReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class NewGroupEventListener extends Listener implements ApplicationListener<NewGroupEvent> {

    @Autowired
    public NewGroupEventListener(final NotificationEvent notificationEvent,
                                 final PermissionReadPlatformService permissionReadPlatformService,
                                 final BasicAuthTenantDetailsService basicAuthTenantDetailsService) {
        super(notificationEvent, permissionReadPlatformService, basicAuthTenantDetailsService);
    }

    @Override
    public void onApplicationEvent(NewGroupEvent event) {
        buildNotification(
                event.getTenantIdentifier(),
                "ACTIVATE_GROUP",
                "group",
                event.getObjectId(),
                "New group created",
                event.getEventType(),
                event.getCurrentUser().getUsername(),
                event.getOfficeId()
        );
    }
}
