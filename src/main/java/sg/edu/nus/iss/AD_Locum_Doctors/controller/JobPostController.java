package sg.edu.nus.iss.AD_Locum_Doctors.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.service.JobPostService;

@Controller
@RequestMapping("/jobpost")
public class JobPostController {
	@Autowired
	private JobPostService jobPostService;

	@GetMapping("/create")
	public String createJobPostPage(Model model) {
		model.addAttribute("jobpost", new JobPost());
		return "jobpost-create";
	}

	@PostMapping("/create")
	public String createJobPost(JobPost jobPost, Model model) {
		jobPostService.create(jobPost);
		return "";
	}
}