package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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
import sg.edu.nus.iss.AD_Locum_Doctors.model.AverageDailyRate;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobAdditionalRemarks;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobPost;
import sg.edu.nus.iss.AD_Locum_Doctors.model.JobStatus;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Organization;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.service.AverageDailyRateService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.JobPostAdditionalRemarksService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.JobPostService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.OrganizationService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.UserService;

@Controller
@RequestMapping(value = { "/dashboard" })
public class DashboardController {
	@Autowired
	UserService userService;
	@Autowired
	OrganizationService orgService;
	@Autowired
	JobPostService jobPostService;
	@Autowired
	JobPostAdditionalRemarksService remarksService;
	@Autowired
	AverageDailyRateService averageDailyRateService;

	static int count = 0;
	static List<JobPost> jobPostList;

	@GetMapping("/clinic")
	public String dashboard(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		Organization org = user.getOrganization();
		List<JobPost> jobPosts = new ArrayList<>();
		jobPosts = jobPostService.findAll().stream()
				.filter(x -> x.getClinicUser().getOrganization().getId() == org.getId()).collect(Collectors.toList());

		// Unfilled Jobs
		List<JobPost> openJobs = new ArrayList<>();
		openJobs = jobPosts.stream().filter(x -> x.getStatus().equals(JobStatus.OPEN))
				.collect(Collectors.toList());
		model.addAttribute("unfilledJobs", openJobs.size());

		// Upcoming Job Bookings
		List<JobPost> upcomingJobs = new ArrayList<>();
		upcomingJobs = jobPosts.stream().filter(x -> x.getStatus().equals(JobStatus.ACCEPTED))
				.collect(Collectors.toList());
		model.addAttribute("upcomingJobs", upcomingJobs.size());

		// Cancellation Rate
		List<JobPost> cancelledJobs = new ArrayList<>();
		cancelledJobs = jobPosts.stream().filter(x -> x.getStatus().equals(JobStatus.CANCELLED))
				.collect(Collectors.toList());
		List<JobPost> jobsNotOpenOrRemoved = new ArrayList<>();
		jobsNotOpenOrRemoved = jobPosts.stream().filter(x -> !x.getStatus().equals(JobStatus.OPEN))
				.filter(x -> !x.getStatus().equals(JobStatus.REMOVED)).collect(Collectors.toList());
		Double cancellationRate = 0.0;
		if (cancelledJobs.size() != 0) {
			cancellationRate = (double) Math.round((cancelledJobs.size() * 100 * 10 / jobsNotOpenOrRemoved.size()))
					/ 10;
		}
		model.addAttribute("cancellationRate", cancellationRate);

		// TODO: Average Job Take Up Speed

		model.addAttribute("averageTakeUpSpeed", "");

		// Job Postings Status
		jobPostList = new ArrayList<JobPost>(jobPosts);
		List<Integer> jobPostCountByStatus = new ArrayList<>();
		List<JobStatus> jobStatusList = Stream.of(JobStatus.values()).filter(s -> !s.equals(JobStatus.DELETED))
				.toList();
		jobStatusList.forEach(s -> {
			count = 0;
			jobPostList.forEach(j -> {
				if (j.getStatus() == s) {
					count++;
				}
			});
			jobPostCountByStatus.add(count);
		});
		model.addAttribute("jobStatusList", jobStatusList);
		model.addAttribute("jobStatusData", jobPostCountByStatus);

		// Time Series Chart
		List<AverageDailyRate> adrList = new ArrayList<>();
		adrList = averageDailyRateService.getAverageDailyRates().stream()
				.sorted(Comparator.comparing(AverageDailyRate::getDate)).collect(Collectors.toList());
		List<LocalDate> dates = new ArrayList<>();
		List<Double> weekday_14MA = new ArrayList<>(), weekday_28MA = new ArrayList<>(),
				weekend_14MA = new ArrayList<>(), weekend_28MA = new ArrayList<>();
		for (AverageDailyRate adr : adrList) {
			dates.add(adr.getDate());
			var weekday_14MA_2dp = adr.getWeekday_14_MA() == null ? null
					: (double) Math.round(adr.getWeekday_14_MA() * 100) / 100;
			var weekday_28MA_2dp = adr.getWeekday_28_MA() == null ? null
					: (double) Math.round(adr.getWeekday_28_MA() * 100) / 100;
			var weekend_14MA_2dp = adr.getWeekend_14_MA() == null ? null
					: (double) Math.round(adr.getWeekend_14_MA() * 100) / 100;
			var weekend_28MA_2dp = adr.getWeekend_28_MA() == null ? null
					: (double) Math.round(adr.getWeekend_28_MA() * 100) / 100;
			weekday_14MA.add(weekday_14MA_2dp);
			weekday_28MA.add(weekday_28MA_2dp);
			weekend_14MA.add(weekend_14MA_2dp);
			weekend_28MA.add(weekend_28MA_2dp);
		}
		model.addAttribute("dates", dates);
		model.addAttribute("weekday_14MA", weekday_14MA);
		model.addAttribute("weekday_28MA", weekday_28MA);
		model.addAttribute("weekend_14MA", weekend_14MA);
		model.addAttribute("weekend_28MA", weekend_28MA);

		// Latest Job Posts
		model.addAttribute("latestJobPosts", jobPosts.stream().filter(x -> x.getDateTimeModified() != null)
				.sorted(Comparator.comparing(JobPost::getDateTimeModified).reversed()).limit(8)
				.collect(Collectors.toList()));

		// User Recent Activities
		Map<LocalDate, List<JobAdditionalRemarks>> dateTimeToRemarks = new HashMap<>();
		dateTimeToRemarks = remarksService.findAll().stream()
				.filter(x -> x.getJobPost().getClinicUser().getId() == user.getId())
				.filter(x -> x.getDateTime() != null)
				.sorted(Comparator.comparing(JobAdditionalRemarks::getDateTime).reversed()).limit(10)
				.collect(Collectors.groupingBy(JobAdditionalRemarks::getDateOnly));
		model.addAttribute("recentActivities", dateTimeToRemarks);
		return "home-clinic";
	}

	@GetMapping("/system-admin")
	public String systemAdminDashboard(Model model, HttpSession session) {
		List<User> users = userService.findAll().stream()
				.sorted(Comparator.comparing(User::getDateRegistered).reversed()).collect(Collectors.toList());
		Month currentMonth = LocalDate.now().getMonth();
		List<Organization> organizations = orgService.getAllOrganizations();
		// New Locums Registered for current month
		List<User> currentMonthUsers = users.stream().filter(x -> x.getDateRegistered().getMonth() == currentMonth)
				.collect(Collectors.toList());
		var registeredLocumsThisMonth = currentMonthUsers.stream()
				.filter(x -> x.getRole().getName().equals("Locum_Doctor"))
				.collect(Collectors.toList()).size();
		model.addAttribute("registeredLocumsThisMonth", registeredLocumsThisMonth);

		// New Clinic Users Registered for current month
		var registeredClinicUsersThisMonth = currentMonthUsers.stream()
				.filter(x -> !x.getRole().getName().equals("System_Admin"))
				.filter(x -> !x.getRole().getName().equals("Locum_Doctor")).collect(Collectors.toList()).size();
		model.addAttribute("registeredClinicUsersThisMonth", registeredClinicUsersThisMonth);

		// New Organizations Registered for current month
		var registeredOrgThisMonth = organizations.stream()
				.filter(x -> x.getDateRegistered().getMonth().equals(currentMonth)).collect(Collectors.toList()).size();
		model.addAttribute("registeredOrgThisMonth", registeredOrgThisMonth);

		// Total Jobs Processed
		List<JobPost> jobPosts = jobPostService.findAll();
		var jobsProcessed = jobPosts.stream().filter(x -> x.getStatus().equals(JobStatus.COMPLETED_PAYMENT_PROCESSED))
				.collect(Collectors.toList()).size();
		model.addAttribute("jobTotals", jobPosts.size());
		model.addAttribute("jobsProcessed", jobsProcessed);

		// Pie Chart - User Category
		List<String> userCategories = new ArrayList<>();
		userCategories.add("Locum Doctors");
		userCategories.add("Clinic Users");
		Integer locumTotals = 0, clinicUserTotals = 0;
		locumTotals = users.stream().filter(x -> x.getRole().getName().equals("Locum_Doctor"))
				.collect(Collectors.toList()).size();
		clinicUserTotals = users.stream().filter(x -> !x.getRole().getName().equals("Locum_Doctor"))
				.filter(x -> !x.getRole().getName().equals("System_Admin"))
				.collect(Collectors.toList()).size();
		List<Integer> totalsByUserCategories = new ArrayList<>();
		totalsByUserCategories.add(locumTotals);
		totalsByUserCategories.add(clinicUserTotals);
		model.addAttribute("userCategories", userCategories);
		model.addAttribute("totalsByUserCategories", totalsByUserCategories);

		// Line Chart - Number of New Users Registered Monthly
		int currentYear = LocalDate.now().getYear();
		int currentMonthValue = LocalDate.now().getMonthValue();
		List<String> past12MonthsList = new ArrayList<>();
		List<LocalDate> past12MonthsDateList = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			if (currentMonthValue == 0) {
				currentYear--;
				currentMonthValue += 12;
			}
			past12MonthsList.add(0, String.valueOf(currentYear) + '-' + String.format("%02d", currentMonthValue));
			past12MonthsDateList.add(LocalDate.of(currentYear, currentMonthValue, 1));
			currentMonthValue--;
		}
		List<Integer> locumCounts = new ArrayList<>();
		List<Integer> clinicUserCounts = new ArrayList<>();
		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		for (int i = 0; i < past12MonthsDateList.size(); i++) {
			LocalDate date = past12MonthsDateList.get(i);
			int locumCountPerMonth = users.stream().filter(x -> x.getDateRegistered().getYear() == date.getYear()
					&& x.getDateRegistered().getMonth() == date.getMonth()
					&& x.getRole().getName().equals("Locum_Doctor")).collect(Collectors.toList()).size();
			locumCounts.add(locumCountPerMonth);
			int clinicUserCountPerMonth = users.stream().filter(x -> x.getDateRegistered().getYear() == date.getYear()
					&& x.getDateRegistered().getMonth() == date.getMonth()
					&& !x.getRole().getName().equals("Locum_Doctor") && !x.getRole().getName().equals("System_Admin"))
					.collect(Collectors.toList()).size();
			clinicUserCounts.add(clinicUserCountPerMonth);
		}
		model.addAttribute("userChartDates", past12MonthsList);
		model.addAttribute("clinicUserCounts", clinicUserCounts);
		model.addAttribute("locumCounts", locumCounts);

		// Latest Organizations
		var latestOrganizations = organizations.stream()
				.sorted(Comparator.comparing(Organization::getDateRegistered).reversed()).limit(10)
				.collect(Collectors.toList());
		model.addAttribute("latestOrganizations", latestOrganizations);

		// Latest Job Posts
		model.addAttribute("latestJobPosts", jobPosts.stream().filter(x -> x.getDateTimeModified() != null)
				.sorted(Comparator.comparing(JobPost::getDateTimeModified).reversed()).limit(8)
				.collect(Collectors.toList()));
		return "home-system-admin";
	}
}
