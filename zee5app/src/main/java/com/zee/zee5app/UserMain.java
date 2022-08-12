package com.zee.zee5app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.zee.zee5app.dto.User;
import com.zee.zee5app.exceptions.NoDataFoundException;
import com.zee.zee5app.exceptions.UnableToGenerateIdException;
import com.zee.zee5app.service.UserService;
import com.zee.zee5app.service.UserServiceImpl;
import com.zee.zee5app.utils.IdComparator;

public class UserMain {

	public static void main(String[] args) {
		UserService userService = UserServiceImpl.getUserServiceInstance();
		try {
			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.println("Enter opration number: ");
				System.out.println("1: insert user");
				System.out.println("2: get user by Id: ");
				System.out.println("3: get all users");
				System.out.println("4: delete user by id");
				System.out.println("5: find all user in decending order by name");
				System.out.println("6: exit from user operation");
				int operation = scanner.nextInt();
				switch (operation) {
				case 1:
					User res_for_insertion = userService.insertuser(new User("an005", "Anurag", "karn",
							LocalDate.of(2022, 07, 18), LocalDate.of(1997, 12, 21), true));
					if (res_for_insertion != null) {
						System.out.println("insertion successfull");
					} else {
						System.out.println("entry not inserted");
					}
					break;
				case 2:
					Optional<User> result = userService.getUserByUserId("Ak1");
					if (result.isPresent()) {
						User user = result.get();
						System.out.println(user);
					} else {
						System.out.println("no record found!!");
					}
					break;
				case 3:
					Optional<List<User>> all_res = userService.getAllUsers();
					System.out.println(all_res.get());
					break;
				case 4:
					try {
						System.out.println(userService.deleteUser("Ak1"));
					} catch (NoDataFoundException e1) {
						e1.printStackTrace();
					}
					break;
				case 5:
					Optional<List<User>> res = userService.findByOrderByUserNameDsc();
					if (!res.isPresent()) {
						System.out.println("No record found for get All users !!!");
					} else {
						List<User> users_in_desc_order = res.get();
						System.out.println(users_in_desc_order);
					}
					break;
				case 6:
					return;

				default:
					break;
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

//--------------method-1--------------------
//			Collections.sort(list);     // using comparable interface

//--------------method-2--------------------
//			Collections.sort(list, new IdComparator());  // using comparator interface

//--------------method-3--------------------
//			Comparator<User> comparator = new Comparator<User>() {
//
//				@Override
//				public int compare(User obj1, User obj2) {
//					return obj1.getUserId().compareTo(obj2.getUserId());
//				}
//			};
//			
//			Collections.sort(list, comparator);

//--------------method-4--------------------
//			Comparator<User> comparator2 = (e1,e2)-> {
//				return e1.getUserId().compareTo(e2.getUserId());
//			};
//			Collections.sort(list, comparator2);

//			list.forEach(e->System.out.println(e));
//----------------------------------------------