package com.changjun.cattoy.controller;

import com.changjun.cattoy.application.ProductService;
import com.changjun.cattoy.domain.Product;
import com.changjun.cattoy.dto.ProductDto;
import com.changjun.cattoy.resources.ProductResource;
import com.github.dozermapper.core.Mapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class ProductController {


    private final Mapper mapper;

    private final ProductService productService;

    public ProductController(Mapper mapper, ProductService productService) {
        this.mapper = mapper;
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity list() {
        List<Product> products = productService.getProducts();
        List<ProductResource> collect = products.stream()
                .map(product -> convertToProductResource(mapper.map(product, ProductDto.class)))
                .collect(toList());
        Link selfRel = linkTo(methodOn(ProductController.class).list()).withSelfRel();
        return ResponseEntity.ok().body(new Resources<>(collect, selfRel));
    }

    private ProductResource convertToProductResource(ProductDto dto) {
        ProductResource productResource = new ProductResource(dto);
        productResource.addUpdateEvents(this.getClass(), dto);
        productResource.addQueryEvents(this.getClass(), dto);
        return productResource;
    }
}
