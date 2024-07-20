package com.clinicwave.clinicwaveusermanagementservice.client.impl;

import com.clinicwave.clinicwaveusermanagementservice.client.NotificationServiceClient;
import com.clinicwave.clinicwaveusermanagementservice.dto.NotificationRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * This class implements the NotificationServiceClient interface and provides methods to send notifications.
 * It uses a RestTemplate to send HTTP requests to the notification service.
 *
 * @author aamir on 7/14/24
 */
@Service
@Slf4j
public class NotificationServiceClientImpl implements NotificationServiceClient {
  private final RestTemplate restTemplate;

  /**
   * Constructor for dependency injection.
   *
   * @param restTemplate The RestTemplate to be used for sending HTTP requests.
   */
  @Autowired
  public NotificationServiceClientImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Sends a notification to the notification service.
   *
   * @param notificationRequestDto The notification request to be sent.
   */
  @Override
  public void sendNotification(NotificationRequestDto notificationRequestDto) {
    try {
      String baseUrl = "http://localhost:8081/";
      String endpoint = "api/notifications/send";

      restTemplate.postForObject(
              baseUrl + endpoint,
              notificationRequestDto,
              Void.class
      );
      log.info("Notification sent successfully to {}", notificationRequestDto.recipient());
    } catch (RestClientException e) {
      log.error("Failed to send notification to {}: {}", notificationRequestDto.recipient(), e.getMessage());
    }
  }
}
