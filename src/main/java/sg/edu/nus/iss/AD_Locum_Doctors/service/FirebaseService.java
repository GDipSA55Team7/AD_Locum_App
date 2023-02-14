package sg.edu.nus.iss.AD_Locum_Doctors.service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.FirebaseDeviceToken;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;

public interface FirebaseService {
	
	void onLoginSaveToken(String token,String username);
	void pushNotificationToFreeLancer(JobPost jobPost);

}
