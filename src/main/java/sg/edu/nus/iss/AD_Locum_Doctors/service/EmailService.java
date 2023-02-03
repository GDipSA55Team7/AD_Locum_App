package sg.edu.nus.iss.AD_Locum_Doctors.service;

import org.springframework.stereotype.Service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.EmailDetails;

// Importing required classes
 
// Interface
@Service
public interface EmailService {
 
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);
 
}