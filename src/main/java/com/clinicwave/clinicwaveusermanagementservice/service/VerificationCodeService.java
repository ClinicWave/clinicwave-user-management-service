package com.clinicwave.clinicwaveusermanagementservice.service;

import com.clinicwave.clinicwaveusermanagementservice.entity.ClinicWaveUser;
import com.clinicwave.clinicwaveusermanagementservice.entity.VerificationCode;
import com.clinicwave.clinicwaveusermanagementservice.dto.VerificationRequestDto;
import com.clinicwave.clinicwaveusermanagementservice.dto.VerificationStatusDto;
import com.clinicwave.clinicwaveusermanagementservice.enums.VerificationCodeTypeEnum;

/**
 * This interface defines the methods that are used to generate and retrieve verification codes for different purposes.
 * It includes a method to generate a verification code for a user and a verification code type.
 * The interface is implemented by the VerificationCodeServiceImpl class.
 *
 * @author aamir on 7/7/24
 */
public interface VerificationCodeService {
  VerificationCode getVerificationCode(ClinicWaveUser clinicWaveUser, VerificationCodeTypeEnum verificationCodeType);

  VerificationStatusDto checkVerificationStatus(String token);

  void verifyAccount(VerificationRequestDto verificationRequestDto);
}
