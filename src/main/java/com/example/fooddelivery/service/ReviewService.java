package com.example.fooddelivery.service;

import com.example.fooddelivery.models.Review;
import com.example.fooddelivery.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ReviewRepository repo;

    public ReviewService() {

    }

    public void saveReview(Review review) {
        this.repo.saveReview(review);
    }

}
