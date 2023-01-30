package sg.edu.nus.iss.AD_Locum_Doctors.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.AD_Locum_Doctors.model.Clinic;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    @Query ("SELECT c FROM Clinic c WHERE c.organization.id=?1")
    List<Clinic> findByOrganizationId(Long id);
}