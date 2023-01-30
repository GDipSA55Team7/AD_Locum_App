package sg.edu.nus.iss.AD_Locum_Doctors.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class AdditionalFeeDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private double additionalFeesAmount;

    @ManyToOne
    private JobPost jobPost;
}
