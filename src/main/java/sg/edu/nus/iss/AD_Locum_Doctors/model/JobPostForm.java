package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobPostForm {
	private String title;

	private String description;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate startDate;

	private String startTimeHour;
	private String startTimeMin;
	private String startTimeAmPm;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate endDate;

	private String endTimeHour;
	private String endTimeMin;
	private String endTimeAmPm;

	private double ratePerHour;

	private Long clinicUserId;

	private Long clinicId;

	public LocalDateTime getStartDateTime() {
		int startTimeHourInt = 0;
		int startTimeMinInt = 0;
		try {
			startTimeHourInt = Integer.parseInt(startTimeHour);
			startTimeMinInt = Integer.parseInt(startTimeMin);
			if (startTimeAmPm.equals("PM") && startTimeHourInt != 12) {
				startTimeHourInt += 12;
			}
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}

		return LocalDateTime.of(
				startDate.getYear(),
				startDate.getMonthValue(),
				startDate.getDayOfMonth(),
				startTimeHourInt,
				startTimeMinInt,
				0);
	}

	public LocalDateTime getEndDateTime() {
		int endTimeHourInt = 0;
		int endTimeMinInt = 0;
		try {
			endTimeHourInt = Integer.parseInt(endTimeHour);
			endTimeMinInt = Integer.parseInt(endTimeMin);
			if (endTimeAmPm.equals("PM") && endTimeHourInt != 12) {
				endTimeHourInt += 12;
			}
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		return LocalDateTime.of(
				endDate.getYear(),
				endDate.getMonthValue(),
				endDate.getDayOfMonth(),
				endTimeHourInt,
				endTimeMinInt,
				0);
	}
}