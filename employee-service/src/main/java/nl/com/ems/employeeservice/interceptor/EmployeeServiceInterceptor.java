package nl.com.ems.employeeservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import nl.com.ems.employeeservice.exception.ForbiddenException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;

@Slf4j
@Component
public class EmployeeServiceInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.debug("Request Method: {}, Endpoint: {} ", request.getMethod(), request.getRequestURI());

        if (log.isDebugEnabled()) {
            logRequestHeaders(request);
        }

        String role = request.getHeader("Role");
        if (role != null && !role.isEmpty()) {
            role = role.toUpperCase();
        }
        String requestURI = request.getRequestURI();
        log.info("Request URI: {}", requestURI);

        if (requestURI.contains("/employees")) {
            String method = request.getMethod();
            if ("POST".equals(method) || "DELETE".equals(method)) {
                if (!"ADMIN".equals(role)) {
                    throw new ForbiddenException("You are not authorized to perform this operation");
                }
            } else if ("GET".equals(method) || "PUT".equals(method)) {
                if (!"ADMIN".equals(role) && !"USER".equals(role)) {
                    throw new ForbiddenException("You are not authorized to perform this operation");
                }
            }

        }
        return true;
    }


    private void logRequestHeaders(HttpServletRequest request) {
        final Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                final String headerName = headerNames.nextElement();
                final String headerValue = request.getHeader(headerName);
                log.debug("Header: {} = {}", headerName, headerValue);
            }
        }
    }

}
