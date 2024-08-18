package com.clinicwave.clinicwaveusermanagementservice.service.impl;

import com.clinicwave.clinicwaveusermanagementservice.client.NotificationServiceClient;
import com.clinicwave.clinicwaveusermanagementservice.domain.ClinicWaveUser;
import com.clinicwave.clinicwaveusermanagementservice.domain.Role;
import com.clinicwave.clinicwaveusermanagementservice.domain.UserType;
import com.clinicwave.clinicwaveusermanagementservice.domain.VerificationCode;
import com.clinicwave.clinicwaveusermanagementservice.dto.ClinicWaveUserDto;
import com.clinicwave.clinicwaveusermanagementservice.dto.NotificationRequestDto;
import com.clinicwave.clinicwaveusermanagementservice.enums.*;
import com.clinicwave.clinicwaveusermanagementservice.exception.ResourceNotFoundException;
import com.clinicwave.clinicwaveusermanagementservice.mapper.ClinicWaveUserMapper;
import com.clinicwave.clinicwaveusermanagementservice.repository.ClinicWaveUserRepository;
import com.clinicwave.clinicwaveusermanagementservice.repository.RoleRepository;
import com.clinicwave.clinicwaveusermanagementservice.repository.UserTypeRepository;
import com.clinicwave.clinicwaveusermanagementservice.service.ClinicWaveUserService;
import com.clinicwave.clinicwaveusermanagementservice.service.VerificationCodeService;
import com.clinicwave.clinicwaveusermanagementservice.util.NotificationUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * This class implements the ClinicWaveUserService interface and provides methods to manage ClinicWaveUser entities.
 * It uses the ClinicWaveUserRepository to interact with the database and the ClinicWaveUserMapper to map between domain objects and data transfer objects.
 * The class is annotated with @Service to indicate that it is a service component in the Spring framework.
 * It is also annotated with @Transactional to ensure that each method is executed within a transaction.
 *
 * @author aamir on 6/13/24
 */
@Service
@Slf4j
public class ClinicWaveUserServiceImpl implements ClinicWaveUserService {
  private final ClinicWaveUserRepository clinicWaveUserRepository;
  private final RoleRepository roleRepository;
  private final UserTypeRepository userTypeRepository;
  private final ClinicWaveUserMapper clinicWaveUserMapper;
  private final VerificationCodeService verificationCodeService;
  private final NotificationServiceClient notificationServiceClient;

  @Value("${clinicwave-user-management-frontend-base-url}")
  private String clinicwaveUserManagementFrontendBaseUrl;

  /**
   * Constructor for the ClinicWaveUserServiceImpl class.
   *
   * @param clinicWaveUserRepository  the ClinicWaveUserRepository to be used for database operations
   * @param roleRepository            the RoleRepository to be used for database operations
   * @param userTypeRepository        the UserTypeRepository to be used for database operations
   * @param clinicWaveUserMapper      the ClinicWaveUserMapper to be used for object mapping
   * @param verificationCodeService   the VerificationCodeService to be used for generating verification codes
   * @param notificationServiceClient the NotificationServiceClient to be used for sending notifications
   */
  @Autowired
  public ClinicWaveUserServiceImpl(ClinicWaveUserRepository clinicWaveUserRepository, RoleRepository roleRepository, UserTypeRepository userTypeRepository, ClinicWaveUserMapper clinicWaveUserMapper, VerificationCodeService verificationCodeService, NotificationServiceClient notificationServiceClient) {
    this.clinicWaveUserRepository = clinicWaveUserRepository;
    this.roleRepository = roleRepository;
    this.userTypeRepository = userTypeRepository;
    this.clinicWaveUserMapper = clinicWaveUserMapper;
    this.verificationCodeService = verificationCodeService;
    this.notificationServiceClient = notificationServiceClient;
  }

  /**
   * Retrieves a ClinicWaveUser entity by its ID and converts it into a ClinicWaveUserDto data transfer object.
   *
   * @param userId the ID of the ClinicWaveUser entity to be retrieved
   * @return the ClinicWaveUserDto data transfer object
   */
  @Override
  public ClinicWaveUserDto getUser(Long userId) {
    ClinicWaveUser clinicWaveUser = findClinicWaveUserById(userId);
    return clinicWaveUserMapper.toDto(clinicWaveUser);
  }

  /**
   * Creates a new ClinicWaveUser entity from the provided ClinicWaveUserDto data transfer object.
   *
   * @param clinicWaveUserDto the ClinicWaveUserDto data transfer object to be used for creating the entity
   * @return the ClinicWaveUserDto data transfer object of the created entity
   */
  @Override
  public ClinicWaveUserDto createUser(ClinicWaveUserDto clinicWaveUserDto) {
    ClinicWaveUser clinicWaveUser = prepareNewClinicWaveUser(clinicWaveUserDto);

    ClinicWaveUser savedClinicWaveUser = clinicWaveUserRepository.save(clinicWaveUser);

    // Generate a verification code for the user
    VerificationCode verificationCode = generateVerificationCode(savedClinicWaveUser);

    // Generate a verification link for the user
    String verificationLink = generateVerificationLink(verificationCode.getToken());

    // Send a notification to the user with the verification code
    sendVerificationNotification(savedClinicWaveUser, verificationCode, verificationLink);

    return clinicWaveUserMapper.toDto(savedClinicWaveUser);
  }

  /**
   * Updates an existing ClinicWaveUser entity with the data from the provided ClinicWaveUserDto data transfer object.
   *
   * @param clinicWaveUserDto the ClinicWaveUserDto data transfer object containing the updated data
   * @return the ClinicWaveUserDto data transfer object of the updated entity
   */
  @Override
  public ClinicWaveUserDto updateUser(Long userId, ClinicWaveUserDto clinicWaveUserDto) {
    ClinicWaveUser clinicWaveUser = findClinicWaveUserById(userId);
    clinicWaveUser.setFirstName(clinicWaveUserDto.firstName());
    clinicWaveUser.setLastName(clinicWaveUserDto.lastName());
    clinicWaveUser.setMobileNumber(clinicWaveUserDto.mobileNumber());
    clinicWaveUser.setUsername(clinicWaveUserDto.username());
    clinicWaveUser.setEmail(clinicWaveUserDto.email());
    clinicWaveUser.setDateOfBirth(clinicWaveUserDto.dateOfBirth());
    clinicWaveUser.setGender(clinicWaveUserDto.gender());
    clinicWaveUser.setBio(clinicWaveUserDto.bio());
    ClinicWaveUser updatedClinicWaveUser = clinicWaveUserRepository.save(clinicWaveUser);
    return clinicWaveUserMapper.toDto(updatedClinicWaveUser);
  }

  /**
   * Deletes a ClinicWaveUser entity by its ID.
   *
   * @param userId the ID of the ClinicWaveUser entity to be deleted
   */
  @Override
  @Transactional
  public void deleteUser(Long userId) {
    ClinicWaveUser clinicWaveUser = findClinicWaveUserById(userId);
    clinicWaveUserRepository.delete(clinicWaveUser);
  }

  /**
   * Retrieves all ClinicWaveUser entities and converts them into a list of ClinicWaveUserDto data transfer objects.
   *
   * @return a list of ClinicWaveUserDto data transfer objects
   */
  @Override
  public List<ClinicWaveUserDto> getAllUsers() {
    List<ClinicWaveUser> clinicWaveUserList = clinicWaveUserRepository.findAll();
    return clinicWaveUserList.stream()
            .map(clinicWaveUserMapper::toDto)
            .toList();
  }

  /**
   * Prepares a new ClinicWaveUser entity from the provided ClinicWaveUserDto data transfer object.
   *
   * @param clinicWaveUserDto the ClinicWaveUserDto data transfer object to be used for creating the entity
   * @return the prepared ClinicWaveUser entity
   */
  private ClinicWaveUser prepareNewClinicWaveUser(ClinicWaveUserDto clinicWaveUserDto) {
    ClinicWaveUser clinicWaveUser = clinicWaveUserMapper.toEntity(clinicWaveUserDto);

    // Set default values for status, role, and user type
    clinicWaveUser.setStatus(UserStatusEnum.PENDING);
    clinicWaveUser.setRole(findRoleByRoleName(RoleNameEnum.ROLE_DEFAULT));
    clinicWaveUser.setUserType(findUserTypeByType(UserTypeEnum.USER_TYPE_DEFAULT));
    return clinicWaveUser;
  }

  /**
   * Generates a verification code for the specified user.
   *
   * @param clinicWaveUser the user for whom the verification code is to be generated
   * @return the generated verification code
   */
  private VerificationCode generateVerificationCode(ClinicWaveUser clinicWaveUser) {
    VerificationCode verificationCode = verificationCodeService.getVerificationCode(clinicWaveUser, VerificationCodeTypeEnum.EMAIL_VERIFICATION);
    log.info("Verification code generated for user {}: {}", clinicWaveUser.getUsername(), verificationCode);
    return verificationCode;
  }

  /**
   * Generates a verification link for the user based on the specified token.
   * clinicwaveUserManagementFrontendBaseUrl is the base URL of the ClinicWave User Management frontend application.
   *
   * @param token the token to be used in the verification link
   * @return the generated verification link
   */
  private String generateVerificationLink(String token) {
    return clinicwaveUserManagementFrontendBaseUrl + "/verification/verify?token=" + token;
  }

  /**
   * Sends a verification notification to the specified user with the verification code and verification link.
   *
   * @param clinicWaveUser   the user to whom the notification is to be sent
   * @param verificationCode the verification code to be sent in the notification
   * @param verificationLink the verification link to be sent in the notification
   */
  private void sendVerificationNotification(ClinicWaveUser clinicWaveUser, VerificationCode verificationCode, String verificationLink) {
    VerificationCodeTypeEnum verificationCodeType = verificationCode.getType();
    NotificationTypeEnum notificationType = NotificationUtil.getNotificationTypeForVerification(verificationCodeType);

    NotificationRequestDto notificationRequestDto = new NotificationRequestDto(
            clinicWaveUser.getEmail(),
            NotificationUtil.getSubjectForVerificationType(verificationCodeType),
            NotificationUtil.getTemplateNameForVerificationType(verificationCodeType),
            Map.of(
                    "verificationCode", verificationCode.getCode(),
                    "userName", clinicWaveUser.getUsername(),
                    "verificationType", verificationCodeType.name(),
                    "verificationLink", verificationLink
            ),
            notificationType,
            NotificationCategoryEnum.VERIFICATION
    );
    notificationServiceClient.sendNotification(notificationRequestDto);
  }

  /**
   * Retrieves a Role entity by its role name.
   *
   * @param roleName the role name of the Role entity to be retrieved
   * @return the Role entity
   * @throws ResourceNotFoundException if the Role entity with the specified role name is not found
   */
  public Role findRoleByRoleName(RoleNameEnum roleName) {
    return roleRepository.findByRoleName(roleName)
            .orElseThrow(() -> new ResourceNotFoundException("Role", "roleName", roleName));
  }

  /**
   * Retrieves a UserType entity by its type.
   *
   * @param type the type of the UserType entity to be retrieved
   * @return the UserType entity
   * @throws ResourceNotFoundException if the UserType entity with the specified type is not found
   */
  public UserType findUserTypeByType(UserTypeEnum type) {
    return userTypeRepository.findByType(type)
            .orElseThrow(() -> new ResourceNotFoundException("UserType", "type", type));
  }

  /**
   * Helper method to find a ClinicWaveUser entity by its ID.
   *
   * @param userId the ID of the ClinicWaveUser entity to be retrieved
   * @return the ClinicWaveUser entity
   * @throws ResourceNotFoundException if the ClinicWaveUser entity with the specified ID is not found
   */
  private ClinicWaveUser findClinicWaveUserById(Long userId) {
    return clinicWaveUserRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("ClinicWaveUser", "id", userId));
  }
}
