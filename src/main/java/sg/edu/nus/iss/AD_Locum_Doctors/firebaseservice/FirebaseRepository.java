package sg.edu.nus.iss.AD_Locum_Doctors.firebaseservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirebaseRepository extends JpaRepository<FirebaseDeviceToken,Integer> {

}
