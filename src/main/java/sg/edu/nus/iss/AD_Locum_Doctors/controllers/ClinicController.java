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
import sg.edu.nus.iss.AD_Locum_Doctors.model.Clinic;
import sg.edu.nus.iss.AD_Locum_Doctors.service.ClinicService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.OrganizationService;

@Controller
public class ClinicController {
   
    @Autowired
    OrganizationService oService;

    @Autowired
    ClinicService cService;

    @GetMapping("/cliniclist")
    public String clinicListPage(Model model){
        List<Clinic> clinics = cService.findAll();
        model.addAttribute("clinicList", clinics);
        return "clinic-list";
    }

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
    return "redirect:/cliniclist";
   }

   @GetMapping("/viewclinic/{id}")
   public String viewClinic(Model model,@PathVariable(value = "id") Long id){
    Clinic clinic = cService.findById(id);
    model.addAttribute("clinic",clinic);
    model.addAttribute("organizationList", oService.getAllOrganizations());
    return "editClinicForm";
   }

   @PostMapping("/editClinic")
   public String editClinic(@ModelAttribute("clinic") @Valid Clinic clinic, BindingResult result,
   Model model){
    cService.saveClinic(clinic);
    return "redirect:/cliniclist";
   }

   @GetMapping("/deleteclinic/{id}")
    public String deleteClinic(Model model, @PathVariable(value = "id") Long id){
        Clinic clinic = cService.findById(id);
        cService.deleteClinic(clinic);
        return "redirect:/cliniclist";
    }
}
