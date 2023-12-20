package com.example.impl.dto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.example.impl.entity.Review;
import com.example.impl.enums.Genres;
import com.example.impl.enums.Language;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MovieRequest {

	@NotBlank(message = "Movie name cannot be blank")
	@Size(max = 255, message = "Movie name must be less than or equal to 255 characters")
	private String movieName;

	@NotNull(message = "Genre cannot be null")
	@Enumerated(EnumType.STRING)
	@Pattern(regexp = "[A-Z]{1}[a-zA-Z\\s]*", message = "Genres  should in capital letters")
	private Genres movieGenres;

	@NotNull(message = "Genre cannot be null,Please Enter any one ACTION ,\r\n"
			+ "	TRILLER,\r\n"
			+ "	ROMANCE,\r\n"
			+ "	COMEDY,\r\n"
			+ "	HORROR,\r\n"
			+ "	DRAMA,\r\n"
			+ "	ADVENTURE,\r\n"
			+ "	CRIME,\r\n"
			+ "	BIOPIC")
	@Enumerated(EnumType.STRING)
	@Pattern(regexp = "[A-Z]*", message = "Language  should in capital letters,Please Enter one Language ENGLISH,\r\n"
			+ "	HINDI,\r\n"
			+ "	TELUGU,\r\n"
			+ "	TAMIL,\r\n"
			+ "	KANNADA,\r\n"
			+ "	MALAYALAM")
	private Language movieLanguage;

	@NotNull(message = "Movie duration cannot be null")
	@PastOrPresent(message = "Movie duration must be in the past or present")
	private LocalTime movieDuration;
	

	private List<Review> requestReviewList = new ArrayList<>();

	public List<Review> getRequestReviewList() {
		return requestReviewList;
	}

	public void setRequestReviewList(List<Review> requestReviewList) {
		this.requestReviewList = requestReviewList;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public Genres getMovieGenres() {
		return movieGenres;
	}

	public void setMovieGenres(Genres movieGenres) {
		this.movieGenres = movieGenres;
	}

	public Language getMovieLanguage() {
		return movieLanguage;
	}

	public void setMovieLanguage(Language movieLanguage) {
		this.movieLanguage = movieLanguage;
	}

	public LocalTime getMovieDuration() {
		return movieDuration;
	}

	public void setMovieDuration(LocalTime movieDuration) {
		this.movieDuration = movieDuration;
	}

	
}
