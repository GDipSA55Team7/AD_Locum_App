package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.service.UserService;

@Controller
public class UserController {
    
    @Autowired
    UserService uService;

    @GetMapping("/UserList")
    public String userListPage(Model model){
        List<User> users = uService.findAll();
        model.addAttribute("userList", users);
        return "user-list";
    }

    
}
