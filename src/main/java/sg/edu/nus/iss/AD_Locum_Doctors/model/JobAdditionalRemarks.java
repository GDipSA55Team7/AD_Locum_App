package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime dateTime;

	@JsonIgnore
	@ManyToOne
	private JobPost jobPost;

	@OneToOne
	private User user;

	public JobAdditionalRemarks(RemarksCategory category, String remarks, LocalDateTime dateTime, JobPost jobPost,
			User user) {
		this.category = category;
		this.remarks = remarks;
		this.dateTime = dateTime;
		this.jobPost = jobPost;
		this.user = user;
	}

	public String getDateTimeString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm a");
		return dateTime.format(formatter);
	}

	public String getTimeOnlyString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm a");
		return  LocalTime.of(dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond()).format(formatter);
	}

	public LocalDate getDateOnly() {
		return LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());
	}

	public Object findByJobPostId(Long id2) {
		return null;
	}
}
