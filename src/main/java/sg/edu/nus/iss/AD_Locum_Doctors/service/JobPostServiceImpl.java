package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPostForm;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.ClinicRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.JobPostRepository;

@Service
public class JobPostServiceImpl implements JobPostService {
	@Autowired
	private JobPostRepository jobPostRepo;

	@Autowired
	private ClinicRepository clinicRepo;

	public List<JobPost> findAll() {
		return jobPostRepo.findAll();
	}

	public JobPost createJobPost(JobPostForm jobPostForm) {
		JobPost newJobPost = new JobPost();
		newJobPost.setDescription(jobPostForm.getDescription());
		newJobPost.setStartDateTime(jobPostForm.getStartDateTime());
		newJobPost.setEndDateTime(jobPostForm.getEndDateTime());
		newJobPost.setRatePerHour(jobPostForm.getRatePerHour());
		newJobPost.setTotalRate(jobPostForm.getTotalRate());
		newJobPost.setClinic(clinicRepo.findById(jobPostForm.getClinicId()).get());
		return jobPostRepo.saveAndFlush(newJobPost);
	}
}
