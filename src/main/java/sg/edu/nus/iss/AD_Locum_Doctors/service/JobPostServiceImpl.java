package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.edu.nus.iss.AD_Locum_Doctors.model.*;

import sg.edu.nus.iss.AD_Locum_Doctors.repository.ClinicRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.JobAdditionalRemarksRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.JobPostRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.RecommendedJobRepository;

@Transactional
@Service
public class JobPostServiceImpl implements JobPostService {
	@Autowired
	private JobPostRepository jobPostRepo;

	@Autowired
	private RecommendedJobRepository recJobRepo;

	@Autowired
	private JobAdditionalRemarksRepository jobAdditionalRemarksRepo;

	@Autowired
	private JobPostAdditionalRemarksService remarksService;

	@Autowired
	private ClinicRepository clinicRepo;

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
		return jobPostRepo.findByIdAndStatus(userId, JobStatus.PENDING_CONFIRMATION_BY_CLINIC);
	}

	@Override
	public List<JobPost> findJobConfirmed(String userId) {
		return jobPostRepo.findByIdAndStatus(userId, JobStatus.ACCEPTED);
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
    public List<JobPost> findAllApplied() {
        return jobPostRepo.findByFreelancerNotNull();
    }

	@Override
	public Map<JobPost, Double> findAllRecommended(Long userId) {
		List<RecommendedJob> recJobsList = recJobRepo.findByUserId(userId);
		Map<JobPost, Double> jobPostMap = new HashMap<JobPost, Double>();
		for (RecommendedJob recJobs : recJobsList) {
			JobPost jobPost = jobPostRepo.getReferenceById(recJobs.getJobId());
			if (jobPost.getStatus() == JobStatus.OPEN) {
				jobPostMap.put(jobPost, recJobs.getSimilarityScore());
			}
		}
		return jobPostMap;
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
	public void delete(JobPost jobpost, JobAdditionalRemarks additionalRemarks, User user) {
		jobpost.setStatus(JobStatus.REMOVED);
		jobPostRepo.saveAndFlush(jobpost);
		additionalRemarks.setCategory(RemarksCategory.DELETION);
		additionalRemarks.setDateTime(LocalDateTime.now());
		additionalRemarks.setJobPost(jobpost);
		additionalRemarks.setUser(user);
		jobAdditionalRemarksRepo.saveAndFlush(additionalRemarks);
	}

	@Override
	public String convertAdditionalFeesToString(JobPost jobPost) {
 	   String additionaFeeDetailsJSONString = "";
       List<AdditionalFeeDetails> additionalDetailsFeeList = jobPost.getAdditionalFeeDetails();
       if(!additionalDetailsFeeList.isEmpty()) {
           for(Integer i = 0 ;  i < additionalDetailsFeeList.size() ; i++) {
          	 double feeAmt =  additionalDetailsFeeList.get(i).getAdditionalFeesAmount();
          	 String feeAmtTo2DP = String.format("%.2f",feeAmt);
        	 String feeDescription =  additionalDetailsFeeList.get(i).getDescription();
        	 additionaFeeDetailsJSONString += feeAmtTo2DP;
        	 additionaFeeDetailsJSONString += ",";
        	 additionaFeeDetailsJSONString += feeDescription;
            if(i != additionalDetailsFeeList.size() - 1) {
           	 additionaFeeDetailsJSONString += ";";
            }
          }
       }
       return additionaFeeDetailsJSONString;
	}


}
