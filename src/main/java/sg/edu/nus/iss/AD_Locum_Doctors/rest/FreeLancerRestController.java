package sg.edu.nus.iss.AD_Locum_Doctors.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.AD_Locum_Doctors.model.EmailDetails;
import sg.edu.nus.iss.AD_Locum_Doctors.model.FreeLancerDTO;
import sg.edu.nus.iss.AD_Locum_Doctors.model.ResetPasswordChecker;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.service.EmailService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.FirebaseServiceImpl;
import sg.edu.nus.iss.AD_Locum_Doctors.service.PasswordResetCheckerService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.UserService;

@RestController
@RequestMapping("api/freelancer")
public class FreeLancerRestController {

	@Autowired
	UserService userService;
	
	@Autowired
    FirebaseServiceImpl firebaseService;
	
	@Autowired
	private PasswordResetCheckerService passwordResetCheckerService;
	
	@Autowired
	private EmailService emailService;

	@PostMapping("/login")
	public ResponseEntity<FreeLancerDTO> loginNewFreeLancer(@RequestBody FreeLancerDTO freeLancerDTO) {
		try {
			// updates all dto fields with registered user data
			FreeLancerDTO existingFreeLancer = userService.loginFreeLancerAndUpdateToken(freeLancerDTO);
			if (existingFreeLancer == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
			}
			return new ResponseEntity<>(existingFreeLancer, HttpStatus.OK); // 200
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<FreeLancerDTO> registerNewFreeLancer(@RequestBody FreeLancerDTO freeLancerDTO) {
		try {
			FreeLancerDTO newFreeLancerDTO = userService.createFreeLancer(freeLancerDTO);
			if (newFreeLancerDTO.getErrorsFieldString().isEmpty()) {
				return new ResponseEntity<>(newFreeLancerDTO, HttpStatus.CREATED);
			}
			// return errorString to client indicate input fields(username,Email,medicalLicenseNo) are non-unique
			else {
				return new ResponseEntity<>(newFreeLancerDTO, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/update")
	public ResponseEntity<FreeLancerDTO> updateNewFreeLancer(@RequestBody FreeLancerDTO freeLancerDTO) {
		try {
			FreeLancerDTO updatedFL = userService.updateFreeLancer(freeLancerDTO);
			if (updatedFL != null && updatedFL.getErrorsFieldString().isEmpty()) {
				return new ResponseEntity<>(updatedFL, HttpStatus.OK);
			}
			return new ResponseEntity<>(updatedFL, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	 @GetMapping("/logout")
	    public ResponseEntity<String> removeToken(@RequestParam String username) {
	    	try {
	    		userService.logoutFreeLancer(username);
				return new ResponseEntity<>("Logout success",HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>("Logout failed due to server-side error", HttpStatus.INTERNAL_SERVER_ERROR);
			}
	    }
	 
//	 	@GetMapping("/password_reset/{userID}/email_trigger")
//		public ResponseEntity<String> sendPasswordResetEmailFreeLancer(@PathVariable("userID") String freeLancerUserID) {
//			try {
//				EmailDetails testEmail = new EmailDetails();
//				User user = userService.findById(Long.parseUnsignedLong(freeLancerUserID));
//				testEmail.setRecipient(user.getEmail());
//				testEmail.setSubject("Your request via your administrator to reset your password");
//				String resetPasswordURL = "http://localhost:8080/api/freelancer/password_reset/admin_triggered?pwresetkey={0}&userid={1}";
//				ResetPasswordChecker resetPWChecker = new ResetPasswordChecker();
//				resetPWChecker.setUserID(user.getId());
//				passwordResetCheckerService.createResetPasswordChecker(resetPWChecker);
//				resetPasswordURL = java.text.MessageFormat.format(resetPasswordURL, resetPWChecker.getId(), user.getId());
//				String emailMessage = "Dear {0}, \n\nIn response to your request to reset your SG Locum Finder account password, please click on the link below to proceed with the change of your password. \n\n{1} \n\nIf this request did not come from you, please inform us immediately.\n\nYours Sincerely,\nSG Locum Administrator";
//				String formattedEmail = java.text.MessageFormat.format(emailMessage, user.getName(), resetPasswordURL);
//				testEmail.setMsgBody(formattedEmail);
//				String status = emailService.sendSimpleMail(testEmail);
//				System.out.println(status);
//				return new ResponseEntity<>("Email reset sent",HttpStatus.OK);
//			} catch (Exception e) {
//				e.printStackTrace();
//				return new ResponseEntity<>("Email reset sending failed due to server-side error", HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//		}
	 
//	 	@GetMapping("/password_reset/admin_triggered")
//		public String passwordReset(@RequestParam String pwresetkey, @RequestParam String userid, Model model) {
//			System.out.println(pwresetkey);
//			System.out.println(userid);
//			Boolean emailCheckerResult = passwordResetCheckerService.checkResetPasswordEmailURLValidity(pwresetkey, userid);
//			if (emailCheckerResult == true) {
//				User user = userService.findById(Long.parseUnsignedLong(userid));
//				System.out.println(pwresetkey);
//				System.out.println(userid);
//				changePasswordForm newPasswordForm = new changePasswordForm();
//				model.addAttribute("passwordForm", newPasswordForm);
//				model.addAttribute("username", user.getUsername());
//				model.addAttribute("userid", user.getId());
//				return "changePassword";
//			} else {
//				return "invalidPasswordResetEmail";
//			}
//		}
	 
	 
}
