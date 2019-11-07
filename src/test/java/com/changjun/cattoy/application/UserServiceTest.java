package com.changjun.cattoy.application;

import com.changjun.cattoy.domain.User;
import com.changjun.cattoy.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
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
        String password = "password";
        User user = User.builder()
                .name("changjun")
                .email("leechang0423@naver.com")
                .password(password)
                .build();

        userService.register(user);
        assertThat(user).isNotEqualTo(password);

        verify(userRepository).save(user);
    }

    @Test
    public void authenticateWithValidAttribute() {
        User mockUser = User.builder()
                .email("leechang0423@naver.com")
                .password("password")
                .build();

        given(userRepository.findByEmail("leechang0423@naver.com")).willReturn(Optional.of(mockUser));
        User user = userService.authenticate("leechang0423@naver.com", "password");
        assertThat(user).isNotNull();
    }

    @Test(expected = EntityNotFoundException.class)
    public void authenticateWithInValidAttribute() {
        given(userRepository.findByEmail("x@naver.com")).willThrow(new EntityNotFoundException());

        userService.authenticate("x@naver.com","pass");
    }
}