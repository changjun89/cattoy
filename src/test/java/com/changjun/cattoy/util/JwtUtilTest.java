package com.changjun.cattoy.util;

import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class JwtUtilTest {

    private static final String SECRET ="12345678901234567890123456789012";

    private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEzLCJuYW1lIjoi7YWM7Iqk7YSwIn0.yI3hxmFPMg4tbbxsUh11AzwfgbfxW_jrUaqFuzPTS64";
    private JwtUtil jwtUtil;

    @Before
    public void setup() {
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    public void creatToken() {
        Long userId = 13L;
        String userName = "테스터";
        String token = jwtUtil.createToken(userId,userName);

        assertThat(token).isNotBlank();
    }

    @Test
    public void parseToken() {
        Claims claims = jwtUtil.parseToken(TOKEN);

        assertThat(claims).isNotNull();
        assertThat(claims.get("userId",Long.class)).isEqualTo(13L);
        assertThat(claims.get("name")).isEqualTo("테스터");
    }
}