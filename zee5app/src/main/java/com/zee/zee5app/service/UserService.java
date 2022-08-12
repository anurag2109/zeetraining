package com.zee.zee5app.service;

import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.User;
import com.zee.zee5app.exceptions.NoDataFoundException;
import com.zee.zee5app.exceptions.UnableToGenerateIdException;

public interface UserService {
	
	public User insertuser(User user) throws UnableToGenerateIdException;
	public String deleteUser(String uid) throws NoDataFoundException;
	public Optional<User> updateUser(String id, User user) throws NoDataFoundException;
	public Optional<List<User>> getAllUsers();
	public Optional<User> getUserByUserId(String userId);
	public Optional<List<User>> findByOrderByUserNameDsc();
	
}
