package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobPostForm {
	private String description;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDateTime startDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDateTime endDate;

	private String startTimeHour;
	private String startTimeMin;
	private String startTimeAmPm;

	private String endTimeHour;
	private String endTimeMin;
	private String endTimeAmPm;

	private double ratePerHour;

	private Long clinicUserId;

	private Long clinicId;
}