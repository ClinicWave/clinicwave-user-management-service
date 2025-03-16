package com.clinicwave.clinicwaveusermanagementservice.config;

import com.clinicwave.clinicwaveusermanagementservice.dto.NotificationRequestDto;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the KafkaProducerConfig class.
 * It tests the configuration of the ProducerFactory and KafkaTemplate beans.
 *
 * @author aamir on 8/25/24
 */
@SpringBootTest
@AllArgsConstructor
class KafkaProducerConfigTest {
  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  private final KafkaProducerConfig kafkaProducerConfig;
  private final ProducerFactory<String, NotificationRequestDto> producerFactory;

  @MockBean
  private KafkaTemplate<String, NotificationRequestDto> kafkaTemplate;

  @Test
  @DisplayName("Test KafkaProducerConfig bean")
  void testKafkaProducerConfig() {
    assertNotNull(kafkaProducerConfig);
  }

  @Test
  @DisplayName("Test KafkaProducerConfig properties")
  void testProducerFactoryConfiguration() {
    assertNotNull(producerFactory);

    // Test if the ProducerFactory is correctly configured
    Map<String, Object> configs = producerFactory.getConfigurationProperties();

    assertEquals(bootstrapServers, configs.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
    assertEquals(StringSerializer.class, configs.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
    assertEquals(JsonSerializer.class, configs.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
    assertEquals("notificationRequest:com.clinicwave.clinicwaveusermanagementservice.dto.NotificationRequestDto",
            configs.get(JsonSerializer.TYPE_MAPPINGS));
  }

  @Test
  @DisplayName("Test KafkaTemplate properties")
  void testKafkaTemplateConfiguration() {
    assertNotNull(kafkaTemplate);
  }

  /**
   * This test demonstrates that the KafkaTemplate can serialize and send a message
   * Note: This doesn't actually send a message to Kafka, it just checks if the operation doesn't throw an exception
   */
  @Test
  @DisplayName("Test KafkaTemplate send message")
  void testSendMessage() {
    NotificationRequestDto dto = new NotificationRequestDto("test@example.com", "Test Subject", "Test Template", Map.of(), null, null);
    assertDoesNotThrow(() -> kafkaTemplate.send("test-topic", dto));
  }
}