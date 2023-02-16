package com.techmarket.app.Repositories;


import com.techmarket.app.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, String> {

 
    //Review findById(String id);
    //Not really sure if we need this one
    List<Review> findByProductId(String productId);
    List<Review> findByUserId(String userId);
    List<Review> findByRating(int rating);



}
