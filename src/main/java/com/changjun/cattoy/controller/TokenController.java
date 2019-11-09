package com.changjun.cattoy.controller;

import com.changjun.cattoy.application.UserService;
import com.changjun.cattoy.domain.User;
import com.changjun.cattoy.dto.RequestSignDto;
import com.changjun.cattoy.dto.ResponseSignDto;
import com.changjun.cattoy.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class TokenController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    public TokenController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/token")
    public ResponseEntity signIn(@RequestBody @Valid RequestSignDto requestSignDto) throws URISyntaxException {
        User user = userService.authenticate(requestSignDto.getEmail(), requestSignDto.getPassword());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        String token = jwtUtil.createToken(user.getId(), user.getName());
        return ResponseEntity.created(new URI("/token")).body(new ResponseSignDto(token));
    }
}
