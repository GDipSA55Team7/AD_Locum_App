package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {
    
 @GetMapping("/Registration")
 public String Registration(){
    return "Registration";
 }

}
