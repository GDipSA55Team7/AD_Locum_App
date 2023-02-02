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
import sg.edu.nus.iss.AD_Locum_Doctors.model.Role;
import sg.edu.nus.iss.AD_Locum_Doctors.service.OrganizationService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.UserService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.RoleService;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    RoleService roleService;

    @Autowired
    HttpSession session;

    List<Organization> organizations= new ArrayList<>();

    List<Role> roles= new ArrayList<>();

    User user;

    private void loadReference(){
        organizations= new ArrayList<>();
        user= (User) session.getAttribute("user");
        roles = roleService.findAllRoles();
        Organization userOrganization = user.getOrganization();
        if (userOrganization != null) {
            Organization organization= organizationService.findById(user.getOrganization().getId());
            organizations.add(organization);
        }
    }

    @GetMapping("/UserList")
    public String userListPage(Model model) {
        loadReference();
        Long userRoleID = user.getRole().getId();
        System.out.print(userRoleID);
        if (userRoleID == 5){
            List<User> users = userService.findAll();
            model.addAttribute("userList", users);
            return "admin_user_list_view";
        }
        else {
            List<User> users = userService.findByOrganizationId(user.getOrganization().getId());
            model.addAttribute("userList", users);
            return "user-list";
        }
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
        Long userRoleID = user.getRole().getId();
        if (userRoleID != 5){
            model.addAttribute("user", userService.findById(id));
            model.addAttribute("organizationList", organizations);
            return "editUserForm";
        }
        else{
            model.addAttribute("user", userService.findById(id));
            organizations = organizationService.getAllOrganizations(); 
            model.addAttribute("organizationList", organizations);
            model.addAttribute("roles", roles);
            return "admin_editUserForm";
        }
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
