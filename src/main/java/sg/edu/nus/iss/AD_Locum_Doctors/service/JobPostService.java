package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobAdditionalRemarks;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPostForm;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;

public interface JobPostService {
	List<JobPost> findAll();
	
	JobPost findJobPostById(String id);

	JobPost createJobPost(JobPostForm jobPostForm, User user);

	void cancel(JobPost jobPost, JobAdditionalRemarks additionalRemarks, User user);
	
	List<JobPost> findJobPostsWithOutstandingPayment();
	
	List<JobPost> findPaidJobPosts();
	
	List<JobPost> findPaidandUnpaidJobPosts();
	
	void delete(JobPost jobPost, JobAdditionalRemarks additionalRemarks, User user);

}
