package com.bellaryinfotech.service;

import java.util.Optional;
import com.bellaryinfotech.DTO.UserRegistrationDTO;
import com.bellaryinfotech.DTO.UserLoginDTO;
import com.bellaryinfotech.DTO.UserResponseDTO;

public interface UserService {
    UserResponseDTO registerUser(UserRegistrationDTO userRegistrationDTO);
    Optional<UserResponseDTO> loginUser(UserLoginDTO userLoginDTO);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
