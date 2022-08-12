package com.zee.zee5app.repo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.naming.InvalidNameException;

import com.zee.zee5app.dto.WebSeries;
import com.zee.zee5app.enums.Geners;
import com.zee.zee5app.exceptions.InvalidIdException;
import com.zee.zee5app.exceptions.NoDataFoundException;
import com.zee.zee5app.exceptions.UnableToGenerateIdException;
import com.zee.zee5app.utils.DBUtils;

public class WebSeriesRepositoryImpl implements WebSeriesRepository {

	private WebSeriesRepositoryImpl() {
		
    }
    
    private static WebSeriesRepository webSeriesRepository;
    
    public static WebSeriesRepository getInstance() {
        
        if(webSeriesRepository == null) {
        	webSeriesRepository = new WebSeriesRepositoryImpl();
            
        }
        
        return webSeriesRepository;
    }

    private DBUtils dbUtils = DBUtils.getInstance();
    
	@Override
	public WebSeries insertWebSeries(WebSeries webSeries) throws UnableToGenerateIdException, FileNotFoundException {
		// trailer file exist or not 
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		File file = new File(webSeries.getTrailer1());
		try {
			if(webSeries.getTrailer1() == null || 
					webSeries.getTrailer1()=="" || 
					!file.exists()) {
				throw new FileNotFoundException("File does not exist");
			}else {
				bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
				bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("d:\\zee5app\\trailers\\"+file.getName()), 2048);
				bufferedOutputStream.write(bufferedInputStream.readAllBytes());
//				System.out.println(file.getName());
				
			} 
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally {
			try {
				bufferedInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String insertStatement = "insert into webseries"
				+ "(webseriesid, actors, webseriesname, director, genre, production, languages,numberofepisodes, trailer)"
				+ " values(?,?,?,?,?,?,?,?,?)";
		
		// connection object
		connection = dbUtils.getConnection();
		
		// statement object(prepared)
		try {
			preparedStatement = connection.prepareStatement(insertStatement);
			preparedStatement.setString(1, dbUtils.webSeriesIdGenerator(webSeries.getWebSeriesName()));
			String actors_name = String.join(",", webSeries.getActors());
			preparedStatement.setString(2, actors_name);
			preparedStatement.setString(3, webSeries.getWebSeriesName());
			preparedStatement.setString(4, webSeries.getDirector());
			preparedStatement.setString(5, webSeries.getGenre().name());
			preparedStatement.setString(6, webSeries.getProduction());
			String langs = String.join(",", webSeries.getLanguages());
			preparedStatement.setString(7, langs);
			preparedStatement.setInt(8, webSeries.getEpisodes());
			preparedStatement.setString(9, webSeries.getTrailer1());
			int result = preparedStatement.executeUpdate();
			if(result > 0) {
				return webSeries;
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbUtils.closeConnection(connection);
		}
		
		return null;
	}

	@Override
	public Optional<WebSeries> updateWebSeries(String webSeriesId, WebSeries webSeries) throws NoDataFoundException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String deleteStatement = "update webseries set actors=?, webseriesname=?, director=?, genre=?, production=?, languages=?,numberofepisodes=?, trailer=? where webseriesid=?";
		connection = dbUtils.getConnection();
		try {
			preparedStatement = connection.prepareStatement(deleteStatement);
			preparedStatement.setString(1, String.join(",", webSeries.getActors()));
			preparedStatement.setString(2, webSeries.getWebSeriesName());
			preparedStatement.setString(3, webSeries.getDirector());
			preparedStatement.setString(4, webSeries.getGenre().name());
			preparedStatement.setString(5,webSeries.getProduction());
			preparedStatement.setString(6, String.join(",", webSeries.getLanguages()));
			preparedStatement.setInt(7, webSeries.getEpisodes());
			preparedStatement.setString(8,webSeries.getTrailer1());
			preparedStatement.setString(9,webSeriesId);
			int res = preparedStatement.executeUpdate();
			if(res > 0)
				return Optional.of(webSeries);
			else
				throw new NoDataFoundException("No data found to update with this webSeries Id !!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			dbUtils.closeConnection(connection);
		}
		return Optional.empty();
	}

	@Override
	public Optional<WebSeries> getWebSeriesByWebSeriesId(String webSeriesId) throws InvalidIdException, InvalidNameException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select * from webseries where webseriesid=?";
		ResultSet resultSet = null;
		// connection object
		connection = dbUtils.getConnection();
		
		// statement object(preparedstatement)
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, webSeriesId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				// record exist
				// result will be inside the RESULTSET object
				// convert User object from resultSet data and return
				WebSeries webSeries = new WebSeries();
				webSeries.setWebSeriesId(resultSet.getString("webseriesid"));
				webSeries.setWebSeriesName(resultSet.getString("webseriesname"));
				webSeries.setDirector(resultSet.getString("director"));
				webSeries.setActors(resultSet.getString("actors").split(","));
				webSeries.setGenre(Geners.valueOf(resultSet.getString("genre")));
				webSeries.setProduction(resultSet.getString("production"));
				webSeries.setEpisodes(resultSet.getInt("numberofepisodes"));
				webSeries.setLanguages(resultSet.getString("languages").split(","));
				webSeries.setTrailer1(resultSet.getString("trailer"));
				return Optional.of(webSeries);
			}else {
				Optional.empty();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbUtils.closeConnection(connection);
		}
		return Optional.empty();
	}

	@Override
	public Optional<List<WebSeries>> getAllWebSeriess() throws InvalidIdException, InvalidNameException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select * from webseries";
		ResultSet resultSet = null;
		// connection object
		connection = dbUtils.getConnection();
		
		// statement object(preparedstatement)
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			List<WebSeries> webSeriess = new ArrayList<>();
			while(resultSet.next()) {
				// record exist
				// result will be inside the RESULTSET object
				// convert User object from resultSet data and return
				WebSeries webSeries = new WebSeries();
				webSeries.setWebSeriesId(resultSet.getString("webseriesid"));
				webSeries.setWebSeriesName(resultSet.getString("webseriesname"));
				webSeries.setDirector(resultSet.getString("director"));
				webSeries.setActors(resultSet.getString("actors").split(","));
				webSeries.setGenre(Geners.valueOf(resultSet.getString("genre")));
				webSeries.setProduction(resultSet.getString("production"));
				webSeries.setEpisodes(resultSet.getInt("numberofepisodes"));
				webSeries.setLanguages(resultSet.getString("languages").split(","));
				webSeries.setTrailer1(resultSet.getString("trailer"));
				webSeriess.add(webSeries);
			}
			return Optional.of(webSeriess);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbUtils.closeConnection(connection);
		}
		return Optional.empty();
	}

	@Override
	public Optional<List<WebSeries>> findByOrderByWebSeriesNameDsc() throws InvalidIdException, InvalidNameException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select * from webseries";
		ResultSet resultSet = null;
		// connection object
		connection = dbUtils.getConnection();
		
		// statement object(preparedstatement)
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			List<WebSeries> webSeriess = new ArrayList<>();
			while(resultSet.next()) {
				// record exist
				// result will be inside the RESULTSET object
				// convert User object from resultSet data and return
				WebSeries webSeries = new WebSeries();
				webSeries.setWebSeriesId(resultSet.getString("webseriesid"));
				webSeries.setWebSeriesName(resultSet.getString("webseriesname"));
				webSeries.setDirector(resultSet.getString("director"));
				webSeries.setActors(resultSet.getString("actors").split(","));
				webSeries.setGenre(Geners.valueOf(resultSet.getString("genre")));
				webSeries.setProduction(resultSet.getString("production"));
				webSeries.setEpisodes(resultSet.getInt("numberofepisodes"));
				webSeries.setLanguages(resultSet.getString("languages").split(","));
				webSeries.setTrailer1(resultSet.getString("trailer"));
				webSeriess.add(webSeries);
			}
			Comparator<WebSeries> comparator = (e1,e2)-> e2.getWebSeriesName().compareTo(e1.getWebSeriesName());
			Collections.sort(webSeriess, comparator);
			return Optional.of(webSeriess);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbUtils.closeConnection(connection);
		}
		return Optional.empty();
	}

	@Override
	public Optional<List<WebSeries>> getAllWebSeriessByGenre(String genre) throws InvalidIdException, InvalidNameException, NoDataFoundException{
		
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select * from webseries where genre=?";
		ResultSet resultSet = null;
		// connection object
		connection = dbUtils.getConnection();
		
		// statement object(preparedstatement)
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, genre);
			resultSet = preparedStatement.executeQuery();
			List<WebSeries> webSeriess = new ArrayList<>();
			while(resultSet.next()) {
				// record exist
				// result will be inside the RESULTSET object
				// convert User object from resultSet data and return				
					WebSeries webSeries = new WebSeries();
					webSeries.setWebSeriesId(resultSet.getString("webseriesid"));
					webSeries.setWebSeriesName(resultSet.getString("webseriesname"));
					webSeries.setDirector(resultSet.getString("director"));
					webSeries.setActors(resultSet.getString("actors").split(","));
					webSeries.setGenre(Geners.valueOf(resultSet.getString("genre")));
					webSeries.setProduction(resultSet.getString("production"));
					webSeries.setEpisodes(resultSet.getInt("numberofepisodes"));
					webSeries.setLanguages(resultSet.getString("languages").split(","));
					webSeries.setTrailer1(resultSet.getString("trailer"));
					webSeriess.add(webSeries);
				
			}
			if(webSeriess.size() == 0) {
				throw new NoDataFoundException("No webSeries found with this gener !!!");
			}
			return Optional.of(webSeriess);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbUtils.closeConnection(connection);
		}
		return Optional.empty();
	}

	@Override
	public Optional<List<WebSeries>> getAllWebSeriessByName(String webSeriesName)throws InvalidIdException, InvalidNameException, NoDataFoundException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select * from webseries where webseriesname=?";
		ResultSet resultSet = null;
		// connection object
		connection = dbUtils.getConnection();
		
		// statement object(preparedstatement)
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, webSeriesName);
			resultSet = preparedStatement.executeQuery();
			List<WebSeries> webSeriess = new ArrayList<>();
			while(resultSet.next()) {
				// record exist
				// result will be inside the RESULTSET object
				// convert User object from resultSet data and return
				
					WebSeries webSeries = new WebSeries();
					webSeries.setWebSeriesId(resultSet.getString("webseriesid"));
					webSeries.setWebSeriesName(resultSet.getString("webseriesname"));
					webSeries.setDirector(resultSet.getString("director"));
					webSeries.setActors(resultSet.getString("actors").split(","));
					webSeries.setGenre(Geners.valueOf(resultSet.getString("genre")));
					webSeries.setProduction(resultSet.getString("production"));
					webSeries.setEpisodes(resultSet.getInt("numberofepisodes"));
					webSeries.setLanguages(resultSet.getString("languages").split(","));
					webSeries.setTrailer1(resultSet.getString("trailer"));
					webSeriess.add(webSeries);
				
			}
			if(webSeriess.size() == 0) {
				throw new NoDataFoundException("No webSeries found with this name !!!");
			}
			return Optional.of(webSeriess);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbUtils.closeConnection(connection);
		}
		return Optional.empty();
	}

	@Override
	public String deleteWebSeriesByWebSeriesId(String webSeriesId) throws NoDataFoundException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String deleteStatement = "delete from webseries where webseriesid=?";
		connection = dbUtils.getConnection();
		try {
			preparedStatement = connection.prepareStatement(deleteStatement);
			preparedStatement.setString(1, webSeriesId);
			int res = preparedStatement.executeUpdate();
			if(res > 0)
				return "success";
			else
				throw new NoDataFoundException("No data found to delete with this user Id !!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			dbUtils.closeConnection(connection);
		}
		return null;
	}

}
