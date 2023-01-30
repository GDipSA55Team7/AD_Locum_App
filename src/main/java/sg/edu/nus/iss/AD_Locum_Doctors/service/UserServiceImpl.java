package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public User authenticate(String username, String pwd) {
        return userRepo.findAll().stream().filter(u -> u.getPassword().equals(pwd) && u.getUsername().equals(username))
                .findFirst().get();
    }

    @Override
    public void deleteUser(User user) {
        userRepo.delete(user);
    }
}
