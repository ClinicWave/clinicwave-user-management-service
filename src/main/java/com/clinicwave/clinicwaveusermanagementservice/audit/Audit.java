package com.clinicwave.clinicwaveusermanagementservice.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This abstract class provides auditing capabilities for entities.
 * It includes fields for tracking the creation and modification of entities.
 * Entities that need to be audited can extend this class.
 *
 * @author aamir on 5/27/24
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class Audit implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * The timestamp when the entity was created.
   * This field is not updatable.
   */
  @CreatedDate
  @Column(updatable = false, nullable = false)
  private LocalDateTime createdAt;

  /**
   * The user who created the entity.
   * This field is not updatable.
   */
  @CreatedBy
  @Column(updatable = false, nullable = false, length = 20)
  private String createdBy;

  /**
   * The timestamp when the entity was last updated.
   */
  @LastModifiedDate
  @Column(insertable = false)
  private LocalDateTime updatedAt;

  /**
   * The user who last updated the entity.
   */
  @LastModifiedBy
  @Column(insertable = false, length = 20)
  private String updatedBy;
}