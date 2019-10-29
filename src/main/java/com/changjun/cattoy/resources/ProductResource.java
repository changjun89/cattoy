package com.changjun.cattoy.resources;

import com.changjun.cattoy.controller.ProductController;
import com.changjun.cattoy.dto.ProductDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class ProductResource extends Resource<ProductDto> {
    private static final String UPDATE_PRODUCT_REL_NM = "update-events";
    private static final String QUERY_PRODUCT_REL_NM = "query-events";

    public ProductResource(ProductDto content, Link... links) {
        super(content, links);
        add(linkTo(ProductController.class).slash(content.getId()).withSelfRel());
    }

    public void addUpdateEvents(Class<? extends ProductController> aClass, ProductDto productDto) {
        add(linkTo(aClass).slash(productDto.getId()).withRel(UPDATE_PRODUCT_REL_NM));
    }

    public void addQueryEvents(Class<? extends ProductController> aClass, ProductDto productDto) {
        add(linkTo(aClass).slash(productDto.getId()).withRel(QUERY_PRODUCT_REL_NM));
    }
}
