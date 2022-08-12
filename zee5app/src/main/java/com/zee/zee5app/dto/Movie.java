package com.zee.zee5app.dto;

import javax.naming.InvalidNameException;

import com.zee.zee5app.enums.Geners;
import com.zee.zee5app.enums.Languages;
import com.zee.zee5app.exceptions.InvalidIdException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Movie {
	
	public Movie(String[] actors, String movieName, String director, Geners genre, String production,
			String[] languages, float movieLength, String trailer1) throws InvalidIdException, InvalidNameException {
		super();
		this.actors = actors;
		this.setMovieName(movieName);
		this.director = director;
		this.genre = genre;
		this.production = production;
		this.setLanguages(languages);
		this.movieLength = movieLength;
		this.setTrailer1(trailer1);
	}
	
	public void setMovieId(String movieId) throws InvalidIdException {
		// movie id should be 10 chars
		int length = movieId.length();
		if(length == 10)
		{
			this.movieId = movieId;
		}
		else {
			throw new InvalidIdException("Invalid movie id");
		}
	}
	public void setActors(String[] actors) {
		this.actors = actors;
	}
	public void setMovieName(String movieName) throws InvalidNameException {
		if(movieName =="" || movieName == null || movieName.length() < 3) {			
			throw new InvalidNameException("Invalid movie name !!");
		}else {
			this.movieName = movieName;
		}
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public void setGenre(Geners genre) {
		this.genre = genre;
	}
	public void setProduction(String production) {
		this.production = production;
	}
	public void setLanguages(String[] languages) throws InvalidNameException {
		int count = 0;
		for (String string : languages) {
//			System.out.println(Languages.valueOf(string));
			for(Languages l : Languages.values()) {
				if(Languages.valueOf(string).compareTo(l)== 0) {
					count++;
				}
			}
		}
		if(count == languages.length)
			this.languages = languages;
		else
			throw new InvalidNameException(director);
	}
	public void setMovieLength(float movieLength) {
		this.movieLength = movieLength;
	}
	public Movie() {
		
	}
	private String movieId;
    private String actors[];
    private String movieName;
    private String director;
    private Geners genre;
    private String production;
    public void setTrailer1(String trailer1) {
		this.trailer1 = trailer1;
	}
	private String languages[];
    private float movieLength;
    private String trailer1;
//    private byte[] trailer2;
}
