package com.techmarket.app.service;

import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.model.Image;
import com.techmarket.app.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageService imageService;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> searchProducts(Pageable pageable, String product) {
        return productRepository.findByProductNameContaining(product, pageable);
    }

    public Product createProduct(Product product) {
        productRepository.save(product);
        return product;
    }



    public Product getProductById(Long id){
        return productRepository.findByProductId(id);
    }

    public void deleteByProductId(Long id) {
        productRepository.deleteByProductId(id);
    }

    public  List<Product> getRandomProducts() {
        return productRepository.findRandomProducts();
    }

    public Page<Product> getByProductNameContaining( String productName,Pageable pageable) {
        return productRepository.findByProductNameContaining(productName, pageable);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }


}
