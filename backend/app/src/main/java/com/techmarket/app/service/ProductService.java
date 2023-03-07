package com.techmarket.app.service;

import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

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

    public Product updateProduct(Long id, Product product) {
        Product updateProduct = productRepository.findByProductId(id);
        updateProduct.setProductName(product.getProductName());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setProductPrice(product.getProductPrice());
        updateProduct.setMainImage(product.getMainImage());
        updateProduct.setImages(product.getImages());
        updateProduct.setTags(product.getTags());
        productRepository.save(updateProduct);
        return updateProduct;
    }

    public Product getProductById(Long id){
        return productRepository.findByProductId(id);
    }

    public void deleteByProductId(Long id) {
        productRepository.deleteByProductId(id);
    }



}
