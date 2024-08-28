package com.clinicwave.clinicwaveusermanagementservice.config;

import com.clinicwave.clinicwaveusermanagementservice.dto.NotificationRequestDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for configuring the Kafka Producer.
 * It sets the bootstrap servers, key serializer and value serializer.
 *
 * @author aamir on 8/21/24
 */
@Configuration
public class KafkaProducerConfig {
  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  /**
   * This method creates a ProducerFactory object with the configuration properties.
   *
   * @return ProducerFactory object
   */
  @Bean
  public ProducerFactory<String, NotificationRequestDto> producerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    configProps.put(JsonSerializer.TYPE_MAPPINGS, "notificationRequest:com.clinicwave.clinicwaveusermanagementservice.dto.NotificationRequestDto");
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  /**
   * This method creates a KafkaTemplate object with the ProducerFactory object.
   *
   * @return KafkaTemplate object
   */
  @Bean
  public KafkaTemplate<String, NotificationRequestDto> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
}
