package com.zee.zee5app.repo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.Movie;
import com.zee.zee5app.dto.User;
import com.zee.zee5app.enums.Geners;
import com.zee.zee5app.exceptions.NoDataFoundException;
import com.zee.zee5app.exceptions.UnableToGenerateIdException;
import com.zee.zee5app.utils.DBUtils;

public class UserRepositoryImpl implements UserRepo {

	private UserRepositoryImpl() {

	}

	private static UserRepo userRepo;

	public static UserRepo getInstance() {
		if (userRepo == null) {
			userRepo = new UserRepositoryImpl();
		}
		return userRepo;
	}

	private DBUtils dbUtils = DBUtils.getInstance();

	@Override
	public User insertUser(User user) throws UnableToGenerateIdException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String insertStatement = "insert into user_table" + "(userid, firstname, lastname, email, doj, dob, active)"
				+ " values(?,?,?,?,?,?,?)";

		// connection object
		connection = dbUtils.getConnection();

		// statement object(prepared)
		try {
			preparedStatement = connection.prepareStatement(insertStatement);
			preparedStatement.setString(1, dbUtils.userIdGenerator(user.getFirstName(), user.getLastName()));
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setString(4, "abc@gmail.com");
			preparedStatement.setDate(5, Date.valueOf(user.getDoj()));
			preparedStatement.setDate(6, Date.valueOf(user.getDob()));
			preparedStatement.setBoolean(7, user.isActive());
			int result = preparedStatement.executeUpdate();
			if (result > 0) {
				return user;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtils.closeConnection(connection);
		}

		return null;
	}

	@Override
	public Optional<User> updateUser(String userId, User user) throws NoDataFoundException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String deleteStatement = "update user_table set firstname=?, lastname=?, email=?, doj=?, dob=?, active=? where userid=?";
		connection = dbUtils.getConnection();
		try {
			preparedStatement = connection.prepareStatement(deleteStatement);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, "abc@gmail.com");
			preparedStatement.setDate(4, Date.valueOf(user.getDoj()));
			preparedStatement.setDate(5, Date.valueOf(user.getDob()));
			preparedStatement.setBoolean(6, true);
			preparedStatement.setString(7, userId);
			int res = preparedStatement.executeUpdate();
			if (res > 0)
				return Optional.of(user);
			else
				throw new NoDataFoundException("No data found to delete with this user Id !!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtils.closeConnection(connection);
		}
		return Optional.empty();
	}

	@Override
	public String deleteUser(String userId) throws NoDataFoundException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String deleteStatement = "delete from user_table where userid=?";
		connection = dbUtils.getConnection();
		try {
			preparedStatement = connection.prepareStatement(deleteStatement);
			preparedStatement.setString(1, userId);
			int res = preparedStatement.executeUpdate();
			if (res > 0)
				return "success";
			else
				throw new NoDataFoundException("No data found to delete with this user Id !!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtils.closeConnection(connection);
		}
		return null;
	}

	@Override
	public Optional<List<User>> getAllUsers() {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select * from user_table";
		ResultSet resultSet = null;
		// connection object
		connection = dbUtils.getConnection();

		// statement object(preparedstatement)
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			List<User> users = new ArrayList<>();
			while (resultSet.next()) {
				// record exist
				// result will be inside the RESULTSET object
				// convert User object from resultSet data and return
				User user = new User();
				user.setUserId(resultSet.getString("userid"));
				user.setFirstName(resultSet.getString("firstname"));
				user.setLastName(resultSet.getString("lastname"));
				user.setDoj(resultSet.getDate("doj").toLocalDate());
				user.setDob(resultSet.getDate("dob").toLocalDate());
				user.setActive(resultSet.getBoolean("active"));
				users.add(user);
			}
			return Optional.of(users);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtils.closeConnection(connection);
		}
		return Optional.empty();
	}

	@Override
	public Optional<List<User>> findByOrderByUserNameDsc() {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select * from user_table";
		ResultSet resultSet = null;
		// connection object
		connection = dbUtils.getConnection();

		// statement object(preparedstatement)
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			List<User> users = new ArrayList<>();
			while (resultSet.next()) {
				// record exist
				// result will be inside the RESULTSET object
				// convert User object from resultSet data and return
				User user = new User();
				user.setUserId(resultSet.getString("userid"));
				user.setFirstName(resultSet.getString("firstname"));
				user.setLastName(resultSet.getString("lastname"));
				user.setDoj(resultSet.getDate("doj").toLocalDate());
				user.setDob(resultSet.getDate("dob").toLocalDate());
				user.setActive(resultSet.getBoolean("active"));
				users.add(user);
			}
			Comparator<User> comparator = (e1, e2) -> e2.getFirstName().compareTo(e1.getFirstName());
			Collections.sort(users, comparator);
			return Optional.of(users);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtils.closeConnection(connection);
		}
		return Optional.empty();
	}

	@Override
	public Optional<User> getUserByUserId(String userId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select * from user_table where userid=?";
		ResultSet resultSet = null;
		// connection object
		connection = dbUtils.getConnection();

		// statement object(preparedstatement)
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				// record exist
				// result will be inside the RESULTSET object
				// convert User object from resultSet data and return
				User user = new User();
				user.setUserId(resultSet.getString("userid"));
				user.setFirstName(resultSet.getString("firstname"));
				user.setLastName(resultSet.getString("lastname"));
				user.setDoj(resultSet.getDate("doj").toLocalDate());
				user.setDob(resultSet.getDate("dob").toLocalDate());
				user.setActive(resultSet.getBoolean("active"));
				return Optional.of(user);
			} else {
				Optional.empty();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtils.closeConnection(connection);
		}
		return Optional.empty();
	}

}
