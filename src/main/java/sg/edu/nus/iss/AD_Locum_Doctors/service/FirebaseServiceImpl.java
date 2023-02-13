package sg.edu.nus.iss.AD_Locum_Doctors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import sg.edu.nus.iss.AD_Locum_Doctors.model.FirebaseDeviceToken;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Notification;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.FirebaseRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.UserRepository;


@Service
public class FirebaseServiceImpl implements FirebaseService{
	
    @Autowired
    private FirebaseRepository firebaseRepository;
    
    @Autowired
    private UserRepository userRepo;

	@Autowired
	private NotificationService notificationService;
    
	public static final String FCM_SERVER_KEY = "AAAAkEI8xZ0:APA91bGKpmYGvLlYNhaXH7VCBqoXHFCPMukHdbNyMh1SDddZs_As_6NxsTd1ETbUJ-6_U7zQr0W5EkKvDsvqn5SgxayAUEBCgrGFxtOVjGsZDDnPB4BKB413VaIPCAiSQYzfUjO74UQT";
	public static final String FCM_API_URL = "https://fcm.googleapis.com/fcm/send";


    
    @Override
    public void onLoginSaveToken(String token,String username) {
    	
    	FirebaseDeviceToken existingFirebaseDeviceToken = firebaseRepository.findDeviceTokenByUserName(username);
    	
    	//Not first login for client(have DB record)
    	if(existingFirebaseDeviceToken != null) {
  
    		System.out.println("ExistingUsername : " + username);
    		
    		//client did not logOut
    		if(existingFirebaseDeviceToken.getIsLoggedIntoMobileApp()) {
    			
    			//check if deviceToken has changed
        		if(existingFirebaseDeviceToken.getToken().equals(token)) {
        			System.out.println( "Username : " + username + " did not logout ,reconnected with same Token");
        		}
        		//deviceToken has changed(Could be due to diff. device or app reinstalled on same device)
        		else {
            		existingFirebaseDeviceToken.setToken(token);
            		firebaseRepository.saveAndFlush(existingFirebaseDeviceToken);
            		User existingLoginUser = userRepo.findByUsername(username);
            		existingLoginUser.setFirebaseDeviceToken(existingFirebaseDeviceToken);
            		userRepo.saveAndFlush(existingLoginUser);
            		System.out.println( "Username : " + username + " did not logout ,reconnected with new Token :" + token);
        		}
    		}
    		//client previously logged out,so relogin
    		else {
    			
    			//token value same as previous so same device and app not reinstalled
        		if(existingFirebaseDeviceToken.getToken().equals(token)) {
        			System.out.println( "Username : " + username + "relogin, Token is the same as previous login");
        		}
        		//deviceToken has changed(Could be due to diff. device or app reinstalled on same device)
        		else {
        			existingFirebaseDeviceToken.setToken(token);
            		firebaseRepository.saveAndFlush(existingFirebaseDeviceToken);
            		User existingLoginUser = userRepo.findByUsername(username);
            		existingLoginUser.setFirebaseDeviceToken(existingFirebaseDeviceToken);
            		userRepo.saveAndFlush(existingLoginUser);
            		System.out.println( "Username : " + username + "relogin, NewToken :" + token);
        		}
           		existingFirebaseDeviceToken.setIsLoggedIntoMobileApp(true);
        		firebaseRepository.saveAndFlush(existingFirebaseDeviceToken);
    		}
    	}
    	//first time client login(no DB record)
    	else {
    		FirebaseDeviceToken firebaseToken = new FirebaseDeviceToken(token,true,username);
    		firebaseRepository.saveAndFlush(firebaseToken);
     		System.out.println("Username : " + username + " has signed into mobile client for the first time"  );
    		System.out.println("This token will be persisted into DB : " + token);
    	}
    }
    
    @Override
	public void pushNotificationToFreeLancer(JobPost jobPost) {
		
		String newJobStatusMsg = "test";
		String title = "test";
		String body = "test";
		
		switch (jobPost.getStatus()) {
		   case ACCEPTED:
			   newJobStatusMsg = "Approved";
			   break;
		   case COMPLETED_PENDING_PAYMENT:
			   newJobStatusMsg = "Completed (Pending_payment)";
			   break;
		   case COMPLETED_PAYMENT_PROCESSED:
			   newJobStatusMsg = "Completed (Payment_processed)";
			   break;
		   case CANCELLED:
			   newJobStatusMsg = "Cancelled";
			   break;
		   default:
		      break;
		}
		
		
		FirebaseDeviceToken deviceToken = firebaseRepository.findDeviceTokenByUserName(jobPost.getFreelancer().getUsername());
		
		if(deviceToken != null ) {

//			Notification notification = Notification.builder()
//					.read(false)
//					.title(title)
//					.body(body)
//					.build();
//
//			notificationService.saveNotification(notification);

			title = "Status Update for Job Post Id : " + jobPost.getId();
			body = "The status has been updated to " + newJobStatusMsg;

			//client logged in,server call FCM API with updated token
			//client not logged in,server call FCM API with last updated token
			if(!newJobStatusMsg.equals("")) {
				sendNotification(deviceToken,
						title,
						body,
						newJobStatusMsg,
						jobPost.getId().toString());
				
				System.out.println("notification successfully push to username : " 
						+ deviceToken.getUser().getUsername()
						+ " token no. : " + deviceToken.getToken()
								);
			}

		}
		else {
			System.out.println("UserName : " + jobPost.getFreelancer().getUsername() + " has not logged in from mobile before so there is no token linked to that account, so unable to push notificaitons");
		}
	}
    
    private void sendNotification(FirebaseDeviceToken deviceToken, String title, String body,String status,String jobId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "key=" + FCM_SERVER_KEY);

        //Consume FCM_API with JSON data messages,FCM API will push notifications to device
        String targetActivityToRedirectToString = "sg.nus.iss.team7.locum.JobDetailActivity";
        String payload = "{\"to\":\"" + deviceToken.getToken() 
        		+ "\",\"data\":{\"click_action\":\"" + targetActivityToRedirectToString
        		+ "\",\"title\":\"" + title 
        		+ "\",\"body\":\"" + body 
        		+ "\",\"newstatus\":\"" +  status
        		+ "\",\"username\":\"" +  deviceToken.getUser().getUsername()
        		+ "\",\"jobid\":\"" +  jobId
        		+ "\"},\"priority\":\"high\"}";

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject(FCM_API_URL, entity, String.class);
    }
    
}
