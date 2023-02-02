package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class ResetPasswordChecker {

    @Id
	private String id = UUID.randomUUID().toString();

    private Long userID;

}
