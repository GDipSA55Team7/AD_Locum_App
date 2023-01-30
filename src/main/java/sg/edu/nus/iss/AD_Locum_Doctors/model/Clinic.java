package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Clinic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String address;

	private String postalCode;

	private String HCICode;

	private String contact;

	@ManyToOne
	private Organization organization;

	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<JobPost> jobPosts;
}
