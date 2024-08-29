package com.clinicwave.clinicwaveusermanagementservice.config;

import com.clinicwave.clinicwaveusermanagementservice.dto.NotificationRequestDto;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.mock;

/**
 * This class provides a mock KafkaTemplate bean for testing purposes.
 *
 * @author aamir on 8/25/24
 */
@TestConfiguration
public class KafkaTemplateMockConfig {
  /**
   * Creates a mock KafkaTemplate bean.
   *
   * @return a mock KafkaTemplate bean
   */
  @Bean
  @Primary
  public KafkaTemplate<String, NotificationRequestDto> mockKafkaTemplate() {
    return mock(KafkaTemplate.class);
  }
}
