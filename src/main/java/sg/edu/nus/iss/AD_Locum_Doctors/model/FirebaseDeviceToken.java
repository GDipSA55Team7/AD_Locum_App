package sg.edu.nus.iss.AD_Locum_Doctors.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class FirebaseDeviceToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;
    
    private Boolean isLoggedIntoMobileApp;
    
    private String userName;
    
    @JsonIgnore
    @OneToOne(mappedBy = "firebaseDeviceToken")
    private User user;

    public FirebaseDeviceToken() {
    }

    public FirebaseDeviceToken(String token,Boolean isLoggedIntoMobileApp,String userName ) {
    		this.token = token;
			this.isLoggedIntoMobileApp = isLoggedIntoMobileApp;
			this.userName =userName;
    }
}
