package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.AD_Locum_Doctors.model.AdditionalFeeDetails;
import sg.edu.nus.iss.AD_Locum_Doctors.model.AverageDailyRate;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Clinic;
import sg.edu.nus.iss.AD_Locum_Doctors.model.EmailDetails;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobAdditionalRemarks;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobStatus;
import sg.edu.nus.iss.AD_Locum_Doctors.model.ManyAdditionalFeeDetailsForm;
import sg.edu.nus.iss.AD_Locum_Doctors.model.RemarksCategory;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.AverageDailyRateRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.service.AdditionalFeeDetailsService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.ClinicService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.EmailService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.JobPostAdditionalRemarksService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.JobPostService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.UserService;

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
	private UserService userService;
	@Autowired
	private JobPostAdditionalRemarksService addRemarksService;

	@Autowired
	private AverageDailyRateRepository avgDailyRateRepo;

	@Autowired
	private EmailService emailService;

	@GetMapping("/list")
	public String jobPostListPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<JobPost> jobPosts = new ArrayList<>();
		jobPosts = jobPostService.findAll();
		switch (user.getRole().getName()) {
			case "System_Admin":
				model.addAttribute("jobPosts", jobPosts);
				break;
			default:
				jobPosts = jobPosts.stream()
						.filter(x -> x.getClinic().getOrganization().getId().equals(user.getOrganization().getId()))
						.filter(x -> !x.getStatus().equals(JobStatus.REMOVED))
						.collect(Collectors.toList());
				model.addAttribute("jobPosts", jobPosts);
		}
		return "jobpost-list";
	}

	@GetMapping("/me")
	public String jobPostListByUser(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<JobPost> jobPosts = jobPostService.findAll().stream()
				.filter(x -> x.getClinicUser().getId() == user.getId()).toList();
		model.addAttribute("jobPosts", jobPosts);
		return "jobpost-list";
	}

	@GetMapping("/create")
	public String createJobPostPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<Clinic> clinics = clinicService.findAll().stream()
				.filter(x -> x.getOrganization().getId() == user.getOrganization().getId()).toList();
		model.addAttribute("clinics", clinics);
		model.addAttribute("jobPost", new JobPost());
		Double weekdayTrend = null;
		Double weekendTrend = null;
		try {
			AverageDailyRate rateTrend = avgDailyRateRepo.findById(LocalDate.now()).get();
			weekdayTrend = rateTrend.getWeekday_28_MA();
			weekendTrend = rateTrend.getWeekend_28_MA();
		} catch (Exception e) {
			// Default values to be used if no data exist as yet.
			// The rates are derived by using the lower range of surveyed existing market
			// rates as referenced.
			weekdayTrend = (double) 80;
			weekendTrend = (double) 100;
		}
		model.addAttribute("weekdayTrend", weekdayTrend);
		model.addAttribute("weekendTrend", weekendTrend);
		return "jobpost-create";
	}

	// Using this for testing - Shaun ... To delete later ...
	@GetMapping("/shauntest")
	public String testpage(Model model, HttpSession session) {
		LocalDate myDate = LocalDate.of(2023, 02, 01);
		AverageDailyRate testID = avgDailyRateRepo.findById(myDate).get();
		List<AverageDailyRate> myList = avgDailyRateRepo.findAll();
		model.addAttribute("testID", testID.getDate());
		model.addAttribute("localdate", LocalDate.now());
		model.addAttribute("testdate", myList.get(1).getDate());
		model.addAttribute("weekday", myList.get(1).getAverage_daily_rate_weekday());
		model.addAttribute("weekend", myList.get(1).getAverage_daily_rate_weekend());
		return "test";
	}

	@PostMapping("/create")
	public String createJobPost(JobPost jobPost, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		jobPostService.createJobPost(jobPost, user);
		return "redirect:/jobpost/list";
	}

	@GetMapping("/{id}")
	public String viewJobPost(@PathVariable String id, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		JobPost jobPost = jobPostService.findJobPostById(id);
		jobPost.setAdditionalRemarks("");
		model.addAttribute("jobPost", jobPost);
		List<JobAdditionalRemarks> remarksList = addRemarksService.findAll().stream()
				.filter(x -> x.getJobPost().getId() == jobPost.getId())
				.sorted(Comparator.comparing(JobAdditionalRemarks::getDateTime).reversed()).toList();
		model.addAttribute("remarksList", remarksList);
		ManyAdditionalFeeDetailsForm additional = new ManyAdditionalFeeDetailsForm();
		additional.setJobPostId(jobPost.getId().toString());
		model.addAttribute("additional", additional);
		for (AdditionalFeeDetails a : jobPost.getAdditionalFeeDetails()) {
			System.out.print(a.getAdditionalFeesAmount());
		}
		switch (user.getRole().getName()) {
			case "System_Admin":
				return "jobpost-view";
			default:
				if (jobPost.getStatus().equals(JobStatus.OPEN) || jobPost.getStatus().equals(JobStatus.CANCELLED)) {
					return "jobpost-view";
				}
				return "jobpost-locum";
		}
	}

	@GetMapping("/{id}/edit")
	public String editJobPost(@PathVariable String id, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		JobPost jobPost = jobPostService.findJobPostById(id);
		if (jobPost.getStatus().equals(JobStatus.OPEN)
				&& user.getOrganization().getId() == jobPost.getClinicUser().getOrganization().getId()) {
			List<Clinic> clinics = clinicService.findAll().stream()
					.filter(x -> x.getOrganization().getId() == user.getOrganization().getId()).toList();
			model.addAttribute("clinics", clinics);
			model.addAttribute("jobPost", jobPost);
			return "jobpost-edit";
		}
		return "redirect:/jobpost/" + id;
	}

	@PostMapping("/{id}/edit")
	public String submitEditJobPost(@PathVariable String id, JobPost jobPost, Model model, HttpSession session) {
		jobPostService.saveJobPost(jobPost);
		return "redirect:/jobpost/" + id;
	}

	@GetMapping("/{id}/delete")
	public String deleteJobPost(@PathVariable String id) {
		JobPost jobPost = jobPostService.findJobPostById(id);
		jobPostService.delete(jobPost);
		return "redirect:/jobpost/list";
	}

	@PostMapping("/update")
	public String updateJobPost(JobPost jobPost, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		String id = jobPost.getId().toString();
		JobPost toUpdateJobPost = jobPostService.findJobPostById(id);
		toUpdateJobPost.setActualStartDateTime(jobPost.getActualStartDateTime());
		toUpdateJobPost.setActualEndDateTime(jobPost.getActualEndDateTime());
		toUpdateJobPost.setAdditionalRemarks(jobPost.getAdditionalRemarks());
		toUpdateJobPost.setStatus(jobPost.getStatus());
		jobPostService.saveJobPost(toUpdateJobPost);
		addRemarksService.createJobPostAdditionalRemarks(RemarksCategory.GENERAL, user, toUpdateJobPost,
				jobPost.getAdditionalRemarks());
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
		addRemarksService.createJobPostAdditionalRemarks(RemarksCategory.CANCELLATION, user, jp,
				jobPost.getAdditionalRemarks());
		sendCancelJobEmail(jobPost);
		return "redirect:/jobpost/" + id;
	}

	@PostMapping(value = "/update", params = "confirm-locum")
	public String confirmJobPostForm(JobPost jobPost, HttpSession session) {
		User user = (User) session.getAttribute("user");
		String id = jobPost.getId().toString();
		JobPost jp = jobPostService.findJobPostById(id);
		jp.setStatus(JobStatus.ACCEPTED);
		jp.setAdditionalRemarks(jobPost.getAdditionalRemarks());
		jobPostService.saveJobPost(jp);
		addRemarksService.createJobPostAdditionalRemarks(RemarksCategory.ACCEPTION, user, jp,
				jobPost.getAdditionalRemarks());
		sendConfirmEmail(jobPost);
		return "redirect:/jobpost/" + id;
	}

	@PostMapping(value = "/update", params = "pending-payment")
	public String setPendingPaymentJobPostForm(JobPost jobPost, HttpSession session) {
		User user = (User) session.getAttribute("user");
		String id = jobPost.getId().toString();
		JobPost jp = jobPostService.findJobPostById(id);
		jp.setStatus(JobStatus.COMPLETED_PENDING_PAYMENT);
		jp.setActualStartDateTime(jobPost.getActualStartDateTime());
		jp.setActualEndDateTime(jobPost.getActualEndDateTime());
		jp.setAdditionalRemarks(jobPost.getAdditionalRemarks());
		jobPostService.saveJobPost(jp);
		addRemarksService.createJobPostAdditionalRemarks(RemarksCategory.COMPLETED_JOB, user, jp,
				jobPost.getAdditionalRemarks());
		return "redirect:/jobpost/" + id;
	}

	@PostMapping(value = "/update", params = "payment-processed")
	public String setPaymentProcessedJobPostForm(JobPost jobPost, HttpSession session) {
		User user = (User) session.getAttribute("user");
		String id = jobPost.getId().toString();
		JobPost jp = jobPostService.findJobPostById(id);
		jp.setStatus(JobStatus.COMPLETED_PAYMENT_PROCESSED);
		jp.setAdditionalRemarks(jobPost.getAdditionalRemarks());
		jobPostService.saveJobPost(jp);
		addRemarksService.createJobPostAdditionalRemarks(RemarksCategory.PROCESSED_PAYMENT, user, jp,
				jobPost.getAdditionalRemarks());
		return "redirect:/jobpost/" + id;
	}

	@PostMapping("/additional")
	public String createManyJobPostAdditional(ManyAdditionalFeeDetailsForm manyAdditionalFeeDetailsForm) {
		List<AdditionalFeeDetails> manyFeeDetails = manyAdditionalFeeDetailsForm.getAdditionalFeeDetails();
		String jobPost_id = manyAdditionalFeeDetailsForm.getJobPostId();
		JobPost jobPost = jobPostService.findJobPostById(jobPost_id);

		if (manyFeeDetails != null) {
			int i = 0;
			while (i < manyFeeDetails.size()) {
				if (manyFeeDetails.get(i).getDescription() != null
						&& manyFeeDetails.get(i).getAdditionalFeesAmount() > 0) {
					manyFeeDetails.get(i).setJobPost(jobPost);
				}
				i++;
			}
			jobPost.setAdditionalFeeDetails(manyFeeDetails);
		}

		jobPostService.saveJobPost(jobPost);

		return "redirect:/jobpost/" + jobPost.getId();
	}

	@GetMapping("/{post_id}/additional/{id}/delete")
	public String deleteAdditionalFee(@PathVariable String post_id, @PathVariable String id) {
		AdditionalFeeDetails feeDetails = additionalFeeDetailsService.getAdditionalFeeDetailsById(id);
		additionalFeeDetailsService.delete(feeDetails);
		return "redirect:/jobpost/" + post_id;
	}

	@GetMapping("/{id}/adminremove")
	public String deleteJobPost(@PathVariable String id, Model model) {
		JobPost jobPost = jobPostService.findJobPostById(id);
		model.addAttribute("jobPost", jobPost);
		model.addAttribute("additionalRemarks", new JobAdditionalRemarks());
		return "admin_jobpost_delete";
	}

	@PostMapping("/{id}/confirmadminremove")
	public String confirmDeleteJobPost(@PathVariable String id, JobAdditionalRemarks additionalRemarks) {
		System.out.println("Delete id:" + id);
		JobPost jobPost = jobPostService.findJobPostById(id);
		User user = userService.findById(Long.parseLong("4"));
		jobPostService.remove(jobPost, additionalRemarks, user);
		return "redirect:/jobpost/list";
	}

	private String sendConfirmEmail(JobPost jobpost) {
		jobpost = jobPostService.findJobPostById(jobpost.getId().toString());
		EmailDetails testEmail = new EmailDetails();
		User user = userService.findById(jobpost.getFreelancer().getId());
		testEmail.setRecipient(user.getEmail());
		testEmail.setSubject("Confirm Job:" + jobpost.getTitle());
		String emailMessage = "Dear {0}, \n\nYour application for {1} has been confirmed. \n\nIf this request did not come from you, please inform us immediately.\n\nYours Sincerely,\nSG Locum Administrator";
		String formattedEmail = java.text.MessageFormat.format(emailMessage, user.getName(), jobpost.getTitle());
		testEmail.setMsgBody(formattedEmail);
		String status = emailService.sendSimpleMail(testEmail);
		System.out.println(status);
		return status;
	}

	private String sendCancelJobEmail(JobPost jobpost) {
		jobpost = jobPostService.findJobPostById(jobpost.getId().toString());
		EmailDetails testEmail = new EmailDetails();
		User user = userService.findById(jobpost.getFreelancer().getId());
		testEmail.setRecipient(user.getEmail());
		testEmail.setSubject("Cancel Job:" + jobpost.getTitle());
		String emailMessage = "Dear {0}, \n\nYour application for {1} has been cancelled. \n\nIf this request did not come from you, please inform us immediately.\n\nYours Sincerely,\nSG Locum Administrator";
		String formattedEmail = java.text.MessageFormat.format(emailMessage, user.getName(), jobpost.getTitle());
		testEmail.setMsgBody(formattedEmail);
		String status = emailService.sendSimpleMail(testEmail);
		System.out.println(status);
		return status;
	}
}