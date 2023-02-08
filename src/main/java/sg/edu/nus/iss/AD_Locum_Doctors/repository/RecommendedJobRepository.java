package sg.edu.nus.iss.AD_Locum_Doctors.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.edu.nus.iss.AD_Locum_Doctors.model.RecommendedJob;

import java.util.List;

@Repository
public interface RecommendedJobRepository extends JpaRepository<RecommendedJob, Long> {
    @Query("select r from RecommendedJob r where r.userId = ?1")
    List<RecommendedJob> findByUserId(Long userId);

}
