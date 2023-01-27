package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.AD_Locum_Doctors.model.Clinic;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPostForm;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.ClinicRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.service.JobPostService;

@Controller
@RequestMapping("/jobpost")
public class JobPostController {
	@Autowired
	private JobPostService jobPostService;

	@Autowired
	private ClinicRepository clinicRepo;

	@GetMapping("/list")
	public String jobPostListPage(Model model) {
		model.addAttribute("jobPostList", jobPostService.findAll());
		return "jobpost-list";
	}

	@GetMapping("/create")
	public String createJobPostPage(Model model) {
		// still working on it
		List<Clinic> clinics = clinicRepo.findAll();
		model.addAttribute("clinics", clinics);
		model.addAttribute("jobPostForm", new JobPostForm());
		return "jobpost-create";
	}

	@PostMapping("/create")
	public String createJobPost(JobPostForm jobPostForm, Model model) {
		// still working on it
		jobPostService.createJobPost(jobPostForm);
		return "redirect:/jobpost/list";
	}
}