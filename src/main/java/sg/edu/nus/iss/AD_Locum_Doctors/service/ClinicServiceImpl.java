package sg.edu.nus.iss.AD_Locum_Doctors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Clinic;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.ClinicRepository;


@Service
@Transactional
public class ClinicServiceImpl implements ClinicService{

    @Autowired
    ClinicRepository clinicRepository;

    @Override
    public void saveClinic(Clinic clinic) {
       clinicRepository.saveAndFlush(clinic);
    }
    
}
