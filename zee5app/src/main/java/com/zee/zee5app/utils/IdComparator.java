package com.zee.zee5app.utils;

import java.util.Comparator;

import com.zee.zee5app.dto.User;

public class IdComparator implements Comparator<User> {

	@Override
	public int compare(User user1, User user2) {
		return user1.getUserId().compareTo(user2.getUserId());
	}

}
