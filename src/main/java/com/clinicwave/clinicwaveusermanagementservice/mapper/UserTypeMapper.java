package com.clinicwave.clinicwaveusermanagementservice.mapper;

import com.clinicwave.clinicwaveusermanagementservice.domain.UserType;
import com.clinicwave.clinicwaveusermanagementservice.dto.UserTypeDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * This class is responsible for mapping between the UserType domain object and the UserTypeDto data transfer object.
 * The class is annotated with @Component to allow Spring to handle its lifecycle.
 *
 * @author aamir on 6/16/24
 */
@Component
public class UserTypeMapper {
  /**
   * Converts a UserType domain object into a UserTypeDto data transfer object.
   *
   * @param userType the UserType object to be converted
   * @return the converted UserTypeDto object
   */
  public UserTypeDto toDto(UserType userType) {
    return Optional.ofNullable(userType)
            .map(u -> new UserTypeDto(u.getId(), u.getType()))
            .orElse(null);
  }

  /**
   * Converts a UserTypeDto data transfer object into a UserType domain object.
   *
   * @param userTypeDto the UserTypeDto object to be converted
   * @return the converted UserType object
   */
  public UserType toEntity(UserTypeDto userTypeDto) {
    return Optional.ofNullable(userTypeDto)
            .map(dto -> new UserType(dto.id(), dto.type()))
            .orElse(null);
  }
}
