package com.example.fooddelivery.controller;


import com.example.fooddelivery.models.Review;

import com.example.fooddelivery.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @GetMapping("/review")
    public String addReview(Model model) {
        model.addAttribute("review", new Review());
        return "user/Review";
    }

    @RequestMapping(value = "/review/save", method = RequestMethod.POST)
    public String saveReview(@ModelAttribute("review") Review review) {
        reviewService.saveReview(review);
        return "redirect:/review";
    }
}
