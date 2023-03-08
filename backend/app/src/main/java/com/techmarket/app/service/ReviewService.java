package com.techmarket.app.service;

import com.techmarket.app.Repositories.ReviewRepository;
import com.techmarket.app.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {


    @Autowired
    private ReviewRepository reviewRepository;

    public Review getReviewById(long id) {
        return reviewRepository.findByReviewId(id);
    }

}
