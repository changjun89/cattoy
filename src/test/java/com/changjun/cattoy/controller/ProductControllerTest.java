package com.changjun.cattoy.controller;

import com.changjun.cattoy.application.ProductService;
import com.changjun.cattoy.domain.Product;
import com.github.dozermapper.core.Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private Mapper mapper;

    @Test
    public void list() throws Exception {
        List<Product> products = new ArrayList<>();
        String name = "쥐돌이";
        Product product = Product.builder()
                .id(1L)
                .name(name)
                .build();

        Product product2 = Product.builder()
                .id(2L)
                .name("켓타워")
                .build();
        products.add(product);
        products.add(product2);

        given(productService.getProducts()).willReturn(products);
        mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$._embedded.productDtoList.[0].name").value(name))
                .andExpect(jsonPath("$._embedded.productDtoList.[0]._links.update-events").exists())
                .andExpect(jsonPath("$._embedded.productDtoList.[0]._links.query-events").exists())
                .andExpect(content().string(containsString(name)))
                .andExpect(jsonPath("$._links.self").exists());

        verify(productService).getProducts();
    }

    @Test
    public void creat() throws Exception {
        String name = "낚시대";
        String maker = "창준컴패니";
        int price = 3000;

        Product product = Product.builder()
                .id(1L)
                .name(name)
                .maker(maker)
                .price(price)
                .build();

        given(productService.addProduct(name, maker, price)).willReturn(product);
        mockMvc.perform(post("/products")
                .content("{\"name\":\"낚시대\",\"maker\":\"창준컴패니\",\"price\":\"3000\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isCreated());

        verify(productService).addProduct("낚시대", "창준컴패니", 3000);
    }
}