package sg.edu.nus.iss.AD_Locum_Doctors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Organization;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.OrganizationRepository;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    OrganizationRepository organizationRepository;

    @Override
    public void CreateOrganization(Organization organization) {
        this.organizationRepository.saveAndFlush(organization);
    }
}
