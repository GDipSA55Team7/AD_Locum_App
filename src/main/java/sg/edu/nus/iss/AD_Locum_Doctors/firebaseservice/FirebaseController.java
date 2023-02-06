package sg.edu.nus.iss.AD_Locum_Doctors.firebaseservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirebaseController {
	 	
	@Autowired
	    private FirebaseService firebaseService;

	    @PostMapping("/api/sendtoken")
	    public ResponseEntity<String> sendToken(@RequestParam String token) {
	        firebaseService.saveToken(token);
	        return new ResponseEntity<>("Token saved successfully", HttpStatus.OK);
	    }

}
