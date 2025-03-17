package com.clinicwave.clinicwaveusermanagementservice.entity;

import com.clinicwave.clinicwaveusermanagementservice.audit.Audit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents a role permission in the ClinicWave system.
 * It extends the Audit class to include audit fields and implements Serializable for ease of use with certain Java frameworks.
 * It includes fields for the associated Role and Permission objects.
 * It is annotated as a JPA Entity, so instances of this class can be automatically persisted in a database.
 *
 * @author aamir on 5/27/24
 */
@Entity
@Table(name = "RolePermission")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission extends Audit implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /**
   * This field represents the associated Role object.
   * It is annotated with @ManyToOne to establish a many-to-one relationship with the Role entity.
   */
  @ManyToOne
  private Role role;

  /**
   * This field represents the associated Permission object.
   * It is annotated with @ManyToOne to establish a many-to-one relationship with the Permission entity.
   */
  @ManyToOne
  private Permission permission;
}