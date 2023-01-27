package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;

public interface JobPostService {
	List<JobPost> findAll();

	JobPost create(JobPost jobPost);
}
