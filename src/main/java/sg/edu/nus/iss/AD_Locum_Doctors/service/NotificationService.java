package sg.edu.nus.iss.AD_Locum_Doctors.service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.Notification;

import java.util.List;

public interface NotificationService {
	
	void saveNotification(Notification notification);

	List<Notification> getNotifications(Long userId);

}
