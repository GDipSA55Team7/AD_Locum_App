package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Organization {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String UEN;

	private String HCI;

	private String address;

	private String contact;

	@OneToMany(mappedBy = "organization")
	private List<User> users;

	@OneToMany(mappedBy = "organization", cascade=CascadeType.PERSIST)
	private List<Clinic> clinics = new ArrayList<>();

}
