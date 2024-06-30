package com.clinicwave.clinicwaveusermanagementservice.domain;

import com.clinicwave.clinicwaveusermanagementservice.audit.Audit;
import com.clinicwave.clinicwaveusermanagementservice.enums.PermissionNameEnum;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents a permission in the ClinicWave system.
 * It extends the Audit class to include audit fields and implements Serializable for ease of use with certain Java frameworks.
 * It includes a field for the permission name.
 * It is annotated as a JPA Entity, so instances of this class can be automatically persisted in a database.
 *
 * @author aamir on 5/27/24
 */
@Entity
@Table(name = "Permission")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends Audit implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  private PermissionNameEnum permissionName;
}