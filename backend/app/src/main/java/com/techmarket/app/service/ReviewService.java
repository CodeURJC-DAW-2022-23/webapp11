package com.techmarket.app.service;

import com.techmarket.app.Repositories.ReviewRepository;
import com.techmarket.app.model.Product;
import com.techmarket.app.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {


    @Autowired
    private ReviewRepository reviewRepository;

    public Review getReviewById(long id) {
        return reviewRepository.findByReviewId(id);
    }


    public Page<Review> getReviewsByProduct(Product product, Pageable pageable) {
        return reviewRepository.findByProduct(product, pageable);
    }
    public List<Review> getAllReviewsByProduct(Product product){
        return reviewRepository.findAllByProduct(product);
    }

    public List<Review> getAllReviewsByProductId(Long id) {
        return reviewRepository.findByProductId(id);
    }
     public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    public void deleteReviewById(long id) {
        reviewRepository.deleteByReviewId(id);
    }
}
