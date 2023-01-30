package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

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

    @Override
    public List<Clinic> findAll() {
        return clinicRepository.findAll();
    }

    @Override
    public Clinic findById(Long id) {
        return clinicRepository.findById(id).get();
    }

    @Override
    public void deleteClinic(Clinic clinic) {
      clinicRepository.delete(clinic); 
    }
    
}