package com.changjun.cattoy.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class JwtUtilTest {

    private static final String SECRET ="12345678901234567890123456789012";

    @Test
    public void creatToken() {
        JwtUtil jwtUtil = new JwtUtil(SECRET);
        Long userId = 13L;
        String userName = "테스터";
        String token = jwtUtil.createToken(userId,userName);

        assertThat(token).isNotBlank();
    }
}