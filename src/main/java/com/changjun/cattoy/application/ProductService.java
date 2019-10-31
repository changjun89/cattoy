package com.changjun.cattoy.application;

import com.changjun.cattoy.domain.Product;
import com.changjun.cattoy.domain.ProductRepository;
import com.changjun.cattoy.dto.ProductDto;
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

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    public Product getProduct(long id) {
        return productRepository.findById(id).get();
    }

    public Product updateProduct(long id, ProductDto productDto) {
        Product product = productRepository.findById(id).get();
        product.changeWithDto(productDto);
        return product;
    }
}
