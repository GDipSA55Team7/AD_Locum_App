package sg.edu.nus.iss.AD_Locum_Doctors.service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

	List<JobPost> findAppliedForRecommender();

	Map<JobPost, Double> findAllRecommended(Long userId);

	void setStatus(JobPost jobPost, JobStatus status, String userId, JobAdditionalRemarks additionalRemarks);

	String convertAdditionalFeesToString(JobPost jobPost);

	List<JobPost> findJobPostByUserId(String id);

}
