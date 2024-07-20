package com.clinicwave.clinicwaveusermanagementservice.controller.integration.testcontainers;

import com.clinicwave.clinicwaveusermanagementservice.config.NotificationServiceClientMockConfig;
import com.clinicwave.clinicwaveusermanagementservice.dto.ClinicWaveUserDto;
import com.clinicwave.clinicwaveusermanagementservice.enums.GenderEnum;
import com.clinicwave.clinicwaveusermanagementservice.repository.ClinicWaveUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class provides integration tests for the ClinicWaveUserController class.
 * It uses the Spring Boot testing framework to test the controller methods.
 * The tests are written using the JUnit 5 testing framework.
 * The tests use the Spring TestRestTemplate class to simulate HTTP requests to the controller.
 * The tests use the @SpringBootTest annotation to start the Spring Boot application context.
 * The tests use the @ActiveProfiles annotation to specify the active profiles for the local.
 * The tests use the @Testcontainers annotation to enable support for Testcontainers.
 * The tests use the PostgreSQLContainer class to start a PostgreSQL container for the test.
 * The tests use the DynamicPropertySource annotation to set the datasource properties for the test.
 * The tests use the ClinicWaveUserRepository to interact with the database.
 */
@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(NotificationServiceClientMockConfig.class)
@Testcontainers
class ClinicWaveUserControllerTestContainersIntegrationTest {
  // Define a PostgreSQL container for the test
  @Container
  static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14")
          .withDatabaseName("clinicwave-user-management")
          .withUsername("root")
          .withPassword("root");

  private final TestRestTemplate restTemplate;

  private final ClinicWaveUserRepository clinicWaveUserRepository;

  private ClinicWaveUserDto createdClinicWaveUserDto;
  private ClinicWaveUserDto createdClinicWaveUserDto2;

  // Set the datasource properties for the test
  @DynamicPropertySource
  static void setDatasourceProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
  }

  /**
   * Constructs a new ClinicWaveUserControllerTestContainersIntegrationTest with the given TestRestTemplate and ClinicWaveUserRepository.
   *
   * @param restTemplate             the TestRestTemplate instance to use for testing
   * @param clinicWaveUserRepository the ClinicWaveUserRepository instance to use for testing
   */
  @Autowired
  public ClinicWaveUserControllerTestContainersIntegrationTest(TestRestTemplate restTemplate, ClinicWaveUserRepository clinicWaveUserRepository) {
    this.restTemplate = restTemplate;
    this.clinicWaveUserRepository = clinicWaveUserRepository;
  }

  /**
   * Sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    createdClinicWaveUserDto = new ClinicWaveUserDto(1L, "Test", "User",
            "1234567890", "testuser", "testuser@example.com",
            LocalDate.of(1990, 1, 1), GenderEnum.MALE, "Test bio");

    createdClinicWaveUserDto2 = new ClinicWaveUserDto(2L, "Jane", "Doe",
            "0987654321", "janedoe", "janedoe@example.com",
            LocalDate.of(1992, 2, 2), GenderEnum.FEMALE, "Jane doe bio");

    clinicWaveUserRepository.deleteAll();
  }

  @Test
  @DisplayName("Create and get user")
  void shouldCreateAndGetUser() {
    // Create user
    ResponseEntity<ClinicWaveUserDto> createResponse = restTemplate.postForEntity("/api/users", createdClinicWaveUserDto, ClinicWaveUserDto.class);
    assertEquals(HttpStatus.OK, createResponse.getStatusCode());
    ClinicWaveUserDto createdUser = createResponse.getBody();
    assertNotNull(createdUser);
    assertNotNull(createdUser.id());

    // Get user
    ResponseEntity<ClinicWaveUserDto> getResponse = restTemplate.getForEntity("/api/users/" + createdUser.id(), ClinicWaveUserDto.class);
    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    ClinicWaveUserDto retrievedUser = getResponse.getBody();
    assertNotNull(retrievedUser);
    assertEquals(createdUser, retrievedUser);
  }

  @Test
  @DisplayName("Update user")
  void shouldUpdateUser() {
    // Create user
    ResponseEntity<ClinicWaveUserDto> createResponse = restTemplate.postForEntity("/api/users", createdClinicWaveUserDto, ClinicWaveUserDto.class);
    ClinicWaveUserDto createdUser = createResponse.getBody();
    assertNotNull(createdUser);

    // Update user
    ClinicWaveUserDto updatedUserDto = new ClinicWaveUserDto(createdUser.id(), "Updated", "User",
            "9876543210", "updateduser", "updateduser@example.com",
            LocalDate.of(1995, 5, 5), GenderEnum.FEMALE, "Updated bio");

    restTemplate.put("/api/users/" + createdUser.id(), updatedUserDto);

    // Get updated user
    ResponseEntity<ClinicWaveUserDto> getResponse = restTemplate.getForEntity("/api/users/" + createdUser.id(), ClinicWaveUserDto.class);
    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    ClinicWaveUserDto retrievedUser = getResponse.getBody();
    assertNotNull(retrievedUser);
    assertEquals(updatedUserDto, retrievedUser);
  }

  @Test
  @DisplayName("Delete user")
  void shouldDeleteUser() {
    // Create user
    ResponseEntity<ClinicWaveUserDto> createResponse = restTemplate.postForEntity("/api/users", createdClinicWaveUserDto, ClinicWaveUserDto.class);
    ClinicWaveUserDto createdUser = createResponse.getBody();
    assertNotNull(createdUser);

    // Delete user
    restTemplate.delete("/api/users/" + createdUser.id());

    // Try to get deleted user
    ResponseEntity<ClinicWaveUserDto> getResponse = restTemplate.getForEntity("/api/users/" + createdUser.id(), ClinicWaveUserDto.class);
    assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
  }

  @Test
  @DisplayName("Get all users")
  void shouldGetAllUsers() {
    // Create multiple users
    restTemplate.postForEntity("/api/users", createdClinicWaveUserDto, ClinicWaveUserDto.class);
    restTemplate.postForEntity("/api/users", createdClinicWaveUserDto2, ClinicWaveUserDto.class);

    // Get all users
    ResponseEntity<List<ClinicWaveUserDto>> getResponse = restTemplate.exchange(
            "/api/users",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<ClinicWaveUserDto>>() {
            }
    );

    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    List<ClinicWaveUserDto> users = getResponse.getBody();
    assertNotNull(users);
    assertEquals(2, users.size());
  }
}