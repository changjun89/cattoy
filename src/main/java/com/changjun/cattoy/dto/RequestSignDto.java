package com.changjun.cattoy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RequestSignDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
