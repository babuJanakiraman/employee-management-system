package nl.com.ems.employeeservice.employeedata;

import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.Base64;

@Configuration
public class EmployeeDatabaseServiceConfig {

    @Bean
    public CloseableHttpClient getHttpClient(EmployeeDatabaseConnectionProperties employeeDatabaseConnectionProperties) {

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(employeeDatabaseConnectionProperties.getMaxHttpPoolSize());
        connectionManager.setDefaultMaxPerRoute(employeeDatabaseConnectionProperties.getMaxHttpPoolSize());
        connectionManager.setDefaultConnectionConfig(ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(employeeDatabaseConnectionProperties.getConnectionTimeoutInMillis()))
                .setSocketTimeout(Timeout.ofMilliseconds(employeeDatabaseConnectionProperties.getReadTimeoutInMillis()))
                .build());

        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectionRequestTimeout(Timeout.ofMilliseconds(employeeDatabaseConnectionProperties.getConnectionTimeoutInMillis()))
                        .setResponseTimeout(Timeout.ofMilliseconds(employeeDatabaseConnectionProperties.getReadTimeoutInMillis()))
                        .setConnectionKeepAlive(TimeValue.ofMilliseconds(employeeDatabaseConnectionProperties.getKeepAliveStrategy()))
                        .build())
                .disableConnectionState()
                .build();
    }

    @Bean
    public RestClient getRestClient(EmployeeDatabaseConnectionProperties employeeDatabaseConnectionProperties, CloseableHttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return RestClient.builder()
                .baseUrl(employeeDatabaseConnectionProperties.getUrl())
                .requestFactory(factory)
                .defaultHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((employeeDatabaseConnectionProperties.getUsername() + ":" + employeeDatabaseConnectionProperties.getPassword()).getBytes()))
                .build();
    }

}