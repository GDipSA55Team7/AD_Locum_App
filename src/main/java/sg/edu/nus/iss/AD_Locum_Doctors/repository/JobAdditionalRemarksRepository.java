package sg.edu.nus.iss.AD_Locum_Doctors.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobAdditionalRemarks;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;

@Repository
public interface JobAdditionalRemarksRepository extends JpaRepository<JobAdditionalRemarks, Long> {
	List<JobAdditionalRemarks> findByJobPost(JobPost jobPost);
}
