package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ManyAdditionalFeeDetailsForm {

    private List<AdditionalFeeDetails> additionalFeeDetails;

    private String jobPostId;
}