package com.zee.zee5app.service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import javax.naming.InvalidNameException;

import com.zee.zee5app.dto.WebSeries;
import com.zee.zee5app.exceptions.InvalidIdException;
import com.zee.zee5app.exceptions.NoDataFoundException;
import com.zee.zee5app.exceptions.UnableToGenerateIdException;
import com.zee.zee5app.repo.WebSeriesRepository;
import com.zee.zee5app.repo.WebSeriesRepositoryImpl;

public class WebSeriesServiceImpl implements WebSeriesService {

	private WebSeriesServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	private static WebSeriesService movieServiceImpl;	// this is of Interface type
	
	public static WebSeriesService getInstance() {
		if(movieServiceImpl == null) {
			movieServiceImpl = new WebSeriesServiceImpl();
		}
		return movieServiceImpl;
		
	}
	
	private WebSeriesRepository movieRepository = WebSeriesRepositoryImpl.getInstance();  // Interface ref = class object()
	
	@Override
	public WebSeries insertWebSeries(WebSeries movie) throws UnableToGenerateIdException, FileNotFoundException {
		// TODO Auto-generated method stub
		return movieRepository.insertWebSeries(movie);
	}

	@Override
	public Optional<WebSeries> updateWebSeries(String movieId, WebSeries movie) throws NoDataFoundException {
		// TODO Auto-generated method stub
		return movieRepository.updateWebSeries(movieId, movie);
	}

	@Override
	public Optional<WebSeries> getWebSeriesByWebSeriesId(String movieId) throws InvalidNameException, InvalidIdException {
		// TODO Auto-generated method stub
		return movieRepository.getWebSeriesByWebSeriesId(movieId);
	}

	@Override
	public Optional<List<WebSeries>> getAllWebSeriess() throws InvalidNameException, InvalidIdException {
		// TODO Auto-generated method stub
		return movieRepository.getAllWebSeriess();
	}

	@Override
	public Optional<List<WebSeries>> getAllWebSeriessByGenre(String genre) throws InvalidNameException, InvalidIdException, NoDataFoundException {
		// TODO Auto-generated method stub
		return movieRepository.getAllWebSeriessByGenre(genre);
	}

	@Override
	public Optional<List<WebSeries>> getAllWebSeriessByName(String movieName) throws InvalidNameException, InvalidIdException, NoDataFoundException {
		// TODO Auto-generated method stub
		return movieRepository.getAllWebSeriessByName(movieName);
	}

	@Override
	public String deleteWebSeriesByWebSeriesId(String movieId) throws NoDataFoundException {
		// TODO Auto-generated method stub
		return movieRepository.deleteWebSeriesByWebSeriesId(movieId);
	}

	@Override
	public Optional<List<WebSeries>> findByOrderByWebSeriesNameDsc() throws InvalidNameException, InvalidIdException {
		// TODO Auto-generated method stub
		return movieRepository.findByOrderByWebSeriesNameDsc();
	}

}
