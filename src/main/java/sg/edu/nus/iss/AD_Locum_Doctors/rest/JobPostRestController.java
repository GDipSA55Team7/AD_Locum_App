package sg.edu.nus.iss.AD_Locum_Doctors.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.nus.iss.AD_Locum_Doctors.model.FreeLancerDTO;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPostApiDTO;
import sg.edu.nus.iss.AD_Locum_Doctors.service.JobPostService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/jobs")
public class JobPostRestController {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    JobPostService jobPostService;

    @GetMapping("/allopen")
    public ResponseEntity<List<JobPostApiDTO>> findAllOpen() {
        List<JobPost> jpl2 = jobPostService.findAllOpen();
        try {
            List<JobPost> jobPostList = jobPostService.findAllOpen();
            List<JobPostApiDTO> jobPostDTOList = new ArrayList<JobPostApiDTO>();
            for (JobPost jobPost :jobPostList) {

                JobPostApiDTO jobPostDTO = new JobPostApiDTO();
                jobPostDTO.setId(jobPost.getId());
                jobPostDTO.setDescription(jobPost.getDescription());
                jobPostDTO.setStartDateTime(jobPost.getStartDateTime());
                jobPostDTO.setEndDateTime(jobPost.getEndDateTime());
                jobPostDTO.setClinic(jobPost.getClinic());
                jobPostDTO.setStatus(jobPost.getStatus());
                jobPostDTO.setTotalRate(jobPost.getTotalRate());
                jobPostDTO.setRatePerHour(jobPost.getRatePerHour());
                jobPostDTO.setClinicUser(jobPost.getClinicUser());
                jobPostDTO.setClinic(jobPost.getClinic());
                jobPostDTOList.add(jobPostDTO);
            }
            if(jobPostList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(jobPostDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
