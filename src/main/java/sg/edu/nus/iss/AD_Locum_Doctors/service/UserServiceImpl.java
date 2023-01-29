package sg.edu.nus.iss.AD_Locum_Doctors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.FreeLancerDTO;
import java.util.List;

import jakarta.transaction.Transactional;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;

    @Override
    public void saveUser(User user) {
        userRepo.saveAndFlush(user);

    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepo.findById(id).get();
    }

    @Transactional
    @Override
    public User authenticate(String username, String pwd) {
        return userRepo.findAll().stream().filter(u -> u.getPassword().equals(pwd) && u.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    @Override
    public void deleteUser(User user) {
       userRepo.delete(user);
    }

	@Override
	public FreeLancerDTO createFreeLancer(FreeLancerDTO freeLancerDTO) {
		//username,email,medicalLicenseNo must be unique
		//get all registered users
		String errorsFieldString ="";
		for(User currentUser : userRepo.findAll()) {
			  if(currentUser.getUsername().equalsIgnoreCase(freeLancerDTO.getUsername())) {
				  errorsFieldString += "username";
			  }
			  if(currentUser.getEmail().equalsIgnoreCase(freeLancerDTO.getEmail())) {
				  errorsFieldString += "email";
			  }
			  //validate against official medical system?
			  if(currentUser.getMedicalLicenseNo().equalsIgnoreCase(freeLancerDTO.getMedicalLicenseNo())) {
				  errorsFieldString += "medical";
			  }
		  }

		System.out.println("errors = " + errorsFieldString);
		//not unique username,email,medicalLicense
		if(!errorsFieldString.isEmpty()) {
			freeLancerDTO.setErrorsFieldString(errorsFieldString);
			return freeLancerDTO;
		}

		User newFreeLancer = new User();
		newFreeLancer.setName(freeLancerDTO.getName());
		newFreeLancer.setUsername(freeLancerDTO.getUsername());
		newFreeLancer.setPassword(freeLancerDTO.getPassword());
		newFreeLancer.setContact(freeLancerDTO.getContact());
		newFreeLancer.setEmail(freeLancerDTO.getEmail());
		newFreeLancer.setMedicalLicenseNo(freeLancerDTO.getMedicalLicenseNo());
		//set role
		System.out.println("before" + freeLancerDTO);
		userRepo.saveAndFlush(newFreeLancer);
		freeLancerDTO.setId(newFreeLancer.getId().toString());
		System.out.println("after" + freeLancerDTO);
		return freeLancerDTO;
	}

	@Override
	public FreeLancerDTO loginFreeLancer(FreeLancerDTO freeLancerDTO) {
		User existingUser = userRepo.findUserByUsernameAndPassword(freeLancerDTO.getUsername(),
				freeLancerDTO.getPassword());

		FreeLancerDTO existingFreeLancerDTO = new FreeLancerDTO();
		if(existingUser != null) {
			existingFreeLancerDTO.setId(existingUser.getId().toString());
			existingFreeLancerDTO.setName(existingUser.getName());
			existingFreeLancerDTO.setUsername(existingUser.getUsername());
			existingFreeLancerDTO.setPassword(existingUser.getPassword());
			existingFreeLancerDTO.setContact(existingUser.getContact());
			existingFreeLancerDTO.setEmail(existingUser.getEmail());
			existingFreeLancerDTO.setMedicalLicenseNo(existingUser.getMedicalLicenseNo());
			existingFreeLancerDTO.setId(existingUser.getId().toString());
			return existingFreeLancerDTO;
		}
		return null;
	}

	@Override
	public Boolean updateFreeLancer(FreeLancerDTO freeLancerDTO) {
		User existingUser = userRepo.findById(Long.valueOf(freeLancerDTO.getId())).orElse(null);
		if(existingUser != null) {
			if(!freeLancerDTO.getContact().equals(existingUser.getContact())) {
				existingUser.setContact(freeLancerDTO.getContact());
			}
			if(!freeLancerDTO.getName().equals(existingUser.getName())) {
                existingUser.setName(freeLancerDTO.getName());
            }
			if(!freeLancerDTO.getEmail().equals(existingUser.getEmail())) {
                existingUser.setEmail(freeLancerDTO.getEmail());
            }
			if(!freeLancerDTO.getMedicalLicenseNo().equals(existingUser.getMedicalLicenseNo())) {
                existingUser.setMedicalLicenseNo(freeLancerDTO.getMedicalLicenseNo());
            }
			if(!freeLancerDTO.getUsername().equals(existingUser.getUsername())) {
                existingUser.setUsername(freeLancerDTO.getUsername());
            }
			if(!freeLancerDTO.getPassword().equals(existingUser.getPassword())) {
                existingUser.setPassword(freeLancerDTO.getPassword());
            }
			userRepo.saveAndFlush(existingUser);
			return true;
		}
		return false;
	}



}
