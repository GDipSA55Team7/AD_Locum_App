package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.AD_Locum_Doctors.model.EmailDetails;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.model.ResetPasswordChecker;
import sg.edu.nus.iss.AD_Locum_Doctors.service.EmailService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.UserService;

@Controller
public class CommonController {
	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@GetMapping(value = { "/", "/login" })
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/home/authenticate")
	public String authenticate(User user, Model model, HttpSession session) {
		User u = userService.authenticate(user.getUsername(), user.getPassword());
		session.setAttribute("user", u);
		switch (u.getRole().getName()) {
			case "System_Admin":
				return "redirect:/system-admin";
			case "Clinic_Admin":
				return "redirect:/clinic-admin";
			case "Clinic_Main_Admin":
				return "redirect:/clinic-admin";
			case "Clinic_User":
				return "redirect:/clinic-user";
			default:
				return "login";
		}
	}

	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	@GetMapping("/system-admin")
	public String systemAdminPage() {
		return "home-system-admin";
	}

	@GetMapping("/clinic-admin")
	public String clinicAdminPage() {
		return "home-clinic-admin";
	}

	@GetMapping("/clinic-user")
	public String clinicUserPage() {
		return "home-clinic-user";
	}

	@GetMapping("/password_reset/{userID}/email_trigger")
	public String sendPasswordResetEmail(@PathVariable("userID") String userID){
		EmailDetails testEmail = new EmailDetails();
		User user = userService.findById(Long.parseUnsignedLong(userID));
		System.out.println(user.getEmail());
		testEmail.setRecipient(user.getEmail());
		testEmail.setSubject("Your request via your administrator to reset your password");
		String resetPasswordURL = "http://localhost:8080/password_reset/{0}?key={1}";
		ResetPasswordChecker resetPWChecker = new ResetPasswordChecker();
		java.text.MessageFormat.format(resetPasswordURL, user.getId(), resetPWChecker.getId());
		String emailMessage = "Dear {0}, \nIn response to your request to reset your SG Locum Finder account password, please click on the link below to proceed with the change of your password. \n \n{1} \n \nIf this request did not come from you, please inform us immediately."; 
		String formattedEmail = java.text.MessageFormat.format(emailMessage, user.getName(), testURL);
		testEmail.setMsgBody(formattedEmail);
		String status = emailService.sendSimpleMail(testEmail);
		System.out.println(status);
		return "redirect:/UserList";
	}
}
