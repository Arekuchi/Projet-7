package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RatingController {
    // TODO: Inject Rating service
    @Autowired
    RatingRepository ratingRepository;

    private static final Logger logger = LogManager.getLogger("You are on the RatingController");


    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        // TODO: find all Rating, add to model
        logger.info("methode home : /rating/list");

        model.addAttribute("ratings", ratingRepository.findAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        logger.info("methode addRatingForm : /rating/add");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        logger.info("methode validate : /rating/validate");
        if (!result.hasErrors()) {
            ratingRepository.save(rating);
            model.addAttribute(rating);
            return "redirect:/rating/list";
        }

        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        logger.info("methode showUpdateForm : /rating/update/{id}");
        Rating rating = ratingRepository.findById(id) .orElseThrow(() -> new IllegalArgumentException("Invalid rating ID :" + id));
        model.addAttribute("rating", rating);


        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list

        logger.info("methode updateRating : /rating/update/{id}");
        if (result.hasErrors()) {
            return "rating/update";
        }
        rating.setId(id);
        ratingRepository.save(rating);
        model.addAttribute("curvePoint", rating);


        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list

        logger.info("methode deleteRating : /rating/delete/{id}");
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating ID :" + id));
        ratingRepository.delete(rating);

        return "redirect:/rating/list";
    }
}
