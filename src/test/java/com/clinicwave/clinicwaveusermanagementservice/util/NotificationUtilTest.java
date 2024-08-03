package com.clinicwave.clinicwaveusermanagementservice.util;

import com.clinicwave.clinicwaveusermanagementservice.enums.NotificationTypeEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.VerificationCodeTypeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains tests for the NotificationUtil class.
 *
 * @author aamir on 8/2/24
 */
class NotificationUtilTest {
  @ParameterizedTest
  @EnumSource(value = VerificationCodeTypeEnum.class, names = {"PHONE_VERIFICATION", "PHONE_NUMBER_CHANGE"})
  @DisplayName("getNotificationTypeForVerification should return SMS for phone-related types")
  void getNotificationTypeForVerification_shouldReturnSMS_forPhoneRelatedTypes(VerificationCodeTypeEnum type) {
    assertEquals(NotificationTypeEnum.SMS, NotificationUtil.getNotificationTypeForVerification(type));
  }

  @ParameterizedTest
  @EnumSource(value = VerificationCodeTypeEnum.class, mode = EnumSource.Mode.EXCLUDE, names = {"PHONE_VERIFICATION", "PHONE_NUMBER_CHANGE"})
  @DisplayName("getNotificationTypeForVerification should return EMAIL for non-phone-related types")
  void getNotificationTypeForVerification_shouldReturnEMAIL_forNonPhoneRelatedTypes(VerificationCodeTypeEnum type) {
    assertEquals(NotificationTypeEnum.EMAIL, NotificationUtil.getNotificationTypeForVerification(type));
  }

  @Test
  @DisplayName("getSubjectForVerificationType should return correct subject for email verification")
  void getSubjectForVerificationType_shouldReturnCorrectSubject_forEmailVerification() {
    assertEquals("Verify Your Email", NotificationUtil.getSubjectForVerificationType(VerificationCodeTypeEnum.EMAIL_VERIFICATION));
  }

  @Test
  @DisplayName("getSubjectForVerificationType should return correct subject for password reset")
  void getSubjectForVerificationType_shouldReturnCorrectSubject_forPasswordReset() {
    assertEquals("Reset Your Password", NotificationUtil.getSubjectForVerificationType(VerificationCodeTypeEnum.PASSWORD_RESET));
  }

  @Test
  @DisplayName("getSubjectForVerificationType should return correct subject for two-factor authentication")
  void getSubjectForVerificationType_shouldReturnCorrectSubject_forTwoFactorAuthentication() {
    assertEquals("Two-Factor Authentication Code", NotificationUtil.getSubjectForVerificationType(VerificationCodeTypeEnum.TWO_FACTOR_AUTHENTICATION));
  }

  @Test
  @DisplayName("getSubjectForVerificationType should return correct subject for account deletion")
  void getSubjectForVerificationType_shouldReturnCorrectSubject_forAccountDeletion() {
    assertEquals("Confirm Account Deletion Request", NotificationUtil.getSubjectForVerificationType(VerificationCodeTypeEnum.ACCOUNT_DELETION));
  }

  @Test
  @DisplayName("getSubjectForVerificationType should return correct subject for email change")
  void getSubjectForVerificationType_shouldReturnCorrectSubject_forEmailChange() {
    assertEquals("Confirm Email Address Change", NotificationUtil.getSubjectForVerificationType(VerificationCodeTypeEnum.EMAIL_CHANGE));
  }

  @Test
  @DisplayName("getSubjectForVerificationType should return default subject for unknown type")
  void getSubjectForVerificationType_shouldReturnDefaultSubject_forUnknownType() {
    assertEquals("Action Required: Verification Needed", NotificationUtil.getSubjectForVerificationType(VerificationCodeTypeEnum.PHONE_VERIFICATION));
  }

  @Test
  @DisplayName("getTemplateNameForVerificationType should return correct template for email verification")
  void getTemplateNameForVerificationType_shouldReturnCorrectTemplate_forEmailVerification() {
    assertEquals("email-verification", NotificationUtil.getTemplateNameForVerificationType(VerificationCodeTypeEnum.EMAIL_VERIFICATION));
  }

  @Test
  @DisplayName("getTemplateNameForVerificationType should return correct template for password reset")
  void getTemplateNameForVerificationType_shouldReturnCorrectTemplate_forPasswordReset() {
    assertEquals("password-reset", NotificationUtil.getTemplateNameForVerificationType(VerificationCodeTypeEnum.PASSWORD_RESET));
  }

  @Test
  @DisplayName("getTemplateNameForVerificationType should return correct template for two-factor authentication")
  void getTemplateNameForVerificationType_shouldReturnCorrectTemplate_forTwoFactorAuthentication() {
    assertEquals("two-factor-authentication", NotificationUtil.getTemplateNameForVerificationType(VerificationCodeTypeEnum.TWO_FACTOR_AUTHENTICATION));
  }

  @Test
  @DisplayName("getTemplateNameForVerificationType should return correct template for account deletion")
  void getTemplateNameForVerificationType_shouldReturnCorrectTemplate_forAccountDeletion() {
    assertEquals("account-deletion", NotificationUtil.getTemplateNameForVerificationType(VerificationCodeTypeEnum.ACCOUNT_DELETION));
  }

  @Test
  @DisplayName("getTemplateNameForVerificationType should return correct template for email change")
  void getTemplateNameForVerificationType_shouldReturnCorrectTemplate_forEmailChange() {
    assertEquals("email-change", NotificationUtil.getTemplateNameForVerificationType(VerificationCodeTypeEnum.EMAIL_CHANGE));
  }

  @Test
  @DisplayName("getTemplateNameForVerificationType should return default template for unknown type")
  void getTemplateNameForVerificationType_shouldReturnDefaultTemplate_forUnknownType() {
    assertEquals("generic-verification", NotificationUtil.getTemplateNameForVerificationType(VerificationCodeTypeEnum.PHONE_VERIFICATION));
  }
}