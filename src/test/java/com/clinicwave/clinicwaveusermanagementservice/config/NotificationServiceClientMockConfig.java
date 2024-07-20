package com.clinicwave.clinicwaveusermanagementservice.config;

import com.clinicwave.clinicwaveusermanagementservice.client.NotificationServiceClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

/**
 * This class provides a test configuration for the application.
 * It uses the Spring Boot testing framework to configure the application context for testing.
 *
 * @author aamir on 7/18/24
 */
@TestConfiguration
public class NotificationServiceClientMockConfig {
  @Bean
  @Primary
  public NotificationServiceClient mockNotificationServiceClient() {
    return mock(NotificationServiceClient.class);
  }
}
