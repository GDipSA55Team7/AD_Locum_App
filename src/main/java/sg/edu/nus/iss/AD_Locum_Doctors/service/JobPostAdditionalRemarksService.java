package sg.edu.nus.iss.AD_Locum_Doctors.service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.RemarksCategory;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;

public interface JobPostAdditionalRemarksService {
	void createJobPostAdditionalRemarks(RemarksCategory category, User user, JobPost jobPost, String remarks);
}
