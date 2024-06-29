package com.clinicwave.clinicwaveusermanagementservice.mapper;

import com.clinicwave.clinicwaveusermanagementservice.domain.ClinicWaveUser;
import com.clinicwave.clinicwaveusermanagementservice.dto.ClinicWaveUserDto;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for mapping between the ClinicWaveUser domain object and the ClinicWaveUserDto data transfer object.
 * The class is annotated with @Component to allow Spring to handle its lifecycle.
 *
 * @author aamir on 6/18/24
 */
@Component
public class ClinicWaveUserMapper {
  /**
   * Converts a ClinicWaveUser domain object into a ClinicWaveUserDto data transfer object.
   *
   * @param clinicWaveUser the ClinicWaveUser object to be converted
   * @return the converted ClinicWaveUserDto object
   */
  public ClinicWaveUserDto toDto(ClinicWaveUser clinicWaveUser) {
    if (clinicWaveUser == null) return null;

    return new ClinicWaveUserDto(
            clinicWaveUser.getId(),
            clinicWaveUser.getFirstName(),
            clinicWaveUser.getLastName(),
            clinicWaveUser.getMobileNumber(),
            clinicWaveUser.getUsername(),
            clinicWaveUser.getEmail(),
            clinicWaveUser.getDateOfBirth(),
            clinicWaveUser.getGender(),
            clinicWaveUser.getBio()
    );
  }

  /**
   * Converts a ClinicWaveUserDto data transfer object into a ClinicWaveUser domain object.
   *
   * @param clinicWaveUserDto the ClinicWaveUserDto object to be converted
   * @return the converted ClinicWaveUser object
   */
  public ClinicWaveUser toEntity(ClinicWaveUserDto clinicWaveUserDto) {
    if (clinicWaveUserDto == null) return null;

    ClinicWaveUser clinicWaveUser = new ClinicWaveUser();
    clinicWaveUser.setId(clinicWaveUserDto.id());
    clinicWaveUser.setFirstName(clinicWaveUserDto.firstName());
    clinicWaveUser.setLastName(clinicWaveUserDto.lastName());
    clinicWaveUser.setMobileNumber(clinicWaveUserDto.mobileNumber());
    clinicWaveUser.setUsername(clinicWaveUserDto.username());
    clinicWaveUser.setEmail(clinicWaveUserDto.email());
    clinicWaveUser.setDateOfBirth(clinicWaveUserDto.dateOfBirth());
    clinicWaveUser.setGender(clinicWaveUserDto.gender());
    clinicWaveUser.setBio(clinicWaveUserDto.bio());
    return clinicWaveUser;
  }
}