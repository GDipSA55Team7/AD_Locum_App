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
		if (user != null) {
			User u = userService.authenticate(user.getUsername(), user.getPassword());
			if (u == null) {
				model.addAttribute("loginMessage", "Incorrect username/password");
				return "login";
			}
			session.setAttribute("user", u);
			if (u.getRole().getName().equals("Clinic_Admin")) {
				return "redirect:/";
			} else if (u.getRole().getName().equals("Locum_Doctor")) {
				return "redirect:/";
			} else if (u.getRole().getName().equals("Clinic_User")) {
				return "redirect:/";
			}
		}
		return "";
	}

	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
