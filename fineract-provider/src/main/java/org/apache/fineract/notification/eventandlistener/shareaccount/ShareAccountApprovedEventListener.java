package org.apache.fineract.notification.eventandlistener.shareaccount;

import org.apache.fineract.infrastructure.security.service.BasicAuthTenantDetailsService;
import org.apache.fineract.notification.eventandlistener.Listener;
import org.apache.fineract.notification.eventandlistener.notification.NotificationEvent;
import org.apache.fineract.useradministration.service.PermissionReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class ShareAccountApprovedEventListener extends Listener implements ApplicationListener<ShareAccountApprovedEvent> {

    @Autowired
    public ShareAccountApprovedEventListener(final NotificationEvent notificationEvent,
                                             final PermissionReadPlatformService permissionReadPlatformService,
                                             final BasicAuthTenantDetailsService basicAuthTenantDetailsService) {
        super(notificationEvent, permissionReadPlatformService, basicAuthTenantDetailsService);
    }

    @Override
    public void onApplicationEvent(final ShareAccountApprovedEvent event) {
        buildNotification(
                event.getTenantIdentifier(),
                "ACTIVATE_SHAREACCOUNT",
                "shareAccount",
                event.getObjectId(),
                "New share account approved",
                event.getEventType(),
                event.getCurrentUser().getUsername(),
                event.getOfficeId()
        );
    }
}
