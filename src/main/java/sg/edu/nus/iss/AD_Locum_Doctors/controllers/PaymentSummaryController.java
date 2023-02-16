package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Organization;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.service.JobPostService;

@Controller
@RequestMapping("/paymentSummary")
public class PaymentSummaryController {
	
    @Autowired
    HttpSession session;

	@Autowired
	private JobPostService jobPostService;

	private Organization userOrganization;

	private User user;

	private void loadReference(){
        user= (User) session.getAttribute("user");
        userOrganization = user.getOrganization();
    }
	
	@GetMapping(value= {""})
	public String UnpaidjobPostsListPage(Model model) {
		loadReference();
		List<JobPost> jobPosts = jobPostService.findJobPostsWithOutstandingPayment(userOrganization.getId());
		model.addAttribute("jobPosts", jobPosts);
		return "Outstanding_Payment";
	}
	
	@GetMapping(value= {"/outstandingPayments"})
	public String AjaxUnpaidjobPostsListPage(Model model) {
		List<JobPost> jobPosts = null;
		jobPosts = jobPostService.findJobPostsWithOutstandingPayment(userOrganization.getId());
		model.addAttribute("jobPosts", jobPosts);
		return "Payment_Subcontent :: content1";
	}
	
	@GetMapping(value= {"/PaidJobs"})
	public String AjaxPaidjobPostsListPage(Model model) {
		List<JobPost> jobPosts = null;
		jobPosts = jobPostService.findPaidJobPosts(userOrganization.getId());
		model.addAttribute("jobPosts", jobPosts);
		return "Payment_Subcontent :: content1";
	}
	
	@GetMapping(value= {"/AllCompletedJobs"})
	public String AjaxAllCompletedjobPostsListPage(Model model) {
		List<JobPost> jobPosts = null;
		jobPosts = jobPostService.findPaidandUnpaidJobPosts(userOrganization.getId());
		model.addAttribute("jobPosts", jobPosts);
		return "Payment_Subcontent :: content1";
	}
}
