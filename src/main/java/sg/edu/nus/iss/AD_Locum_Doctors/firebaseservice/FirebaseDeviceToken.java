package sg.edu.nus.iss.AD_Locum_Doctors.firebaseservice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class FirebaseDeviceToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    public FirebaseDeviceToken() {
    }

    public FirebaseDeviceToken(String token) {
        this.token = token;
    }

}