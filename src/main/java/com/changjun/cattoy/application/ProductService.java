package com.changjun.cattoy.application;

import com.changjun.cattoy.domain.Product;
import com.changjun.cattoy.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void addProduct(String name) {
        productRepository.save(name);
    }
}
