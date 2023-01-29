package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPostForm;

public interface JobPostService {
	List<JobPost> findAll();

	List<JobPost> findAllOpen();

	JobPost createJobPost(JobPostForm jobPostForm);
}
