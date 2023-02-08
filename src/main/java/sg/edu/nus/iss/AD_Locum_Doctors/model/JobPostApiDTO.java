package sg.edu.nus.iss.AD_Locum_Doctors.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobPostApiDTO {

    private long id;

    private String description;

    private String title;

    private String startDateTime;

    private String endDateTime;

    private double ratePerHour;

    private double totalRate;
    
    private String additionalFeeListString;

    private JobStatus status;

    private User clinicUser;

    private User freelancer;

    private Clinic clinic;

    private double similarityScore;

    private String paymentDate;
    
    private String paymentRefNo;
}
