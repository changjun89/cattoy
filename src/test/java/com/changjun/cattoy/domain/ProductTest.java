package com.changjun.cattoy.domain;

import com.changjun.cattoy.dto.ProductDto;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ProductTest {

    private Product product;
    private final String name = "쥐돌이";
    private final String maker = "메이커";
    private final int price = 3000;


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

    @Test
    public void dozerTest() {
        // Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        ProductDto dto = new ProductDto();
        dto.setMaker(maker);
        dto.setName(name);
        dto.setPrice(price);

        Product product = mapper.map(dto, Product.class);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getMaker()).isEqualTo(maker);

    }
}
