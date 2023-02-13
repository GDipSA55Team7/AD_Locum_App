package sg.edu.nus.iss.AD_Locum_Doctors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Notification;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("select n from Notification n where n.userId = ?1")
    List<Notification> findByUserId(Long userId);

}