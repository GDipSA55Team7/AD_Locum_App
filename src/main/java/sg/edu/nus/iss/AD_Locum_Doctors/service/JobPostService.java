package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobAdditionalRemarks;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobStatus;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;

public interface JobPostService {
	List<JobPost> findAll();

	List<JobPost> findAllOpen();

	List<JobPost> findJobHistory(String userId);

	List<JobPost> findJobApplied(String userId);

	List<JobPost> findJobConfirmed(String userId);

	JobPost createJobPost(JobPost jobPost, User user);

	JobPost findJobPostById(Long id);

	JobPost findJobPostById(String id);

	void cancel(JobPost jobPost);

	void delete(JobPost jobPost);

	List<JobPost> findJobPostsCreatedByUser(User user);

	void cancel(JobPost jobPost, JobAdditionalRemarks additionalRemarks, User user);

	List<JobPost> findJobPostsWithOutstandingPayment(Long userOrgID);

	void saveJobPost(JobPost jobPost);

	List<JobPost> findPaidJobPosts(Long userOrgId);

	List<JobPost> findPaidandUnpaidJobPosts(Long userOrgId);

	void remove(JobPost jobPost, JobAdditionalRemarks additionalRemarks, User user);

	void setStatus(JobPost jobPost, JobStatus status, String userId, JobAdditionalRemarks additionalRemarks);
}
