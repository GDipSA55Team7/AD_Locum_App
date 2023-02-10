package sg.edu.nus.iss.AD_Locum_Doctors.firebaseservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirebaseController {
	 	
	@Autowired
	    private FirebaseService firebaseService;

//	    @GetMapping("/api/onloginupdatetoken")
//	    public ResponseEntity<String> sendToken(@RequestParam String token,@RequestParam String username) {
//	    	try {
//		    	firebaseService.onLoginSaveToken(token,username);
//		        return new ResponseEntity<>("Token saved successfully", HttpStatus.OK);
//			} catch (Exception e) {
//				e.printStackTrace();
//				return new ResponseEntity<>("Token failed to be updated due to server-side error", HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//	    }

	    @GetMapping("/api/onlogout")
	    public ResponseEntity<String> removeToken(@RequestParam String username) {
	    	
	    	try {
	    		firebaseService.onLogOut(username);
				return new ResponseEntity<>("Token deleted successfully",HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>("Token failed to be deleted due to server-side error", HttpStatus.INTERNAL_SERVER_ERROR);
			}
	    }
}
