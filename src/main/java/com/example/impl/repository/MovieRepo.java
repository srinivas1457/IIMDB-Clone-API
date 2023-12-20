package com.example.impl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.impl.entity.Movie;
import com.example.impl.enums.Genres;

public interface MovieRepo extends JpaRepository<Movie, Integer> {

	public Movie findByMovieName(String movieName);
	
	@Query("select m from Movie m where m.movieGenres = ?1")
	public List<Movie> findMovieByGenres(Genres movieGenres);

}
