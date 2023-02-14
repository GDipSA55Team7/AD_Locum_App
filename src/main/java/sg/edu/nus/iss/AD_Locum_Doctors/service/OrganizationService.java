package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.Organization;

public interface OrganizationService {

    void CreateOrganization(Organization organization);

    List<Organization> getAllOrganizations();
    
    Organization findById(Long id);
    
    Organization updateOrganizationDetails(Organization organization);
   
}
