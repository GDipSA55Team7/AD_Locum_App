package sg.edu.nus.iss.AD_Locum_Doctors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.AD_Locum_Doctors.model.AverageCompensationRate;

@Repository
public interface AverageCompensationRateRepository extends JpaRepository<AverageCompensationRate, Long> {

}
