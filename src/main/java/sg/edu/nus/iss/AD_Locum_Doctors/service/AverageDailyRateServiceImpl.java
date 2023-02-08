package sg.edu.nus.iss.AD_Locum_Doctors.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.edu.nus.iss.AD_Locum_Doctors.model.AverageDailyRate;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.AverageDailyRateRepository;

@Transactional
@Service
public class AverageDailyRateServiceImpl implements AverageDailyRateService {
	@Autowired
	AverageDailyRateRepository averageDailyRateRepo;

	@Override
	public List<AverageDailyRate> getAverageDailyRates() {
		return averageDailyRateRepo.findAll();
	}
}
