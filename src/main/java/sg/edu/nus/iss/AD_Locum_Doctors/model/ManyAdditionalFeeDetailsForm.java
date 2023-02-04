package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ManyAdditionalFeeDetailsForm {

    private List<AdditionalFeeDetails> additionalFeeDetails;

    private String jobPostId;
}