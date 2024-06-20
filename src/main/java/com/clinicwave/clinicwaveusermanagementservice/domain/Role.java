package com.clinicwave.clinicwaveusermanagementservice.domain;

import com.clinicwave.clinicwaveusermanagementservice.audit.Audit;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * This class represents a role in the ClinicWave system.
 * It extends the Audit class to include audit fields and implements Serializable for ease of use with certain Java frameworks.
 * It includes fields for the role name, role description, and a set of RolePermission objects.
 * It is annotated as a JPA Entity, so instances of this class can be automatically persisted in a database.
 *
 * @author aamir on 5/27/24
 */
@Entity
@Table(name = "Role")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Role extends Audit implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, unique = true)
  private String roleName;
  private String roleDescription;

  /**
   * This field represents a set of RolePermission objects associated with the role.
   * It is annotated with @OneToMany to establish a one-to-many relationship with the RolePermission entity.
   * The 'cascade = CascadeType.ALL' attribute means that any changes made to the role will also be applied to the associated RolePermission objects.
   * The 'fetch = FetchType.EAGER' attribute means that the associated RolePermission objects will be loaded automatically when the role is loaded.
   * The 'mappedBy = "role"' attribute indicates that the 'role' field in the RolePermission entity is the owner of the relationship.
   */
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "role")
  private Set<RolePermission> rolePermissionSet;
}