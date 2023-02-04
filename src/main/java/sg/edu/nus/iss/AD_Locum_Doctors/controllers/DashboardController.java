package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobAdditionalRemarks;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.JobAdditionalRemarksRepository;
import sg.edu.nus.iss.AD_Locum_Doctors.repository.JobPostRepository;

@Controller
@RequestMapping(value = { "/dashboard" })
public class DashboardController {

	@Autowired
	JobPostRepository jobPostRepo;
	@Autowired
	JobAdditionalRemarksRepository remarksRepo;

	@GetMapping("/clinic-admin")
	public String dashboard(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		Map<LocalDate, List<JobAdditionalRemarks>> dateTimeToRemarks = remarksRepo.findAll().stream()
				.filter(x -> x.getJobPost().getClinicUser().getId() == user.getId())
				.sorted(Comparator.comparing(JobAdditionalRemarks::getDateTime).reversed()).limit(10)
				.collect(Collectors.groupingBy(JobAdditionalRemarks::getDateOnly));
		model.addAttribute("recentActivities", dateTimeToRemarks);
		return "home-clinic-admin";
	}
}
