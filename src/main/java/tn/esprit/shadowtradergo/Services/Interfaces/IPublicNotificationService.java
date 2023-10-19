package tn.esprit.shadowtradergo.Services.Interfaces;

import tn.esprit.shadowtradergo.DAO.Entities.Notification;
import tn.esprit.shadowtradergo.DAO.Entities.PublicNotification;

import java.util.List;

public interface IPublicNotificationService {
    PublicNotification add(PublicNotification n);
    PublicNotification edit(PublicNotification n);
    List<PublicNotification> getAll();
    PublicNotification getById(Long id);
    void delete(long id);
    List<PublicNotification> getSentPublicNotificationsByUserId(long idUser);
    public void sendGlobalNotification();
}
