package com.changjun.cattoy.application;

import com.changjun.cattoy.domain.Product;
import com.changjun.cattoy.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(String name, String maker, int price) {
        Product product = Product.builder()
                .name(name)
                .maker(maker)
                .price(price)
                .build();
        return productRepository.save(product);
    }

    public Product addProduct(Product resource) {
        return productRepository.save(resource);
    }
}
