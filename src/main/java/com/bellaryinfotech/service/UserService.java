package com.bellaryinfotech.service;

import java.util.Optional;
import com.bellaryinfotech.model.User;

public interface UserService {
    User registerUser(User user);
    Optional<User> loginUser(String email, String password);
}
