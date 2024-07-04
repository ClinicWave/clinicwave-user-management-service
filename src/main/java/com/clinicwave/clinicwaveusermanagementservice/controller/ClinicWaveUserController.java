package com.clinicwave.clinicwaveusermanagementservice.controller;

import com.clinicwave.clinicwaveusermanagementservice.dto.ClinicWaveUserDto;
import com.clinicwave.clinicwaveusermanagementservice.service.ClinicWaveUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class defines the RESTful API endpoints for managing ClinicWaveUser entities.
 * It uses the ClinicWaveUserService to handle the business logic for each endpoint.
 * The class is annotated with @RestController to indicate that it is a controller component in the Spring framework.
 * It is also annotated with @RequestMapping to specify the base URL for all endpoints.
 *
 * @author aamir on 6/22/24
 */
@RestController
@RequestMapping(value = "/api/users", produces = "application/json")
public class ClinicWaveUserController {
  private final ClinicWaveUserService clinicWaveUserService;

  /**
   * Constructor for the ClinicWaveUserController class.
   *
   * @param clinicWaveUserService the ClinicWaveUserService to be used for handling business logic
   */
  @Autowired
  public ClinicWaveUserController(ClinicWaveUserService clinicWaveUserService) {
    this.clinicWaveUserService = clinicWaveUserService;
  }

  /**
   * Retrieves a ClinicWaveUser entity by its ID and returns it as a response entity.
   *
   * @param userId the ID of the ClinicWaveUser entity to be retrieved
   * @return the response entity containing the ClinicWaveUserDto data transfer object
   */
  @GetMapping("/{userId}")
  public ResponseEntity<ClinicWaveUserDto> getUser(@PathVariable Long userId) {
    return ResponseEntity.ok(clinicWaveUserService.getUser(userId));
  }

  /**
   * Creates a new ClinicWaveUser entity from the provided ClinicWaveUserDto data transfer object.
   *
   * @param clinicWaveUserDto the ClinicWaveUserDto data transfer object to be used for creating the entity
   * @return the response entity containing the ClinicWaveUserDto data transfer object of the created entity
   */
  @PostMapping
  public ResponseEntity<ClinicWaveUserDto> createUser(@Valid @RequestBody ClinicWaveUserDto clinicWaveUserDto) {
    return ResponseEntity.ok(clinicWaveUserService.createUser(clinicWaveUserDto));
  }

  /**
   * Updates an existing ClinicWaveUser entity with the provided ClinicWaveUserDto data transfer object.
   *
   * @param userId            the ID of the ClinicWaveUser entity to be updated
   * @param clinicWaveUserDto the ClinicWaveUserDto data transfer object to be used for updating the entity
   * @return the response entity containing the ClinicWaveUserDto data transfer object of the updated entity
   */
  @PutMapping("/{userId}")
  public ResponseEntity<ClinicWaveUserDto> updateUser(@PathVariable Long userId, @Valid @RequestBody ClinicWaveUserDto clinicWaveUserDto) {
    return ResponseEntity.ok(clinicWaveUserService.updateUser(userId, clinicWaveUserDto));
  }

  /**
   * Deletes a ClinicWaveUser entity by its ID.
   *
   * @param userId the ID of the ClinicWaveUser entity to be deleted
   * @return a response entity with no content
   */
  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    clinicWaveUserService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }

  /**
   * Retrieves all ClinicWaveUser entities and returns them as a list of ClinicWaveUserDto data transfer objects.
   *
   * @return the response entity containing the list of ClinicWaveUserDto data transfer objects
   */
  @GetMapping
  public ResponseEntity<List<ClinicWaveUserDto>> getAllUsers() {
    return ResponseEntity.ok(clinicWaveUserService.getAllUsers());
  }
}
