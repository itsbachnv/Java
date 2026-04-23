package com.foodapp.foodapp.user.service;

import com.foodapp.foodapp.user.entity.Product;
import com.foodapp.foodapp.user.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<Product> searchByName(String keyword) {
        return  productRepository.findAll().stream().filter(x->x.getName().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
    }
}
