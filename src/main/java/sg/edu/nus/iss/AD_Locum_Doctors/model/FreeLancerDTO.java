package sg.edu.nus.iss.AD_Locum_Doctors.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FreeLancerDTO {

	    private String id;

	    private String username;

	    private String password;

	    private String name;

	    private String email;

	    private String contact;

	    private String medicalLicenseNo;
	    
	    private String errorsFieldString;
	    
	    private String deviceToken;
}
