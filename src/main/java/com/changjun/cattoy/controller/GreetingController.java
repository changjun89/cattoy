package com.changjun.cattoy.controller;

import com.changjun.cattoy.application.GreetingService;
import com.changjun.cattoy.dto.GreetingDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/")
    public GreetingDto greeting(@RequestParam(required = false) String name) {
        GreetingDto greeting = new GreetingDto();
        greeting.setName("changjun");
        greeting.setMessage(greetingService.getMessage(name));
        return greeting;
    }
}
