package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Clinic;
import sg.edu.nus.iss.AD_Locum_Doctors.service.ClinicService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.OrganizationService;

@Controller
public class ClinicController {
   
    @Autowired
    OrganizationService oService;

    @Autowired
    ClinicService cService;

    @GetMapping("/addClinicForm")
    public String addClinicForm(Model model){
        Clinic clinic = new Clinic();
        model.addAttribute("clinic", clinic);
        model.addAttribute("organizationList", oService.getAllOrganizations());
        return "addClinicForm";
    }

    @PostMapping("/saveClinic")
    public String saveClinic(@ModelAttribute("clinic") @Valid Clinic clinic, BindingResult result,
   Model model){
    cService.saveClinic(clinic);
    return "";
   }
}
