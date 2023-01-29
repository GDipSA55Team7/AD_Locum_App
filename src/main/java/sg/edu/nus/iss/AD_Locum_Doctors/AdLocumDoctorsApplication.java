package sg.edu.nus.iss.AD_Locum_Doctors;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.edu.nus.iss.AD_Locum_Doctors.model.Clinic;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobStatus;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Organization;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Role;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
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
			
			Role r1 = new Role();
			r1.setName("Locum_Doctor");
			roleRepo.saveAndFlush(r1);
			
			Role r2 = new Role();
			r2.setName("Clinic_Admin");
			roleRepo.saveAndFlush(r2);
			
			Role r3 = new Role();
			r3.setName("Clinic_User");
			roleRepo.saveAndFlush(r3);
			
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
			
			Organization org1 = new Organization();
			org1.setName("Raffles Medical Group Ltd");
			org1.setUEN("198901967K");
			Clinic c3 = new Clinic();
			c3.setName("Raffles Medical (Lot1 Shoppers Mall");
			c3.setAddress("21 CHOA CHU KANG AVE 4 LOT 1 SHOPPERS' MALL #B1-07A SINGAPORE 689812");
			c3.setPostalCode("689812");
			c3.setOrganization(org1);
			Clinic c4 = new Clinic();
			c4.setName("Raffles Medical (Loyang Point");
			c4.setAddress("BLK 259 PASIR RIS STREET 21 LOYANG POINT #02-33");
			c4.setPostalCode("510259");
			c4.setOrganization(org1);
			Clinic c5 = new Clinic();
			c5.setName("Raffles Medical (Raffles City Shopping Centre)");
			c5.setAddress("252 NORTH BRIDGE ROAD RAFFLES CITY SHOPPING CENTRE #02-17");
			c5.setPostalCode("179103");
			c5.setOrganization(org1);
			List<Clinic> rafflesClinics = new ArrayList<>();
			rafflesClinics.add(c3);
			rafflesClinics.add(c4);
			rafflesClinics.add(c5);
			org1.setClinics(rafflesClinics);
			organizationRepo.saveAndFlush(org1);

			User testUser1 = new User();
			testUser1.setName("Robert Lin");
			testUser1.setEmail("RobertLin@gmail.com");
			testUser1.setUsername("dr_robert");
			testUser1.setPassword("password123");
			testUser1.setContact("97117782");
			testUser1.setMedicalLicenseNo("M31234H");
			testUser1.setRole(r1);
			userRepo.saveAndFlush(testUser1);
			
			JobPost jp1 = new JobPost();
			jp1.setClinic(c1);
			jp1.setDescription("Looking for locum doctor for Punggol Family clinic");
			jp1.setStartDate(LocalDate.of(2023, 01, 11));
			jp1.setEndDate(LocalDate.of(2023, 02, 28));
			jp1.setStartTime(LocalTime.of(8, 30));
			jp1.setEndTime(LocalTime.of(18, 30));
			jp1.setRatePerHour(100);
			jp1.setFreelancer(testUser1);
			jp1.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jobPostRepo.saveAndFlush(jp1);

			JobPost jp2 = new JobPost();
			jp2.setClinic(c2);
			jp2.setDescription("Looking for locum physiotherapist");
			jp2.setStartDate(LocalDate.of(2023, 02, 1));
			jp2.setEndDate(LocalDate.of(2023, 03, 30));
			jp2.setStartTime(LocalTime.of(15, 30));
			jp2.setEndTime(LocalTime.of(23, 30));
			jp2.setStatus(JobStatus.PENDING_ACCEPTANCE);
			jp2.setRatePerHour(88.8);
			jp2.setFreelancer(testUser1);
			jobPostRepo.saveAndFlush(jp2);

			JobPost jp3 = new JobPost();
			jp3.setClinic(c2);
			jp3.setDescription("Looking for locum occupational therapist");
			jp3.setStartDate(LocalDate.of(2023, 02, 1));
			jp3.setEndDate(LocalDate.of(2023, 03, 30));
			jp3.setStartTime(LocalTime.of(15, 30));
			jp3.setEndTime(LocalTime.of(23, 30));
			jp3.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jp3.setFreelancer(testUser1);
			jp3.setRatePerHour(88.8);

			User testUser2 = new User();
			testUser2.setName("Mary Tan");
			testUser2.setEmail("MaryTan@gmail.com");
			testUser2.setUsername("dr_mary");
			testUser2.setPassword("password123");
			testUser2.setContact("92231880");
			testUser2.setMedicalLicenseNo("M11266G");
			testUser2.setRole(r1);
			userRepo.saveAndFlush(testUser2);
			
			JobPost jp4 = new JobPost();
			jp4.setClinic(c3);
			jp4.setDescription("Raffles Medical Group in Lot1 looking for locum urgently!");
			jp4.setStartDate(LocalDate.of(2023, 01, 11));
			jp4.setEndDate(LocalDate.of(2023, 01, 11));
			jp4.setStartTime(LocalTime.of(18, 30));
			jp4.setEndTime(LocalTime.of(20, 30));
			jp4.setRatePerHour(100);
			jp4.setFreelancer(testUser2);
			jp4.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jobPostRepo.saveAndFlush(jp4);
			
			JobPost jp5 = new JobPost();
			jp5.setClinic(c4);
			jp5.setDescription("Raffles Medical Group in Loyang Point looking for locum urgently!");
			jp5.setStartDate(LocalDate.of(2023, 01, 15));
			jp5.setEndDate(LocalDate.of(2023, 01, 15));
			jp5.setStartTime(LocalTime.of(18, 30));
			jp5.setEndTime(LocalTime.of(20, 30));
			jp5.setRatePerHour(100);
			jp5.setFreelancer(testUser2);
			jp5.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jobPostRepo.saveAndFlush(jp5);
			
			JobPost jp6 = new JobPost();
			jp6.setClinic(c5);
			jp6.setDescription("Raffles Medical Group in Raffles City Shopping Centre looking for locum urgently!");
			jp6.setStartDate(LocalDate.of(2023, 01, 20));
			jp6.setEndDate(LocalDate.of(2023, 01, 20));
			jp6.setStartTime(LocalTime.of(18, 30));
			jp6.setEndTime(LocalTime.of(20, 30));
			jp6.setRatePerHour(100);
			jp6.setFreelancer(testUser2);
			jp6.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jobPostRepo.saveAndFlush(jp6);
			
			JobPost jp7 = new JobPost();
			jp7.setClinic(c5);
			jp7.setDescription("Raffles Medical Group in Lot1 looking for locum urgently!");
			jp7.setStartDate(LocalDate.of(2022, 12, 30));
			jp7.setEndDate(LocalDate.of(2023, 12, 30));
			jp7.setStartTime(LocalTime.of(18, 30));
			jp7.setEndTime(LocalTime.of(20, 30));
			jp7.setRatePerHour(100);
			jp7.setFreelancer(testUser2);
			jp7.setStatus(JobStatus.COMPLETED_PAYMENT_PROCESSED);
			jobPostRepo.saveAndFlush(jp7);
		};
	}
}
