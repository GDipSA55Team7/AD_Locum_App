package sg.edu.nus.iss.AD_Locum_Doctors.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import jakarta.persistence.CascadeType;

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

	@JsonIgnore
	@ManyToOne
	private Organization organization;

	@JsonIgnore
	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
	private List<JobPost> jobPosts;
}
