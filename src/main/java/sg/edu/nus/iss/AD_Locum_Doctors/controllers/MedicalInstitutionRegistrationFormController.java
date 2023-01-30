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
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Organization;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.service.OrganizationService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.UserService;

@Controller
@RequestMapping("/register")
public class MedicalInstitutionRegistrationFormController {

    @Autowired
    OrganizationService orgService;

    @Autowired
    UserService userService;

    @GetMapping("/medicalInstitutionRegistrationForm")
    public String medicalInstitutionRegistrationForm(Model model) {
        Organization organization = new Organization();
        model.addAttribute("organization", organization);
        return "medicalInstitutionRegistrationForm";
    }

    @PostMapping("/registerOrg")
    public String registerOrg(@ModelAttribute("organization") @Valid Organization organization, BindingResult result,
            Model model) {
        orgService.CreateOrganization(organization);
        return "redirect:/register/registerAdmin/" + organization.getId();
    }

    @GetMapping("/registerAdmin/{id}")
    public String registerAdmin(Model model, @PathVariable(value = "id") Long id) {
        Organization organization = orgService.findById(id);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        model.addAttribute("user", new User());
        model.addAttribute("organizationList", organizations);
        return "registerAdminForm";
    }

    @PostMapping("/createAdmin")
    public String createAdmin(@ModelAttribute("user") @Valid User user, BindingResult result,
            Model model, HttpSession session) {
        userService.saveUser(user);
        return "redirect:/login";
    }
}
