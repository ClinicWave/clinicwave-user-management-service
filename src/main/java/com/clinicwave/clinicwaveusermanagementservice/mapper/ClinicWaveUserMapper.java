package com.clinicwave.clinicwaveusermanagementservice.mapper;

import com.clinicwave.clinicwaveusermanagementservice.entity.ClinicWaveUser;
import com.clinicwave.clinicwaveusermanagementservice.dto.ClinicWaveUserDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
    return Optional.ofNullable(clinicWaveUser)
            .map(c -> new ClinicWaveUserDto(
                    c.getId(),
                    c.getFirstName(),
                    c.getLastName(),
                    c.getMobileNumber(),
                    c.getUsername(),
                    c.getEmail(),
                    c.getDateOfBirth(),
                    c.getGender(),
                    c.getBio()
            ))
            .orElse(null);
  }

  /**
   * Converts a ClinicWaveUserDto data transfer object into a ClinicWaveUser domain object.
   *
   * @param clinicWaveUserDto the ClinicWaveUserDto object to be converted
   * @return the converted ClinicWaveUser object
   */
  public ClinicWaveUser toEntity(ClinicWaveUserDto clinicWaveUserDto) {
    return Optional.ofNullable(clinicWaveUserDto)
            .map(dto -> {
              ClinicWaveUser clinicWaveUser = new ClinicWaveUser();
              clinicWaveUser.setId(dto.id());
              clinicWaveUser.setFirstName(dto.firstName());
              clinicWaveUser.setLastName(dto.lastName());
              clinicWaveUser.setMobileNumber(dto.mobileNumber());
              clinicWaveUser.setUsername(dto.username());
              clinicWaveUser.setEmail(dto.email());
              clinicWaveUser.setDateOfBirth(dto.dateOfBirth());
              clinicWaveUser.setGender(dto.gender());
              clinicWaveUser.setBio(dto.bio());
              return clinicWaveUser;
            })
            .orElse(null);
  }
}