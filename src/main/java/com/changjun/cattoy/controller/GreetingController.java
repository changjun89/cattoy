package com.changjun.cattoy.controller;

import com.changjun.cattoy.application.GreetingService;
import com.changjun.cattoy.dto.GreetingDto;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
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
    public GreetingDto greeting(Authentication authentication, @RequestParam(required = false) String name) {
        String myName = "changjun";

        // 인증 객체가 있다면 이름을 바꿔줍니다.
        if (authentication != null) {
            Claims claims = (Claims) authentication.getPrincipal();
            myName = claims.get("name", String.class);
        }

        GreetingDto greeting = new GreetingDto();
        greeting.setName(myName);
        greeting.setMessage(greetingService.getMessage(name));
        return greeting;
    }
}
