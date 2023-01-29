package sg.edu.nus.iss.AD_Locum_Doctors.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;


@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    List<JobPost> findByStatus(String status);

@Query("Select j from JobPost j where j.status = 3")
List<JobPost> findJobPostsWithOutstandingPayment();

}
