package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository uRepo;

    @Override
    public void saveUser(User user) {
        uRepo.saveAndFlush(user);
        
    }

    @Override
    public List<User> findAll() {
        return uRepo.findAll();
    }


    
}
