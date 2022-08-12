package com.zee.zee5app.repo;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import javax.naming.InvalidNameException;

import com.zee.zee5app.dto.WebSeries;
import com.zee.zee5app.exceptions.InvalidIdException;
import com.zee.zee5app.exceptions.NoDataFoundException;
import com.zee.zee5app.exceptions.UnableToGenerateIdException;

public interface WebSeriesRepository {
	public WebSeries insertWebSeries(WebSeries webSeries) throws UnableToGenerateIdException, FileNotFoundException;
    public Optional<WebSeries> updateWebSeries(String webSeriesId, WebSeries webSeries) throws NoDataFoundException;

    public Optional<WebSeries> getWebSeriesByWebSeriesId(String webSeriesId) throws InvalidIdException, InvalidNameException;
    public Optional<List<WebSeries>> getAllWebSeriess() throws InvalidIdException, InvalidNameException;
    public Optional<List<WebSeries>> findByOrderByWebSeriesNameDsc() throws InvalidIdException, InvalidNameException;
    public Optional<List<WebSeries>> getAllWebSeriessByGenre(String genre) throws InvalidIdException, InvalidNameException, NoDataFoundException;
    public Optional<List<WebSeries>> getAllWebSeriessByName(String webSeriesName) throws InvalidIdException, InvalidNameException, NoDataFoundException;
    
    public String deleteWebSeriesByWebSeriesId(String webSeriesId) throws NoDataFoundException;
}
