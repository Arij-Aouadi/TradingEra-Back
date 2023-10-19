package tn.esprit.shadowtradergo.Services.Classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.PublicNotification;
import tn.esprit.shadowtradergo.DAO.Entities.WSResponseMessage;
import tn.esprit.shadowtradergo.DAO.Repositories.PublicNotificationRepository;
import tn.esprit.shadowtradergo.Services.Interfaces.IPublicNotificationService;

import java.util.List;
@Service

public class PublicNotificationService implements IPublicNotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    PublicNotificationRepository publicNotificationRepository;

    @Autowired
    public PublicNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @Override
    public PublicNotification add(PublicNotification n) {
        return publicNotificationRepository.save(n);
    }

    @Override
    public PublicNotification edit(PublicNotification n) {
        return publicNotificationRepository.save(n);
    }

    @Override
    public List<PublicNotification> getAll() {
        return publicNotificationRepository.findAll();
    }

    @Override
    public PublicNotification getById(Long id) {
        return publicNotificationRepository.findById(id).get();
    }

    @Override
    public void delete(long id) {
        publicNotificationRepository.deleteById(id);

    }

    @Override
    public List<PublicNotification> getSentPublicNotificationsByUserId(long idUser) {
        return publicNotificationRepository.getPublicNotificationForUser(idUser);
    }

    @Override
    public void sendGlobalNotification() {
        WSResponseMessage message = new WSResponseMessage("Global Notification");

        messagingTemplate.convertAndSend("/topic/global-notifications", message);
    }
}
