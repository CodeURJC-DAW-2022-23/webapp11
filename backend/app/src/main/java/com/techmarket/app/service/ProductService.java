package com.techmarket.app.service;

import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findByProductId(id);
    }

    public void deleteByProductId(Long id) {
        productRepository.deleteByProductId(id);
    }



}
