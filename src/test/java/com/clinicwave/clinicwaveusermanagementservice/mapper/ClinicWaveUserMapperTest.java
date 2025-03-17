package com.clinicwave.clinicwaveusermanagementservice.mapper;

import com.clinicwave.clinicwaveusermanagementservice.entity.ClinicWaveUser;
import com.clinicwave.clinicwaveusermanagementservice.dto.ClinicWaveUserDto;
import com.clinicwave.clinicwaveusermanagementservice.enums.GenderEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ClinicWaveUserMapper}.
 * It tests the mapping between {@link ClinicWaveUser} and {@link ClinicWaveUserDto}.
 *
 * @author aamir on 6/19/24
 */
@SpringBootTest
class ClinicWaveUserMapperTest {
  private final ClinicWaveUserMapper clinicWaveUserMapper;

  private ClinicWaveUser clinicWaveUser;
  private ClinicWaveUserDto clinicWaveUserDto;

  /**
   * Constructor for the test class, initializes the mapper.
   *
   * @param clinicWaveUserMapper the mapper to be tested
   */
  @Autowired
  public ClinicWaveUserMapperTest(ClinicWaveUserMapper clinicWaveUserMapper) {
    this.clinicWaveUserMapper = clinicWaveUserMapper;
  }

  /**
   * Sets up the test data before each test.
   */
  @BeforeEach
  void setUp() {
    clinicWaveUser = new ClinicWaveUser();
    clinicWaveUser.setId(1L);
    clinicWaveUser.setFirstName("John");
    clinicWaveUser.setLastName("Doe");
    clinicWaveUser.setMobileNumber("1234567890");
    clinicWaveUser.setUsername("johndoe");
    clinicWaveUser.setEmail("john.doe@example.com");
    clinicWaveUser.setDateOfBirth(LocalDate.of(1990, 1, 1));
    clinicWaveUser.setGender(GenderEnum.MALE);
    clinicWaveUser.setBio("Bio");

    clinicWaveUserDto = new ClinicWaveUserDto(
            1L,
            "John",
            "Doe",
            "1234567890",
            "johndoe",
            "john.doe@example.com",
            LocalDate.of(1990, 1, 1),
            GenderEnum.MALE,
            "Bio"
    );
  }

  @Test
  @DisplayName("Should map ClinicWaveUser to ClinicWaveUserDto")
  void testToDto() {
    ClinicWaveUserDto result = clinicWaveUserMapper.toDto(clinicWaveUser);

    assertNotNull(result);
    assertEquals(clinicWaveUserDto.id(), result.id());
    assertEquals(clinicWaveUserDto.firstName(), result.firstName());
    assertEquals(clinicWaveUserDto.lastName(), result.lastName());
    assertEquals(clinicWaveUserDto.mobileNumber(), result.mobileNumber());
    assertEquals(clinicWaveUserDto.username(), result.username());
    assertEquals(clinicWaveUserDto.email(), result.email());
    assertEquals(clinicWaveUserDto.dateOfBirth(), result.dateOfBirth());
    assertEquals(clinicWaveUserDto.gender(), result.gender());
    assertEquals(clinicWaveUserDto.bio(), result.bio());
  }

  @Test
  @DisplayName("Should map ClinicWaveUserDto to ClinicWaveUser")
  void testToEntity() {
    ClinicWaveUser result = clinicWaveUserMapper.toEntity(clinicWaveUserDto);

    assertNotNull(result);
    assertEquals(clinicWaveUser.getId(), result.getId());
    assertEquals(clinicWaveUser.getFirstName(), result.getFirstName());
    assertEquals(clinicWaveUser.getLastName(), result.getLastName());
    assertEquals(clinicWaveUser.getMobileNumber(), result.getMobileNumber());
    assertEquals(clinicWaveUser.getUsername(), result.getUsername());
    assertEquals(clinicWaveUser.getEmail(), result.getEmail());
    assertEquals(clinicWaveUser.getDateOfBirth(), result.getDateOfBirth());
    assertEquals(clinicWaveUser.getGender(), result.getGender());
    assertEquals(clinicWaveUser.getBio(), result.getBio());
  }

  @Test
  @DisplayName("Should map ClinicWaveUser to ClinicWaveUserDto and back")
  void shouldMapClinicWaveUserToClinicWaveUserDto() {
    ClinicWaveUserDto intermediateDto = clinicWaveUserMapper.toDto(clinicWaveUser);
    ClinicWaveUser roundTripUser = clinicWaveUserMapper.toEntity(intermediateDto);

    assertNotNull(roundTripUser);
    assertEquals(clinicWaveUser.getId(), roundTripUser.getId());
    assertEquals(clinicWaveUser.getFirstName(), roundTripUser.getFirstName());
    assertEquals(clinicWaveUser.getLastName(), roundTripUser.getLastName());
    assertEquals(clinicWaveUser.getMobileNumber(), roundTripUser.getMobileNumber());
    assertEquals(clinicWaveUser.getUsername(), roundTripUser.getUsername());
    assertEquals(clinicWaveUser.getEmail(), roundTripUser.getEmail());
    assertEquals(clinicWaveUser.getDateOfBirth(), roundTripUser.getDateOfBirth());
    assertEquals(clinicWaveUser.getGender(), roundTripUser.getGender());
    assertEquals(clinicWaveUser.getBio(), roundTripUser.getBio());
  }

  @Test
  @DisplayName("Should handle null values")
  void shouldHandleNullValues() {
    ClinicWaveUser nullUser = null;
    ClinicWaveUserDto nullDto = null;

    assertNull(clinicWaveUserMapper.toDto(nullUser));
    assertNull(clinicWaveUserMapper.toEntity(nullDto));
  }
}