package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobAdditionalRemarks;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.service.JobPostService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.UserService;

@Controller 
@RequestMapping("/superadmin")
public class SuperAdminController {
	
	@Autowired
	private JobPostService jobPostService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/listJobPosts")
	public String jobPostListPage(Model model) {
		model.addAttribute("jobPosts", jobPostService.findAll());
		return "admin_jobpost_list";
	}
	
	@GetMapping("/{id}")
	public String viewJobPost(@PathVariable String id, Model model) {
		JobPost jobPost = jobPostService.findJobPostById(id);
		model.addAttribute("jobPost", jobPost);
		return "admin_jobpost_view";
	}
	
	@GetMapping("/{id}/cancel")
	public String cancelJobPost(@PathVariable String id, Model model) {
		JobPost jobPost = jobPostService.findJobPostById(id);
		model.addAttribute("jobPost", jobPost);
		model.addAttribute("additionalRemarks", new JobAdditionalRemarks());
		return "admin_jobpost_cancel";
	}
	
	@PostMapping("/{id}/confirmcancel")
	public String confirmcancelJobPost(@PathVariable String id, JobAdditionalRemarks additionalRemarks) {
		System.out.println("Cancel id:" + id);
		JobPost jobPost = jobPostService.findJobPostById(id);
		User user = userService.findById(Long.parseLong("4"));
		jobPostService.cancel(jobPost, additionalRemarks, user);
		return "redirect:/superadmin/listJobPosts";
	}
	
	@GetMapping("/{id}/remove")
	public String deleteJobPost(@PathVariable String id, Model model) {
		JobPost jobPost = jobPostService.findJobPostById(id);
		model.addAttribute("jobPost", jobPost);
		model.addAttribute("additionalRemarks", new JobAdditionalRemarks());
		return "admin_jobpost_delete";
	}
	
	@PostMapping("/{id}/confirmremove")
	public String confirmDeleteJobPost(@PathVariable String id, JobAdditionalRemarks additionalRemarks) {
		System.out.println("Delete id:" + id);
		JobPost jobPost = jobPostService.findJobPostById(id);
		User user = userService.findById(Long.parseLong("4"));
		jobPostService.delete(jobPost, additionalRemarks, user);
		return "redirect:/superadmin/listJobPosts";
	}

}
