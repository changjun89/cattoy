package com.changjun.cattoy.domain;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository {

    List<Product> findAll();

    void save(String name);
}
