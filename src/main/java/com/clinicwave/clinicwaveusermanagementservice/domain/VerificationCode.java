package com.clinicwave.clinicwaveusermanagementservice.domain;

import com.clinicwave.clinicwaveusermanagementservice.audit.Audit;
import com.clinicwave.clinicwaveusermanagementservice.enums.VerificationCodeTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

/**
 * This class represents a verification code entity in the database.
 * It extends the Audit class to include audit fields.
 * It is annotated as a JPA Entity, so instances of this class can be automatically persisted in a database.
 *
 * @author aamir on 7/7/24
 */
@Entity
@Table(name = "VerificationCode")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCode extends Audit {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String code;

  @Column(nullable = false)
  private LocalDateTime expiryDate = LocalDateTime.now().plusDays(3);

  @Column(nullable = false)
  private Boolean isUsed = false;

  @Column(nullable = false)
  private Boolean isVerified = false;

  private LocalDateTime verifiedAt;

  @Column(nullable = false)
  private Integer attemptCount = 0;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private VerificationCodeTypeEnum type;

  @Column(nullable = false, unique = true)
  private String token;

  @ManyToOne
  @JoinColumn(nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private ClinicWaveUser clinicWaveUser;
}
