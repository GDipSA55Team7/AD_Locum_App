package sg.edu.nus.iss.AD_Locum_Doctors.interceptor;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.AD_Locum_Doctors.model.User;

@Component
public class SessionInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException {
		HttpSession session = request.getSession();
		if (!request.getRequestURI().equals("/login")) {
			if (request.getRequestURI().equals("/home/authenticate")) {
				return true;
			}
			if (session.getAttribute("user") == null) {
				response.sendRedirect("/login");
				return false;
			} else {
				return true;
			}
		} else {
			if (session.getAttribute("user") == null) {
				return true;
			} else {
				User u = (User) session.getAttribute("user");
				switch (u.getRole().getName()) {
					case "System_Admin":
						response.sendRedirect("/system-admin");
						break;
					case "Clinic_Admin":
						response.sendRedirect("/clinic-admin");
						break;
					case "Clinic_User":
						response.sendRedirect("/clinic-user");
						break;
				}
				return false;
			}
		}
	}
}
