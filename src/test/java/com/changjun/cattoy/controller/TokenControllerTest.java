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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
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
    public void signInWithValidAttributes() throws Exception {
        User mockUser = User.builder()
                .email("leechang0423@naver.com")
                .password("password")
                .build();
        given(userService.authenticate("leechang0423@naver.com","psssword")).willReturn(mockUser);
        mockMvc.perform(post("/token")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\"email\":\"leechang0423@naver.com\",\"password\":\"password\"}"))
                .andExpect(status().isCreated());
        verify(userService).authenticate(any(), any() );
    }

    @Test
    public void signInWithInValidAttributes() throws Exception {
        given(userService.authenticate("leechang0423@naver.com","psssword")).willReturn(null);
        mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\"leechang0423@naver.com\",\"password\":\"password\"}"))
                .andExpect(status().isNotFound());
        verify(userService).authenticate(any(), any() );
    }
}