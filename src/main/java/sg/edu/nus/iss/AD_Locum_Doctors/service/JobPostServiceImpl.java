package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobAdditionalRemarks;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPostForm;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobStatus;
import sg.edu.nus.iss.AD_Locum_Doctors.model.RemarksCategory;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.ClinicRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.JobAdditionalRemarksRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.JobPostRepository;

@Service
public class JobPostServiceImpl implements JobPostService {
	@Autowired
	private JobPostRepository jobPostRepo;

	@Autowired
	private JobAdditionalRemarksRepository jobAdditionalRemarksRepo;

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
	public List<JobPost> findJobHistory(String userId) {
		return jobPostRepo.findByIdAndStatusOrStatus(userId, JobStatus.COMPLETED_PENDING_PAYMENT,
				JobStatus.COMPLETED_PAYMENT_PROCESSED);
	}

	@Override
	public JobPost createJobPost(JobPostForm jobPostForm) {
		return null;
	}

	// public JobPost createJobPost(JobPostForm jobPostForm) { }
	public JobPost findJobPostById(String id) {
		return jobPostRepo.findById(Long.parseLong(id)).orElse(null);
	}

	public List<JobPost> findJobPostsWithOutstandingPayment() {
		return jobPostRepo.findJobPostsWithOutstandingPayment();
	}

	@Override
	public void saveJobPost(JobPost jobPost) {
		jobPostRepo.saveAndFlush(jobPost);
	}

	public List<JobPost> findPaidJobPosts() {
		return jobPostRepo.findPaidJobPosts();
	}

	public List<JobPost> findPaidandUnpaidJobPosts() {
		return jobPostRepo.findPaidAndUnpaidJobPosts();
	}

	@Override
	public void setStatus(JobPost jobPost, JobStatus status, String userId) {
		User freelancer = userService.findById(Long.valueOf(userId));

		switch (status) {
			case OPEN -> {
				jobPost.setStatus(JobStatus.OPEN);
			}
			case PENDING_CONFIRMATION_BY_CLINIC -> {
				jobPost.setStatus(JobStatus.PENDING_CONFIRMATION_BY_CLINIC);
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
		// newJobPost.setTotalRate(jobPostForm.getTotalRate());
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

	public void cancel(JobPost jobpost, JobAdditionalRemarks additionalRemarks, User user) {
		jobpost.setStatus(JobStatus.CANCELLED);
		jobPostRepo.saveAndFlush(jobpost);
		additionalRemarks.setCategory(RemarksCategory.CANCELLATION);
		additionalRemarks.setDate(LocalDate.now());
		additionalRemarks.setJobPost(jobpost);
		additionalRemarks.setUser(user);
		jobAdditionalRemarksRepo.saveAndFlush(additionalRemarks);
	}

	public void delete(JobPost jobpost, JobAdditionalRemarks additionalRemarks, User user) {
		jobpost.setStatus(JobStatus.REMOVED);
		jobPostRepo.saveAndFlush(jobpost);
		additionalRemarks.setCategory(RemarksCategory.DELETION);
		additionalRemarks.setDate(LocalDate.now());
		additionalRemarks.setJobPost(jobpost);
		additionalRemarks.setUser(user);
		jobAdditionalRemarksRepo.saveAndFlush(additionalRemarks);
	}
}
