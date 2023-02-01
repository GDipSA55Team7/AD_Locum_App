package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class JobAdditionalRemarks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private RemarksCategory category;

	private String remarks = "";

	private LocalDate date;

	@JsonIgnore
	@ManyToOne
	private JobPost jobPost;

	@OneToOne
	private User user;

}
