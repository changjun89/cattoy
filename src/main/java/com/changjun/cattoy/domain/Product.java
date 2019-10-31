package com.changjun.cattoy.domain;

import com.changjun.cattoy.dto.ProductDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.NumberFormat;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    private static final String DEFAULTS_IMG_URL = "";

    @Id
    @GeneratedValue
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

    public void changeWithDto(ProductDto productDto) {
        name = productDto.getName();
        maker = productDto.getMaker();
        price = productDto.getPrice();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maker='" + maker + '\'' +
                ", price=" + price +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }

}
