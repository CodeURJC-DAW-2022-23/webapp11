package com.techmarket.app.Repositories;



import com.techmarket.app.model.Product;

import java.util.ArrayList;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    //Product findByProductId(String productId);
    //Not really sure if we need this one
    
    List<Product> findByProductPrice(String price);

    List<Product> findByTags(String tags);

    List<Product> findByProductName(String prodcutName);

}
