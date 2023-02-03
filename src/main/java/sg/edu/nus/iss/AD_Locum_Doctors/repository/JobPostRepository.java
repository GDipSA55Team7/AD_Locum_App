package sg.edu.nus.iss.AD_Locum_Doctors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobStatus;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;

import java.util.List;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {

	// Hard coded organization ID for purpose of testing. Actual implementation will
	// require us to retrieve user's organization ID
	@Query("Select j from JobPost j where j.clinic.organization.id = ?1 and j.status = 3 ORDER BY j.endDateTime DESC")
	List<JobPost> findJobPostsWithOutstandingPayment(Long userOrgId);

	@Query("Select j from JobPost j where j.clinic.organization.id = ?1 and j.status = 4 ORDER BY j.endDateTime DESC")
	List<JobPost> findPaidJobPosts(Long userOrgId);

	List<JobPost> findByStatus(JobStatus status);

	@Query("select j from JobPost j where j.freelancer.id = ?1 and j.status = ?2 or j.status = ?3 or j.status = ?4")
	List<JobPost> findByIdAndStatusOrStatus(String id, JobStatus status, JobStatus status1, JobStatus status2);

	@Query("select j from JobPost j where j.freelancer.id = ?1 and j.status = ?2")
	List<JobPost> findByIdAndStatus(String id, JobStatus status);

	List<JobPost> findByClinicUser(User user);

	@Query("Select j from JobPost j where j.clinic.organization.id = ?1 and (j.status = 3 OR j.status = 4) ORDER BY j.endDateTime DESC")
	List<JobPost> findPaidAndUnpaidJobPosts(Long userOrgId);

}
