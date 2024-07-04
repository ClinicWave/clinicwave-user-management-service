package com.clinicwave.clinicwaveusermanagementservice.service.impl;

import com.clinicwave.clinicwaveusermanagementservice.domain.ClinicWaveUser;
import com.clinicwave.clinicwaveusermanagementservice.domain.Role;
import com.clinicwave.clinicwaveusermanagementservice.domain.UserType;
import com.clinicwave.clinicwaveusermanagementservice.dto.ClinicWaveUserDto;
import com.clinicwave.clinicwaveusermanagementservice.enums.GenderEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.RoleNameEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.UserStatusEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.UserTypeEnum;
import com.clinicwave.clinicwaveusermanagementservice.exception.ResourceNotFoundException;
import com.clinicwave.clinicwaveusermanagementservice.mapper.ClinicWaveUserMapper;
import com.clinicwave.clinicwaveusermanagementservice.repository.ClinicWaveUserRepository;
import com.clinicwave.clinicwaveusermanagementservice.repository.RoleRepository;
import com.clinicwave.clinicwaveusermanagementservice.repository.UserTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class is a test class for the ClinicWaveUserServiceImpl class.
 * It uses JUnit 5 and Mockito to test the methods of the ClinicWaveUserServiceImpl class.
 * It tests the basic CRUD operations for the ClinicWaveUser entity.
 *
 * @author aamir on 6/18/24
 */
@ActiveProfiles("h2")
@ExtendWith(MockitoExtension.class)
class ClinicWaveUserServiceImplTest {
  @Mock
  private ClinicWaveUserRepository clinicWaveUserRepository;

  @Mock
  private RoleRepository roleRepository;

  @Mock
  private UserTypeRepository userTypeRepository;

  @Mock
  private ClinicWaveUserMapper clinicWaveUserMapper;

  @InjectMocks
  private ClinicWaveUserServiceImpl clinicWaveUserService;

  private ClinicWaveUser clinicWaveUser;
  private ClinicWaveUserDto clinicWaveUserDto;
  private Role role;
  private UserType userType;

  /**
   * Sets up the test data before each test.
   * Creates a ClinicWaveUser entity and a ClinicWaveUserDto data transfer object.
   * Creates a Role entity and a UserType entity.
   */
  @BeforeEach
  void setUp() {
    clinicWaveUser = new ClinicWaveUser();
    clinicWaveUser.setId(1L);
    clinicWaveUser.setFirstName("John");
    clinicWaveUser.setLastName("Doe");
    clinicWaveUser.setMobileNumber("1234567890");
    clinicWaveUser.setUsername("johndoe");
    clinicWaveUser.setEmail("john@example.com");
    clinicWaveUser.setDateOfBirth(LocalDate.of(1990, 1, 1));
    clinicWaveUser.setGender(GenderEnum.MALE);
    clinicWaveUser.setBio("Test bio");

    clinicWaveUserDto = new ClinicWaveUserDto(1L, "John", "Doe", "1234567890", "johndoe", "john@example.com",
            LocalDate.of(1990, 1, 1), GenderEnum.MALE, "Test bio");

    role = new Role();
    role.setRoleName(RoleNameEnum.ROLE_DEFAULT);

    userType = new UserType();
    userType.setType(UserTypeEnum.USER_TYPE_DEFAULT);
  }

  @Test
  @DisplayName("getUser returns ClinicWaveUserDto when ClinicWaveUser exists")
  void getUser_returnsClinicWaveUserDto_whenClinicWaveUserExists() {
    when(clinicWaveUserRepository.findById(1L)).thenReturn(Optional.of(clinicWaveUser));
    when(clinicWaveUserMapper.toDto(clinicWaveUser)).thenReturn(clinicWaveUserDto);

    ClinicWaveUserDto result = clinicWaveUserService.getUser(1L);

    assertEquals(clinicWaveUserDto, result);
    verify(clinicWaveUserMapper, times(1)).toDto(clinicWaveUser);
    verify(clinicWaveUserRepository, times(1)).findById(1L);
  }

  @Test
  @DisplayName("getUser throws ResourceNotFoundException when ClinicWaveUser does not exist")
  void getUser_throwsResourceNotFoundException_whenClinicWaveUserDoesNotExist() {
    when(clinicWaveUserRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> clinicWaveUserService.getUser(1L));
    verify(clinicWaveUserRepository, times(1)).findById(1L);
    verify(clinicWaveUserMapper, never()).toDto(any(ClinicWaveUser.class));
  }

  @Test
  @DisplayName("createUser returns created ClinicWaveUser")
  void createUser_returnsCreatedClinicWaveUserDto() {
    when(clinicWaveUserMapper.toEntity(clinicWaveUserDto)).thenReturn(clinicWaveUser);
    when(roleRepository.findByRoleName(RoleNameEnum.ROLE_DEFAULT)).thenReturn(Optional.of(role));
    when(userTypeRepository.findByType(UserTypeEnum.USER_TYPE_DEFAULT)).thenReturn(Optional.of(userType));
    when(clinicWaveUserRepository.save(clinicWaveUser)).thenReturn(clinicWaveUser);
    when(clinicWaveUserMapper.toDto(clinicWaveUser)).thenReturn(clinicWaveUserDto);

    ClinicWaveUserDto result = clinicWaveUserService.createUser(clinicWaveUserDto);

    assertEquals(clinicWaveUserDto, result);
    assertEquals(UserStatusEnum.PENDING, clinicWaveUser.getStatus());
    assertEquals(role, clinicWaveUser.getRole());
    assertEquals(userType, clinicWaveUser.getUserType());
    verify(clinicWaveUserMapper, times(1)).toEntity(clinicWaveUserDto);
    verify(clinicWaveUserRepository, times(1)).save(clinicWaveUser);
    verify(clinicWaveUserMapper, times(1)).toDto(clinicWaveUser);
  }

  @Test
  @DisplayName("updateUser returns updated ClinicWaveUserDto when ClinicWaveUser exists")
  void updateUser_returnsUpdatedClinicWaveUserDtoDto_whenClinicWaveUserExists() {
    when(clinicWaveUserRepository.findById(1L)).thenReturn(Optional.of(clinicWaveUser));
    when(clinicWaveUserRepository.save(clinicWaveUser)).thenReturn(clinicWaveUser);
    when(clinicWaveUserMapper.toDto(clinicWaveUser)).thenReturn(clinicWaveUserDto);

    ClinicWaveUserDto result = clinicWaveUserService.updateUser(1L, clinicWaveUserDto);

    assertEquals(clinicWaveUserDto, result);
    verify(clinicWaveUserRepository, times(1)).findById(1L);
    verify(clinicWaveUserRepository, times(1)).save(clinicWaveUser);
    verify(clinicWaveUserMapper, times(1)).toDto(clinicWaveUser);
  }

  @Test
  @DisplayName("updateUser throws ResourceNotFoundException when ClinicWaveUser does not exist")
  void updateUser_throwsResourceNotFoundException_whenClinicWaveUserDoesNotExist() {
    when(clinicWaveUserRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> clinicWaveUserService.updateUser(1L, clinicWaveUserDto));
    verify(clinicWaveUserRepository, times(1)).findById(1L);
    verify(clinicWaveUserRepository, never()).save(any(ClinicWaveUser.class));
    verify(clinicWaveUserMapper, never()).toDto(any(ClinicWaveUser.class));
  }

  @Test
  @DisplayName("deleteUser deletes ClinicWaveUser")
  void deleteUser_shouldDeleteUser() {
    when(clinicWaveUserRepository.findById(1L)).thenReturn(Optional.of(clinicWaveUser));

    clinicWaveUserService.deleteUser(1L);

    verify(clinicWaveUserRepository, times(1)).findById(1L);
    verify(clinicWaveUserRepository, times(1)).delete(clinicWaveUser);
  }

  @Test
  @DisplayName("deleteUser does not throw when ClinicWaveUser exists")
  void deleteUser_doesNotThrow_whenClinicWaveUserExists() {
    when(clinicWaveUserRepository.findById(1L)).thenReturn(Optional.of(clinicWaveUser));

    assertDoesNotThrow(() -> clinicWaveUserService.deleteUser(1L));
    verify(clinicWaveUserRepository, times(1)).findById(1L);
    verify(clinicWaveUserRepository, times(1)).delete(clinicWaveUser);
  }

  @Test
  @DisplayName("deleteUser throws ResourceNotFoundException when ClinicWaveUser does not exist")
  void deleteUser_throwsResourceNotFoundException_whenClinicWaveUserDoesNotExist() {
    when(clinicWaveUserRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> clinicWaveUserService.deleteUser(1L));
    verify(clinicWaveUserRepository, times(1)).findById(1L);
    verify(clinicWaveUserRepository, never()).delete(any(ClinicWaveUser.class));
  }

  @Test
  @DisplayName("getAllUsers returns all Users")
  void getAllUsers_returnAllUsers() {
    List<ClinicWaveUser> clinicWaveUserList = Arrays.asList(clinicWaveUser, clinicWaveUser);
    when(clinicWaveUserRepository.findAll()).thenReturn(clinicWaveUserList);
    when(clinicWaveUserMapper.toDto(clinicWaveUser)).thenReturn(clinicWaveUserDto);

    List<ClinicWaveUserDto> result = clinicWaveUserService.getAllUsers();

    assertEquals(2, result.size());
    assertEquals(clinicWaveUserDto, result.get(0));
    assertEquals(clinicWaveUserDto, result.get(1));
    verify(clinicWaveUserRepository, times(1)).findAll();
    verify(clinicWaveUserMapper, times(2)).toDto(clinicWaveUser);
  }

  @Test
  @DisplayName("findRoleByRoleName returns Role")
  void findRoleByRoleName_shouldReturnRole() {
    when(roleRepository.findByRoleName(RoleNameEnum.ROLE_DEFAULT)).thenReturn(Optional.of(role));

    Role result = clinicWaveUserService.findRoleByRoleName(RoleNameEnum.ROLE_DEFAULT);

    assertEquals(role, result);
    verify(roleRepository, times(1)).findByRoleName(RoleNameEnum.ROLE_DEFAULT);
  }

  @Test
  @DisplayName("findRoleByRoleName throws ResourceNotFoundException")
  void findRoleByRoleName_shouldThrowResourceNotFoundException() {
    when(roleRepository.findByRoleName(RoleNameEnum.ROLE_DEFAULT)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> clinicWaveUserService.findRoleByRoleName(RoleNameEnum.ROLE_DEFAULT));
    verify(roleRepository, times(1)).findByRoleName(RoleNameEnum.ROLE_DEFAULT);
  }

  @Test
  @DisplayName("findUserTypeByType returns UserType")
  void findUserTypeByType_shouldReturnUserType() {
    when(userTypeRepository.findByType(UserTypeEnum.USER_TYPE_DEFAULT)).thenReturn(Optional.of(userType));

    UserType result = clinicWaveUserService.findUserTypeByType(UserTypeEnum.USER_TYPE_DEFAULT);

    assertEquals(userType, result);
    verify(userTypeRepository, times(1)).findByType(UserTypeEnum.USER_TYPE_DEFAULT);
  }

  @Test
  @DisplayName("findUserTypeByType throws ResourceNotFoundException")
  void findUserTypeByType_shouldThrowResourceNotFoundException() {
    when(userTypeRepository.findByType(UserTypeEnum.USER_TYPE_DEFAULT)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> clinicWaveUserService.findUserTypeByType(UserTypeEnum.USER_TYPE_DEFAULT));
    verify(userTypeRepository, times(1)).findByType(UserTypeEnum.USER_TYPE_DEFAULT);
  }
}