package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.service.JobPostService;

@Controller
@RequestMapping("/paymentSummary")
public class PaymentSummaryController {
	
	@Autowired
	private JobPostService jobPostService;
	
	@GetMapping(value= {""})
	public String UnpaidjobPostsListPage(Model model) {
		List<JobPost> jobPosts = jobPostService.findJobPostsWithOutstandingPayment();;
		model.addAttribute("jobPosts", jobPosts);
		return "Outstanding_Payment";
	}
	
	@GetMapping(value= {"/outstandingPayments"})
	public String AjaxUnpaidjobPostsListPage(Model model) {
		List<JobPost> jobPosts = null;
		jobPosts = jobPostService.findJobPostsWithOutstandingPayment();
		model.addAttribute("jobPosts", jobPosts);
		return "Payment_Subcontent :: content1";
	}
	
	@GetMapping(value= {"/PaidJobs"})
	public String AjaxPaidjobPostsListPage(Model model) {
		List<JobPost> jobPosts = null;
		jobPosts = jobPostService.findPaidJobPosts();
		model.addAttribute("jobPosts", jobPosts);
		return "Payment_Subcontent :: content1";
	}
	
	@GetMapping(value= {"/AllCompletedJobs"})
	public String AjaxAllCompletedjobPostsListPage(Model model) {
		List<JobPost> jobPosts = null;
		jobPosts = jobPostService.findPaidandUnpaidJobPosts();
		model.addAttribute("jobPosts", jobPosts);
		return "Payment_Subcontent :: content1";
	}
}
