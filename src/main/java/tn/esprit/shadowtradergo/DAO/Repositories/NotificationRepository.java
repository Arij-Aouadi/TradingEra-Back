package tn.esprit.shadowtradergo.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.shadowtradergo.DAO.Entities.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    @Query("SELECT  n FROM Notification n  WHERE n.user.id=:id")
    List<Notification> getNotificationByUser(@Param("id") long id);
    @Query("SELECT  n FROM Notification n  WHERE n.user.id=:id  and n.status != 'PENDING'")
    List<Notification> getSentNotificationByUser(@Param("id") long id);

}
