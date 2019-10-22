package com.changjun.cattoy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Greeting {
    private static final String DEFAULTS_MESSAGE = "Hello";

    private String name;

    public String getMessage() {
        return name == null ? DEFAULTS_MESSAGE : "Hello " + name;
    }
}
