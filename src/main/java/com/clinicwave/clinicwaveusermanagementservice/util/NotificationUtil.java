package com.clinicwave.clinicwaveusermanagementservice.util;

import com.clinicwave.clinicwaveusermanagementservice.enums.NotificationTypeEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.VerificationCodeTypeEnum;

/**
 * This utility class provides helper methods for working with notifications.
 *
 * @author aamir on 8/2/24
 */
public class NotificationUtil {
  /**
   * Private constructor to prevent instantiation.
   */
  private NotificationUtil() {
  }

  /**
   * This method returns the notification type for a given verification code type.
   *
   * @param verificationCodeType the verification code type
   * @return the notification type
   */
  public static NotificationTypeEnum getNotificationTypeForVerification(VerificationCodeTypeEnum verificationCodeType) {
    return switch (verificationCodeType) {
      case PHONE_VERIFICATION, PHONE_NUMBER_CHANGE -> NotificationTypeEnum.SMS;
      default -> NotificationTypeEnum.EMAIL;
    };
  }

  /**
   * This method returns the subject for a given verification code type.
   *
   * @param type the verification code type
   * @return the subject for the verification code
   */
  public static String getSubjectForVerificationType(VerificationCodeTypeEnum type) {
    return switch (type) {
      case EMAIL_VERIFICATION -> "Verify Your Email";
      case PASSWORD_RESET -> "Reset Your Password";
      case TWO_FACTOR_AUTHENTICATION -> "Two-Factor Authentication Code";
      case ACCOUNT_DELETION -> "Confirm Account Deletion Request";
      case EMAIL_CHANGE -> "Confirm Email Address Change";
      default -> "Action Required: Verification Needed";
    };
  }

  /**
   * This method returns the template name for a given verification code type.
   *
   * @param type the verification code type
   * @return the template name for the verification code
   */
  public static String getTemplateNameForVerificationType(VerificationCodeTypeEnum type) {
    return switch (type) {
      case EMAIL_VERIFICATION -> "email-verification";
      case PASSWORD_RESET -> "password-reset";
      case TWO_FACTOR_AUTHENTICATION -> "two-factor-authentication";
      case ACCOUNT_DELETION -> "account-deletion";
      case EMAIL_CHANGE -> "email-change";
      default -> "generic-verification";
    };
  }
}
