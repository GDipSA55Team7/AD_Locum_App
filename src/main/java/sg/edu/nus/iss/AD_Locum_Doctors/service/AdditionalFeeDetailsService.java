package sg.edu.nus.iss.AD_Locum_Doctors.service;

import org.springframework.stereotype.Service;
import sg.edu.nus.iss.AD_Locum_Doctors.model.*;

@Service
public interface AdditionalFeeDetailsService {
    AdditionalFeeDetails getAdditionalFeeDetailsById(String id);
    void createAdditionalFeeDetail(AdditionalFeeDetailsForm form, JobPost jobPost);

    void delete(AdditionalFeeDetails additionalFeeDetails);
}
