package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
	
	private String contact_person_name;

	private String contact;
	
	private Boolean active = true;

	@JsonIgnore
	@OneToMany(mappedBy = "organization")
	private List<User> users;

	@OneToMany(mappedBy = "organization", cascade = CascadeType.PERSIST)
	private List<Clinic> clinics = new ArrayList<>();

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime dateRegistered = LocalDateTime.now();

	public String getDateRegisteredString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm a");
		return dateRegistered.format(formatter);
	}
}
