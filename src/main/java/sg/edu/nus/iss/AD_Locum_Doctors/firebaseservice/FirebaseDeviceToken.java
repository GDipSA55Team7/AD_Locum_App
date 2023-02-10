package sg.edu.nus.iss.AD_Locum_Doctors.firebaseservice;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;

@Entity
@Data
public class FirebaseDeviceToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;
    
    
    //default should be true
    private Boolean isLoggedIntoMobileApp;
    
    @JsonIgnore
    @OneToOne(mappedBy = "firebaseDeviceToken")
    private User user;

    public FirebaseDeviceToken() {
    }



    public FirebaseDeviceToken(String token,Boolean isLoggedIntoMobileApp ) {
    		this.token = token;
			this.isLoggedIntoMobileApp = isLoggedIntoMobileApp;
    	
    }

}
