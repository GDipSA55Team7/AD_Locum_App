package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobAdditionalRemarks;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobStatus;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Organization;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.service.JobPostAdditionalRemarksService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.JobPostService;

@Controller
@RequestMapping(value = { "/dashboard" })
public class DashboardController {

	@Autowired
	JobPostService jobPostService;
	@Autowired
	JobPostAdditionalRemarksService remarksService;

	static int count = 0;

	@GetMapping("/clinic")
	public String dashboard(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		Organization org = user.getOrganization();
		List<JobPost> jobPosts = jobPostService.findAll().stream()
				.filter(x -> x.getClinicUser().getOrganization().getId() == org.getId()).toList();
		// Job Postings Status
		List<Integer> jobPostCountByStatus = new ArrayList<>();
		List<JobStatus> jobStatusList = Stream.of(JobStatus.values()).filter(s -> !s.equals(JobStatus.DELETED))
				.toList();
		jobStatusList.forEach(s -> {
			count = 0;
			jobPosts.forEach(j -> {
				if (j.getStatus() == s) {
					count++;
				}
			});
			if (count != 0) {
				jobPostCountByStatus.add(count);
			}
		});
		model.addAttribute("jobStatusList", jobStatusList);
		model.addAttribute("jobStatusData", jobPostCountByStatus);
		// Latest Job Posts
		model.addAttribute("latestJobPosts", jobPosts.stream().limit(8).toList());
		// User Recent Activities
		Map<LocalDate, List<JobAdditionalRemarks>> dateTimeToRemarks = remarksService.findAll().stream()
				.filter(x -> x.getJobPost().getClinicUser().getId() == user.getId())
				.sorted(Comparator.comparing(JobAdditionalRemarks::getDateTime).reversed()).limit(10)
				.collect(Collectors.groupingBy(JobAdditionalRemarks::getDateOnly));
		model.addAttribute("recentActivities", dateTimeToRemarks);
		return "home-clinic";
	}
}
