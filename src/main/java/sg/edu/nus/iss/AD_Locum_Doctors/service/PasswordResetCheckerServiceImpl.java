package sg.edu.nus.iss.AD_Locum_Doctors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.ResetPasswordChecker;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.PasswordResetCheckerRepository;

@Service
public class PasswordResetCheckerServiceImpl implements PasswordResetCheckerService {

	@Autowired
	private PasswordResetCheckerRepository passwordResetCheckerRepo;

	@Override
	public ResetPasswordChecker createResetPasswordChecker(ResetPasswordChecker pwchecker) {
		return passwordResetCheckerRepo.saveAndFlush(pwchecker);
	}

	@Override
	public Boolean checkResetPasswordEmailURLValidity(String key, String userID) {
		try {
			System.out.println(key);
			ResetPasswordChecker checker = passwordResetCheckerRepo.findById(key).get();
			Long convertUserID = Long.parseLong(userID);
			if (checker != null) {
				if (checker.getValid() == true) {
					System.out.println("Not null!");
					if (checker.getUserID().equals(convertUserID)) {
						checker.setValid(false);
						passwordResetCheckerRepo.saveAndFlush(checker);
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
