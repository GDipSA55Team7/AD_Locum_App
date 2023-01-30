package sg.edu.nus.iss.AD_Locum_Doctors.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPostApiDTO;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobStatus;
import sg.edu.nus.iss.AD_Locum_Doctors.service.JobPostService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/jobs")
public class JobPostRestController {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    JobPostService jobPostService;

    @GetMapping("/allopen")
    public ResponseEntity<List<JobPostApiDTO>> findAllOpen() {
        try {
            List<JobPost> jobPostList = jobPostService.findAllOpen();
            List<JobPostApiDTO> jobPostDTOList = new ArrayList<>();
            for (JobPost jobPost :jobPostList) {
                JobPostApiDTO jobPostDTO = setJobPostDTO(jobPost);
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

    @GetMapping("/job")
    public ResponseEntity<JobPostApiDTO> findById(@RequestParam String id) {
        try {
            JobPost jobPost = jobPostService.findJobPostById(id);
            if (jobPost != null) {
                JobPostApiDTO jobPostDTO = setJobPostDTO(jobPost);
                return new ResponseEntity<>(jobPostDTO, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/job")
    public ResponseEntity<JobPostApiDTO> setJobStatus(@RequestParam String id, @RequestParam String status,
                                                      @RequestParam String userId) {
        try {
            JobPost jobPost = jobPostService.findJobPostById(id);
            if (jobPost != null) {
                if (Objects.equals(status, "apply")) {
                    jobPostService.setStatus(jobPost, JobStatus.PENDING_ACCEPTANCE, userId);
                }
                else if (Objects.equals(status, "cancel")) {
                    jobPostService.setStatus(jobPost, JobStatus.OPEN, userId);
                }
                JobPostApiDTO jobPostDTO = setJobPostDTO(jobPost);
                return new ResponseEntity<>(jobPostDTO, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/history")
    public ResponseEntity<List<JobPostApiDTO>> findJobHistory(@RequestParam String id) {
        try {
            List<JobPost> jobPostList = jobPostService.findJobHistory(id);
            List<JobPostApiDTO> jobPostDTOList = new ArrayList<>();
            for (JobPost jobPost :jobPostList) {
                JobPostApiDTO jobPostDTO = setJobPostDTO(jobPost);
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

    private JobPostApiDTO setJobPostDTO(JobPost jobPost) {
        JobPostApiDTO jobPostDTO = new JobPostApiDTO();
        jobPostDTO.setId(jobPost.getId());
        jobPostDTO.setDescription(jobPost.getDescription());
        jobPostDTO.setStartDateTime(jobPost.getStartDateTime().toString());
        jobPostDTO.setEndDateTime(jobPost.getEndDateTime().toString());
        jobPostDTO.setClinic(jobPost.getClinic());
        jobPostDTO.setStatus(jobPost.getStatus());
        jobPostDTO.setTotalRate(jobPost.getTotalRate());
        jobPostDTO.setRatePerHour(jobPost.getRatePerHour());
        jobPostDTO.setClinicUser(jobPost.getClinicUser());
        jobPostDTO.setClinic(jobPost.getClinic());
        jobPostDTO.setFreelancer(jobPost.getFreelancer());

        return jobPostDTO;
    }
}
