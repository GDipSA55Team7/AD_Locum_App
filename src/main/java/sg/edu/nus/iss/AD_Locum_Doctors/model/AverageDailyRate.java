package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class AverageDailyRate {

	@Id
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	private Double average_daily_rate_weekday;

	private Double average_daily_rate_weekend;

	private Double weekday_28_MA;

	private Double weekend_28_MA;

	private Double weekday_14_MA;

	private Double weekend_14_MA;
}
