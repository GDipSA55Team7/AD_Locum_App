package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Organization;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.service.OrganizationService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.UserService;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    HttpSession session;

    List<Organization> organizations= new ArrayList<>();

    User user;

    private void loadReference(){
        organizations= new ArrayList<>();
        user= (User) session.getAttribute("user");
        Organization organization= organizationService.findById(user.getOrganization().getId());
        organizations.add(organization);

    }

    @GetMapping("/UserList")
    public String userListPage(Model model) {
        loadReference();
        List<User> users = userService.findByOrganizationId(user.getOrganization().getId());
        model.addAttribute("userList", users);
        return "user-list";
    }

    @GetMapping("/register/addUserForm")
    public String addUserForm(Model model) {
        loadReference();
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("organizationList", organizations);
        return "addUserForm";
    }

    @PostMapping("/saveUser")
    public String saveClinic(@ModelAttribute("user") @Valid User user, BindingResult result,
            Model model) {
        userService.saveUser(user);
        return "redirect:/UserList";
    }

    @GetMapping("/viewUser/{id}")
    public String viewClinic(Model model, @PathVariable(value = "id") Long id) {
        loadReference();
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("organizationList", organizations);
        return "editUserForm";
    }

    @PostMapping("/editUser")
    public String editUser(@ModelAttribute("user") @Valid User user, BindingResult result,
            Model model) {
        userService.saveUser(user);
        return "redirect:/UserList";
    }

    @GetMapping("/deleteuser/{id}")
    public String deleteClinic(Model model, @PathVariable(value = "id") Long id) {
        User user = userService.findById(id);
        userService.deleteUser(user);
        return "redirect:/UserList";
    }

}
