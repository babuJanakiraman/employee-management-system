package nl.com.ems.employeeservice.config;

import lombok.AllArgsConstructor;
import nl.com.ems.employeeservice.interceptor.EmployeeServiceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final EmployeeServiceInterceptor employeeServiceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(employeeServiceInterceptor);
    }
}
