package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.reactor.DebugAgentEnvironmentPostProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.AD_Locum_Doctors.model.FreeLancerDTO;
import sg.edu.nus.iss.AD_Locum_Doctors.service.UserService;

@RestController
@RequestMapping("api/freelancer") 
public class FreeLancerRestController {
	
	@Autowired
	UserService userService;
	
	
	@PostMapping("/login")
	public ResponseEntity<FreeLancerDTO> loginNewFreeLancer(@RequestBody FreeLancerDTO freeLancerDTO) {
		try {
			FreeLancerDTO existingFreeLancer = userService.loginFreeLancer(freeLancerDTO);
			if(existingFreeLancer == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(existingFreeLancer,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<FreeLancerDTO> registerNewFreeLancer(@RequestBody FreeLancerDTO freeLancerDTO) {
		try {
			FreeLancerDTO newFreeLancerDTO = userService.createFreeLancer(freeLancerDTO);
			return new ResponseEntity<>(newFreeLancerDTO,HttpStatus.CREATED);
//			if(!newFreeLancerDTO.getErrorsFieldString().isEmpty()) {
//				return new ResponseEntity<>(newFreeLancerDTO,HttpStatus.CONFLICT);
//			}
//			else {
//				return new ResponseEntity<>(newFreeLancerDTO,HttpStatus.CREATED);
//			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> updateNewFreeLancer(@RequestBody FreeLancerDTO freeLancerDTO) {
		try {
			System.out.println("updateController");
			if(!userService.updateFreeLancer(freeLancerDTO)) {
				System.out.println("NOT FOUND");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			System.out.println("UPDATED" + "\n" + freeLancerDTO);
			return new ResponseEntity<>(freeLancerDTO,HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("ERROR" + "\n" + freeLancerDTO);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
