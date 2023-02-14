package sg.edu.nus.iss.AD_Locum_Doctors.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String name;

    private String email;

    private String contact;

    private String medicalLicenseNo;

    private Organization organization;

    private Role role;

    private Boolean active = true;

}
