package com.example.impl.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.impl.dto.ReviewRequest;
import com.example.impl.dto.ReviewResponse;
import com.example.impl.util.ResponseStructure;

public interface ReviewService {
	
	public ResponseEntity<ResponseStructure<ReviewResponse>> addReview(ReviewRequest request,int userId,int MovieId);
	public ResponseEntity<ResponseStructure<ReviewResponse>> findReviewById(int reviewId);
	public ResponseEntity<ResponseStructure<ReviewResponse>> updateReviewById(ReviewRequest request,int reviewId);
	public ResponseEntity<ResponseStructure<ReviewResponse>> deleteReviewById(int reviewId);
	public ResponseEntity<ResponseStructure<List<ReviewResponse>>> AllReview();
	public ResponseEntity<ResponseStructure<List<ReviewResponse>>> findReviewByMovieId(int movieId);
		
}
