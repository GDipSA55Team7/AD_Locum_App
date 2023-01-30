package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPostForm;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobStatus;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
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

	public JobPost findJobPostById(String id) {
		return jobPostRepo.findById(Long.parseLong(id)).orElse(null);
	}

	public List<JobPost> findJobPostsWithOutstandingPayment() {
		return jobPostRepo.findJobPostsWithOutstandingPayment();
	}

	public JobPost createJobPost(JobPostForm jobPostForm, User user) {
		JobPost newJobPost = new JobPost();
		newJobPost.setClinicUser(user);
		newJobPost.setDescription(jobPostForm.getDescription());
		newJobPost.setStartDateTime(jobPostForm.getStartDateTime());
		newJobPost.setEndDateTime(jobPostForm.getEndDateTime());
		newJobPost.setRatePerHour(jobPostForm.getRatePerHour());
		newJobPost.setTotalRate(jobPostForm.getTotalRate());
		newJobPost.setClinic(clinicRepo.findById(jobPostForm.getClinicId()).get());
		return jobPostRepo.saveAndFlush(newJobPost);
	}

	public void cancel(JobPost jobPost) {
		jobPost.setStatus(JobStatus.CANCELLED);
		jobPostRepo.save(jobPost);
	}

	public void delete(JobPost jobPost) {
		jobPostRepo.delete(jobPost);
	}

	public List<JobPost> findJobPostsCreatedByUser(User user) {
		return jobPostRepo.findByClinicUser(user);
	}
}
