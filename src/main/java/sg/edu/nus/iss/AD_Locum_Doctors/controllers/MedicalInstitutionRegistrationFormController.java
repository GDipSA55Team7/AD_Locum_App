package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Organization;
import sg.edu.nus.iss.AD_Locum_Doctors.service.OrganizationService;

@Controller
public class MedicalInstitutionRegistrationFormController {
    
    @Autowired
    OrganizationService orgService;

    @GetMapping("/medicalInstitutionRegistrationForm")
    public String medicalInstitutionRegistrationForm(Model model){
        Organization organization = new Organization();
        model.addAttribute("organization", organization);
        return "medicalInstitutionRegistrationForm";
    }

    @PostMapping("/registerOrg")
    public String registerOrg(@ModelAttribute("organization") @Valid Organization organization, BindingResult result,
   Model model){
    orgService.CreateOrganization(organization);
    return "";
   }
}
