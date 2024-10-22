package com.example.backend.controller;

import com.example.backend.dto.CreateRatingRequest;
import com.example.backend.dto.RatingResponse;
import com.example.backend.model.Rating;
import com.example.backend.service.RatingService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public void save(@RequestBody @NotNull CreateRatingRequest request, @AuthenticationPrincipal OAuth2User user) {
        Rating rating = request.toRating(user.getAttributes().get("login").toString());

        ratingService.save(rating);
    }

    @GetMapping("/{movieId}")
    public RatingResponse get(@AuthenticationPrincipal OAuth2User user, @PathVariable String movieId) {
        Rating rating = ratingService.get(user.getAttributes().get("login").toString(), movieId);

        return RatingResponse.from(rating);
    }
}
