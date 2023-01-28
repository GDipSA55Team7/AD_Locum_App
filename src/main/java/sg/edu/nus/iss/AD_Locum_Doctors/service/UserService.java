package sg.edu.nus.iss.AD_Locum_Doctors.service;

import sg.edu.nus.iss.AD_Locum_Doctors.model.FreeLancerDTO;

public interface UserService {

	FreeLancerDTO createFreeLancer(FreeLancerDTO freeLancerDTO);
	FreeLancerDTO loginFreeLancer(FreeLancerDTO freeLancerDTO);
	Boolean updateFreeLancer(FreeLancerDTO freeLancerDTO);
}
