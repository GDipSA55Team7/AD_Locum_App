package sg.edu.nus.iss.AD_Locum_Doctors.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class changePasswordForm {
    
    @NotBlank(message="Must not be blank")
    @Size(min=6, message="Password must be at least 6 characters")
    private String newPassword;

    @NotBlank(message="Must not be blank")
    @Size(min=6, message="Password must be at least 6 characters")
    private String confirmNewPassword;

    private Long userID;
}
