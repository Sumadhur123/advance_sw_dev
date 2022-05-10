package com.example.fooddelivery.repository;

import com.example.fooddelivery.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public ReviewRepository() {
    }

    public void saveReview(Review review) {
        this.jdbcTemplate.update("insert into review (feed_id, feed_name, feed_review, feed_stars) values(?, ?, ?, ?)",
                new Object[]{review.getFeed_id(), review.getFeed_name(), review.getFeed_review(), review.getFeed_stars()});
    }
}
