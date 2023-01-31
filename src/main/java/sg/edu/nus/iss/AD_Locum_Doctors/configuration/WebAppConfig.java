package sg.edu.nus.iss.AD_Locum_Doctors.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import sg.edu.nus.iss.AD_Locum_Doctors.interceptor.SessionInterceptor;

@Component
public class WebAppConfig implements WebMvcConfigurer {
	@Autowired
	SessionInterceptor sessionInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sessionInterceptor).excludePathPatterns("/api/**", "/css/**", "/dist/**", "/bootstrap/**",
				"/plugins/**", "/registration", "/register/**");
	}
}