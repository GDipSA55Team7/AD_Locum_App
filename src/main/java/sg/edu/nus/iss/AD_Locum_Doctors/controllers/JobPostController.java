package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.AD_Locum_Doctors.model.AdditionalFeeDetailsForm;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Clinic;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobAdditionalRemarks;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPostForm;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobStatus;
import sg.edu.nus.iss.AD_Locum_Doctors.model.RemarksCategory;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.JobAdditionalRemarksRepository;
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

	@Autowired
	private JobAdditionalRemarksRepository jobAdditionalRemarksRepo;

	@GetMapping("/list")
	public String jobPostListPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<JobPost> jobPosts = new ArrayList<>();
		if (user != null) {
			switch (user.getRole().getName()) {
				case "System_Admin":
					jobPosts = jobPostService.findAll();
					break;
				default:
					jobPosts = jobPostService.findJobPostsCreatedByUser(user).stream()
							.filter(x -> !x.getStatus().equals(JobStatus.DELETED))
							.filter(x -> !x.getStatus().equals(JobStatus.REMOVED))
							.toList();
					break;
			}
			model.addAttribute("jobPosts", jobPosts);
		}
		return "jobpost-list";
	}

	@GetMapping("/create")
	public String createJobPostPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		List<Clinic> clinics = clinicService.findAll();
		model.addAttribute("clinics", clinics);
		model.addAttribute("jobPostForm", new JobPostForm());
		return "jobpost-create";
	}

	@PostMapping("/create")
	public String createJobPost(JobPostForm jobPostForm, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		jobPostService.createJobPost(jobPostForm, user);
		return "redirect:/jobpost/list";
	}

	@GetMapping("/{id}")
	public String viewJobPost(@PathVariable String id, Model model) {
		JobPost jobPost = jobPostService.findJobPostById(id);
		model.addAttribute("jobPost", jobPost);
		model.addAttribute("statusList", List.of(
				JobStatus.OPEN,
				JobStatus.PENDING_CONFIRMATION_BY_CLINIC,
				JobStatus.ACCEPTED,
				JobStatus.COMPLETED_PENDING_PAYMENT,
				JobStatus.COMPLETED_PAYMENT_PROCESSED,
				JobStatus.CANCELLED,
				JobStatus.DELETED));

		AdditionalFeeDetailsForm additional = new AdditionalFeeDetailsForm();
		additional.setJobPostId(Long.parseLong(id));
		model.addAttribute("additional", additional);
		if (jobPost.getStatus().equals(JobStatus.OPEN)) {
			return "jobpost-view";
		} else if (jobPost.getStatus().equals(JobStatus.PENDING_CONFIRMATION_BY_CLINIC)) {
			return "jobpost-locum";
		} else if (jobPost.getStatus().equals(JobStatus.CANCELLED)) {
			return "jobpost-cancelled";
		}
		return "jobpost-accepted-view";
	}

	@GetMapping("/{id}/delete")
	public String deleteJobPost(@PathVariable String id) {
		JobPost jobPost = jobPostService.findJobPostById(id);
		jobPostService.delete(jobPost);
		return "redirect:/jobpost/list";
	}

	@PostMapping("/update")
	public String updateJobPost(JobPost jobPost, Model model) {
		String id = jobPost.getId().toString();
		JobPost toUpdateJobPost = jobPostService.findJobPostById(id);
		toUpdateJobPost.setActualStartDateTime(jobPost.getActualStartDateTime());
		toUpdateJobPost.setActualEndDateTime(jobPost.getActualEndDateTime());
		toUpdateJobPost.setAdditionalRemarks(jobPost.getAdditionalRemarks());
		toUpdateJobPost.setStatus(jobPost.getStatus());
		jobPostService.saveJobPost(toUpdateJobPost);

		return "redirect:/jobpost/" + jobPost.getId();
	}

	@PostMapping(value = "/update", params = "cancel-locum")
	public String cancelJobPostForm(JobPost jobPost, HttpSession session) {
		User user = (User) session.getAttribute("user");
		String id = jobPost.getId().toString();
		JobPost jp = jobPostService.findJobPostById(id);
		jp.setStatus(JobStatus.CANCELLED);
		jp.setAdditionalRemarks(jobPost.getAdditionalRemarks());
		jobPostService.saveJobPost(jp);

		JobAdditionalRemarks additionalRemarks = new JobAdditionalRemarks();
		additionalRemarks.setCategory(RemarksCategory.CANCELLATION);
		additionalRemarks.setDate(LocalDate.now());
		additionalRemarks.setJobPost(jp);
		additionalRemarks.setUser(user);
		additionalRemarks.setRemarks(jp.getAdditionalRemarks());
		jobAdditionalRemarksRepo.saveAndFlush(additionalRemarks);
		return "redirect:/jobpost/list";
	}

	@PostMapping("/additional")
	public String createJobPostAdditional(AdditionalFeeDetailsForm additionalFeeDetailsForm) {
		JobPost jobPost = jobPostService.findJobPostById(additionalFeeDetailsForm.getJobPostId().toString());
		additionalFeeDetailsService.createAdditionalFeeDetail(additionalFeeDetailsForm, jobPost);

		return "redirect:/jobpost/" + jobPost.getId();
	}
}