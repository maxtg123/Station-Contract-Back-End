package com.contract.common.entity;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditEntityListener implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    Object object = SecurityContextHolder.getContext().getAuthentication();
    if (object == null) {
      return Optional.of("System");
    } else {
      Object principal = SecurityContextHolder.getContext().getAuthentication().getName();
      if (!principal.toString().equals("anonymousUser")
          && principal.getClass().equals(String.class)) {
        return Optional.of(principal.toString());
      } else {
        return Optional.of("System");
      }
    }
  }

}
