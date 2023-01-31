package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.Clinic;

@Service
public interface ClinicService {

    void saveClinic(Clinic clinic);

    List<Clinic> findAll();

    Clinic findById(Long id);

    void deleteClinic(Clinic clinic);

    List<Clinic> findByOrganizationId(Long id);
}