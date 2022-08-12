package com.zee.zee5app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import com.zee.zee5app.exceptions.UnableToGenerateIdException;

public class DBUtils {

	private DBUtils() {

	}

	private static DBUtils dbUtils;

	public static DBUtils getInstance() {
		if (dbUtils == null) {
			dbUtils = new DBUtils();
		}
		return dbUtils;
	}
	
	private Properties loadProperties(){
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = DBUtils.class.getClassLoader().getResourceAsStream("application.properties"); // reading the application.properties file
			properties.load(inputStream);
			return properties;
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return properties;
	}
	
	public void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		// to provide the connection
		Properties properties = loadProperties();
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(properties.getProperty("db.url"), 
													 properties.getProperty("db.username"), 
													 properties.getProperty("db.password"));
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String userIdGenerator(String firstname, String lastname) throws UnableToGenerateIdException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select id from useridgenerator";
		String updatequery = "update useridgenerator set id=? where id=?";
		ResultSet resultSet = null;
		int old_id;
		// connection object
		connection = dbUtils.getConnection();
		
		try {
			// get old id from useridgenerator table and increment the id and append it for userid
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next() == true) {
				int num = resultSet.getInt(1);
				old_id = num;
				num++;
				int length_of_id = (int)(Math.log10(num)+1);
				String userid = firstname.charAt(0)+""+lastname.charAt(0)+"0".repeat(10-2-length_of_id)+num;
				
				// update the incremented id to useridgenerator table
				preparedStatement = connection.prepareStatement(updatequery);
				preparedStatement.setInt(1, num);
				preparedStatement.setInt(2, old_id);
				int res = preparedStatement.executeUpdate();
				if(res == 0)
					throw new UnableToGenerateIdException("Unable to generate id");
				
				// return userid
				return userid;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new UnableToGenerateIdException("Unable to generate id due to "+e.getMessage());
		}finally {
			this.closeConnection(connection);
		}
		return null;
	}
	public String movieIdGenerator(String moviename) throws UnableToGenerateIdException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select id from movieidgenerator";
		String updatequery = "update movieidgenerator set id=? where id=?";
		ResultSet resultSet = null;
		int old_id;
		// connection object
		connection = dbUtils.getConnection();
		
		try {
			// get old id from useridgenerator table and increment the id and append it for userid
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next() == true) {
				int num = resultSet.getInt(1);
				old_id = num;
				num++;
				int length_of_id = (int)(Math.log10(num)+1);
				String movieId = moviename.substring(0, 2)+"0".repeat(10-2-length_of_id)+num; // to make the id of length 10
				
				// update the incremented id to useridgenerator table
				preparedStatement = connection.prepareStatement(updatequery);
				preparedStatement.setInt(1, num);
				preparedStatement.setInt(2, old_id);
				int res = preparedStatement.executeUpdate();
				if(res == 0)
					throw new UnableToGenerateIdException("Unable to generate id");
				
				// return userid
				return movieId;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new UnableToGenerateIdException("Unable to generate id due to "+e.getMessage());
		}finally {
			this.closeConnection(connection);
		}
		return null;
	}
	public String webSeriesIdGenerator(String webSeries) throws UnableToGenerateIdException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select id from webseriesidgenerator";
		String updatequery = "update webseriesidgenerator set id=? where id=?";
		ResultSet resultSet = null;
		int old_id;
		// connection object
		connection = dbUtils.getConnection();
		
		try {
			// get old id from useridgenerator table and increment the id and append it for userid
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next() == true) {
				int num = resultSet.getInt(1);
				old_id = num;
				num++;
				int length_of_id = (int)(Math.log10(num)+1);
				String webseriesId = webSeries.substring(0, 2)+"0".repeat(10-2-length_of_id)+num; // to make the id of length 10
				
				// update the incremented id to useridgenerator table
				preparedStatement = connection.prepareStatement(updatequery);
				preparedStatement.setInt(1, num);
				preparedStatement.setInt(2, old_id);
				int res = preparedStatement.executeUpdate();
				if(res == 0)
					throw new UnableToGenerateIdException("Unable to generate id");
				
				// return userid
				return webseriesId;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new UnableToGenerateIdException("Unable to generate id due to "+e.getMessage());
		}finally {
			this.closeConnection(connection);
		}
		return null;
	}


	
}
