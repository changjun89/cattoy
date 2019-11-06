package com.changjun.cattoy.application;

import com.changjun.cattoy.domain.User;
import com.changjun.cattoy.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User register(User user) {
        user.hasPassword(passwordEncoder);
        return userRepository.save(user);
    }
}
