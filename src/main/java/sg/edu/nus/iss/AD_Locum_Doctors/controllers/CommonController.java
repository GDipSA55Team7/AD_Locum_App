package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.service.UserService;

@Controller
public class CommonController {
	@Autowired
	private UserService userService;

	@GetMapping(value = { "/", "/login" })
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/home/authenticate")
	public String authenticate(User user, Model model, HttpSession session) {
		// not completed yet
		User u = userService.authenticate(user.getUsername(), user.getPassword());
		if (u == null) {
			model.addAttribute("loginMessage", "Incorrect username/password");
			return "login";
		}
		session.setAttribute("user", u);
		switch (u.getRole().getName()) {
			case "System_Admin":
				return "redirect:/system-admin";
			case "Clinic_Admin":
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
}
