package sg.edu.nus.iss.AD_Locum_Doctors.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Notification;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Organization;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.NotificationRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.OrganizationRepository;

import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
}
