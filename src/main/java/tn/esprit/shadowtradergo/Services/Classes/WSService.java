package tn.esprit.shadowtradergo.Services.Classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.WSResponseMessage;
import tn.esprit.shadowtradergo.Services.Classes.NotificationService;
import tn.esprit.shadowtradergo.Services.Interfaces.IPublicNotificationService;

@Service
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;
    private final IPublicNotificationService publicNotificationService;

    @Autowired
    public WSService(SimpMessagingTemplate messagingTemplate, NotificationService notificationService, PublicNotificationService publicNotificationService) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
        this.publicNotificationService = publicNotificationService;
    }

    public void notifyFrontend(final String message) {
        WSResponseMessage response = new WSResponseMessage(message);
        publicNotificationService.sendGlobalNotification();

        messagingTemplate.convertAndSend("/topic/messages", response);
    }

    public void notifyUser(final String id, final String message) {
        WSResponseMessage response = new WSResponseMessage(message);

        notificationService.sendPrivateNotification(id);
        messagingTemplate.convertAndSendToUser(id, "/topic/private-messages", response);
    }
}
