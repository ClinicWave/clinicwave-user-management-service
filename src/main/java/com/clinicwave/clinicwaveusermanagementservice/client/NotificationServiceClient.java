package com.clinicwave.clinicwaveusermanagementservice.client;

import com.clinicwave.clinicwaveusermanagementservice.dto.NotificationRequestDto;

/**
 * This interface defines the methods for sending notifications.
 *
 * @author aamir on 7/14/24
 */
public interface NotificationServiceClient {
  void sendNotification(NotificationRequestDto notificationRequestDto);
}
