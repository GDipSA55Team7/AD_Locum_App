package sg.edu.nus.iss.AD_Locum_Doctors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.edu.nus.iss.AD_Locum_Doctors.model.AdditionalFeeDetails;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Clinic;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobAdditionalRemarks;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobStatus;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Organization;
import sg.edu.nus.iss.AD_Locum_Doctors.model.RemarksCategory;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Role;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.AdditionalFeeDetailsRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.AverageCompensationRateRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.ClinicRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.JobAdditionalRemarksRepository;
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
			UserRepository userRepo,
			AdditionalFeeDetailsRepository additionalFeeDetailsRepository,
			JobAdditionalRemarksRepository jobRemarksRepo) {

		return args -> {

			Role r1 = new Role();
			r1.setName("Locum_Doctor");
			roleRepo.saveAndFlush(r1);

			Role r2 = new Role();
			r2.setName("Clinic_Main_Admin");
			roleRepo.saveAndFlush(r2);

			Role r3 = new Role();
			r3.setName("Clinic_User");
			roleRepo.saveAndFlush(r3);

			Role r4 = new Role();
			r4.setName("Clinic_Admin");
			roleRepo.saveAndFlush(r4);

			Role r5 = new Role();
			r5.setName("System_Admin");
			roleRepo.saveAndFlush(r5);

			Organization org1 = new Organization();
			org1.setName("Raffles Medical Group Ltd");
			org1.setUEN("198901967K");
			organizationRepo.saveAndFlush(org1);
			Organization org_sc = new Organization();
			org_sc.setName("SC Organization");
			org_sc.setUEN("222901967A");
			organizationRepo.saveAndFlush(org_sc);
			Organization org_pg = new Organization();
			org_pg.setName("PG Organization");
			org_pg.setUEN("333901967B");
			organizationRepo.saveAndFlush(org_pg);

			Clinic c1 = new Clinic();
			c1.setName("Punggol Family Clinic");
			c1.setAddress("Punggol");
			c1.setPostalCode("S123456");
			c1.setOrganization(org_pg);
			clinicRepo.saveAndFlush(c1);

			Clinic c2 = new Clinic();
			c2.setName("SilverCross Clinic (Yishun)");
			c2.setAddress("Yishun");
			c2.setPostalCode("S654321");
			c2.setOrganization(org_sc);
			clinicRepo.saveAndFlush(c2);

			Clinic c3 = new Clinic();
			c3.setName("Raffles Medical (Lot1 Shoppers Mall)");
			c3.setAddress("21 CHOA CHU KANG AVE 4 LOT 1 SHOPPERS' MALL #B1-07A SINGAPORE 689812");
			c3.setPostalCode("689812");
			c3.setOrganization(org1);
			clinicRepo.saveAndFlush(c3);

			Clinic c4 = new Clinic();
			c4.setName("Raffles Medical (Loyang Point)");
			c4.setAddress("BLK 259 PASIR RIS STREET 21 LOYANG POINT #02-33");
			c4.setPostalCode("510259");
			c4.setOrganization(org1);
			clinicRepo.saveAndFlush(c4);

			Clinic c5 = new Clinic();
			c5.setName("Raffles Medical (Raffles City Shopping Centre)");
			c5.setAddress("252 NORTH BRIDGE ROAD RAFFLES CITY SHOPPING CENTRE #02-17");
			c5.setPostalCode("179103");
			org1.setName("Raffles Medical Group Ltd");
			org1.setUEN("198901967K");
			c5.setOrganization(org1);
			clinicRepo.saveAndFlush(c5);

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

			User testUser2 = new User();
			testUser2.setName("Mary Tan");
			testUser2.setEmail("MaryTan@gmail.com");
			testUser2.setUsername("dr_mary");
			testUser2.setPassword("password123");
			testUser2.setContact("92231880");
			testUser2.setMedicalLicenseNo("M11266G");
			testUser2.setRole(r1);
			userRepo.saveAndFlush(testUser2);

			User testUser3 = new User();
			testUser3.setName("Jon Ng");
			testUser3.setEmail("JonNg@rmg.com.sg");
			testUser3.setUsername("jon");
			testUser3.setPassword("password123");
			testUser3.setContact("91119111");
			testUser3.setOrganization(org1);
			testUser3.setRole(r2);
			userRepo.saveAndFlush(testUser3);

			User testUser5 = new User();
			testUser5.setName("Ben");
			testUser5.setEmail("shanfu87@yahoo.com");
			testUser5.setUsername("ben");
			testUser5.setPassword("password123");
			testUser5.setContact("93339333");
			testUser5.setOrganization(org1);
			testUser5.setRole(r3);
			userRepo.saveAndFlush(testUser5);

			User testUser6 = new User();
			testUser6.setName("Ann");
			testUser6.setEmail("ann@locumapp.com.sg");
			testUser6.setUsername("ann");
			testUser6.setPassword("password123");
			testUser6.setContact("92229222");
			testUser6.setRole(r5);
			userRepo.saveAndFlush(testUser6);

			User testUser7 = new User();
			testUser7.setName("Ron");
			testUser7.setEmail("ron@rmg.com.sg");
			testUser7.setUsername("ron");
			testUser7.setPassword("password123");
			testUser7.setContact("98889888");
			testUser7.setRole(r5);
			userRepo.saveAndFlush(testUser7);

			JobPost jp1 = new JobPost();
			jp1.setDateModified(LocalDateTime.of(2023, 01, 1, 18, 30, 0));
			jp1.setClinic(c1);
			jp1.setTitle("Looking for Locum Doctor");
			jp1.setDescription("Looking for locum doctor for Punggol Family clinic");
			jp1.setStartDateTime(LocalDateTime.of(2023, 02, 22, 18, 30, 0));
			jp1.setEndDateTime(LocalDateTime.of(2023, 02, 22, 20, 30, 0));
			jp1.setRatePerHour(100);
			jp1.setClinicUser(testUser3);
			jp1.setFreelancer(testUser1);
			jp1.setStatus(JobStatus.OPEN);
			jobPostRepo.saveAndFlush(jp1);

			JobPost jp2 = new JobPost();
			jp2.setDateModified(LocalDateTime.of(2023, 01, 1, 18, 30, 0));
			jp2.setClinic(c2);
			jp2.setTitle("Looking for locum physiotherapist");
			jp2.setDescription("Looking for locum physiotherapist to perform the following ...");
			jp2.setStartDateTime(LocalDateTime.of(2023, 02, 25, 18, 30, 0));
			jp2.setEndDateTime(LocalDateTime.of(2023, 02, 25, 20, 30, 0));
			jp2.setStatus(JobStatus.OPEN);
			jp2.setRatePerHour(88.8);
			jp2.setClinicUser(testUser3);
			jp2.setFreelancer(testUser1);
			jobPostRepo.saveAndFlush(jp2);

			JobPost jp3 = new JobPost();
			jp3.setDateModified(LocalDateTime.of(2023, 01, 1, 18, 30, 0));
			jp3.setClinic(c2);
			jp3.setTitle("Looking for locum occupational therapist");
			jp3.setDescription("Looking for locum occupational therapist");
			jp3.setStartDateTime(LocalDateTime.of(2023, 02, 28, 18, 30, 0));
			jp3.setEndDateTime(LocalDateTime.of(2023, 02, 28, 20, 30, 0));
			jp3.setStatus(JobStatus.CANCELLED);
			jp3.setClinicUser(testUser3);
			jp3.setFreelancer(testUser1);
			jp3.setRatePerHour(88.8);
			jp3.setAdditionalRemarks("cancelled");
			jobPostRepo.saveAndFlush(jp3);

			JobPost jp4 = new JobPost();
			jp4.setDateModified(LocalDateTime.of(2023, 01, 3, 18, 30, 0));
			jp4.setClinic(c3);
			jp4.setTitle("Urgent Request for RMG @ Lot 1");
			jp4.setDescription("Raffles Medical Group in Lot1 looking for locum urgently!");
			jp4.setStartDateTime(LocalDateTime.of(2023, 01, 11, 18, 30, 0));
			jp4.setEndDateTime(LocalDateTime.of(2023, 01, 11, 20, 30, 0));
			jp4.setRatePerHour(100);
			jp4.setClinicUser(testUser3);
			jp4.setFreelancer(testUser1);
			jp4.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jp4.setActualStartDateTime(LocalDateTime.of(2023, 01, 11, 18, 30, 0));
			jp4.setActualEndDateTime(LocalDateTime.of(2023, 01, 11, 21, 30, 0));
			jobPostRepo.saveAndFlush(jp4);

			JobPost jp5 = new JobPost();
			jp5.setDateModified(LocalDateTime.of(2023, 01, 1, 18, 30, 0));
			jp5.setClinic(c4);
			jp5.setDescription("Raffles Medical Group in Loyang Point looking for locum urgently!");
			jp5.setStartDateTime(LocalDateTime.of(2023, 01, 15, 18, 30, 0));
			jp5.setEndDateTime(LocalDateTime.of(2023, 01, 15, 22, 30, 0));
			jp5.setRatePerHour(100);
			jp5.setFreelancer(testUser2);
			jp5.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jobPostRepo.saveAndFlush(jp5);

			JobPost jp6 = new JobPost();
			jp6.setDateModified(LocalDateTime.of(2023, 01, 1, 18, 30, 0));
			jp6.setClinic(c5);
			jp6.setTitle("Urgent Request for RMG @ Raffles City");
			jp6.setDescription("Raffles Medical Group in Raffles City Shopping Centre looking for locum urgently!");
			jp6.setStartDateTime(LocalDateTime.of(2023, 01, 20, 18, 30, 0));
			jp6.setEndDateTime(LocalDateTime.of(2023, 01, 20, 20, 30, 0));
			jp6.setRatePerHour(100);
			jp6.setFreelancer(testUser2);
			jp6.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jobPostRepo.saveAndFlush(jp6);

			JobPost jp7 = new JobPost();
			jp7.setDateModified(LocalDateTime.of(2023, 01, 5, 18, 30, 0));
			jp7.setClinic(c5);
			jp7.setTitle("Urgent Request for RMG @ Raffles City");
			jp7.setDescription("Raffles Medical Group in Raffles City looking for locum urgently!");
			jp7.setStartDateTime(LocalDateTime.of(2022, 12, 30, 18, 30, 0));
			jp7.setEndDateTime(LocalDateTime.of(2022, 12, 30, 21, 00, 0));
			jp7.setRatePerHour(100);
			jp7.setFreelancer(testUser2);
			jp7.setClinicUser(testUser3);
			jp7.setStatus(JobStatus.PENDING_CONFIRMATION_BY_CLINIC);
			jobPostRepo.saveAndFlush(jp7);

			JobPost jp8 = new JobPost();
			jp8.setDateModified(LocalDateTime.of(2023, 01, 1, 18, 30, 0));
			jp8.setClinic(c5);
			jp8.setTitle("Urgent Request for RMG @ Raffles City");
			jp8.setDescription("Looking for locum urgently!");
			jp8.setStartDateTime(LocalDateTime.of(2022, 12, 30, 18, 30, 0));
			jp8.setEndDateTime(LocalDateTime.of(2022, 12, 30, 21, 00, 0));
			jp8.setRatePerHour(100);
			jp8.setFreelancer(testUser2);
			jp8.setClinicUser(testUser3);
			jp8.setStatus(JobStatus.DELETED);
			jobPostRepo.saveAndFlush(jp8);

			JobPost jp9 = new JobPost();
			jp9.setDateModified(LocalDateTime.of(2023, 01, 1, 18, 30, 0));
			jp9.setClinic(c5);
			jp9.setTitle("Urgent Request for RMG @ Raffles City");
			jp9.setDescription("Looking for locum urgently!");
			jp9.setStartDateTime(LocalDateTime.of(2022, 12, 30, 18, 30, 0));
			jp9.setEndDateTime(LocalDateTime.of(2022, 12, 30, 21, 00, 0));
			jp9.setRatePerHour(100);
			jp9.setFreelancer(testUser2);
			jp9.setClinicUser(testUser3);
			jp9.setStatus(JobStatus.REMOVED);
			jobPostRepo.saveAndFlush(jp9);

			JobPost jp10 = new JobPost();
			jp10.setDateModified(LocalDateTime.of(2023, 01, 2, 18, 30, 0));
			jp10.setClinic(c5);
			jp10.setTitle("Urgent Request for RMG @ Raffles City");
			jp10.setDescription("Looking for locum urgently!");
			jp10.setStartDateTime(LocalDateTime.of(2022, 12, 30, 18, 30, 0));
			jp10.setEndDateTime(LocalDateTime.of(2022, 12, 30, 21, 00, 0));
			jp10.setRatePerHour(100);
			jp10.setFreelancer(testUser2);
			jp10.setClinicUser(testUser3);
			jp10.setStatus(JobStatus.COMPLETED_PAYMENT_PROCESSED);
			jobPostRepo.saveAndFlush(jp10);

			JobPost jp11 = new JobPost();
			jp11.setDateModified(LocalDateTime.of(2023, 01, 4, 18, 30, 0));
			jp11.setClinic(c5);
			jp11.setTitle("Urgent Request for RMG @ Raffles City");
			jp11.setDescription("Looking for locum urgently!");
			jp11.setStartDateTime(LocalDateTime.of(2022, 12, 30, 18, 30, 0));
			jp11.setEndDateTime(LocalDateTime.of(2022, 12, 30, 21, 00, 0));
			jp11.setRatePerHour(100);
			jp11.setFreelancer(testUser2);
			jp11.setClinicUser(testUser3);
			jp11.setStatus(JobStatus.ACCEPTED);
			jobPostRepo.saveAndFlush(jp11);

			AdditionalFeeDetails afdJob3 = new AdditionalFeeDetails();
			afdJob3.setJobPost(jp3);
			afdJob3.setDescription("Swab");
			afdJob3.setAdditionalFeesAmount(50);
			additionalFeeDetailsRepository.saveAndFlush(afdJob3);

			AdditionalFeeDetails afdJob3_1 = new AdditionalFeeDetails();
			afdJob3_1.setJobPost(jp3);
			afdJob3_1.setDescription("Transport");
			afdJob3_1.setAdditionalFeesAmount(60);
			additionalFeeDetailsRepository.saveAndFlush(afdJob3_1);

			JobAdditionalRemarks jp1_remark_1 = new JobAdditionalRemarks();
			jp1_remark_1.setJobPost(jp1);
			jp1_remark_1.setUser(testUser1);
			jp1_remark_1.setCategory(RemarksCategory.CANCELLATION);
			jp1_remark_1.setDate(LocalDate.of(2021, 2, 1));
			jp1_remark_1.setRemarks("Cancelled due to ...");
			jobRemarksRepo.saveAndFlush(jp1_remark_1);

			JobAdditionalRemarks jp1_remark_2 = new JobAdditionalRemarks();
			jp1_remark_2.setJobPost(jp1);
			jp1_remark_2.setUser(testUser2);
			jp1_remark_2.setCategory(RemarksCategory.CANCELLATION);
			jp1_remark_2.setDate(LocalDate.of(2021, 2, 2));
			jp1_remark_2.setRemarks("Cancelled due to ...");
			jobRemarksRepo.saveAndFlush(jp1_remark_2);
		};
	}
}
