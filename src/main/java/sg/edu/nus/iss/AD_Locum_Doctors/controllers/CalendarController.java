package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPostEventDTO;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobStatus;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.service.JobPostService;

@RequestMapping("/calendar")
@Controller
public class CalendarController {
	@Autowired
	JobPostService jobPostService;

	@GetMapping
	public String calendar(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<JobPostEventDTO> jobPostEvents = new ArrayList<>();
		List<JobPost> jobPosts = jobPostService.findAll();
		if (!user.getRole().getName().equals("System_Admin")) {
			jobPosts = jobPosts.stream()
					.filter(x -> x.getClinic().getOrganization().getId().equals(user.getOrganization().getId()))
					.filter(x -> !x.getStatus().equals(JobStatus.REMOVED))
					.collect(Collectors.toList());
		}
		for (JobPost j : jobPosts) {
			JobPostEventDTO jpEvent = new JobPostEventDTO();
			jpEvent.setTitle(j.getTitle());
			jpEvent.setStart(j.getStartDateTime());
			jpEvent.setEnd(j.getEndDateTime());
			jpEvent.setUrl("/jobpost/" + j.getId());
			if (j.getStatus() == JobStatus.OPEN) {
				jpEvent.setColor("#3C8DBC");
			} else if (j.getStatus() == JobStatus.PENDING_CONFIRMATION_BY_CLINIC) {
				jpEvent.setColor("#FFC107");
			} else if (j.getStatus() == JobStatus.ACCEPTED) {
				jpEvent.setColor("#28A745");
			} else if (j.getStatus() == JobStatus.COMPLETED_PENDING_PAYMENT) {
				jpEvent.setColor("#DC3545");
			} else if (j.getStatus() == JobStatus.COMPLETED_PAYMENT_PROCESSED) {
				jpEvent.setColor("#001F3F");
			} else if (j.getStatus() == JobStatus.CANCELLED || j.getStatus() == JobStatus.REMOVED) {
				jpEvent.setColor("rgba(108,117,125,0.3)");
			}
			jobPostEvents.add(jpEvent);
		}
		model.addAttribute("jobPostEvents", jobPostEvents);
		return "jobpost-calendar";
	}
}
