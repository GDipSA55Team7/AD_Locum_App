package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime startDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime endDateTime;

	private double ratePerHour;

	private double totalRate;

	private JobStatus status = JobStatus.OPEN;

	@ManyToOne
	private User clinicUser;

	@ManyToOne
	private User freelancer;

	@ManyToOne
	private Clinic clinic;

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
	
	public double computeTotalRate() {
		Long minutes = ChronoUnit.MINUTES.between(startDateTime, endDateTime);
		Double convertToHour = ((double) minutes) / 60;
		Double totalRate = ratePerHour * convertToHour;
		return totalRate;
	}
}
