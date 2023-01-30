package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPostForm;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobStatus;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;

public interface JobPostService {
	List<JobPost> findAll();
	List<JobPost> findAllOpen();
	List<JobPost> findJobHistory(String userId);
	JobPost createJobPost(JobPostForm jobPostForm);
	JobPost findJobPostById(String id);
	void cancel(JobPost jobPost);
	void delete(JobPost jobPost);
	List<JobPost> findJobPostsCreatedByUser(User user);
	List<JobPost> findJobPostsWithOutstandingPayment();
	void setStatus(JobPost jobPost, JobStatus status, String userId);
}
