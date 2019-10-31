package com.changjun.cattoy.controller;

import com.changjun.cattoy.application.ProductService;
import com.changjun.cattoy.domain.Product;
import com.changjun.cattoy.dto.ProductDto;
import com.changjun.cattoy.resources.ProductResource;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/products", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class ProductController {

    private final ProductService productService;
    @Autowired
    private Mapper mapper;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity list() {
        List<Product> products = productService.getProducts();
        List<ProductResource> collect = products.stream()
                .map(product -> convertToProductResource(mapper.map(product, ProductDto.class)))
                .collect(toList());
        Link selfRel = linkTo(methodOn(ProductController.class).list()).withSelfRel();
        return ResponseEntity.ok().body(new Resources<>(collect, selfRel));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody ProductDto productDto) {
        Product resource = mapper.map(productDto, Product.class);
        Product product = productService.addProduct(resource);
        productDto.setId(product.getId());
        URI uri = linkTo(methodOn(ProductController.class).create(productDto)).slash(product.getId()).toUri();
        return ResponseEntity.created(uri).body(convertToProductResource(productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable long id) {
        Product product = productService.getProduct(id);
        ProductDto productDto = mapper.map(product, ProductDto.class);
        return ResponseEntity.ok().body(productDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody ProductDto resource) {
        Product product = productService.updateProduct(id, resource);
        ProductDto productDto = mapper.map(product, ProductDto.class);
        return ResponseEntity.ok().body(productDto);
    }

    private ProductResource convertToProductResource(ProductDto dto) {
        ProductResource productResource = new ProductResource(dto);
        productResource.addUpdateEvents(this.getClass(), dto);
        productResource.addQueryEvents(this.getClass(), dto);
        return productResource;
    }
}
