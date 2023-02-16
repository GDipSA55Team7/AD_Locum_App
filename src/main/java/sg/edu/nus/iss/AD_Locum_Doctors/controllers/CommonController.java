package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.AD_Locum_Doctors.model.EmailDetails;
import sg.edu.nus.iss.AD_Locum_Doctors.model.ResetPasswordChecker;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.model.changePasswordForm;
import sg.edu.nus.iss.AD_Locum_Doctors.service.EmailService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.PasswordResetCheckerService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.UserService;
import sg.edu.nus.iss.AD_Locum_Doctors.validator.AdminTriggeredPasswordResetValidator;
import sg.edu.nus.iss.AD_Locum_Doctors.validator.UserValidator;

@Controller
public class CommonController {

	@Autowired
	private AdminTriggeredPasswordResetValidator adminTriggeredPasswordResetValidator;
	@Autowired
	private UserValidator userValidator;

	@InitBinder("passwordForm")
	private void initAdminTriggeredPasswordResetValidator(WebDataBinder binder) {
		binder.addValidators(this.adminTriggeredPasswordResetValidator);
	}

	@InitBinder("user")
	private void initUserStaffFormBinder(WebDataBinder binder) {
		binder.addValidators(userValidator);
	}

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Autowired
	Environment env;

	@Autowired
	private PasswordResetCheckerService passwordResetCheckerService;

	@GetMapping(value = { "/", "/dashboard" })
	public String index(HttpSession session) {
		User u = (User) session.getAttribute("user");
		if (u.getRole().getName().equals("System_Admin")) {
			return "redirect:/dashboard/system-admin";
		} else if (!u.getRole().getName().equals("Locum_Doctor")) {
			return "redirect:/dashboard/clinic";
		}
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/home/authenticate")
	public String authenticate(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		if (result.hasErrors()) {
			return "login";
		}
		User u = userService.authenticate(user.getUsername(), user.getPassword());
		session.setAttribute("user", u);
		return "redirect:/dashboard";
	}

	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	@GetMapping("/password_reset/{userID}/email_trigger")
	public String sendPasswordResetEmail(@PathVariable("userID") String userID) {
		EmailDetails testEmail = new EmailDetails();
		User user = userService.findById(Long.parseUnsignedLong(userID));
		testEmail.setRecipient(user.getEmail());
		testEmail.setSubject("Your request via your administrator to reset your password");
		String resetPasswordURL = env.getProperty("mail.url")
				+ "password_reset/admin_triggered?pwresetkey={0}&userid={1}";
		ResetPasswordChecker resetPWChecker = new ResetPasswordChecker();
		resetPWChecker.setUserID(user.getId());
		passwordResetCheckerService.createResetPasswordChecker(resetPWChecker);
		resetPasswordURL = java.text.MessageFormat.format(resetPasswordURL, resetPWChecker.getId(), user.getId());
		String emailMessage = "Dear {0}, \n\nIn response to your request to reset your SG Locum Finder account password, please click on the link below to proceed with the change of your password. \n\n{1} \n\nIf this request did not come from you, please inform us immediately.\n\nYours Sincerely,\nSG Locum Administrator";
		String formattedEmail = java.text.MessageFormat.format(emailMessage, user.getName(), resetPasswordURL);
		testEmail.setMsgBody(formattedEmail);
		String status = emailService.sendSimpleMail(testEmail);
		System.out.println(status);
		return "redirect:/UserList";
	}

	@GetMapping("/password_reset/admin_triggered")
	public String passwordReset(@RequestParam String pwresetkey, @RequestParam String userid, Model model) {
		System.out.println(pwresetkey);
		System.out.println(userid);
		Boolean emailCheckerResult = passwordResetCheckerService.checkResetPasswordEmailURLValidity(pwresetkey, userid);
		if (emailCheckerResult == true) {
			User user = userService.findById(Long.parseUnsignedLong(userid));
			System.out.println(pwresetkey);
			System.out.println(userid);
			changePasswordForm newPasswordForm = new changePasswordForm();
			model.addAttribute("passwordForm", newPasswordForm);
			model.addAttribute("username", user.getUsername());
			model.addAttribute("userid", user.getId());
			return "changePassword";
		} else {
			return "invalidPasswordResetEmail";
		}
	}

	@PostMapping("/password_reset/admin_triggered")
	public String submitPasswordReset(@Valid @ModelAttribute("passwordForm") changePasswordForm passwordForm,
			BindingResult bindingResult, Model model) {
		User user = userService.findById(passwordForm.getUserID());
		if (bindingResult.hasErrors()) {
			model.addAttribute("username", user.getUsername());
			model.addAttribute("userid", user.getId());
			model.addAttribute("passwordForm", passwordForm);
			return "changePassword";
		} else {
			user.setPassword(passwordForm.getNewPassword());
			userService.saveUser(user);
			return "redirect:/login";
		}
	}
}
