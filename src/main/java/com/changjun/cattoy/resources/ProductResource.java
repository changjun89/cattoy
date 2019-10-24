package com.changjun.cattoy.resources;

import com.changjun.cattoy.controller.ProductController;
import com.changjun.cattoy.dto.ProductDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ProductResource extends Resource<ProductDto> {

    public ProductResource(ProductDto content, Link... links) {
        super(content, links);
        add(linkTo(methodOn(ProductController.class).list()).slash(content.getId()).withSelfRel());
    }

    public void addUpdateEvents(Class<? extends ProductController> aClass, ProductDto productDto) {
        add(linkTo(methodOn(aClass).list()).slash(productDto.getId()).withRel("update-events"));
    }

    public void addQueryEvents(Class<? extends ProductController> aClass, ProductDto productDto) {
        add(linkTo(methodOn(aClass).list()).slash(productDto.getId()).withRel("query-events"));
    }
}
