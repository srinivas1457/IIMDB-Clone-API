package com.example.impl.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.impl.dto.MovieRequest;
import com.example.impl.dto.MovieResponse;
import com.example.impl.enums.Genres;
import com.example.impl.util.ResponseStructure;

public interface MovieService {
	
	
	public ResponseEntity<ResponseStructure<MovieResponse>> addMovie(MovieRequest movieRequest);
	public ResponseEntity<ResponseStructure<MovieResponse>> findMovieById(int movieId);
	public ResponseEntity<ResponseStructure<MovieResponse>> findMovieByName(String movieName);
	public ResponseEntity<ResponseStructure<MovieResponse>> updateMovieById(MovieRequest request,int movieID);
	public ResponseEntity<ResponseStructure<MovieResponse>> deleteMovieById(int movieId);
	public ResponseEntity<ResponseStructure<List<MovieResponse>>> findAllMovies();
	public ResponseEntity<ResponseStructure<List<MovieResponse>>> findMoviesByGenres(String movieGenres);
	
	
	

}
