package sg.edu.nus.iss.AD_Locum_Doctors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.FreeLancerDTO;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Role;

import java.util.List;

import jakarta.transaction.Transactional;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.RoleRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;
    
    @Autowired
    RoleRepository roleRepo;

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
		// Check against all Registered users. Username,Email,medicalLicenseNo must be unique 
		String errorsFieldString ="";
		List<User> allRegisteredUsersList = userRepo.findAll();
		if(!allRegisteredUsersList.isEmpty()) {
			for(User currentUser : allRegisteredUsersList ) {
				  if(currentUser.getUsername() != null &&  currentUser.getUsername().equals(freeLancerDTO.getUsername())) {
					  errorsFieldString += "username";
				  }
				  if(currentUser.getEmail() != null &&  currentUser.getEmail().equals(freeLancerDTO.getEmail())) {
					  errorsFieldString += "email";
				  }
				  //Not validating against any official/external medicalLicenseNumber Record
				  if(currentUser.getMedicalLicenseNo() != null  && currentUser.getMedicalLicenseNo().equals(freeLancerDTO.getMedicalLicenseNo())) {
					  errorsFieldString += "medical";
				  }
			  }
		}
//        if(errorsFieldString.isEmpty()) {
//    		System.out.println("Username,Email,medicalLicenseNo are unique" );
//        }
//        else {
//        	System.out.println("NonUnique Fields" + errorsFieldString);
//        }
		
		//Username/email/medicalLicense is not unique
		if(!errorsFieldString.isEmpty()) {
			freeLancerDTO.setErrorsFieldString(errorsFieldString);
			//System.out.println("Returning errString to controller" + errorsFieldString );
			return freeLancerDTO;
		}

		//proceed to register new FreeLancerUser
		
		User newFreeLancerUser = new User();
		newFreeLancerUser.setName(freeLancerDTO.getName());
		newFreeLancerUser.setUsername(freeLancerDTO.getUsername());
		newFreeLancerUser.setPassword(freeLancerDTO.getPassword());
		newFreeLancerUser.setContact(freeLancerDTO.getContact());
		newFreeLancerUser.setEmail(freeLancerDTO.getEmail());
		newFreeLancerUser.setMedicalLicenseNo(freeLancerDTO.getMedicalLicenseNo());
		//set role
		Role locumDoctorRole =  roleRepo.findByName("Locum_Doctor");
		newFreeLancerUser.setRole(locumDoctorRole);

		userRepo.saveAndFlush(newFreeLancerUser);
		
		//return newly created UserId DTO to client with 
		freeLancerDTO.setId(newFreeLancerUser.getId().toString());
		//System.out.println("DTO after setting newly created UserId : " + freeLancerDTO);
		
		return freeLancerDTO;
	}

	@Override
	public FreeLancerDTO loginFreeLancer(FreeLancerDTO freeLancerDTO) {
		User existingUser = userRepo.findUserByUsernameAndPassword(freeLancerDTO.getUsername(),
				freeLancerDTO.getPassword());
	
		//Found Registered User
		if(existingUser != null && existingUser.getRole().getName().equals("Locum_Doctor")) {
			freeLancerDTO.setId(existingUser.getId().toString());   //tag id
			freeLancerDTO.setName(existingUser.getName());
			freeLancerDTO.setUsername(existingUser.getUsername());
			freeLancerDTO.setPassword(existingUser.getPassword());
			freeLancerDTO.setContact(existingUser.getContact());
			freeLancerDTO.setEmail(existingUser.getEmail());
			freeLancerDTO.setMedicalLicenseNo(existingUser.getMedicalLicenseNo());
			return freeLancerDTO;
		}
		return null;
	}

	@Override
	public FreeLancerDTO updateFreeLancer(FreeLancerDTO freeLancerDTO) {
		User existingUser = userRepo.findById(Long.valueOf(freeLancerDTO.getId())).orElse(null);
		if(existingUser != null && freeLancerDTO != null) {
			
			List<User> checkAgainstUsers = userRepo.findAll().stream().filter(u->u.getId() != existingUser.getId()).toList();
			
			if(!freeLancerDTO.getContact().equals(existingUser.getContact())) {
				existingUser.setContact(freeLancerDTO.getContact());
			}
			if(!freeLancerDTO.getName().equals(existingUser.getName())) {
                existingUser.setName(freeLancerDTO.getName());
            }
			if(!freeLancerDTO.getPassword().equals(existingUser.getPassword())) {
                existingUser.setPassword(freeLancerDTO.getPassword());
            }
			
			if(!checkAgainstUsers.isEmpty()) {
				System.out.println("listcheckusersNotEmpty");
				String errString = "";
		
				if(!freeLancerDTO.getEmail().equals(existingUser.getEmail())) {
					String result =  checkIfFieldIsUnique(checkAgainstUsers,"email", freeLancerDTO.getEmail());
					System.out.println( "Result" + result);
					if(!result.contains("email")) {
						existingUser.setEmail(freeLancerDTO.getEmail());
						System.out.println("set email true");
					}
					else {
						errString += result;
					}
	            }
				if(!freeLancerDTO.getMedicalLicenseNo().equals(existingUser.getMedicalLicenseNo())) {
					System.out.println("medical");
					String result =  checkIfFieldIsUnique(checkAgainstUsers,"medical", freeLancerDTO.getMedicalLicenseNo());
					System.out.println( "Result" + result);
					if(!result.contains("medical")) {
						existingUser.setMedicalLicenseNo(freeLancerDTO.getMedicalLicenseNo());
						System.out.println("set medicalno true");
					}
					else {
						errString += result;
					}
					System.out.println("medicalmethod complete");
	            }
				userRepo.saveAndFlush(existingUser);
				freeLancerDTO.setErrorsFieldString(errString);
				return freeLancerDTO;
			}
		}
		return null;
	}

	@Override
	public String checkIfFieldIsUnique(List<User> checkAgainstUsers,String fieldName, String fieldValue) {
		String errStr = "err";
		try {
			for(User user : checkAgainstUsers) {
				if(fieldName.equals("email")) {
					if( user.getEmail().equals(fieldValue)) {
						errStr += fieldName;
						return errStr;
					}
					
				}
				if(fieldName.equals("medical")) {
					System.out.println("inside submethod medical 1");
					if( user.getMedicalLicenseNo() != null &&  user.getMedicalLicenseNo().equals(fieldValue)) {
						errStr += fieldName;
						return errStr;
					}
				}
			}
			System.out.println("output : " +  errStr );
			return errStr;
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		return errStr;
	}
}
