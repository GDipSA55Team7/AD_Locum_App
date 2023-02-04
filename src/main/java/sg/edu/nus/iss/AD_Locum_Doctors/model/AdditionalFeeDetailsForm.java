package sg.edu.nus.iss.AD_Locum_Doctors.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdditionalFeeDetailsForm {
    private String description;

    private double additionalFeesAmount;

    private Long jobPostId;
}