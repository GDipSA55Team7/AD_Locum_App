package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPostForm;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;

public interface JobPostService {
	List<JobPost> findAll();

	JobPost createJobPost(JobPostForm jobPostForm, User user);
	
	List<JobPost> findJobPostsWithOutstandingPayment();
}
