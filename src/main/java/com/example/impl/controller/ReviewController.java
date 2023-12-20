package com.example.impl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.impl.dto.ReviewRequest;
import com.example.impl.dto.ReviewResponse;
import com.example.impl.service.ReviewService;
import com.example.impl.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
//@RequestMapping("/reviews")
@RequestMapping
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@PostMapping("/{userId}/{movieId}/reviews")
	public ResponseEntity<ResponseStructure<ReviewResponse>> addReview(@RequestBody @Valid ReviewRequest reviewRequest,@PathVariable int userId,@PathVariable int movieId) {
		return reviewService.addReview( reviewRequest, userId, movieId);
	}
	
	@GetMapping("/reviews/{reviewId}")
	public ResponseEntity<ResponseStructure<ReviewResponse>> findReviewById(@PathVariable int reviewId)
	{
		return reviewService.findReviewById(reviewId);
	}
	
	@PutMapping("/reviews/{reviewId}")
	public ResponseEntity<ResponseStructure<ReviewResponse>> updateReviewById(@RequestBody @Valid ReviewRequest request,@PathVariable int reviewId){
		return reviewService.updateReviewById(request, reviewId);
	}
	
	@DeleteMapping("/reviews/{reviewId}")
	public ResponseEntity<ResponseStructure<ReviewResponse>> deleteReviewById(@PathVariable int reviewId){
		return reviewService.deleteReviewById(reviewId);
	}
	
	@GetMapping("/reviews")
	public ResponseEntity<ResponseStructure<List<ReviewResponse>>> AllReview() {
		return reviewService.AllReview();
	}
	
	@GetMapping("/{movieId}/reviews")
	public ResponseEntity<ResponseStructure<List<ReviewResponse>>> getReviewByMovieId(@PathVariable int movieId) {
		return reviewService.findReviewByMovieId(movieId);
	}
	
	

}
