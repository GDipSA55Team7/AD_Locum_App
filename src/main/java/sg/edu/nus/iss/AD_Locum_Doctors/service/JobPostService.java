package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import org.springframework.stereotype.Service;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobAdditionalRemarks;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPostForm;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;

@Service
public interface JobPostService {
	List<JobPost> findAll();
	
	JobPost findJobPostById(String id);

	JobPost createJobPost(JobPostForm jobPostForm, User user);

	void cancel(JobPost jobPost);

	void delete(JobPost jobPost);

	List<JobPost> findJobPostsCreatedByUser(User user);

	void cancel(JobPost jobPost, JobAdditionalRemarks additionalRemarks, User user);
	
	List<JobPost> findJobPostsWithOutstandingPayment();

	void saveJobPost(JobPost jobPost);
	
	List<JobPost> findPaidJobPosts();
	
	List<JobPost> findPaidandUnpaidJobPosts();
	
	void delete(JobPost jobPost, JobAdditionalRemarks additionalRemarks, User user);
}
