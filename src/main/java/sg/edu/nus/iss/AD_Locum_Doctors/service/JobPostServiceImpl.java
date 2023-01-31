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

	public List<JobPost> findAll() {
		return jobPostRepo.findAll();
	}
	
	public JobPost findJobPostById(String id) {
		return jobPostRepo.findById(Long.parseLong(id)).orElse(null);
	}
	
	public List<JobPost> findJobPostsWithOutstandingPayment(){
		return jobPostRepo.findJobPostsWithOutstandingPayment();
	}
	
	public List<JobPost> findPaidJobPosts(){
		return jobPostRepo.findPaidJobPosts();
	}
	
	public List<JobPost> findPaidandUnpaidJobPosts(){
		return jobPostRepo.findPaidAndUnpaidJobPosts();
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
