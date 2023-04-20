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
public class UserProductsService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getCartProducts(Pageable pageable, User user) {
        List<Product> products = user.getShoppingCart();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Product> list;
        //create a page of products from the cart
        if (products.size() < startItem) {
            list = List.of();
        } else {
            int toIndex = Math.min(startItem + pageSize, products.size());
            list = products.subList(startItem, toIndex);
        }

        return new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), products.size());





    }

    public Page<Product> getWishlistProducts(Pageable pageable, User user) {
        List<Product> products = user.getWishlist();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Product> list;
        //create a page of products from the cart
        if (products.size() < startItem) {
            list = List.of();
        } else {
            int toIndex = Math.min(startItem + pageSize, products.size());
            list = products.subList(startItem, toIndex);
        }

        return new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), products.size());

    }



}
