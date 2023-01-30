package sg.edu.nus.iss.AD_Locum_Doctors.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobStatus;

import sg.edu.nus.iss.AD_Locum_Doctors.model.User;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
List<JobPost> findByStatus(JobStatus status);

@Query("select j from JobPost j where j.freelancer.id = ?1 and j.status = ?2 or j.status = ?3")
List<JobPost> findByIdAndStatusOrStatus(String id, JobStatus status, JobStatus status1);

@Query("Select j from JobPost j where j.status = 3")
List<JobPost> findJobPostsWithOutstandingPayment();

List<JobPost> findByClinicUser(User user);
}
