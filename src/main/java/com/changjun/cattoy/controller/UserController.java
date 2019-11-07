package com.changjun.cattoy.controller;

import com.changjun.cattoy.application.UserService;
import com.changjun.cattoy.domain.User;
import com.changjun.cattoy.dto.UserDto;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class UserController {

    @Autowired
    private Mapper mapper;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity signUp(@RequestBody UserDto userDto) throws URISyntaxException {
        User user = mapper.map(userDto,User.class);
        User newUser = userService.register(user);
        URI uri = new URI("/users/"+newUser.getId());
        return ResponseEntity.created(uri).build();
    }
}
