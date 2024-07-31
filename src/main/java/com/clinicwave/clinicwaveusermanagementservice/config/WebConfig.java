package com.clinicwave.clinicwaveusermanagementservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up web-related configurations.
 * Implements the WebMvcConfigurer interface to customize the default Spring MVC configuration.
 * <p>
 * This configuration specifically sets up CORS (Cross-Origin Resource Sharing) mappings.
 *
 * @author aamir on 7/31/24
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
  /**
   * The base URL of the Clinicwave User Management frontend application.
   * This value is injected from the application's properties file.
   */
  @Value("${clinicwave-user-management-frontend-base-url}")
  private String clinicwaveUserManagementFrontendBaseUrl;

  /**
   * Configures CORS mappings.
   *
   * @param registry the CorsRegistry to add mappings to
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins(clinicwaveUserManagementFrontendBaseUrl)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
  }
}