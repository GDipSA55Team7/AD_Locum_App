package sg.edu.nus.iss.AD_Locum_Doctors.service;

import org.springframework.stereotype.Service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.Role;

@Service
public interface RoleService {
    
    Role findByName(String name);
}
