package com.example.fooddelivery.models;

public class Review {
    private Integer feed_id;
    private String feed_name;
    private String feed_review;
    private Integer feed_stars;

    public Review() {
    }

    public Review(Integer feed_id, String feed_name, String feed_review, Integer feed_stars) {
        this.feed_id = feed_id;
        this.feed_name = feed_name;
        this.feed_review = feed_review;
        this.feed_stars = feed_stars;
    }


    public Integer getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(Integer feed_id) {
        this.feed_id = feed_id;
    }

    public String getFeed_name() {
        return feed_name;
    }

    public void setFeed_name(String feed_name) {
        this.feed_name = feed_name;
    }

    public String getFeed_review() {
        return feed_review;
    }

    public void setFeed_review(String feed_review) {
        this.feed_review = feed_review;
    }

    public Integer getFeed_stars() {
        return feed_stars;
    }

    public void setFeed_stars(Integer feed_stars) {
        this.feed_stars = feed_stars;
    }
}
