package com.clinicwave.clinicwaveusermanagementservice.entity;

import com.clinicwave.clinicwaveusermanagementservice.audit.Audit;
import com.clinicwave.clinicwaveusermanagementservice.enums.GenderEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * This class represents a user in the ClinicWave system.
 * It extends the Audit class to include audit fields and implements Serializable for ease of use with certain Java frameworks.
 * It includes several fields related to user information, such as name, contact details, and role.
 * It is annotated as a JPA Entity, so instances of this class can be automatically persisted in a database.
 *
 * @author aamir on 5/29/24
 */
@Entity
@Table(name = "ClinicWaveUser")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClinicWaveUser extends Audit implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String mobileNumber;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private LocalDate dateOfBirth;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private GenderEnum gender;

  private String bio;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserStatusEnum status;

  @ManyToOne
  private Role role;

  @ManyToOne
  private UserType userType;
}