package com.zee.zee5app.service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import javax.naming.InvalidNameException;

import com.zee.zee5app.dto.Movie;
import com.zee.zee5app.exceptions.InvalidIdException;
import com.zee.zee5app.exceptions.NoDataFoundException;
import com.zee.zee5app.exceptions.UnableToGenerateIdException;

public interface MovieService {
	public Movie insertMovie(Movie movie) throws UnableToGenerateIdException, FileNotFoundException;
    public Optional<Movie> updateMovie(String movieId, Movie movie) throws NoDataFoundException, InvalidIdException;

    public Optional<Movie> getMovieByMovieId(String movieId) throws InvalidNameException, InvalidIdException;
    public Optional<List<Movie>> getAllMovies() throws InvalidNameException, InvalidIdException;
    public Optional<List<Movie>> getAllMoviesByGenre(String genre) throws InvalidNameException, InvalidIdException, NoDataFoundException;
    public Optional<List<Movie>> getAllMoviesByName(String movieName) throws InvalidNameException, InvalidIdException, NoDataFoundException;
    public Optional<List<Movie>> findByOrderByMovieNameDsc() throws InvalidNameException, InvalidIdException;
    public String deleteMovieByMovieId(String movieId) throws NoDataFoundException;
}
