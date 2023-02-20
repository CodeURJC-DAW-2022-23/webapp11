package com.techmarket.app.Repositories;



import com.techmarket.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, String> {

    //Product findByProductId(String productId);
    //Not really sure if we need this one
    
    List<Product> findByProductPrice(String price);

    List<Product> findByTags(String tags);

    List<Product> findByProductName(String prodcutName);

}
