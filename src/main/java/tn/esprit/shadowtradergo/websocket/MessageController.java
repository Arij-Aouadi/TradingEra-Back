package tn.esprit.shadowtradergo.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import tn.esprit.shadowtradergo.DAO.Entities.WSMessage;
import tn.esprit.shadowtradergo.DAO.Entities.WSResponseMessage;
import tn.esprit.shadowtradergo.Services.Classes.NotificationService;
import tn.esprit.shadowtradergo.Services.Classes.PublicNotificationService;

import java.security.Principal;

@Controller
public class MessageController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PublicNotificationService publicNotificationService;

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public WSResponseMessage getMessage(final WSMessage message) throws InterruptedException {
        Thread.sleep(1000);
        publicNotificationService.sendGlobalNotification();
        return new WSResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()));
    }

    @MessageMapping("/private-message")
    @SendToUser("/topic/private-messages")
    public WSResponseMessage getPrivateMessage(final WSMessage message,
                                               final Principal principal) throws InterruptedException {
        Thread.sleep(1000);
        notificationService.sendPrivateNotification(principal.getName());
        return new WSResponseMessage(HtmlUtils.htmlEscape(
                "Sending private message to user " + principal.getName() + ": "
                        + message.getMessageContent())
        );
    }
}
