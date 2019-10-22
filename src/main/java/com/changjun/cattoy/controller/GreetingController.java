package com.changjun.cattoy.controller;

import com.changjun.cattoy.dto.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/")
    public Greeting greeting(@RequestParam(name = "name", defaultValue = "world") String name) {
        Greeting greeting = new Greeting();
        greeting.setMessage("Hello " + name);
        greeting.setName("changjun");
        return greeting;
    }
}
