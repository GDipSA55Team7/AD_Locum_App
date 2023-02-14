package sg.edu.nus.iss.AD_Locum_Doctors.controllers;

import java.util.ArrayList;
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
import sg.edu.nus.iss.AD_Locum_Doctors.model.Clinic;
import sg.edu.nus.iss.AD_Locum_Doctors.model.Organization;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;
import sg.edu.nus.iss.AD_Locum_Doctors.service.OrganizationService;
import sg.edu.nus.iss.AD_Locum_Doctors.service.UserService;

@Controller
@RequestMapping("/organization")
public class OrganizationController {
	
    @Autowired
    HttpSession session;
	
    private User user;
    
    private void loadReference() {
        user = (User) session.getAttribute("user");
    }

	@Autowired
	private OrganizationService organizationService;
	
    @Autowired
    UserService userService;
	
	@GetMapping("/list")
	public String organizationListPage(Model model) {
		List<Organization> organizations = new ArrayList<>();
		organizations = organizationService.getAllOrganizations().stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
		model.addAttribute("orgList", organizations);
		return "organization_list";
	}
	
    @GetMapping("/viewDetails/{id}")
    public String viewOrganization(Model model, @PathVariable(value = "id") Long id) {
    	loadReference();
    	Organization org = organizationService.findById(id);
    	List<Clinic> clinics = org.getClinics();
        model.addAttribute("org", org);
        model.addAttribute("clinicList", clinics);
        return "edit_org_details";
    }
    
    @PostMapping("/editOrgDetails/{id}")
    public String editOrgDetails(Organization org) {
    	organizationService.updateOrganizationDetails(org);
        return "redirect:/organization/list";
    }
    
    @GetMapping("/disable/{id}")
    public String disableOrganizationAndUsers(Model model, @PathVariable(value = "id") Long id) {
    	Organization org = organizationService.findById(id);
    	org.setActive(false);
    	organizationService.updateOrganizationDetails(org);
    	List<User> orgUsers = org.getUsers();
    	for (User _user: orgUsers) {
    		_user.setActive(false);	
    		userService.deactivateUser(_user);
    	}
        return "redirect:/organization/list";
    }
    
    @GetMapping("/activate/{id}")
    public String reactivateOrganizationAndUsers(Model model, @PathVariable(value = "id") Long id) {
    	Organization org = organizationService.findById(id);
    	org.setActive(true);
    	organizationService.updateOrganizationDetails(org);
    	List<User> orgUsers = org.getUsers();
    	for (User _user: orgUsers) {
    		_user.setActive(true);	
    		userService.reactivateUser(_user);
    	}
        return "redirect:/organization/list";
    }

}
