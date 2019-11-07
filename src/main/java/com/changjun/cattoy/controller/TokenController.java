package com.changjun.cattoy.controller;

import com.changjun.cattoy.application.UserService;
import com.changjun.cattoy.domain.User;
import com.changjun.cattoy.dto.SignDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class TokenController {

    private final UserService userService;

    public TokenController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/token")
    public ResponseEntity signIn(@RequestBody SignDto signDto) throws URISyntaxException {
        User user = userService.authenticate(signDto.getEmail(), signDto.getPassword());
        if(user ==null) {
            return ResponseEntity.notFound().build();
        }
        URI uri = new URI("/");
        return ResponseEntity.created(uri).build();
    }
}
