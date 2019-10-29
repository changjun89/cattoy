package com.changjun.cattoy.application;

import com.changjun.cattoy.domain.Product;
import com.changjun.cattoy.domain.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


public class ProductServiceTest {

    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    public void getProductsWithNoProduct() {
        List<Product> mockProducts = new ArrayList<>();
        given(productRepository.findAll()).willReturn(mockProducts);

        List<Product> products = productService.getProducts();

        verify(productRepository).findAll();

        assertThat(products.isEmpty()).isTrue();
    }

    @Test
    public void getProductsWithOneProduct() {
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(Product.builder().name("쥐돌이").build());
        given(productRepository.findAll()).willReturn(mockProducts);

        List<Product> products = productService.getProducts();

        verify(productRepository).findAll();

        assertThat(products.isEmpty()).isFalse();
    }

    @Test
    public void addProduct() {
        productService.addProduct("낚시대", "쥐돌이", 3000);
        verify(productRepository).save(any());
    }

    @Test
    public void addProductByProduct() {
        String name = "낚시대";
        String maker = "창준컴패니";
        int price = 3000;

        Product product = Product.builder()
                .name(name)
                .maker(maker)
                .price(price)
                .build();

        productService.addProduct(product);
        verify(productRepository).save(any());
    }
}