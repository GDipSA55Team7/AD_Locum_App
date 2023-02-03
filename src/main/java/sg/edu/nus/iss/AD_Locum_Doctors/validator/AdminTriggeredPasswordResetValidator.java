package sg.edu.nus.iss.AD_Locum_Doctors.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.edu.nus.iss.AD_Locum_Doctors.model.changePasswordForm;

@Component
public class AdminTriggeredPasswordResetValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz){
        return changePasswordForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
    	changePasswordForm pwform = (changePasswordForm) obj;
    	if (!(pwform.getNewPassword().equals(pwform.getConfirmNewPassword()))){
    		errors.rejectValue("confirmNewPassword", "confirmNewPassword", "Password & Confirm Password do not match!");
    	}
    }
  
}
