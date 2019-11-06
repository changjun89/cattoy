package com.changjun.cattoy.application;

import com.changjun.cattoy.domain.User;
import com.changjun.cattoy.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.verify;


public class UserServiceTest {

    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(passwordEncoder, userRepository);
    }
    @Test
    public void register() {
        User user = User.builder()
                .name("changjun")
                .email("leechang0423@naver.com")
                .password("password")
                .build();

        userService.register(user);

        verify(userRepository).save(user);
    }
}