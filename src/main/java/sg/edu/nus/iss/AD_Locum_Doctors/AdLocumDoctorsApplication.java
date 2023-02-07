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
			c1.setAddress("322, #01-03 Sumang Walk");
			c1.setPostalCode("820322");
			c1.setOrganization(org_pg);
			c1.setHCICode("21M0064");
			clinicRepo.saveAndFlush(c1);

			Clinic c2 = new Clinic();
			c2.setName("SilverCross Clinic (Yishun)");
			c2.setAddress("Yishun");
			c2.setPostalCode("654321");
			c2.setOrganization(org_sc);
			c2.setHCICode("20M0355");
			clinicRepo.saveAndFlush(c2);

			Clinic c3 = new Clinic();
			c3.setName("Raffles Medical (Lot1 Shoppers Mall)");
			c3.setAddress("21 CHOA CHU KANG AVE 4 LOT 1 SHOPPERS' MALL #B1-07A SINGAPORE 689812");
			c3.setPostalCode("689812");
			c3.setHCICode("15M0364");
			c3.setOrganization(org1);
			clinicRepo.saveAndFlush(c3);

			Clinic c4 = new Clinic();
			c4.setName("Raffles Medical (Loyang Point)");
			c4.setAddress("BLK 259 PASIR RIS STREET 21 LOYANG POINT #02-33");
			c4.setPostalCode("510259");
			c4.setHCICode("17M0142");
			c4.setOrganization(org1);
			clinicRepo.saveAndFlush(c4);

			Clinic c5 = new Clinic();
			c5.setName("Raffles Medical (Raffles City Shopping Centre)");
			c5.setContact("88834567");
			c5.setEmail("rafflemedical111@gmail.com");
			c5.setAddress("252 NORTH BRIDGE ROAD RAFFLES CITY SHOPPING CENTRE #02-17");
			c5.setPostalCode("179103");
			c5.setHCICode("21M0229");
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
			testUser7.setRole(r4);
			userRepo.saveAndFlush(testUser7);

			JobPost jp1 = new JobPost();
			jp1.setClinic(c3);
			jp1.setTitle("Looking for locum @ RMG Lot 1");
			jp1.setDescription("Looking for locum doctor");
			jp1.setStartDateTime(LocalDateTime.of(2023, 02, 22, 18, 30, 0));
			jp1.setEndDateTime(LocalDateTime.of(2023, 02, 22, 20, 30, 0));
			jp1.setRatePerHour(100);
			jp1.setClinicUser(testUser3);
			jp1.setFreelancer(null);
			jp1.setStatus(JobStatus.OPEN);
			jobPostRepo.saveAndFlush(jp1);

			JobPost jp2 = new JobPost();
			jp2.setClinic(c3);
			jp2.setTitle("Looking for locum physiotherapist");
			jp2.setDescription("Looking for locum physiotherapist to perform the following ...");
			jp2.setStartDateTime(LocalDateTime.of(2023, 02, 25, 18, 30, 0));
			jp2.setEndDateTime(LocalDateTime.of(2023, 02, 25, 20, 30, 0));
			jp2.setStatus(JobStatus.OPEN);
			jp2.setRatePerHour(88.8);
			jp2.setClinicUser(testUser3);
			jp2.setFreelancer(null);
			jobPostRepo.saveAndFlush(jp2);

			JobPost jp3 = new JobPost();
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
			jp5.setClinic(c4);
			jp5.setTitle("Looking for locum @ RMG Loyang Point");
			jp5.setDescription("Raffles Medical Group in Loyang Point looking for locum urgently!");
			jp5.setStartDateTime(LocalDateTime.of(2023, 01, 15, 18, 30, 0));
			jp5.setEndDateTime(LocalDateTime.of(2023, 01, 15, 22, 30, 0));
			jp5.setActualStartDateTime(LocalDateTime.of(2023, 01, 15, 18, 30, 0));
			jp5.setActualEndDateTime(LocalDateTime.of(2023, 01, 15, 22, 30, 0));
			jp5.setRatePerHour(100);
			jp5.setClinicUser(testUser5);
			jp5.setFreelancer(testUser2);
			jp5.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jobPostRepo.saveAndFlush(jp5);

			JobPost jp6 = new JobPost();
			jp6.setClinic(c5);
			jp6.setTitle("Urgent Request for RMG @ Raffles City");
			jp6.setDescription("Raffles Medical Group in Raffles City Shopping Centre looking for locum urgently!");
			jp6.setStartDateTime(LocalDateTime.of(2023, 01, 20, 18, 30, 0));
			jp6.setEndDateTime(LocalDateTime.of(2023, 01, 20, 20, 30, 0));
			jp6.setActualStartDateTime(LocalDateTime.of(2023, 01, 20, 18, 30, 0));
			jp6.setActualEndDateTime(LocalDateTime.of(2023, 01, 20, 20, 30, 0));
			jp6.setRatePerHour(100);
			jp6.setClinicUser(testUser5);
			jp6.setFreelancer(testUser2);
			jp6.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jobPostRepo.saveAndFlush(jp6);

			JobPost jp7 = new JobPost();
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
			jp8.setClinic(c5);
			jp8.setTitle("Urgent Request for RMG @ Raffles City");
			jp8.setDescription("Looking for locum urgently!");
			jp8.setStartDateTime(LocalDateTime.of(2022, 12, 30, 18, 30, 0));
			jp8.setEndDateTime(LocalDateTime.of(2022, 12, 30, 21, 00, 0));
			jp8.setRatePerHour(100);
			jp8.setFreelancer(testUser2);
			jp8.setClinicUser(testUser3);
			jp8.setStatus(JobStatus.REMOVED);
			jobPostRepo.saveAndFlush(jp8);

			JobPost jp9 = new JobPost();
			jp9.setClinic(c5);
			jp9.setTitle("Urgent Request for RMG @ Raffles City");
			jp9.setDescription("Looking for locum urgently!");
			jp9.setStartDateTime(LocalDateTime.of(2022, 12, 30, 18, 30, 0));
			jp9.setEndDateTime(LocalDateTime.of(2022, 12, 30, 21, 00, 0));
			jp9.setRatePerHour(100);
			jp9.setFreelancer(testUser2);
			jp9.setClinicUser(testUser3);
			jp9.setStatus(JobStatus.PENDING_CONFIRMATION_BY_CLINIC);
			jobPostRepo.saveAndFlush(jp9);

			JobPost jp10 = new JobPost();
			jp10.setClinic(c5);
			jp10.setTitle("Urgent Request for RMG @ Raffles City");
			jp10.setDescription("Looking for locum urgently!");
			jp10.setStartDateTime(LocalDateTime.of(2022, 12, 30, 18, 30, 0));
			jp10.setEndDateTime(LocalDateTime.of(2022, 12, 30, 21, 00, 0));
			jp10.setActualStartDateTime(LocalDateTime.of(2022, 12, 30, 18, 30, 0));
			jp10.setActualEndDateTime(LocalDateTime.of(2022, 12, 30, 21, 00, 0));
			jp10.setPaymentDate( LocalDate.of(2023, 01, 15));
			jp10.setPaymentReferenceNumber("23011510");
			jp10.setRatePerHour(100);
			jp10.setFreelancer(testUser2);
			jp10.setClinicUser(testUser3);
			jp10.setStatus(JobStatus.COMPLETED_PAYMENT_PROCESSED);
			jobPostRepo.saveAndFlush(jp10);

			JobPost jp11 = new JobPost();
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

			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.CANCELLATION, "Cancelled due to ...",
					LocalDateTime.of(2021, 2, 2, 1, 0, 0), jp1, testUser2));
			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.CANCELLATION, "Cancelled due to ...",
					LocalDateTime.of(2021, 2, 1, 1, 0, 0), jp1, testUser1));
			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.CREATED, "",
					LocalDateTime.of(2021, 1, 12, 11, 0, 0), jp1, testUser3));

			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.CREATED, "",
					LocalDateTime.of(2021, 2, 12, 1, 0, 0), jp2, testUser3));

			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.CANCELLATION, "",
					LocalDateTime.of(2021, 2, 12, 12, 0, 0), jp3, testUser3));
			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.ACCEPTION, "",
					LocalDateTime.of(2021, 1, 21, 13, 0, 0), jp3, testUser3));
			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.CREATED, "",
					LocalDateTime.of(2021, 1, 20, 12, 0, 0), jp3, testUser3));

			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.COMPLETED_JOB, "",
					LocalDateTime.of(2021, 2, 12, 2, 0, 0), jp4, testUser3));

			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.CREATED, "",
					LocalDateTime.of(2021, 2, 20, 12, 0, 0), jp5, testUser5));

			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.CREATED, "",
					LocalDateTime.of(2021, 2, 20, 3, 0, 0), jp6, testUser5));

			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.PENDING, "",
					LocalDateTime.of(2021, 2, 20, 14, 0, 0), jp7, testUser3));
			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.CREATED, "",
					LocalDateTime.of(2021, 2, 20, 13, 0, 0), jp7, testUser3));

			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.REMOVED, "",
					LocalDateTime.of(2021, 2, 21, 1, 0, 0), jp8, testUser3));
			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.CREATED, "",
					LocalDateTime.of(2021, 2, 21, 1, 0, 0), jp8, testUser3));

			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.REMOVED, "",
					LocalDateTime.of(2021, 2, 21, 3, 0, 0), jp9, testUser3));
			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.CREATED, "",
					LocalDateTime.of(2021, 2, 21, 2, 0, 0), jp9, testUser3));

			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.PROCESSED_PAYMENT, "",
					LocalDateTime.of(2021, 2, 21, 13, 0, 0), jp10, testUser3));

			jobRemarksRepo.saveAndFlush(new JobAdditionalRemarks(RemarksCategory.ACCEPTION, "",
					LocalDateTime.of(2021, 2, 21, 13, 0, 0), jp11, testUser3));

			// Recommender mock data

			User ru1 = new User();
			testUser1.setName("Luffy");
			testUser1.setEmail("Luffy@gmail.com");
			testUser1.setUsername("luffy");
			testUser1.setPassword("luffy");
			testUser1.setContact("81381332");
			testUser1.setMedicalLicenseNo("M31223H");
			testUser1.setRole(r1);
			userRepo.saveAndFlush(ru1);

			Clinic cNorth = new Clinic();
			cNorth.setName("Raffles Medical - Causeway Point");
			cNorth.setAddress("1 Woodlands Square #05-20 Causeway Point");
			cNorth.setPostalCode("738099");
			cNorth.setOrganization(org1);
			cNorth.setHCICode("21M0162");
			clinicRepo.saveAndFlush(cNorth);

			Clinic cEast = new Clinic();
			cEast.setName("Raffles Medical - Bedok North Blk 203");
			cEast.setAddress("Blk 203 Bedok North Street 1, #01-467");
			cEast.setPostalCode("460203");
			cEast.setOrganization(org1);
			cEast.setHCICode("21M0163");
			clinicRepo.saveAndFlush(cEast);

			Clinic cWest = new Clinic();
			cWest.setName("Raffles Medical - Jurong East");
			cWest.setAddress("Blk 131 Jurong Gateway Road, #01-267");
			cWest.setPostalCode("600131");
			cWest.setOrganization(org1);
			cWest.setHCICode("21M0164");
			clinicRepo.saveAndFlush(cWest);

			Clinic cSouth = new Clinic();
			cSouth.setName("Raffles Medical Anson Centre");
			cSouth.setAddress("51 Anson Road, #01-51 Anson Centre");
			cSouth.setPostalCode("079904");
			cSouth.setOrganization(org1);
			cSouth.setHCICode("21M0165");
			clinicRepo.saveAndFlush(cSouth);

			Clinic cCentral = new Clinic();
			cCentral.setName("Raffles Medical Bishan");
			cCentral.setAddress("Bishan Street 22, #01-177 Blk 283");
			cCentral.setPostalCode("570283");
			cCentral.setOrganization(org1);
			cCentral.setHCICode("21M0161");
			clinicRepo.saveAndFlush(cCentral);

			// Applied/Confirmed jobs

			JobPost jr1 = new JobPost();
			jr1.setClinic(cEast);
			jr1.setTitle("Recommender accepted 1");
			jr1.setDescription("Raffles Medical Group in Lot1 looking for locum urgently!");
			jr1.setStartDateTime(LocalDateTime.of(2023, 1, 2, 10, 30, 0));
			jr1.setEndDateTime(LocalDateTime.of(2023, 1, 2, 20, 30, 0));
			jr1.setRatePerHour(100);
			jr1.setClinicUser(testUser3);
			jr1.setFreelancer(ru1);
			jr1.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jr1.setActualStartDateTime(LocalDateTime.of(2023, 1, 2, 10, 30, 0));
			jr1.setActualEndDateTime(LocalDateTime.of(2023, 1, 2, 21, 30, 0));
			jobPostRepo.saveAndFlush(jr1);

			JobPost jr2 = new JobPost();
			jr2.setClinic(cEast);
			jr2.setTitle("Recommender accepted 2");
			jr2.setDescription("Raffles Medical Group in Lot1 looking for locum urgently!");
			jr2.setStartDateTime(LocalDateTime.of(2023, 1, 9, 10, 30, 0));
			jr2.setEndDateTime(LocalDateTime.of(2023, 1, 9, 20, 30, 0));
			jr2.setRatePerHour(100);
			jr2.setClinicUser(testUser3);
			jr2.setFreelancer(ru1);
			jr2.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jr2.setActualStartDateTime(LocalDateTime.of(2023, 1, 9, 10, 30, 0));
			jr2.setActualEndDateTime(LocalDateTime.of(2023, 1, 9, 21, 30, 0));
			jobPostRepo.saveAndFlush(jr2);

			JobPost jr3 = new JobPost();
			jr3.setClinic(cEast);
			jr3.setTitle("Recommender accepted 3");
			jr3.setDescription("Raffles Medical Group in Lot1 looking for locum urgently!");
			jr3.setStartDateTime(LocalDateTime.of(2023, 1, 16, 10, 30, 0));
			jr3.setEndDateTime(LocalDateTime.of(2023, 1, 16, 20, 30, 0));
			jr3.setRatePerHour(100);
			jr3.setClinicUser(testUser3);
			jr3.setFreelancer(ru1);
			jr3.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jr3.setActualStartDateTime(LocalDateTime.of(2023, 1, 16, 10, 30, 0));
			jr3.setActualEndDateTime(LocalDateTime.of(2023, 1, 16, 21, 30, 0));
			jobPostRepo.saveAndFlush(jr3);

			JobPost jr4 = new JobPost();
			jr4.setClinic(cEast);
			jr4.setTitle("Recommender accepted 4");
			jr4.setDescription("Raffles Medical Group in Lot1 looking for locum urgently!");
			jr4.setStartDateTime(LocalDateTime.of(2023, 1, 23, 10, 30, 0));
			jr4.setEndDateTime(LocalDateTime.of(2023, 1, 23, 20, 30, 0));
			jr4.setRatePerHour(100);
			jr4.setClinicUser(testUser3);
			jr4.setFreelancer(ru1);
			jr4.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jr4.setActualStartDateTime(LocalDateTime.of(2023, 1, 23, 10, 30, 0));
			jr4.setActualEndDateTime(LocalDateTime.of(2023, 1, 23, 21, 30, 0));
			jobPostRepo.saveAndFlush(jr4);

			JobPost jr5 = new JobPost();
			jr5.setClinic(cCentral);
			jr5.setTitle("Recommender accepted 5");
			jr5.setDescription("Raffles Medical Group in Lot1 looking for locum urgently!");
			jr5.setStartDateTime(LocalDateTime.of(2023, 1, 1, 14, 30, 0));
			jr5.setEndDateTime(LocalDateTime.of(2023, 1, 1, 18, 30, 0));
			jr5.setRatePerHour(100);
			jr5.setClinicUser(testUser3);
			jr5.setFreelancer(ru1);
			jr5.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jr5.setActualStartDateTime(LocalDateTime.of(2023, 1, 1, 14, 30, 0));
			jr5.setActualEndDateTime(LocalDateTime.of(2023, 1, 1, 18, 30, 0));
			jobPostRepo.saveAndFlush(jr5);

			JobPost jr6 = new JobPost();
			jr6.setClinic(cCentral);
			jr6.setTitle("Recommender accepted 6");
			jr6.setDescription("Raffles Medical Group in Lot1 looking for locum urgently!");
			jr6.setStartDateTime(LocalDateTime.of(2023, 1, 8, 14, 30, 0));
			jr6.setEndDateTime(LocalDateTime.of(2023, 1, 8, 18, 30, 0));
			jr6.setRatePerHour(100);
			jr6.setClinicUser(testUser3);
			jr6.setFreelancer(ru1);
			jr6.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jr6.setActualStartDateTime(LocalDateTime.of(2023, 1, 8, 14, 30, 0));
			jr6.setActualEndDateTime(LocalDateTime.of(2023, 1, 8, 18, 30, 0));
			jobPostRepo.saveAndFlush(jr6);

			JobPost jr7 = new JobPost();
			jr7.setClinic(cCentral);
			jr7.setTitle("Recommender accepted 7");
			jr7.setDescription("Raffles Medical Group in Lot1 looking for locum urgently!");
			jr7.setStartDateTime(LocalDateTime.of(2023, 1, 15, 15, 30, 0));
			jr7.setEndDateTime(LocalDateTime.of(2023, 1, 15, 19, 30, 0));
			jr7.setRatePerHour(100);
			jr7.setClinicUser(testUser3);
			jr7.setFreelancer(ru1);
			jr7.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jr7.setActualStartDateTime(LocalDateTime.of(2023, 1, 15, 15, 30, 0));
			jr7.setActualEndDateTime(LocalDateTime.of(2023, 1, 15, 19, 30, 0));
			jobPostRepo.saveAndFlush(jr7);

			JobPost jr8 = new JobPost();
			jr8.setClinic(cEast);
			jr8.setTitle("Recommender accepted 8");
			jr8.setDescription("Raffles Medical Group in Lot1 looking for locum urgently!");
			jr8.setStartDateTime(LocalDateTime.of(2023, 1, 11, 19, 30, 0));
			jr8.setEndDateTime(LocalDateTime.of(2023, 1, 12, 1, 30, 0));
			jr8.setRatePerHour(100);
			jr8.setClinicUser(testUser3);
			jr8.setFreelancer(ru1);
			jr8.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jr8.setActualStartDateTime(LocalDateTime.of(2023, 1, 11, 19, 30, 0));
			jr8.setActualEndDateTime(LocalDateTime.of(2023, 1, 12, 1, 30, 0));
			jobPostRepo.saveAndFlush(jr8);

			JobPost jr9 = new JobPost();
			jr9.setClinic(cEast);
			jr9.setTitle("Recommender accepted 9");
			jr9.setDescription("Raffles Medical Group in Lot1 looking for locum urgently!");
			jr9.setStartDateTime(LocalDateTime.of(2023, 1, 18, 23, 30, 0));
			jr9.setEndDateTime(LocalDateTime.of(2023, 1, 19, 6, 30, 0));
			jr9.setRatePerHour(100);
			jr9.setClinicUser(testUser3);
			jr9.setFreelancer(ru1);
			jr9.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jr9.setActualStartDateTime(LocalDateTime.of(2023, 1, 18, 23, 30, 0));
			jr9.setActualEndDateTime(LocalDateTime.of(2023, 1, 19, 6, 30, 0));
			jobPostRepo.saveAndFlush(jr9);

			JobPost jr10 = new JobPost();
			jr10.setClinic(cEast);
			jr10.setTitle("Recommender accepted 10");
			jr10.setDescription("Raffles Medical Group in Lot1 looking for locum urgently!");
			jr10.setStartDateTime(LocalDateTime.of(2023, 1, 30, 10, 30, 0));
			jr10.setEndDateTime(LocalDateTime.of(2023, 1, 30, 14, 30, 0));
			jr10.setRatePerHour(100);
			jr10.setClinicUser(testUser3);
			jr10.setFreelancer(ru1);
			jr10.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
			jr10.setActualStartDateTime(LocalDateTime.of(2023, 1, 30, 10, 30, 0));
			jr10.setActualEndDateTime(LocalDateTime.of(2023, 1, 30, 14, 30, 0));
			jobPostRepo.saveAndFlush(jr10);

			// Open jobs

			JobPost jr11 = new JobPost();
			jr11.setClinic(cEast);
			jr11.setTitle("Recommender open 11");
			jr11.setDescription("Looking for locum occupational therapist");
			jr11.setStartDateTime(LocalDateTime.of(2023, 2, 13, 10, 30, 0));
			jr11.setEndDateTime(LocalDateTime.of(2023, 2, 13, 20, 30, 0));
			jr11.setStatus(JobStatus.OPEN);
			jr11.setClinicUser(testUser3);
			jr11.setRatePerHour(88.8);
			jobPostRepo.saveAndFlush(jr11);

			JobPost jr12 = new JobPost();
			jr12.setClinic(cEast);
			jr12.setTitle("Recommender open 12");
			jr12.setDescription("Looking for locum occupational therapist");
			jr12.setStartDateTime(LocalDateTime.of(2023, 2, 20, 12, 30, 0));
			jr12.setEndDateTime(LocalDateTime.of(2023, 2, 20, 22, 30, 0));
			jr12.setStatus(JobStatus.OPEN);
			jr12.setClinicUser(testUser3);
			jr12.setRatePerHour(88.8);
			jobPostRepo.saveAndFlush(jr12);

			JobPost jr13 = new JobPost();
			jr13.setClinic(cCentral);
			jr13.setTitle("Recommender open 13");
			jr13.setDescription("Looking for locum occupational therapist");
			jr13.setStartDateTime(LocalDateTime.of(2023, 2, 12, 10, 30, 0));
			jr13.setEndDateTime(LocalDateTime.of(2023, 2, 12, 14, 30, 0));
			jr13.setStatus(JobStatus.OPEN);
			jr13.setClinicUser(testUser3);
			jr13.setRatePerHour(88.8);
			jobPostRepo.saveAndFlush(jr13);

			JobPost jr14 = new JobPost();
			jr14.setClinic(cCentral);
			jr14.setTitle("Recommender open 14");
			jr14.setDescription("Looking for locum occupational therapist");
			jr14.setStartDateTime(LocalDateTime.of(2023, 2, 19, 14, 30, 0));
			jr14.setEndDateTime(LocalDateTime.of(2023, 2, 19, 18, 30, 0));
			jr14.setStatus(JobStatus.OPEN);
			jr14.setClinicUser(testUser3);
			jr14.setRatePerHour(88.8);
			jobPostRepo.saveAndFlush(jr14);

			JobPost jr15 = new JobPost();
			jr15.setClinic(cEast);
			jr15.setTitle("Recommender open 15");
			jr15.setDescription("Looking for locum occupational therapist");
			jr15.setStartDateTime(LocalDateTime.of(2023, 2, 15, 10, 30, 0));
			jr15.setEndDateTime(LocalDateTime.of(2023, 2, 15, 18, 30, 0));
			jr15.setStatus(JobStatus.OPEN);
			jr15.setClinicUser(testUser3);
			jr15.setRatePerHour(88.8);
			jobPostRepo.saveAndFlush(jr15);

			JobPost jr16 = new JobPost();
			jr16.setClinic(cEast);
			jr16.setTitle("Recommender open 16");
			jr16.setDescription("Looking for locum occupational therapist");
			jr16.setStartDateTime(LocalDateTime.of(2023, 2, 22, 23, 30, 0));
			jr16.setEndDateTime(LocalDateTime.of(2023, 2, 23, 7, 30, 0));
			jr16.setStatus(JobStatus.OPEN);
			jr16.setClinicUser(testUser3);
			jr16.setRatePerHour(88.8);
			jobPostRepo.saveAndFlush(jr16);

			JobPost jr17 = new JobPost();
			jr17.setClinic(cEast);
			jr17.setTitle("Recommender open 17");
			jr17.setDescription("Looking for locum occupational therapist");
			jr17.setStartDateTime(LocalDateTime.of(2023, 2, 20, 12, 30, 0));
			jr17.setEndDateTime(LocalDateTime.of(2023, 2, 20, 16, 30, 0));
			jr17.setStatus(JobStatus.OPEN);
			jr17.setClinicUser(testUser3);
			jr17.setRatePerHour(88.8);
			jobPostRepo.saveAndFlush(jr17);

			JobPost jr18 = new JobPost();
			jr18.setClinic(cWest);
			jr18.setTitle("Recommender open 18");
			jr18.setDescription("Looking for locum occupational therapist");
			jr18.setStartDateTime(LocalDateTime.of(2023, 2, 20, 21, 30, 0));
			jr18.setEndDateTime(LocalDateTime.of(2023, 2, 21, 8, 30, 0));
			jr18.setStatus(JobStatus.OPEN);
			jr18.setClinicUser(testUser3);
			jr18.setRatePerHour(88.8);
			jobPostRepo.saveAndFlush(jr18);

			JobPost jr19 = new JobPost();
			jr19.setClinic(cNorth);
			jr19.setTitle("Recommender open 19");
			jr19.setDescription("Looking for locum occupational therapist");
			jr19.setStartDateTime(LocalDateTime.of(2023, 2, 19, 10, 30, 0));
			jr19.setEndDateTime(LocalDateTime.of(2023, 2, 19, 21, 30, 0));
			jr19.setStatus(JobStatus.OPEN);
			jr19.setClinicUser(testUser3);
			jr19.setRatePerHour(88.8);
			jobPostRepo.saveAndFlush(jr19);

			JobPost jr20 = new JobPost();
			jr20.setClinic(cNorth);
			jr20.setTitle("Recommender open 20");
			jr20.setDescription("Looking for locum occupational therapist");
			jr20.setStartDateTime(LocalDateTime.of(2023, 2, 22, 12, 30, 0));
			jr20.setEndDateTime(LocalDateTime.of(2023, 2, 22, 15, 30, 0));
			jr20.setStatus(JobStatus.OPEN);
			jr20.setClinicUser(testUser3);
			jr20.setRatePerHour(88.8);
			jobPostRepo.saveAndFlush(jr20);

		};
	}
}
