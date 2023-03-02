package com.techmarket.app.Repositories;

import com.techmarket.app.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    long deleteByProductId(Long id);

    // Pageable findAll
    Page<Product> findAll(Pageable pageable);

    List<Product> findAllByTags(String tag);

    // Find products by name but allow for partial matches
    @Query("SELECT p FROM Product p WHERE p.productName LIKE %?1%")  // ?1 is the first parameter passed to the method and %?1% means that we allow for partial matches (e.g. "iphone" will match "iphone 14") because we are using LIKE
    Page<Product> findByProductNameContaining(String productName, Pageable pageable);

    @Query("select p from Product p where p.user.email = :email")
    Page<Product> findProductsInShoppingCart(@Param("email") String email, Pageable pageable);

    @Query("select p from Product p where p.user.email = :email")
    Page<Product> findProductsInWishlist(@Param("email") String email, Pageable pageable);


    //void deleteAllById(String productId);

    // For adding new products, we will use the save method from JpaRepository

}
