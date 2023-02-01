package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.Role;

@Service
public interface RoleService {
    
    List<Role> findAll();
    
    Role findByName(String name);
}
