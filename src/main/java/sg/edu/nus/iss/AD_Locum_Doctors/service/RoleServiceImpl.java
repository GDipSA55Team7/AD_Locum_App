package sg.edu.nus.iss.AD_Locum_Doctors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Role;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.RoleRepository;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepo;

    @Override
    public Role findByName(String name){
        return roleRepo.findByName(name).get();
    }

}
