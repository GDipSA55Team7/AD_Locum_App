package sg.edu.nus.iss.AD_Locum_Doctors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.AD_Locum_Doctors.model.Clinic;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {

}