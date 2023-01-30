package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import com.sun.jdi.LongValue;
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
	@Autowired
	private UserService userService;

	public List<JobPost> findAll() {
		return jobPostRepo.findAll();
	}

	@Override
	public List<JobPost> findAllOpen() {
		return jobPostRepo.findByStatus(JobStatus.OPEN);
	}

	@Override
	public JobPost createJobPost(JobPostForm jobPostForm) {
		return null;
	}

	//	public JobPost createJobPost(JobPostForm jobPostForm) { }
	public JobPost findJobPostById(String id) {
		return jobPostRepo.findById(Long.parseLong(id)).orElse(null);
	}


	public List<JobPost> findJobPostsWithOutstandingPayment() {
		return jobPostRepo.findJobPostsWithOutstandingPayment();
	}

	@Override
	public void setStatus(JobPost jobPost, JobStatus status, String userId) {
		User freelancer = userService.findById(Long.valueOf(userId));

		switch (status) {
			case OPEN -> {
				jobPost.setStatus(JobStatus.OPEN);
			}
			case PENDING_ACCEPTANCE -> {
				jobPost.setStatus(JobStatus.PENDING_ACCEPTANCE);
				jobPost.setFreelancer(freelancer);
			}
			case ACCEPTED -> {
				jobPost.setStatus(JobStatus.ACCEPTED);
			}
			case COMPLETED_PENDING_PAYMENT -> {
				jobPost.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			}
			case COMPLETED_PAYMENT_PROCESSED -> {
				jobPost.setStatus(JobStatus.COMPLETED_PAYMENT_PROCESSED);
			}
			case CANCELLED -> {
				jobPost.setStatus(JobStatus.CANCELLED);
			}
			case DELETED -> {
				jobPost.setStatus(JobStatus.DELETED);
			}
		}
		jobPostRepo.save(jobPost);
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
