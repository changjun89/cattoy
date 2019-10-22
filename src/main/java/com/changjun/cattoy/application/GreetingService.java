package com.changjun.cattoy.application;

public class GreetingService {
    private static final String DEFAULTS_MESSAGE = "Hello World";
    private static final String PREFIX_MESSAGE = "Hello ";

    public String getMessage(String name) {
        return name == null ? DEFAULTS_MESSAGE : PREFIX_MESSAGE + name;
    }

}
