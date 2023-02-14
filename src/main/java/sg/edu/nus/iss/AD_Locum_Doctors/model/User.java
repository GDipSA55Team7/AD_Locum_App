package sg.edu.nus.iss.AD_Locum_Doctors.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@SecondaryTable(name = "user_password", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(table = "user_password")
    private String password;

    private String name;

    private String email;

    private String contact;

    private String medicalLicenseNo;

    @JsonIgnore
    @ManyToOne
    private Organization organization;

    @JsonIgnore
    @ManyToOne
    private Role role;

    private Boolean active = true;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dateRegistered = LocalDateTime.now();

    @JsonIgnore
    @OneToMany(mappedBy = "clinicUser", cascade = CascadeType.ALL)
    private List<JobPost> jobPosts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "freelancer", cascade = CascadeType.ALL)
    private List<JobPost> jobApplications = new ArrayList<>();
    
    @JsonIgnore
    @OneToOne(optional = true)
    @JoinColumn(name = "firebase_device_token_id")
    private FirebaseDeviceToken firebaseDeviceToken;
}
