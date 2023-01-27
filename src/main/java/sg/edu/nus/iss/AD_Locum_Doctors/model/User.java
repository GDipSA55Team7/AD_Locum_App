package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String password;

	private String name;

	private String email;

	private String contact;

	private String medicalLicenseNo;

	@ManyToOne
	private Organization organization;

	@ManyToOne
	private Role role;

	@OneToMany(mappedBy = "clinicUser")
	private List<JobPost> jobPosts = new ArrayList<>();

	@OneToMany(mappedBy = "freelancer")
	private List<JobPost> jobApplications = new ArrayList<>();
}
