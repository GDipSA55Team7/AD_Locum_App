package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class JobPost {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime startDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime endDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime actualStartDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime actualEndDateTime;

	private double ratePerHour;

	//private double totalRate;

	private JobStatus status = JobStatus.OPEN;

	@ManyToOne
	private User clinicUser;

	@ManyToOne
	private User freelancer;

	@ManyToOne
	@JoinColumn(name="clinic_id")
	private Clinic clinic;

	@OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL)
	private List<AdditionalFeeDetails> additionalFeeDetails;

	private String additionalRemarks;

	private String paymentReferenceNumber;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate paymentDate;

	public String getRatePerHourString() {
		return "$" + String.format("%.2f", ratePerHour) + "/h";
	}

	public String getStartDateTimeString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm a");
		return startDateTime.format(formatter);
	}

	public String getEndDateTimeString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm a");
		return endDateTime.format(formatter);
	}
}
