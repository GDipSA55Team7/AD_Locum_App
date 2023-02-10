package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobAdditionalRemarks;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.RemarksCategory;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;

public interface JobPostAdditionalRemarksService {
	List<JobAdditionalRemarks> findAll();

	void createJobPostAdditionalRemarks(RemarksCategory category, User user, JobPost jobPost, String remarks);
}
