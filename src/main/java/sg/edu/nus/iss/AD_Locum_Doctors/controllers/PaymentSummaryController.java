package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.service.JobPostService;

@Controller
@RequestMapping("/paymentSummary")
public class PaymentSummaryController {
	
	@Autowired
	private JobPostService jobPostService;
	
	@GetMapping(value= {"/","/outstandingPayments"})
	public String jobPostListPage(Model model) {
		
		List<JobPost> jobPosts = jobPostService.findJobPostsWithOutstandingPayment();
		for (JobPost j: jobPosts) {
			j.getFreelancer().getName();
			System.out.println(j.getStatus());
		}
		model.addAttribute("jobPosts", jobPosts);	
		return "Outstanding_Payment";
	}
	

}
