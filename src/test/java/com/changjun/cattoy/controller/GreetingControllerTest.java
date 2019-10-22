package com.changjun.cattoy.controller;

import com.changjun.cattoy.application.GreetingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(GreetingController.class)
public class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GreetingService greetingService;

    @Test
    public void helloWithoutName() throws Exception {
        given(greetingService.getMessage(null)).willReturn("Hello");
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello")))
                .andExpect(content().string(containsString("changjun")));

        verify(greetingService).getMessage(null);
    }

    @Test
    public void helloWithName() throws Exception {
        String name = "jun";
        given(greetingService.getMessage(name)).willReturn("Hello jun");
        mockMvc.perform(get("/")
                .param("name", name)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello jun")))
                .andExpect(content().string(containsString("changjun")));
        verify(greetingService).getMessage(name);
    }
}
