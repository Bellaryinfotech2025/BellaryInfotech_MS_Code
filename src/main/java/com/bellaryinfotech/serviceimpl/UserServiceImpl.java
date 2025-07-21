package com.bellaryinfotech.serviceimpl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bellaryinfotech.model.User;
import com.bellaryinfotech.repo.UserRepository;
import com.bellaryinfotech.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

    // Register User (without password encoding)
    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // Login User (Simple match without BCrypt)
    @Override
    public Optional<User> loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && password.equals(user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }
}
