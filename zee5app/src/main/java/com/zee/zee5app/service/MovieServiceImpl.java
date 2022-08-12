package com.zee.zee5app.service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import javax.naming.InvalidNameException;

import com.zee.zee5app.dto.Movie;
import com.zee.zee5app.exceptions.InvalidIdException;
import com.zee.zee5app.exceptions.NoDataFoundException;
import com.zee.zee5app.exceptions.UnableToGenerateIdException;
import com.zee.zee5app.repo.MovieRepository;
import com.zee.zee5app.repo.MovieRepositoryImpl;

public class MovieServiceImpl implements MovieService {

	private MovieServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	private static MovieService movieServiceImpl;	// this is of Interface type
	
	public static MovieService getInstance() {
		if(movieServiceImpl == null) {
			movieServiceImpl = new MovieServiceImpl();
		}
		return movieServiceImpl;
		
	}
	
	private MovieRepository movieRepository = MovieRepositoryImpl.getInstance();  // Interface ref = class object()
	
	@Override
	public Movie insertMovie(Movie movie) throws UnableToGenerateIdException, FileNotFoundException {
		// TODO Auto-generated method stub
		return movieRepository.insertMovie(movie);
	}

	@Override
	public Optional<Movie> updateMovie(String movieId, Movie movie) throws NoDataFoundException, InvalidIdException {
		// TODO Auto-generated method stub
		return movieRepository.updateMovie(movieId, movie);
	}

	@Override
	public Optional<Movie> getMovieByMovieId(String movieId) throws InvalidNameException, InvalidIdException {
		// TODO Auto-generated method stub
		return movieRepository.getMovieByMovieId(movieId);
	}

	@Override
	public Optional<List<Movie>> getAllMovies() throws InvalidNameException, InvalidIdException {
		// TODO Auto-generated method stub
		return movieRepository.getAllMovies();
	}

	@Override
	public Optional<List<Movie>> getAllMoviesByGenre(String genre) throws InvalidNameException, InvalidIdException, NoDataFoundException {
		// TODO Auto-generated method stub
		return movieRepository.getAllMoviesByGenre(genre);
	}

	@Override
	public Optional<List<Movie>> getAllMoviesByName(String movieName) throws InvalidNameException, InvalidIdException, NoDataFoundException {
		// TODO Auto-generated method stub
		return movieRepository.getAllMoviesByName(movieName);
	}

	@Override
	public String deleteMovieByMovieId(String movieId) throws NoDataFoundException {
		// TODO Auto-generated method stub
		return movieRepository.deleteMovieByMovieId(movieId);
	}

	@Override
	public Optional<List<Movie>> findByOrderByMovieNameDsc() throws InvalidNameException, InvalidIdException {
		// TODO Auto-generated method stub
		return movieRepository.findByOrderByMovieNameDsc();
	}

}
