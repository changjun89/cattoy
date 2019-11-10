package com.changjun.cattoy.controller;

import com.changjun.cattoy.application.ProductService;
import com.changjun.cattoy.domain.Product;
import com.changjun.cattoy.dto.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    private static final String ADMIN_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjEsIm5hbWUiOiLqtIDrpqzsnpAifQ." +
            "EyrTP4OAGH9fA7lYxHrmJibf9QpBZnijtet-bWiTu2k";

    private static final String TESTER_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjEzLCJuYW1lIjoi7YWM7Iqk7YSwIn0." +
            "yI3hxmFPMg4tbbxsUh11AzwfgbfxW_jrUaqFuzPTS64";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void list() throws Exception {
        List<Product> products = new ArrayList<>();
        String name = "쥐돌이";
        String maker = "창준컴패니";
        Product product = Product.builder()
                .id(1L)
                .name(name)
                .maker(maker)
                .price(4000)
                .build();

        Product product2 = Product.builder()
                .id(2L)
                .name("켓타워")
                .maker(maker)
                .price(3000)
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
    public void createValidAttribute() throws Exception {
        Long id = 1L;
        String name = "낚시대";
        String maker = "창준컴패니";
        int price = 3000;

        Product product = Product.builder()
                .id(id)
                .name(name)
                .maker(maker)
                .price(price)
                .build();

        given(productService.addProduct(any())).willReturn(product);
        mockMvc.perform(post("/products")
                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                .content("{\"name\":\"낚시대\",\"maker\":\"창준컴패니\",\"price\":\"3000\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION));

        verify(productService).addProduct(any());
    }

    @Test
    public void createInvalidPrice() throws Exception {
        mockMvc.perform(post("/products")
                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                .content("{\"name\":\"낚시대\",\"maker\":\"창준컴패니\",\"price\":\"-50\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInvalidAttribute() throws Exception {
        mockMvc.perform(post("/products")
                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                .content("{\"name\":\"낚시대\",\"maker\":,\"price\":\"3000\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void destroy() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(productService).deleteProduct(1);
    }

    @Test
    public void detail() throws Exception {

        Long id = 13L;
        String name = "낚시대";
        String maker = "창준컴패니";
        int price = 3000;

        Product product = Product.builder()
                .id(id)
                .name(name)
                .maker(maker)
                .price(price)
                .build();

        given(productService.getProduct(id)).willReturn(product);

        mockMvc.perform(get("/products/13"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("낚시대")));

        verify(productService).getProduct(13L);
    }

    @Test
    public void detailWithNotExist() throws Exception {

        given(productService.getProduct(404L)).willThrow(new EntityNotFoundException());

        mockMvc.perform(get("/products/404"))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(productService).getProduct(404L);
    }

    @Test
    public void update() throws Exception {
        ProductDto productDto = ProductDto.builder()
                .name("낚시대")
                .maker("달랩")
                .price(5000)
                .build();

        Long id = 13L;
        String name = "낚시대";
        String maker = "창준컴패니";
        int price = 3000;
        Product product = Product.builder()
                .id(id)
                .name(name)
                .maker(maker)
                .price(price)
                .build();

        given(productService.updateProduct(id, productDto)).willReturn(product);

        mockMvc.perform(
                patch("/products/13")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(productDto))
        )
                .andDo(print())
                .andExpect(status().isOk());

        verify(productService).updateProduct(13L, productDto);
    }

    @Test
    public void createWithoutAuthentication() throws Exception {
        mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"낚시대\",\"maker\":\"달랩\"," +
                                "\"price\":5000}")
        )
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void createWithoutAdminRole() throws Exception {
        mockMvc.perform(
                post("/products")
                        .header("Authorization", "Bearer " + TESTER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"낚시대\",\"maker\":\"달랩\"," +
                                "\"price\":5000}")
        )
                .andExpect(status().isForbidden());
    }
}