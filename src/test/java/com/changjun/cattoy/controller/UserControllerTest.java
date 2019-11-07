package com.changjun.cattoy.controller;

import com.changjun.cattoy.application.UserService;
import com.changjun.cattoy.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void signUp() throws Exception {
        User mockUser = User.builder()
                .id(1L)
                .name("changjun")
                .email("leechang0423@naver.com")
                .password("password")
                .build();
        given(userService.register(any())).willReturn(mockUser);


        mockMvc.perform(post("/users")
                .content("{\"name\":\"changjun\",\"email\":\"leechang0423@naver.com\",\"password\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andExpect(header().string(HttpHeaders.LOCATION,"/users/1"))
                .andExpect(status().isCreated());

        verify(userService).register(any());
    }
}