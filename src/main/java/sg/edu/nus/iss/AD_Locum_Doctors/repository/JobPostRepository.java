package sg.edu.nus.iss.AD_Locum_Doctors.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
	
	//Hard coded organization ID for purpose of testing. Actual implementation will require us to retrieve user's organization ID
	@Query("Select j from JobPost j where j.clinic.organization = 1 and j.status = 3 ORDER BY j.endDateTime DESC")
	List<JobPost> findJobPostsWithOutstandingPayment();
	
	@Query("Select j from JobPost j where j.clinic.organization = 1 and j.status = 4 ORDER BY j.endDateTime DESC")
	List<JobPost> findPaidJobPosts();

	@Query("Select j from JobPost j where j.clinic.organization = 1 and (j.status = 3 OR j.status = 4) ORDER BY j.endDateTime DESC")
	List<JobPost> findPaidAndUnpaidJobPosts();
	
}
