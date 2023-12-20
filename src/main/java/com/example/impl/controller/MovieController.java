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

import com.example.impl.dto.MovieRequest;
import com.example.impl.dto.MovieResponse;
import com.example.impl.enums.Genres;
import com.example.impl.service.MovieService;
import com.example.impl.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
//@RequestMapping("/movies")
@RequestMapping
public class MovieController {

	@Autowired
	private MovieService movieService;

	@PostMapping("/movies")
	public ResponseEntity<ResponseStructure<MovieResponse>> saveStudent(@RequestBody @Valid MovieRequest movieRequest) {
		return movieService.addMovie(movieRequest);
	}

	@GetMapping("/movies/{movieId}")
	public ResponseEntity<ResponseStructure<MovieResponse>> findMovieById(@PathVariable int movieId) {
		return movieService.findMovieById(movieId);
	}

	@GetMapping("/movieNames/{movieName}/movies")
	public ResponseEntity<ResponseStructure<MovieResponse>> findMovieByName(@PathVariable String movieName) {
		return movieService.findMovieByName(movieName);
	}

	@PutMapping("/movies/{movieId}")
	public ResponseEntity<ResponseStructure<MovieResponse>> updateMovieById(@RequestBody @Valid MovieRequest request,
			@PathVariable int movieID) {
		return movieService.updateMovieById(request, movieID);
	}

	@DeleteMapping("/movies/{movieId}")
	public ResponseEntity<ResponseStructure<MovieResponse>> deleteMovieById(@PathVariable int movieId) {
		return movieService.deleteMovieById(movieId);
	}

	@GetMapping("/movies")
	public ResponseEntity<ResponseStructure<List<MovieResponse>>> findAllMovies() {
		return movieService.findAllMovies();
	}

	@GetMapping("/geners/{geners}/movies")
	public ResponseEntity<ResponseStructure<List<MovieResponse>>> findMovieByGenres(@PathVariable String geners) {
		return movieService.findMoviesByGenres(geners);
	}

}
