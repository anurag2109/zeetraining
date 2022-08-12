package com.zee.zee5app.service;

import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.User;
import com.zee.zee5app.exceptions.NoDataFoundException;
import com.zee.zee5app.exceptions.UnableToGenerateIdException;
import com.zee.zee5app.repo.UserRepo;
import com.zee.zee5app.repo.UserRepositoryImpl;

public class UserServiceImpl implements UserService {
	
	
	// -------- creating singleton object ------------------
	// steps:
	// 1. create private constructor
	// 2. create private static reference of Interface pointing to "null"
	// 3. create public static getInstance function 
	
	private UserServiceImpl() {
		
	}
	private static UserService service = null;
	public static UserService getUserServiceInstance() {
		if(service == null) {
			service = new UserServiceImpl();
		}
		return service;
	}
	// ------------------------------------------------------
	
	private UserRepo repo = UserRepositoryImpl.getInstance();
	
	// insertion user
	@Override
	public User insertuser(User user) throws UnableToGenerateIdException {
		return repo.insertUser(user);
	}
	
	// delete user
	@Override
	public String deleteUser(String uid) throws NoDataFoundException {
		return repo.deleteUser(uid);
	}
	
	// update user
	@Override
	public Optional<User> updateUser(String id, User user) throws NoDataFoundException {
		return repo.updateUser(id, user);
	}
	
	// get all users
	@Override
	public Optional<List<User>> getAllUsers() {
		return repo.getAllUsers();
	}
	
	// get specific user
	@Override
	public Optional<User> getUserByUserId(String userId) {
		return repo.getUserByUserId(userId);
	}

	@Override
	public Optional<List<User>> findByOrderByUserNameDsc() {
		// TODO Auto-generated method stub
		return repo.findByOrderByUserNameDsc();
	}
	
}
