package nl.com.ems.employeeservice.employeedata;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class EmployeeDatabaseConnectionProperties {

    @Value("${employeeDatabaseService.maxHttpPoolSize:20}")
    private int maxHttpPoolSize;

    @Value("${employeeDatabaseService.readTimeoutInMillis:3000}")
    private int readTimeoutInMillis;

    @Value("${employeeDatabaseService.connectionTimeoutInMillis:300}")
    private int connectionTimeoutInMillis;

    @Value("${employeeDatabaseService.url}")
    private String url;

    @Value("${employeeDatabaseService.keepAliveStrategy:5000}")
    private int keepAliveStrategy;

    @Value("${employeeDatabaseService.username}")
    private String username;

    @Value("${employeeDatabaseService.password}")
    private String password;

}
