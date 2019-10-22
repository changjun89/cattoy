package com.changjun.cattoy.application;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GreetingServiceTest {

    private GreetingService greetingService;

    @Before
    public void setup() {
        greetingService = new GreetingService();
    }

    @Test
    public void getMessageWithNullName() {
        assertThat(greetingService.getMessage(null)).isEqualTo("Hello World");
    }

    @Test
    public void getMessageWithName() {
        assertThat(greetingService.getMessage("changjun")).isEqualTo("Hello changjun");
    }
}