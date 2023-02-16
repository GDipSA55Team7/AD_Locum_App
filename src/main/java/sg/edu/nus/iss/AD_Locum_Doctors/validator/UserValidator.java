package sg.edu.nus.iss.AD_Locum_Doctors.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.service.UserService;

@Component
public class UserValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Autowired
	private UserService userService;

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		if (user.getUsername().equals("") || user.getPassword().equals("")) {
			errors.rejectValue("username", "error.username", "Username or password cannot be empty.");
		} else if (userService.authenticate(user.getUsername(), user.getPassword()) == null) {
			errors.rejectValue("username", "error.username", "Wrong username or password.");
		}
	}
}
