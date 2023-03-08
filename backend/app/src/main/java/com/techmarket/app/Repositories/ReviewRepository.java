package com.techmarket.app.Repositories;


import com.techmarket.app.model.Product;
import com.techmarket.app.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface ReviewRepository extends JpaRepository<Review, String> {



    Review findByReviewId(long id);

    Optional<Review> findById(Long id);


    //List<Review> findByProductId(String product_id);
    List<Review> findByUserId(Long user_id);
    List<Review> findByRating(int rating);


    List<Review> findAllByProduct(Product product);

    Page<Review> findByProduct(Product product, Pageable pageable);
}
