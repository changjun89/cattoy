package com.changjun.cattoy.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ProductTest {

    private final String name = "쥐돌이";
    private final String maker = "메이커";
    private final int price = 3000;
    private Product product;

    @Before
    public void setup() {
        //when
        product = Product.builder()
                .name(name)
                .maker(maker)
                .price(price)
                .build();
    }

    @Test
    public void creation() {
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getMaker()).isEqualTo(maker);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getPriceWithComma()).isEqualTo("3,000");
    }

    @Test
    public void defaultImage() {
        assertThat(product.getImgUrl()).isEqualTo("");
    }

    @Test
    public void imageChange() {
        //given
        String imgUrl = "http://image.cattoy.co.kr";

        //when
        product.changeImageUrl(imgUrl);

        //then
        assertThat(product.getImgUrl()).isEqualTo(imgUrl);
    }
}
