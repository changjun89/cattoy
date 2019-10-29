package com.changjun.cattoy.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String maker;
    private int price;
}
