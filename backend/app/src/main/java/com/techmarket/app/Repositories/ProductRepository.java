package com.techmarket.app.Repositories;

import com.techmarket.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    //Product findByProductId(String productId);
    //Not really sure if we need this one
    
    List<Product> findByProductPrice(String price);

    List<Product> findByTags(String tags);

    List<Product> findByProductName(String productName);

    Product findByProductId(Long id);

    //void deleteAllById(String productId);

    // For adding new products, we will use the save method from JpaRepository

}
