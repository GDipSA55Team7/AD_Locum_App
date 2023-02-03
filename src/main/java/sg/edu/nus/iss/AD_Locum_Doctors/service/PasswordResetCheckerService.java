package sg.edu.nus.iss.AD_Locum_Doctors.service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.ResetPasswordChecker;

public interface PasswordResetCheckerService {
    
    ResetPasswordChecker createResetPasswordChecker(ResetPasswordChecker pwchecker);
    
    Boolean checkResetPasswordEmailURLValidity(String key, String userID);
}
