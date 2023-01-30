package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.AD_Locum_Doctors.model.*;
import sg.edu.nus.iss.AD_Locum_Doctors.service.AdditionalFeeDetailsService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.ClinicService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.JobPostService;

@Controller
@RequestMapping("/jobpost")
public class JobPostController {
	@Autowired
	private JobPostService jobPostService;

	@Autowired
	private AdditionalFeeDetailsService additionalFeeDetailsService;

	@Autowired
	private ClinicService clinicService;

	@GetMapping("/list")
	public String jobPostListPage(Model model) {
		model.addAttribute("jobPosts", jobPostService.findAll());
		return "jobpost-list";
	}

	@GetMapping("/create")
	public String createJobPostPage(Model model, HttpSession session) {
		List<Clinic> clinics = clinicService.findAll();
		model.addAttribute("clinics", clinics);
		model.addAttribute("jobPostForm", new JobPostForm());
		return "jobpost-create";
	}

	@PostMapping("/create")
	public String createJobPost(JobPostForm jobPostForm, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		jobPostService.createJobPost(jobPostForm, user);
		return "redirect:/jobpost/list";
	}

	@GetMapping("/{id}")
	public String viewJobPost(@PathVariable String id, Model model) {
		JobPost jobPost = jobPostService.findJobPostById(id);
		model.addAttribute("jobPost", jobPost);
		model.addAttribute("statusList", List.of(
				JobStatus.OPEN,
				JobStatus.PENDING_ACCEPTANCE,
				JobStatus.ACCEPTED,
				JobStatus.COMPLETED_PENDING_PAYMENT,
				JobStatus.COMPLETED_PAYMENT_PROCESSED,
				JobStatus.CANCELLED,
				JobStatus.DELETED));

		AdditionalFeeDetailsForm additional = new AdditionalFeeDetailsForm();
		additional.setJobPostId(Long.parseLong(id));
		model.addAttribute("additional", additional);
		return "jobpost-view";
	}

	@GetMapping("/{id}/cancel")
	public String cancelJobPost(@PathVariable String id) {
		JobPost jobPost = jobPostService.findJobPostById(id);
		jobPostService.cancel(jobPost);
		return "redirect:/jobpost/list";
	}

	@PostMapping("/update")
	public String updateJobPost(JobPost jobPost, Model model) {
		String id = jobPost.getId().toString();
		JobPost toUpdateJobPost= jobPostService.findJobPostById(id);
		toUpdateJobPost.setActualStartDateTime(jobPost.getActualStartDateTime());
		toUpdateJobPost.setActualEndDateTime(jobPost.getActualEndDateTime());
		toUpdateJobPost.setAdditionalRemarks(jobPost.getAdditionalRemarks());
		toUpdateJobPost.setStatus(jobPost.getStatus());
		jobPostService.saveJobPost(toUpdateJobPost);

		return "redirect:/jobpost/"+jobPost.getId();
	}

	@PostMapping("/additional")
	public String createJobPostAdditional(AdditionalFeeDetailsForm additionalFeeDetailsForm)
	{
		JobPost jobPost = jobPostService.findJobPostById(additionalFeeDetailsForm.getJobPostId().toString());
		additionalFeeDetailsService.createAdditionalFeeDetail(additionalFeeDetailsForm, jobPost);

		return "redirect:/jobpost/"+jobPost.getId();
	}
}