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

import com.zee.zee5app.dto.Movie;
import com.zee.zee5app.enums.Geners;
import com.zee.zee5app.exceptions.InvalidIdException;
import com.zee.zee5app.exceptions.NoDataFoundException;
import com.zee.zee5app.exceptions.UnableToGenerateIdException;
import com.zee.zee5app.utils.DBUtils;

public class MovieRepositoryImpl implements MovieRepository {

	private MovieRepositoryImpl() {
		
    }
    
    private static MovieRepository movieRepository;
    
    public static MovieRepository getInstance() {
        
        if(movieRepository == null) {
        	movieRepository = new MovieRepositoryImpl();
            
        }
        
        return movieRepository;
    }

    private DBUtils dbUtils = DBUtils.getInstance();
    
	//@Override
	public Movie insertMovie(Movie movie) throws UnableToGenerateIdException, FileNotFoundException {
		// trailer file exist or not 
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		File file = new File(movie.getTrailer1());
		try {
			if(movie.getTrailer1() == null || 
					movie.getTrailer1()=="" || 
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
		String insertStatement = "insert into movies"
				+ "(movieid, actors, moviename, director, genre, production, languages,movielength, trailer)"
				+ " values(?,?,?,?,?,?,?,?,?)";
		
		// connection object
		connection = dbUtils.getConnection();
		
		// statement object(prepared)
		try {
			preparedStatement = connection.prepareStatement(insertStatement);
			preparedStatement.setString(1, dbUtils.movieIdGenerator(movie.getMovieName()));
			String actors_name = String.join(",", movie.getActors());
			preparedStatement.setString(2, actors_name);
			preparedStatement.setString(3, movie.getMovieName());
			preparedStatement.setString(4, movie.getDirector());
			preparedStatement.setString(5, movie.getGenre().name());
			preparedStatement.setString(6, movie.getProduction());
			String langs = String.join(",", movie.getLanguages());
			preparedStatement.setString(7, langs);
			preparedStatement.setFloat(8, movie.getMovieLength());
			preparedStatement.setString(9, movie.getTrailer1());
			int result = preparedStatement.executeUpdate();
			if(result > 0) {
				return movie;
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
	public Optional<Movie> updateMovie(String movieId, Movie movie) throws NoDataFoundException, InvalidIdException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String deleteStatement = "update movies set actors=?, moviename=?, director=?, genre=?, production=?, languages=?,movielength=?, trailer=? where movieid=?";
		connection = dbUtils.getConnection();
		try {
			preparedStatement = connection.prepareStatement(deleteStatement);
			preparedStatement.setString(1, String.join(",", movie.getActors()));
			preparedStatement.setString(2, movie.getMovieName());
			preparedStatement.setString(3, movie.getDirector());
			preparedStatement.setString(4, movie.getGenre().name());
			preparedStatement.setString(5,movie.getProduction());
			preparedStatement.setString(6, String.join(",", movie.getLanguages()));
			preparedStatement.setFloat(7, movie.getMovieLength());
			preparedStatement.setString(8,movie.getTrailer1());
			preparedStatement.setString(9,movieId);
			int res = preparedStatement.executeUpdate();
			if(res > 0) {
				
				movie.setMovieId(movieId);
				return Optional.of(movie);
			}
			else
				throw new NoDataFoundException("No data found to update with this movie Id !!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			dbUtils.closeConnection(connection);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Movie> getMovieByMovieId(String movieId) throws InvalidIdException, InvalidNameException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select * from movies where movieid=?";
		ResultSet resultSet = null;
		// connection object
		connection = dbUtils.getConnection();
		
		// statement object(preparedstatement)
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, movieId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				// record exist
				// result will be inside the RESULTSET object
				// convert User object from resultSet data and return
				Movie movie = new Movie();
				movie.setMovieId(resultSet.getString("movieid"));
				movie.setMovieName(resultSet.getString("moviename"));
				movie.setDirector(resultSet.getString("director"));
				movie.setActors(resultSet.getString("actors").split(","));
				movie.setGenre(Geners.valueOf(resultSet.getString("genre")));
				movie.setProduction(resultSet.getString("production"));
				movie.setMovieLength(resultSet.getFloat("movielength"));
				movie.setLanguages(resultSet.getString("languages").split(","));
				return Optional.of(movie);
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
	public Optional<List<Movie>> getAllMovies() throws InvalidIdException, InvalidNameException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select * from movies";
		ResultSet resultSet = null;
		// connection object
		connection = dbUtils.getConnection();
		
		// statement object(preparedstatement)
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			List<Movie> movies = new ArrayList<>();
			while(resultSet.next()) {
				// record exist
				// result will be inside the RESULTSET object
				// convert User object from resultSet data and return
				Movie movie = new Movie();
				movie.setMovieId(resultSet.getString("movieid"));
				movie.setMovieName(resultSet.getString("moviename"));
				movie.setDirector(resultSet.getString("director"));
				movie.setActors(resultSet.getString("actors").split(","));
				movie.setGenre(Geners.valueOf(resultSet.getString("genre")));
				movie.setProduction(resultSet.getString("production"));
				movie.setMovieLength(resultSet.getFloat("movielength"));
				movie.setLanguages(resultSet.getString("languages").split(","));
				movies.add(movie);
			}
			return Optional.of(movies);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbUtils.closeConnection(connection);
		}
		return Optional.empty();
	}

	@Override
	public Optional<List<Movie>> findByOrderByMovieNameDsc() throws InvalidIdException, InvalidNameException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select * from movies";
		ResultSet resultSet = null;
		// connection object
		connection = dbUtils.getConnection();
		
		// statement object(preparedstatement)
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			List<Movie> movies = new ArrayList<>();
			while(resultSet.next()) {
				// record exist
				// result will be inside the RESULTSET object
				// convert User object from resultSet data and return
				Movie movie = new Movie();
				movie.setMovieId(resultSet.getString("movieid"));
				movie.setMovieName(resultSet.getString("moviename"));
				movie.setDirector(resultSet.getString("director"));
				movie.setActors(resultSet.getString("actors").split(","));
				movie.setGenre(Geners.valueOf(resultSet.getString("genre")));
				movie.setProduction(resultSet.getString("production"));
				movie.setMovieLength(resultSet.getFloat("movielength"));
				movie.setLanguages(resultSet.getString("languages").split(","));
				movies.add(movie);
			}
			Comparator<Movie> comparator = (e1,e2)-> e2.getMovieName().compareTo(e1.getMovieName());
			Collections.sort(movies, comparator);
			return Optional.of(movies);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbUtils.closeConnection(connection);
		}
		return Optional.empty();
	}

	@Override
	public Optional<List<Movie>> getAllMoviesByGenre(String genre) throws InvalidIdException, InvalidNameException, NoDataFoundException{
		
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select * from movies where genre=?";
		ResultSet resultSet = null;
		// connection object
		connection = dbUtils.getConnection();
		
		// statement object(preparedstatement)
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, genre);
			resultSet = preparedStatement.executeQuery();
			List<Movie> movies = new ArrayList<>();
			while(resultSet.next()) {
				// record exist
				// result will be inside the RESULTSET object
				// convert User object from resultSet data and return					
					Movie movie = new Movie();
					movie.setMovieId(resultSet.getString("movieid"));
					movie.setMovieName(resultSet.getString("moviename"));
					movie.setDirector(resultSet.getString("director"));
					movie.setActors(resultSet.getString("actors").split(","));
					movie.setGenre(Geners.valueOf(resultSet.getString("genre")));
					movie.setProduction(resultSet.getString("production"));
					movie.setMovieLength(resultSet.getFloat("movielength"));
					movie.setLanguages(resultSet.getString("languages").split(","));
					movies.add(movie);
				
			}
			if(movies.size() == 0) {
				throw new NoDataFoundException("No movie found with this gener !!!");
			}
			return Optional.of(movies);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbUtils.closeConnection(connection);
		}
		return Optional.empty();
	}

	@Override
	public Optional<List<Movie>> getAllMoviesByName(String movieName)throws InvalidIdException, InvalidNameException, NoDataFoundException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select * from movies where moviename=?";
		ResultSet resultSet = null;
		// connection object
		connection = dbUtils.getConnection();
		
		// statement object(preparedstatement)
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,movieName);
			resultSet = preparedStatement.executeQuery();
			List<Movie> movies = new ArrayList<>();
			while(resultSet.next()) {
				// record exist
				// result will be inside the RESULTSET object
				// convert User object from resultSet data and return					
					Movie movie = new Movie();
					movie.setMovieId(resultSet.getString("movieid"));
					movie.setMovieName(resultSet.getString("moviename"));
					movie.setDirector(resultSet.getString("director"));
					movie.setActors(resultSet.getString("actors").split(","));
					movie.setGenre(Geners.valueOf(resultSet.getString("genre")));
					movie.setProduction(resultSet.getString("production"));
					movie.setMovieLength(resultSet.getFloat("movielength"));
					movie.setLanguages(resultSet.getString("languages").split(","));
					movies.add(movie);
				
			}
			if(movies.size() == 0) {
				throw new NoDataFoundException("No movie found with this name !!!");
			}
			return Optional.of(movies);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbUtils.closeConnection(connection);
		}
		return Optional.empty();
	}

	@Override
	public String deleteMovieByMovieId(String movieId) throws NoDataFoundException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String deleteStatement = "delete from movies where movieid=?";
		connection = dbUtils.getConnection();
		try {
			preparedStatement = connection.prepareStatement(deleteStatement);
			preparedStatement.setString(1, movieId);
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
