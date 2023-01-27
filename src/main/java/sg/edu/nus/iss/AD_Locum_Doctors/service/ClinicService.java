package sg.edu.nus.iss.AD_Locum_Doctors.service;

import org.springframework.stereotype.Service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.Clinic;

@Service
public interface ClinicService {
    
    void saveClinic(Clinic clinic);
}
