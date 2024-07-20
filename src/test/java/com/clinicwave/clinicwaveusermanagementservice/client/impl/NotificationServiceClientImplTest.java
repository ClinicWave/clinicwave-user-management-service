package com.clinicwave.clinicwaveusermanagementservice.client.impl;

import com.clinicwave.clinicwaveusermanagementservice.dto.NotificationRequestDto;
import com.clinicwave.clinicwaveusermanagementservice.enums.NotificationCategoryEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.NotificationTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * This class contains unit tests for the NotificationServiceClientImpl class.
 *
 * @author aamir on 7/17/24
 */
@ExtendWith(MockitoExtension.class)
class NotificationServiceClientImplTest {
  private NotificationServiceClientImpl notificationServiceClient;

  private static final String BASE_URL = "http://localhost:8081/";
  private static final String ENDPOINT = "api/notifications/send";

  @Mock
  private RestTemplate restTemplate;

  private NotificationRequestDto notificationRequestDto;

  /**
   * Sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    notificationServiceClient = new NotificationServiceClientImpl(restTemplate);

    // Create a sample NotificationRequestDto
    Map<String, Object> templateParams = new HashMap<>();
    templateParams.put("userName", "John Doe");
    templateParams.put("verificationCode", "123456");

    notificationRequestDto = new NotificationRequestDto(
            "john.doe@example.com",
            "Account Verification",
            "verification-email-template",
            templateParams,
            NotificationTypeEnum.EMAIL,
            NotificationCategoryEnum.VERIFICATION
    );
  }

  @Test
  @DisplayName("Send notification with successful request")
  void sendNotification_SuccessfulRequest_NoExceptionThrown() {
    when(restTemplate.postForObject(anyString(), any(), eq(Void.class))).thenReturn(null);

    assertDoesNotThrow(() -> notificationServiceClient.sendNotification(notificationRequestDto));

    verify(restTemplate, times(1)).postForObject(
            BASE_URL + ENDPOINT,
            notificationRequestDto,
            Void.class
    );
  }

  @Test
  @DisplayName("Send notification handling RestClientException")
  void sendNotification_RestClientException_ExceptionPropagated() {
    when(restTemplate.postForObject(anyString(), any(), eq(Void.class)))
            .thenThrow(new RestClientException("Failed to send notification"));

    Exception exception = assertThrows(RestClientException.class,
            () -> notificationServiceClient.sendNotification(notificationRequestDto));

    assertEquals("Failed to send notification", exception.getMessage());
    verify(restTemplate, times(1)).postForObject(
            BASE_URL + ENDPOINT,
            notificationRequestDto,
            Void.class
    );
  }

  @Test
  @DisplayName("Send notification with different notification type")
  void sendNotification_DifferentNotificationType_CorrectDtoSent() {
    NotificationRequestDto smsNotificationDto = new NotificationRequestDto(
            "+1234567890",
            "SMS Notification",
            "sms-template",
            Map.of("message", "Your appointment is confirmed"),
            NotificationTypeEnum.SMS,
            NotificationCategoryEnum.GENERAL
    );

    when(restTemplate.postForObject(anyString(), any(), eq(Void.class))).thenReturn(null);

    assertDoesNotThrow(() -> notificationServiceClient.sendNotification(smsNotificationDto));

    verify(restTemplate, times(1)).postForObject(
            BASE_URL + ENDPOINT,
            smsNotificationDto,
            Void.class
    );
  }
}