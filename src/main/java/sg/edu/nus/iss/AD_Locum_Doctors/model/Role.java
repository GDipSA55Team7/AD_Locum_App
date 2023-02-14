package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@JsonIgnore
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private List<User> users;

	public Role(String name) {
		this.name = name;
	}
}
