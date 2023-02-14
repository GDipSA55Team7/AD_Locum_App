package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobPostEventDTO {
	private String title;

	private LocalDateTime start;

	private LocalDateTime end;

	private String color = "";

	private String url = "";

	private String display = "block";
}
