package com.changjun.cattoy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class TokenController {

    @PostMapping("/token")
    public ResponseEntity signIn() throws URISyntaxException {
        URI uri = new URI("/");
        return ResponseEntity.created(uri).build();
    }
}
