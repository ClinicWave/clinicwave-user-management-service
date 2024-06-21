package com.clinicwave.clinicwaveusermanagementservice.repository;

import com.clinicwave.clinicwaveusermanagementservice.domain.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface extends JpaRepository and provides CRUD operations for UserType entity.
 * JpaRepository is a JPA specific extension of Repository which provides JPA related methods such as flushing the persistence context and deleting records in a batch.
 *
 * @author aamir on 6/8/24
 */
public interface UserTypeRepository extends JpaRepository<UserType, Long> {
}
