package sg.edu.nus.iss.AD_Locum_Doctors;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.edu.nus.iss.AD_Locum_Doctors.model.Clinic;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.AverageCompensationRateRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.ClinicRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.JobPostRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.OrganizationRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.RoleRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.UserRepository;

@SpringBootApplication
public class AdLocumDoctorsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdLocumDoctorsApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRun(
			AverageCompensationRateRepository averageCompensationRateRepo,
			ClinicRepository clinicRepo,
			JobPostRepository jobPostRepo,
			OrganizationRepository organizationRepo,
			RoleRepository roleRepo,
			UserRepository userRepo) {
		return args -> {

			Clinic c1 = new Clinic();
			c1.setName("Punggol Family Clinic");
			c1.setAddress("Punggol");
			c1.setPostalCode("S123456");
			clinicRepo.saveAndFlush(c1);

			Clinic c2 = new Clinic();
			c2.setName("SilverCross Clinic (Yishun)");
			c2.setAddress("Yishun");
			c2.setPostalCode("S654321");
			clinicRepo.saveAndFlush(c2);

			JobPost jp1 = new JobPost();
			jp1.setClinic(c1);
			jp1.setDescription("Looking for locum doctor for Punggol Family clinic");
			jp1.setStartDate(LocalDate.of(2023, 01, 11));
			jp1.setEndDate(LocalDate.of(2023, 02, 28));
			jp1.setStartTime(LocalTime.of(8, 30));
			jp1.setEndTime(LocalTime.of(18, 30));
			jp1.setRatePerHour(100);
			jobPostRepo.saveAndFlush(jp1);

			JobPost jp2 = new JobPost();
			jp2.setClinic(c2);
			jp2.setDescription("Looking for locum physiotherapist");
			jp2.setStartDate(LocalDate.of(2023, 02, 1));
			jp2.setEndDate(LocalDate.of(2023, 03, 30));
			jp2.setStartTime(LocalTime.of(15, 30));
			jp2.setEndTime(LocalTime.of(23, 30));
			jp2.setStatus("PENDING");
			jp2.setRatePerHour(88.8);
			jobPostRepo.saveAndFlush(jp2);
		};
	}
}
