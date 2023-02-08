package sg.edu.nus.iss.AD_Locum_Doctors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.AD_Locum_Doctors.model.AdditionalFeeDetails;
import sg.edu.nus.iss.AD_Locum_Doctors.model.AdditionalFeeDetailsForm;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.AdditionalFeeDetailsRepository;

@Service
public class AdditionalFeeDetailsServiceImpl implements AdditionalFeeDetailsService{

    @Autowired
    AdditionalFeeDetailsRepository additionalFeeDetailsRepository;

    @Override
    public AdditionalFeeDetails getAdditionalFeeDetailsById(String id) {
        return additionalFeeDetailsRepository.findById(Long.parseLong(id)).orElse(null);
    }

    @Override
    public void createAdditionalFeeDetail(AdditionalFeeDetailsForm form, JobPost jobPost) {
        AdditionalFeeDetails additionalFeeDetails = new AdditionalFeeDetails();
        additionalFeeDetails.setDescription(form.getDescription());
        additionalFeeDetails.setAdditionalFeesAmount(form.getAdditionalFeesAmount());
        additionalFeeDetails.setJobPost(jobPost);
        additionalFeeDetailsRepository.saveAndFlush(additionalFeeDetails);
    }

    @Override
    public void delete(AdditionalFeeDetails additionalFeeDetails) {
        additionalFeeDetailsRepository.delete(additionalFeeDetails);
    }
}
