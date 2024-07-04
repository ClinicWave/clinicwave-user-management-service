package com.clinicwave.clinicwaveusermanagementservice.service;

import com.clinicwave.clinicwaveusermanagementservice.dto.ClinicWaveUserDto;

import java.util.List;

/**
 * This interface defines the methods that the ClinicWaveUserService class must implement.
 *
 * @author aamir on 6/16/24
 */
public interface ClinicWaveUserService {
  ClinicWaveUserDto getUser(Long userId);

  ClinicWaveUserDto createUser(ClinicWaveUserDto clinicWaveUserDto);

  ClinicWaveUserDto updateUser(Long userId, ClinicWaveUserDto clinicWaveUserDto);

  void deleteUser(Long userId);

  List<ClinicWaveUserDto> getAllUsers();
}
