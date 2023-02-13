package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobAdditionalRemarks;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobStatus;
import sg.edu.nus.iss.AD_Locum_Doctors.model.RemarksCategory;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.JobAdditionalRemarksRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.JobPostRepository;

@Transactional
@Service
public class JobPostServiceImpl implements JobPostService {
	@Autowired
	private JobPostRepository jobPostRepo;

	@Autowired
	private JobAdditionalRemarksRepository jobAdditionalRemarksRepo;

	@Autowired
	private JobPostAdditionalRemarksService remarksService;

	@Autowired
	private UserService userService;

	@Override
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
				JobStatus.COMPLETED_PAYMENT_PROCESSED, JobStatus.CANCELLED);
	}

	@Override
	public List<JobPost> findJobApplied(String userId) {
		return jobPostRepo.findByStatus(JobStatus.PENDING_CONFIRMATION_BY_CLINIC);
	}

	@Override
	public List<JobPost> findJobConfirmed(String userId) {
		return jobPostRepo.findByIdAndStatus(userId, JobStatus.ACCEPTED);
	}

	@Override
	public JobPost findJobPostById(Long id) {
		return jobPostRepo.findById(id).orElse(null);
	}

	@Override
	public JobPost findJobPostById(String id) {
		return jobPostRepo.findById(Long.parseLong(id)).orElse(null);
	}

	@Override
	public List<JobPost> findJobPostsWithOutstandingPayment(Long userOrgId) {
		return jobPostRepo.findJobPostsWithOutstandingPayment(userOrgId);
	}

	@Override
	public void saveJobPost(JobPost jobPost) {
		jobPostRepo.saveAndFlush(jobPost);
	}

	@Override
	public List<JobPost> findPaidJobPosts(Long userOrgId) {
		return jobPostRepo.findPaidJobPosts(userOrgId);
	}

	@Override
	public List<JobPost> findPaidandUnpaidJobPosts(Long userOrgId) {
		return jobPostRepo.findPaidAndUnpaidJobPosts(userOrgId);
	}

	@Override
	public void setStatus(JobPost jobPost, JobStatus status, String userId, JobAdditionalRemarks addRemarks) {
		User freelancer = userService.findById(Long.valueOf(userId));

		switch (status) {
			case OPEN -> {
				jobPost.setStatus(JobStatus.OPEN);
				jobPost.setFreelancer(null);
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
			case REMOVED -> {
				jobPost.setStatus(JobStatus.REMOVED);
			}
		}
		jobPostRepo.save(jobPost);
		if (addRemarks != null) {
			jobAdditionalRemarksRepo.save(addRemarks);
		}
	}

	@Override
	public JobPost createJobPost(JobPost jobPost, User user) {
		jobPost.setClinicUser(user);
		jobPostRepo.saveAndFlush(jobPost);
		remarksService.createJobPostAdditionalRemarks(RemarksCategory.CREATED, user, jobPost,
				jobPost.getAdditionalRemarks());
		return jobPost;
	}

	@Override
	public void cancel(JobPost jobPost) {
		jobPost.setStatus(JobStatus.CANCELLED);
		jobPostRepo.save(jobPost);
	}

	@Override
	public void delete(JobPost jobPost) {
		jobPostRepo.delete(jobPost);
	}

	@Override
	public List<JobPost> findJobPostsCreatedByUser(User user) {
		return jobPostRepo.findByClinicUser(user);
	}

	@Override
	public void cancel(JobPost jobpost, JobAdditionalRemarks additionalRemarks, User user) {
		jobpost.setStatus(JobStatus.CANCELLED);
		jobPostRepo.saveAndFlush(jobpost);
		additionalRemarks.setCategory(RemarksCategory.CANCELLATION);
		additionalRemarks.setDateTime(LocalDateTime.now());
		additionalRemarks.setJobPost(jobpost);
		additionalRemarks.setUser(user);
		jobAdditionalRemarksRepo.saveAndFlush(additionalRemarks);
	}

	@Override
	public void remove(JobPost jobpost, JobAdditionalRemarks additionalRemarks, User user) {
		jobpost.setStatus(JobStatus.REMOVED);
		jobPostRepo.saveAndFlush(jobpost);
		additionalRemarks.setCategory(RemarksCategory.REMOVED);
		additionalRemarks.setDateTime(LocalDateTime.now());
		additionalRemarks.setJobPost(jobpost);
		additionalRemarks.setUser(user);
		jobAdditionalRemarksRepo.saveAndFlush(additionalRemarks);
	}
}
