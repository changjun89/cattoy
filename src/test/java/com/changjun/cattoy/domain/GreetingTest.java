package com.changjun.cattoy.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class GreetingTest {

    @Test
    public void getMessageWithoutName() {
        Greeting greeting = Greeting.builder()
                .build();

        assertThat(greeting.getMessage()).isEqualTo("Hello");
    }

    @Test
    public void getMessageWithName() {
        Greeting greeting = Greeting.builder()
                .name("changjun")
                .build();

        assertThat(greeting.getMessage()).isEqualTo("Hello changjun");
    }
}