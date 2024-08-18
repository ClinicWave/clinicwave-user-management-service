package com.clinicwave.clinicwaveusermanagementservice.repository;

import com.clinicwave.clinicwaveusermanagementservice.domain.ClinicWaveUser;
import com.clinicwave.clinicwaveusermanagementservice.domain.VerificationCode;
import com.clinicwave.clinicwaveusermanagementservice.enums.VerificationCodeTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * This interface extends the JpaRepository interface and provides methods to interact with the VerificationCode entity in the database.
 * It is used to perform CRUD operations on the VerificationCode entity.
 *
 * @author aamir on 7/7/24
 */
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
  List<VerificationCode> findAllByClinicWaveUser(ClinicWaveUser clinicWaveUser);

  Optional<VerificationCode> findByToken(String token);

  Optional<VerificationCode> findTopByClinicWaveUserAndTypeOrderByCreatedAtDesc(ClinicWaveUser clinicWaveUser, VerificationCodeTypeEnum type);
}
