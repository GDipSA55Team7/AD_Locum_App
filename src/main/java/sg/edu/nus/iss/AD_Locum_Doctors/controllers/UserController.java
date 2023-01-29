package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.service.OrganizationService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.UserService;

@Controller
public class UserController {
    
    @Autowired
    UserService uService;

    @Autowired
    OrganizationService oService;

    @GetMapping("/UserList")
    public String userListPage(Model model){
        List<User> users = uService.findAll();
        model.addAttribute("userList", users);
        return "user-list";
    }

    @GetMapping("/addUserForm")
    public String addUserForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("organizationList", oService.getAllOrganizations());
        return "addUserForm";
    }

    @PostMapping("/saveUser")
    public String saveClinic(@ModelAttribute("user") @Valid User user, BindingResult result,
   Model model){
    uService.saveUser(user);
    return "redirect:/UserList";
   }

   @GetMapping("/viewUser/{id}")
   public String viewClinic(Model model,@PathVariable(value = "id") Long id){
    model.addAttribute("user", uService.findById(id));
    model.addAttribute("organizationList", oService.getAllOrganizations());
    return "editUserForm";
   }

   @PostMapping("/editUser")
   public String editUser(@ModelAttribute("user") @Valid User user, BindingResult result,
   Model model){
    uService.saveUser(user);
    return "redirect:/UserList";
   }

}
