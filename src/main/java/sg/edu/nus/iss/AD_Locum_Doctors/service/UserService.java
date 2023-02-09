package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import org.springframework.stereotype.Service;
import sg.edu.nus.iss.AD_Locum_Doctors.model.FreeLancerDTO;

import sg.edu.nus.iss.AD_Locum_Doctors.model.User;

@Service
public interface UserService {

    void saveUser(User user);

    List<User> findAll();

    User findById(Long id);

    User authenticate(String username, String pwd);

    void deleteUser(User user);
    
    User deactivateUser(User user);

    User reactivateUser(User user);

    List<User> findByOrganizationId(Long id);

	FreeLancerDTO createFreeLancer(FreeLancerDTO freeLancerDTO);
	
	FreeLancerDTO loginFreeLancer(FreeLancerDTO freeLancerDTO);
	
	FreeLancerDTO updateFreeLancer(FreeLancerDTO freeLancerDTO);
	
	String checkIfFieldIsUnique(List<User> checkAgainstUsers,String fieldName,String fieldValue);
	
}
