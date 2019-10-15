package com.changjun.cattoy.controller;

import com.changjun.cattoy.dto.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

  @GetMapping("/")
  public Greeting greeting() {
    Greeting greeting = new Greeting();
    greeting.setMessage("Hello World");
    greeting.setName("changjun");
    return greeting;
  }
}
