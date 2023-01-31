package sg.edu.nus.iss.AD_Locum_Doctors.service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobAdditionalRemarks;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPostForm;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobStatus;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;

import java.util.List;

public interface JobPostService {
	List<JobPost> findAll();

	List<JobPost> findAllOpen();

	List<JobPost> findJobHistory(String userId);

	JobPost createJobPost(JobPostForm jobPostForm);

	JobPost findJobPostById(String id);

	void cancel(JobPost jobPost);

	void delete(JobPost jobPost);

	List<JobPost> findJobPostsCreatedByUser(User user);

	void cancel(JobPost jobPost, JobAdditionalRemarks additionalRemarks, User user);

	List<JobPost> findJobPostsWithOutstandingPayment();

	void saveJobPost(JobPost jobPost);

	List<JobPost> findPaidJobPosts();

	List<JobPost> findPaidandUnpaidJobPosts();

	void delete(JobPost jobPost, JobAdditionalRemarks additionalRemarks, User user);

	void setStatus(JobPost jobPost, JobStatus status, String userId);
}
