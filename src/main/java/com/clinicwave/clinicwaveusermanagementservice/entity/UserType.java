package com.clinicwave.clinicwaveusermanagementservice.entity;

import com.clinicwave.clinicwaveusermanagementservice.audit.Audit;
import com.clinicwave.clinicwaveusermanagementservice.enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents a user type in the ClinicWave system.
 * It extends the Audit class to include audit fields and implements Serializable for ease of use with certain Java frameworks.
 * It includes a field for the type of user.
 * It is annotated as a JPA Entity, so instances of this class can be automatically persisted in a database.
 *
 * @author aamir on 5/30/24
 */
@Entity
@Table(name = "UserType")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserType extends Audit implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  private UserTypeEnum type;
}