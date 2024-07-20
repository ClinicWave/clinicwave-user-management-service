package com.clinicwave.clinicwaveusermanagementservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * This class provides a configuration for the RestTemplate bean.
 *
 * @author aamir on 7/11/24
 */
@Configuration
public class RestTemplateConfig {
  /**
   * Creates a RestTemplate bean.
   *
   * @return The RestTemplate bean.
   */
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
