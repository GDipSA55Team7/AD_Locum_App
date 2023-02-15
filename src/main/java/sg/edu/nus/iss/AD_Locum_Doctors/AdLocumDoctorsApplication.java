package sg.edu.nus.iss.AD_Locum_Doctors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import sg.edu.nus.iss.AD_Locum_Doctors.repository.AdditionalFeeDetailsRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.ClinicRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.FirebaseRepository;
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
			ClinicRepository clinicRepo,
			JobPostRepository jobPostRepo,
			OrganizationRepository organizationRepo,
			RoleRepository roleRepo,
			UserRepository userRepo,
			AdditionalFeeDetailsRepository additionalFeeDetailsRepository,
			JobAdditionalRemarksRepository jobRemarksRepo,
			FirebaseRepository firebaseRepository,
			Environment env) {

		return args -> {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection con = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
					env.getProperty("spring.datasource.username"),
					env.getProperty("spring.datasource.password"));
			System.out.println("Connection established......");
			ScriptRunner sr = new ScriptRunner(con);
			Reader reader = new BufferedReader(new FileReader("src/main/resources/sql/data.sql"));
			sr.runScript(reader);
		};
	}
}
