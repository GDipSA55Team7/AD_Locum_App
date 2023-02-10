package sg.edu.nus.iss.AD_Locum_Doctors.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.iss.AD_Locum_Doctors.model.AverageDailyRate;

public interface AverageDailyRateRepository extends JpaRepository<AverageDailyRate, LocalDate> {

}

