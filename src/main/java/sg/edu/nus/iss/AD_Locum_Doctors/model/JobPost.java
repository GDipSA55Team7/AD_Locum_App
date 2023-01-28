package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.time.LocalDate;
import java.time.LocalTime;

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
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate startDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate endDate;

	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;

	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;

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

	public String getStartTimeString() {
		String appendAMPM = "";
		if (startTime.isBefore(LocalTime.of(12, 0))) {
			appendAMPM = " AM";
		} else {
			appendAMPM = " PM";
		}
		return startTime + appendAMPM;
	}

	public String getEndTimeString() {
		String appendAMPM = "";
		if (endTime.isBefore(LocalTime.of(12, 0))) {
			appendAMPM = " AM";
		} else {
			appendAMPM = " PM";
		}
		return endTime + appendAMPM;
	}
}
