package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.JobPostRepository;

@Service
public class JobPostServiceImpl implements JobPostService {
	@Autowired
	private JobPostRepository jobPostRepo;

	public List<JobPost> findAll(){
		return jobPostRepo.findAll();
	}

	public JobPost create(JobPost jobPost){
		return jobPostRepo.saveAndFlush(jobPost);
	}
}
