package com.bellaryinfotech.serviceimpl;
 
 

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bellaryinfotech.model.User;
import com.bellaryinfotech.repo.UserRepository;
import com.bellaryinfotech.service.UserService;
import com.bellaryinfotech.DTO.UserRegistrationDTO;
import com.bellaryinfotech.DTO.UserLoginDTO;
import com.bellaryinfotech.DTO.UserResponseDTO;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO registerUser(UserRegistrationDTO userRegistrationDTO) {
        // Create new user entity
        User user = new User();
        user.setFullname(userRegistrationDTO.getFullname());
        user.setUsername(userRegistrationDTO.getUsername());
        user.setRole(userRegistrationDTO.getRole());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword())); // Encrypt password
        user.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
        user.setRegisterTime(LocalDateTime.now());
        user.setRegisterDate(LocalDate.now());
        user.setVerified(true);  
        
       
        User savedUser = userRepository.save(user);
        
        // Convert to response DTO
        return convertToResponseDTO(savedUser);
    }

    @Override
    public Optional<UserResponseDTO> loginUser(UserLoginDTO userLoginDTO) {
        Optional<User> userOptional = userRepository.findByEmail(userLoginDTO.getEmail());
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
             
            if (!user.getVerified()) {
                throw new RuntimeException("Email not verified. Please verify your email first.");
            }
            // Check if password matches using BCrypt
            if (passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
                return Optional.of(convertToResponseDTO(user));
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private UserResponseDTO convertToResponseDTO(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getFullname(),
            user.getUsername(),
            user.getRole(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getRegisterTime(),
            user.getRegisterDate(),
            user.getVerified()
        );
    }
}
