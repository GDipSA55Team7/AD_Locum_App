package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobAdditionalRemarks;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.RemarksCategory;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.JobAdditionalRemarksRepository;

@Transactional
@Service
public class JobPostAdditionalRemarksServiceImpl implements JobPostAdditionalRemarksService {

	@Autowired
	JobAdditionalRemarksRepository addRemarksRepo;

	@Override
	public List<JobAdditionalRemarks> findAll() {
		return addRemarksRepo.findAll();
	}

	@Override
	public void createJobPostAdditionalRemarks(RemarksCategory category, User user, JobPost jobPost, String remarks) {
		JobAdditionalRemarks additionalRemarks = new JobAdditionalRemarks();
		additionalRemarks.setCategory(category);
		additionalRemarks.setDateTime(LocalDateTime.now());
		additionalRemarks.setJobPost(jobPost);
		additionalRemarks.setUser(user);
		additionalRemarks.setRemarks(jobPost.getAdditionalRemarks());
		addRemarksRepo.saveAndFlush(additionalRemarks);
	}
}
