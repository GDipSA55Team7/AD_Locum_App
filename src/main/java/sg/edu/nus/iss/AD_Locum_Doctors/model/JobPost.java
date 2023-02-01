package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class JobPost {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title = "";

	private String description = "";

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

	private JobStatus status = JobStatus.OPEN;

	@JsonIgnore
	@ManyToOne
	private User clinicUser;

	@JsonIgnore
	@ManyToOne
	private User freelancer;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "clinic_id")
	private Clinic clinic;

	@OneToMany(mappedBy = "jobPost")
	private List<JobAdditionalRemarks> jobAdditionalRemarks = new ArrayList<>();

	@OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL)
	private List<AdditionalFeeDetails> additionalFeeDetails = new ArrayList<>();

	private String additionalRemarks = "";

	private String paymentReferenceNumber = "";

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

	public double computeTotalRate() {
		Long minutes = ChronoUnit.MINUTES.between(startDateTime, endDateTime);
		Double convertToHour = ((double) minutes) / 60;
		Double totalRate = ratePerHour * convertToHour;
		return totalRate;
	}
}
