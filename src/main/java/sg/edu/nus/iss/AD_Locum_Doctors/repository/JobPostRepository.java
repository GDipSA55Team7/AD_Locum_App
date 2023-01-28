package sg.edu.nus.iss.AD_Locum_Doctors.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
	
	@Query("Select j from JobPost j where j.status = 'Completed_Pending_Payment'")
	List<JobPost> findJobPostsWithOutstandingPayment();

}
