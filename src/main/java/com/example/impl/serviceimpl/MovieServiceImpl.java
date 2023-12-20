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

import com.example.impl.dto.MovieRequest;
import com.example.impl.dto.MovieResponse;
import com.example.impl.dto.UserResponse;
import com.example.impl.entity.Movie;
import com.example.impl.entity.User;
import com.example.impl.enums.Genres;
import com.example.impl.exception.MovieDataNotPresent;
import com.example.impl.exception.MovieNotFoundByIdException;
import com.example.impl.exception.MovieNotFoundByNameException;
import com.example.impl.exception.UsersDataNotPresent;
import com.example.impl.repository.MovieRepo;
import com.example.impl.repository.ReviewRepo;
import com.example.impl.service.MovieService;
import com.example.impl.util.ResponseStructure;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepo movieRepo;

	@Autowired
	private ReviewRepo reviewRepo;

	private Movie convertMovie(MovieRequest movieRequest) {
		Movie movie = new Movie();
		movie.setMovieName(movieRequest.getMovieName());
		movie.setMovieLanguage(movieRequest.getMovieLanguage());
		movie.setMovieGenres(movieRequest.getMovieGenres());
		movie.setMovieDuration(movieRequest.getMovieDuration());
		movie.setMovieReviewList(movieRequest.getRequestReviewList());
		return movie;
	}

	private MovieResponse convertMovieResponce(Movie movie) {
		MovieResponse movieResponse = new MovieResponse();
		movieResponse.setMovieId(movie.getMovieId());
		movieResponse.setMovieName(movie.getMovieName());
		movieResponse.setMovieLanguage(movie.getMovieLanguage());
		movieResponse.setMovieGenres(movie.getMovieGenres());
		movieResponse.setMovieDuration(movie.getMovieDuration());
		return movieResponse;
	}

	@Override
	public ResponseEntity<ResponseStructure<MovieResponse>> addMovie(MovieRequest movieRequest) {
		Movie movie = convertMovie(movieRequest);

		/*
		 * for Movie movie=new Movie() movie.setMovieName(movieRequest.getMovieName());
		 * movie.setMovieLanguage(movieRequest.getMovieLanguage());
		 * movie.setMovieGenres(movieRequest.getMovieGenres());
		 * movie.setMovieDuration(movieRequest.getMovieDuration());
		 * movie.setMovieReviewList(movieRequest.getRequestReviewList());
		 */

		Movie movie2 = movieRepo.save(movie);

		MovieResponse movieResponse = convertMovieResponce(movie2);
		/*
		 * MovieResponse movieResponse=new MovieResponse(); //
		 * movieResponse.setMovieId(movie2.getMovieId()); //
		 * movieResponse.setMovieName(movie2.getMovieName()); //
		 * movieResponse.setMovieLanguage(movie2.getMovieLanguage()); //
		 * movieResponse.setMovieGenres(movie2.getMovieGenres()); //
		 * movieResponse.setMovieDuration(movie2.getMovieDuration());
		 */
		ResponseStructure<MovieResponse> structure = new ResponseStructure<MovieResponse>();
		structure.setStatusCode(HttpStatus.CREATED.value());
		structure.setMessage("Movie Data Inserted Successfully");
		structure.setData(movieResponse);

		return new ResponseEntity<ResponseStructure<MovieResponse>>(structure, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<MovieResponse>> findMovieById(int movieId) {
		Optional<Movie> optional = movieRepo.findById(movieId);
		if (optional.isPresent()) {
			Movie movie = optional.get();

			MovieResponse movieResponse = convertMovieResponce(movie);
			movieResponse.setMovieRating(reviewRepo.avgRating(movie.getMovieId()));

			Map<String, String> o = new HashMap<>();
			o.put("reviews", "/" + movie.getMovieId() + "/reviews");
			movieResponse.setOptions(o);

			ResponseStructure<MovieResponse> structure = new ResponseStructure<>();
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("Movie Data Found Successfully");
			structure.setData(movieResponse);

			return new ResponseEntity<ResponseStructure<MovieResponse>>(structure, HttpStatus.FOUND);

		} else {
			throw new MovieNotFoundByIdException("Movie Id NotFound to Fetch the Data !!");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<MovieResponse>> findMovieByName(String movieName) {
		Movie movie = movieRepo.findByMovieName(movieName);
		if (movie != null) {
			MovieResponse movieResponse = convertMovieResponce(movie);
			movieResponse.setMovieRating(reviewRepo.avgRating(movie.getMovieId()));
			Map<String, String> o = new HashMap<>();
			o.put("reviews", "/" + movie.getMovieId() + "/reviews");
			movieResponse.setOptions(o);

			ResponseStructure<MovieResponse> structure = new ResponseStructure<>();
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("Movie Data Found Successfully");
			structure.setData(movieResponse);

			return new ResponseEntity<ResponseStructure<MovieResponse>>(structure, HttpStatus.FOUND);

		} else {
			throw new MovieNotFoundByNameException("Name NotFound to Fetch the Data !!");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<MovieResponse>> updateMovieById(MovieRequest request, int movieID) {

		Optional<Movie> optional = movieRepo.findById(movieID);
		if (optional.isPresent()) {
			Movie existingMovie = optional.get();

			Movie movie = convertMovie(request);
			movie.setMovieId(existingMovie.getMovieId());
			Movie movie2 = movieRepo.save(movie);

			MovieResponse response = convertMovieResponce(movie2);

			ResponseStructure<MovieResponse> structure = new ResponseStructure<>();
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			structure.setMessage("Movie Data Updated Successfully");
			structure.setData(response);

			return new ResponseEntity<ResponseStructure<MovieResponse>>(structure, HttpStatus.ACCEPTED);

		} else {
			throw new MovieNotFoundByIdException("Id NotFound to Update the Movie Data !!");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<MovieResponse>> deleteMovieById(int movieId) {
		Optional<Movie> optional = movieRepo.findById(movieId);
		if (optional.isPresent()) {
			Movie movie = optional.get();
			movieRepo.delete(movie);

			MovieResponse response = convertMovieResponce(movie);
			response.setMovieRating(reviewRepo.avgRating(movie.getMovieId()));

			ResponseStructure<MovieResponse> structure = new ResponseStructure<>();
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setMessage("Movie deleted successfully!!!");
			structure.setData(response);
			return new ResponseEntity<ResponseStructure<MovieResponse>>(structure, HttpStatus.OK);
		} else {
			throw new MovieNotFoundByIdException("Id Not Found to delete the Movie Data !!");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<List<MovieResponse>>> findAllMovies() {

		List<Movie> movieList = movieRepo.findAll();
		if (!movieList.isEmpty()) {
			List<MovieResponse> list = new ArrayList<>();
			for (Movie movie : movieList) {

				MovieResponse movieResponse = convertMovieResponce(movie);
				movieResponse.setMovieRating(reviewRepo.avgRating(movie.getMovieId()));
				Map<String, String> o = new HashMap<>();
				o.put("reviews", "/" + movie.getMovieId() + "/reviews");
				movieResponse.setOptions(o);

				list.add(movieResponse);
			}

			ResponseStructure<List<MovieResponse>> structure = new ResponseStructure<>();
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("Movie Records Found");
			structure.setData(list);

			return new ResponseEntity<ResponseStructure<List<MovieResponse>>>(structure, HttpStatus.FOUND);
		} else {
			throw new MovieDataNotPresent("No Movies Data Present!!");
		}

	}

	@Override
	public ResponseEntity<ResponseStructure<List<MovieResponse>>> findMoviesByGenres(String movieGenres) {
		Genres genres=Genres.valueOf(movieGenres);
		
		List<Movie> movieList = movieRepo.findMovieByGenres(genres);
		if (!movieList.isEmpty()) {
			List<MovieResponse> list = new ArrayList<>();
			for (Movie movie : movieList) {

				MovieResponse movieResponse = convertMovieResponce(movie);
				movieResponse.setMovieRating(reviewRepo.avgRating(movie.getMovieId()));
				Map<String, String> o = new HashMap<>();
				o.put("reviews", "/" + movie.getMovieId() + "/reviews");
				movieResponse.setOptions(o);

				list.add(movieResponse);
			}

			ResponseStructure<List<MovieResponse>> structure = new ResponseStructure<>();
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("Movie Records Found");
			structure.setData(list);

			return new ResponseEntity<ResponseStructure<List<MovieResponse>>>(structure, HttpStatus.FOUND);
		} else {
			throw new MovieDataNotPresent("No Movies Data Present Based on " + movieGenres + " !!");
		}

//		List<Movie> movieList=movieRepo.findAll();
//		if(movieList.isEmpty()) {
//			throw new MovieDataNotPresent("No Movies Data Present Based on " + movieGenres + " !!");
//		}
//		
//		List<MovieResponse> matchingMovies=new ArrayList<>();
//		Genres genres=Genres.valueOf(movieGenres);
//		if(genres!=null) {
//			for(Movie movie: movieList ) {
//				if(movie.getMovieGenres().equals(genres)) {
//					MovieResponse movieResponse=convertMovieResponce(movie);
////					movieResponse.setMovieRating(reviewRepo.avgRating(movie.getMovieId()));
//					Map<String, String> o = new HashMap<>();
//					o.put("reviews", "/" + movie.getMovieId() + "/reviews");
//					movieResponse.setOptions(o);
//	
//					matchingMovies.add(movieResponse);
//				}
//			}
//		}
//		ResponseStructure<List<MovieResponse>> structure = new ResponseStructure<>();
//		structure.setStatusCode(HttpStatus.FOUND.value());
//		structure.setMessage("Movie Records Found");
//		structure.setData(matchingMovies);
//
//		return new ResponseEntity<ResponseStructure<List<MovieResponse>>>(structure, HttpStatus.FOUND);

}
}