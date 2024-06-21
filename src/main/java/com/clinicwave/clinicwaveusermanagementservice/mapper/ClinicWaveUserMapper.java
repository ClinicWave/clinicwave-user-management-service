package com.clinicwave.clinicwaveusermanagementservice.mapper;

import com.clinicwave.clinicwaveusermanagementservice.domain.ClinicWaveUser;
import com.clinicwave.clinicwaveusermanagementservice.dto.ClinicWaveUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for mapping between the ClinicWaveUser domain object and the ClinicWaveUserDto data transfer object.
 * It uses the RoleMapper and UserTypeMapper to handle the mapping of nested objects.
 * The class is annotated with @Component to allow Spring to handle its lifecycle.
 *
 * @author aamir on 6/18/24
 */
@Component
public class ClinicWaveUserMapper {
  private final RoleMapper roleMapper;
  private final UserTypeMapper userTypeMapper;

  /**
   * Constructor for the ClinicWaveUserMapper class.
   * It initializes the roleMapper and userTypeMapper with the provided mappers.
   *
   * @param roleMapper     the RoleMapper to be used for mapping Role objects
   * @param userTypeMapper the UserTypeMapper to be used for mapping UserType objects
   */
  @Autowired
  public ClinicWaveUserMapper(RoleMapper roleMapper, UserTypeMapper userTypeMapper) {
    this.roleMapper = roleMapper;
    this.userTypeMapper = userTypeMapper;
  }

  /**
   * Converts a ClinicWaveUser domain object into a ClinicWaveUserDto data transfer object.
   * It uses the RoleMapper and UserTypeMapper to convert the nested Role and UserType objects.
   *
   * @param clinicWaveUser the ClinicWaveUser object to be converted
   * @return the converted ClinicWaveUserDto object
   */
  public ClinicWaveUserDto toDto(ClinicWaveUser clinicWaveUser) {
    return new ClinicWaveUserDto(
            clinicWaveUser.getId(),
            clinicWaveUser.getFirstName(),
            clinicWaveUser.getLastName(),
            clinicWaveUser.getMobileNumber(),
            clinicWaveUser.getUsername(),
            clinicWaveUser.getEmail(),
            clinicWaveUser.getDateOfBirth(),
            clinicWaveUser.getGender(),
            clinicWaveUser.getBio(),
            clinicWaveUser.getStatus(),
            clinicWaveUser.getRole() != null ?
                    roleMapper.toDto(clinicWaveUser.getRole()) : null,
            clinicWaveUser.getUserType() != null ?
                    userTypeMapper.toDto(clinicWaveUser.getUserType()) : null
    );
  }

  /**
   * Converts a ClinicWaveUserDto data transfer object into a ClinicWaveUser domain object.
   * It uses the RoleMapper and UserTypeMapper to convert the nested RoleDto and UserTypeDto objects.
   *
   * @param clinicWaveUserDto the ClinicWaveUserDto object to be converted
   * @return the converted ClinicWaveUser object
   */
  public ClinicWaveUser toEntity(ClinicWaveUserDto clinicWaveUserDto) {
    return new ClinicWaveUser(
            clinicWaveUserDto.id(),
            clinicWaveUserDto.firstName(),
            clinicWaveUserDto.lastName(),
            clinicWaveUserDto.mobileNumber(),
            clinicWaveUserDto.username(),
            clinicWaveUserDto.email(),
            clinicWaveUserDto.dateOfBirth(),
            clinicWaveUserDto.gender(),
            clinicWaveUserDto.bio(),
            clinicWaveUserDto.status(),
            clinicWaveUserDto.role() != null ?
                    roleMapper.toEntity(clinicWaveUserDto.role()) : null,
            clinicWaveUserDto.userType() != null ?
                    userTypeMapper.toEntity(clinicWaveUserDto.userType()) : null
    );
  }
}