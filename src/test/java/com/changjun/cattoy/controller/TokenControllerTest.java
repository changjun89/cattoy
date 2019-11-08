package com.changjun.cattoy.controller;

import com.changjun.cattoy.application.UserService;
import com.changjun.cattoy.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TokenController.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void signInWithValidAttribute() throws Exception {
        String email = "leechang0423@naver.com";
        String password = "password";

        User mockUser = User.builder()
                .email(email)
                .password(password)
                .build();
        given(userService.authenticate(email, password)).willReturn(mockUser);

        mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\"leechang0423@naver.com\",\"password\":\"password\"}"))
                .andExpect(status().isCreated());

        verify(userService).authenticate(email, password);
    }

    @Test
    public void signInWithInValidAttribute() throws Exception {
        String email = "x@naver.com";
        String password = "password";
        given(userService.authenticate(email, password)).willReturn(null);

        mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\"x@naver.com\",\"password\":\"password\"}"))
                .andExpect(status().isNotFound());

        verify(userService).authenticate("x@naver.com", "password");
    }

    @Test
    public void signInNoPasswordAndEmail() throws Exception {
        mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{}")
        )
                .andExpect(status().isBadRequest());
    }

}