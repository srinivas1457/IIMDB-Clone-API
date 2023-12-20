package com.example.impl.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.impl.dto.ReviewRequest;
import com.example.impl.dto.ReviewResponse;
import com.example.impl.entity.Movie;
import com.example.impl.entity.Review;
import com.example.impl.entity.User;
import com.example.impl.exception.MovieNotFoundByIdException;
import com.example.impl.exception.ReviewDataNotPresent;
import com.example.impl.exception.ReviewNotfoundByIdException;
import com.example.impl.exception.UserNotFoundByIdException;
import com.example.impl.repository.MovieRepo;
import com.example.impl.repository.ReviewRepo;
import com.example.impl.repository.UserRepo;
import com.example.impl.service.ReviewService;
import com.example.impl.util.ResponseStructure;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepo reviewRepo;

	@Autowired
	private MovieRepo movieRepo;

	@Autowired
	private UserRepo userRepo;

	private Review convertReview(ReviewRequest reviewRequest) {
		Review review = new Review();
		review.setReviewMessage(reviewRequest.getReviewMessage());
		review.setReviewRating(reviewRequest.getReviewRating());

		return review;
	}

	private ReviewResponse convertReviewResponse(Review review) {
		ReviewResponse response = new ReviewResponse();
		response.setReviewId(review.getReviewId());
		response.setReviewMessage(review.getReviewMessage());
		response.setReviewRating(review.getReviewRating());
		return response;
	}

	@Override
	public ResponseEntity<ResponseStructure<ReviewResponse>> addReview(ReviewRequest reviewRequest, int userId,
			int movieId) {
		Optional<User> userOptional = userRepo.findById(userId);
		Optional<Movie> movieOptional = movieRepo.findById(movieId);
		if (userOptional.isPresent()) {
			if (movieOptional.isPresent()) {
				Review review = convertReview(reviewRequest);
				review.setUser(userOptional.get());
				review.setMovie(movieOptional.get());

				Review review2 = reviewRepo.save(review);

				ReviewResponse reviewResponse = convertReviewResponse(review2);
				Map<String, String> hyperLink = new HashMap<>();
				hyperLink.put("movies", "/movies/"+review2.getMovie().getMovieId());
				hyperLink.put("users", "/users/"+review2.getUser().getUserId());
				reviewResponse.setOptions(hyperLink);

				ResponseStructure<ReviewResponse> structure = new ResponseStructure<>();
				structure.setMessage("Review Data Inserted Successfully");
				structure.setStatusCode(HttpStatus.CREATED.value());
				structure.setData(reviewResponse);

				return new ResponseEntity<ResponseStructure<ReviewResponse>>(structure, HttpStatus.CREATED);

			} else {
				throw new MovieNotFoundByIdException("Movie Id NotFound to Fetch the Data !!");
			}
		} else {
			throw new UserNotFoundByIdException("Id NotFound to Fetch the Data !!");
		}

	}

	@Override
	public ResponseEntity<ResponseStructure<ReviewResponse>> findReviewById(int reviewId) {
		Optional<Review> optional = reviewRepo.findById(reviewId);
		if (optional.isPresent()) {
			Review review2 = optional.get();

			ReviewResponse reviewResponse = convertReviewResponse(review2);
			Map<String, String> hyperLink = new HashMap<>();
			hyperLink.put("movies", "/movies/"+review2.getMovie().getMovieId());
			hyperLink.put("users", "/users/"+review2.getUser().getUserId());
			reviewResponse.setOptions(hyperLink);

			ResponseStructure<ReviewResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("Review Data Found Successfully");
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setData(reviewResponse);

			return new ResponseEntity<ResponseStructure<ReviewResponse>>(responseStructure, HttpStatus.FOUND);
		} else {
			throw new ReviewNotfoundByIdException("Review Id Not Found to Fetch the Data !!");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<ReviewResponse>> updateReviewById(ReviewRequest request, int reviewId) {
		Optional<Review> optional = reviewRepo.findById(reviewId);
		if (optional.isPresent()) {
			
			Review existingReview=optional.get();
			
			Review review = convertReview(request);
			
			review.setReviewId(existingReview.getReviewId());
			review.setReviewRating(null);
			
			Review review2 = reviewRepo.save(review);
			ReviewResponse reviewResponse = convertReviewResponse(review2);
			ResponseStructure<ReviewResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("Review Data Updated Successfully");
			responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
			responseStructure.setData(reviewResponse);

			return new ResponseEntity<ResponseStructure<ReviewResponse>>(responseStructure, HttpStatus.ACCEPTED);
		} else {
			throw new ReviewNotfoundByIdException("Review Id Not Found to Fetch the Data !!");

		}

	}
	

	@Override
	public ResponseEntity<ResponseStructure<ReviewResponse>> deleteReviewById(int reviewId) {

		Optional<Review> optional = reviewRepo.findById(reviewId);
		if (optional.isPresent()) {
			Review review = optional.get();
			reviewRepo.delete(review);

			ReviewResponse reviewResponse = convertReviewResponse(review);

			ResponseStructure<ReviewResponse> structure = new ResponseStructure<>();
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setMessage("Review data deleted successfully!!!");
			structure.setData(reviewResponse);
			return new ResponseEntity<ResponseStructure<ReviewResponse>>(structure, HttpStatus.FOUND);
		} else {
			throw new MovieNotFoundByIdException("Review Id NotFound to delete the Movie Data !!");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<List<ReviewResponse>>> AllReview() {
		List<Review> reviewList = reviewRepo.findAll();
		if (!reviewList.isEmpty()) {
			List<ReviewResponse> list = new ArrayList<>();
			for (Review review : reviewList) {

				ReviewResponse reviewResponse = convertReviewResponse(review);
				Map<String, String> hyperLink = new HashMap<>();
				hyperLink.put("movies", "/movies/"+review.getMovie().getMovieId());
				hyperLink.put("users", "/users/"+review.getUser().getUserId());
				reviewResponse.setOptions(hyperLink);
			}

			ResponseStructure<List<ReviewResponse>> structure = new ResponseStructure<>();
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("Review Records Found");
			structure.setData(list);

			return new ResponseEntity<ResponseStructure<List<ReviewResponse>>>(structure, HttpStatus.FOUND);
		} else {
			throw new ReviewDataNotPresent("No Review Data Present!!");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<List<ReviewResponse>>> findReviewByMovieId(int movieId) {
		Optional<Movie> optional = movieRepo.findById(movieId);
		if (optional.isPresent()) {
			List<Review> reviewsList = reviewRepo.findReviewByMovieId(movieId);
			if (!reviewsList.isEmpty()) {
				List<ReviewResponse> list = new ArrayList<>();
				for (Review review : reviewsList) {

					ReviewResponse reviewResponse = convertReviewResponse(review);

					Map<String, String> userHyperLink = new HashMap<>();
					userHyperLink.put("users", "/users/"+review.getUser().getUserId());
					reviewResponse.setOptions(userHyperLink);
					list.add(reviewResponse);
		
				}

				ResponseStructure<List<ReviewResponse>> structure = new ResponseStructure<>();
				structure.setStatusCode(HttpStatus.FOUND.value());
				structure.setMessage("Review Records Found");
				structure.setData(list);

				return new ResponseEntity<ResponseStructure<List<ReviewResponse>>>(structure, HttpStatus.FOUND);
			} else {
				throw new ReviewDataNotPresent("No Review Data Present!!");
			}

		} else {
			throw new MovieNotFoundByIdException("Movie Id NotFound to Fetch the Data !!");
		}
	}

}
