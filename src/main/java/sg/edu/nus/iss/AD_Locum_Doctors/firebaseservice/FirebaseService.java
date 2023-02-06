package sg.edu.nus.iss.AD_Locum_Doctors.firebaseservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class FirebaseService {
	
    @Autowired
    private FirebaseRepository firebaseRepository;
    
	public static final String FCM_SERVER_KEY = "AAAAkEI8xZ0:APA91bGKpmYGvLlYNhaXH7VCBqoXHFCPMukHdbNyMh1SDddZs_As_6NxsTd1ETbUJ-6_U7zQr0W5EkKvDsvqn5SgxayAUEBCgrGFxtOVjGsZDDnPB4BKB413VaIPCAiSQYzfUjO74UQT";
	public static final String FCM_API_URL = "https://fcm.googleapis.com/fcm/send";
	//public static final String DEVICE_REGISTRATION_TOKEN = "cQfc-cykR1iflB9PKKPr7S:APA91bG_0DnqUba0BCLEe-SuRpDxKKoM2Kh2DQtxEbK8NKy9X5D-kMuaDca2ioKWAnwCCAXEOxDGVbRII9cSZ_Qa1i24nBP-eeqkUNUXP1dQUnkC3SHtJuIvN6UvgiXfLDRpNiVnH89O";
	

    public static void sendNotification(String registrationToken, String title, String body,String status,String jobId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "key=" + FCM_SERVER_KEY);

        //notification messages
        //String payload = "{\"to\":\"" + registrationToken + "\",\"notification\":{\"title\":\"" + title + "\",\"body\":\"" + body + "\"}}";
        //String payload = "{\"to\":\"" + registrationToken + "\",\"notification\":{\"title\":\"" + title + "\",\"body\":\"" + body + "\",\"click_action\":\"MainActivity\"}}";

        //data messages
        String payload = "{\"to\":\"" + registrationToken 
        		+ "\",\"data\":{\"click_action\":\"" + "sg.nus.iss.team7.locum.JobDetailActivity" 
        		+ "\",\"title\":\"" + title 
        		+ "\",\"body\":\"" + body 
        		+ "\",\"newstatus\":\"" +  status
        		+ "\",\"jobid\":\"" +  jobId
        		+ "\"},\"priority\":\"high\"}";


        HttpEntity<String> entity = new HttpEntity<>(payload, headers);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject(FCM_API_URL, entity, String.class);
    }

    public void saveToken(String token) {
    	
    	FirebaseDeviceToken existingFirebaseDeviceToken = firebaseRepository.findById(1).orElse(null);
    	
    	if(existingFirebaseDeviceToken != null) {
    		existingFirebaseDeviceToken.setToken(token);
    		firebaseRepository.saveAndFlush(existingFirebaseDeviceToken);
    		System.out.println("existingToken : " + token);
    	}
    	else {
    		FirebaseDeviceToken firebaseToken = new FirebaseDeviceToken(token);
    		firebaseRepository.saveAndFlush(firebaseToken);
    		System.out.println("newToken : " + token);
    	}
    	
        
    }
}
