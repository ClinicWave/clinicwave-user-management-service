package com.clinicwave.clinicwaveusermanagementservice.repository;

import com.clinicwave.clinicwaveusermanagementservice.entity.Role;
import com.clinicwave.clinicwaveusermanagementservice.enums.RoleNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This interface extends JpaRepository and provides CRUD operations for Role entity.
 * JpaRepository is a JPA specific extension of Repository which provides JPA related methods such as flushing the persistence context and deleting records in a batch.
 *
 * @author aamir on 6/8/24
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByRoleName(RoleNameEnum roleName);
}
