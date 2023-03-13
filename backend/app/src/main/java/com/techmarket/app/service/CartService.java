package com.techmarket.app.service;


import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.model.Product;
import com.techmarket.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getCartProducts(Pageable pageable, User user) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        List<Product> cartProducts = user.getShoppingCart();


        return new PageImpl<>(cartProducts, pageRequest, cartProducts.size());
    }

}
