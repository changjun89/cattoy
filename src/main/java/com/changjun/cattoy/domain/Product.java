package com.changjun.cattoy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.NumberFormat;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    private static final String DEFAULTS_IMG_URL = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String maker;
    private int price;
    private String imgUrl;

    public String getPriceWithComma() {
        return NumberFormat.getInstance().format(price);
    }

    public void changeImageUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl == null ? DEFAULTS_IMG_URL : imgUrl;
    }
}
