package sg.edu.nus.iss.AD_Locum_Doctors.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

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
    @OneToOne(mappedBy = "firebaseDeviceToken",cascade = CascadeType.ALL)
    private User user;

    public FirebaseDeviceToken() {
    }

    public FirebaseDeviceToken(String token,Boolean isLoggedIntoMobileApp,String userName ) {
    		this.token = token;
			this.isLoggedIntoMobileApp = isLoggedIntoMobileApp;
			this.userName =userName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, isLoggedIntoMobileApp, userName);
    }
}
