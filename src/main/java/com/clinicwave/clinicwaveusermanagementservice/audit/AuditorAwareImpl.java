package com.clinicwave.clinicwaveusermanagementservice.audit;

import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * This class provides the current auditor for auditing purposes.
 * It implements the AuditorAware interface from Spring Data JPA.
 *
 * @author aamir on 5/27/24
 */
@Component(value = "auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<String> {
  /**
   * The getCurrentAuditor method returns the username of the current user.
   * In this implementation, the username is hardcoded as "user".
   * TODO: method should return the username of the currently authenticated user.
   *
   * @return the username of the current auditor
   */
  @Override
  @NonNull
  public Optional<String> getCurrentAuditor() {
    return Optional.of("user");
  }
}