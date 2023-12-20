package com.example.impl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.impl.entity.Review;

public interface ReviewRepo extends JpaRepository<Review, Integer> {

	@Query("select r from Review r where r.movie.movieId=?1")
	public List<Review> findReviewByMovieId(int movieId);

//@Query("SELECT r FROM Review r WHERE r.movie.movieId = ?1")
//public List<Review> findReviewByMovieId(int movieId);
	
	@Query("select avg(r.reviewRating) from Review r where r.movie.movieId=?1")
	public Double avgRating(int movieId);
	
}